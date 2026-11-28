package com.teamrefund.gre.service.email;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.teamrefund.gre.dao.email.EmailDraftDao;
import com.teamrefund.gre.dao.gmail.CaseEmailDao;
import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.dto.query.email.EmailSendAttempt;
import com.teamrefund.gre.dto.request.email.EmailDraftContentRequest;
import com.teamrefund.gre.entity.email.EmailDraftStatus;
import com.teamrefund.gre.entity.email.EmailSendStatus;
import com.teamrefund.gre.entity.gmail.GmailConnection;
import com.teamrefund.gre.entity.gmail.GmailConnectionStatus;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import com.teamrefund.gre.service.email.EmailDraftTransactionService.SendClaim;
import com.teamrefund.gre.service.gmail.GmailConnectionService;
import com.teamrefund.gre.service.gmail.GmailSendService;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

class EmailDraftWorkflowTest {
  private static final long DRAFT_ID = 7;
  private static final String LOGIN_ID = "buyer";

  private final EmailDraftDao dao = mock(EmailDraftDao.class);
  private final GmailConnectionService connections = mock(GmailConnectionService.class);
  private final EmailDraftTransactionService transactions =
      new EmailDraftTransactionService(dao, mock(CaseEmailDao.class), connections);

  @Test
  void approvalSavesEditsWithoutCallingGmail() {
    var draft = draft();
    var request = new EmailDraftContentRequest();
    request.setTo("seller@example.test");
    request.setSubject("Updated");
    request.setBody("Updated body");
    var savedAt = Instant.parse("2026-11-26T00:00:00Z");
    when(dao.lockOwnedDraft(DRAFT_ID, LOGIN_ID)).thenReturn(draft);
    when(dao.updateDraft(DRAFT_ID, request.getTo(), request.getSubject(), request.getBody()))
        .thenReturn(savedAt);

    assertThat(transactions.approveDraft(LOGIN_ID, DRAFT_ID, request)).isEqualTo(savedAt);

    verify(dao, never()).updateDraftStatus(anyLong(), any(EmailDraftStatus.class));
    verifyNoInteractions(connections);
  }

  @Test
  void sendClaimsAnApprovedDraft() {
    var draft = draft();
    draft.setStatus(EmailDraftStatus.FINALIZED);
    var connection = new GmailConnection();
    connection.setId(3L);
    connection.setStatus(GmailConnectionStatus.CONNECTED);
    connection.setGoogleEmail("buyer@example.test");
    var attempt = attempt(EmailSendStatus.SENDING);
    when(dao.lockOwnedDraft(DRAFT_ID, LOGIN_ID)).thenReturn(draft);
    when(dao.findAttempt(DRAFT_ID)).thenReturn(null, attempt);
    when(connections.getConnection(LOGIN_ID)).thenReturn(connection);
    when(dao.insertSendAttempt(eq(DRAFT_ID), eq(3L), eq("click-123"), anyString())).thenReturn(1);

    var claim = transactions.claimSend(LOGIN_ID, DRAFT_ID, "click-123");

    assertThat(claim.attempt()).isSameAs(attempt);
    verify(dao).insertSendAttempt(eq(DRAFT_ID), eq(3L), eq("click-123"), anyString());
  }

  @Test
  void sendRejectsAnUnapprovedDraftBeforeCallingGmail() {
    when(dao.lockOwnedDraft(DRAFT_ID, LOGIN_ID)).thenReturn(draft());
    var gmail = mock(GmailSendService.class);

    assertThatThrownBy(
            () -> new EmailDraftSendService(transactions, gmail).send(LOGIN_ID, DRAFT_ID, "key"))
        .isInstanceOfSatisfying(
            BusinessException.class,
            error -> assertThat(error.getErrorCode()).isEqualTo(ErrorCode.INVALID_CASE_STATE));

    verifyNoInteractions(gmail, connections);
  }

  @Test
  void repeatedRequestsReturnTheExistingAttemptWithoutCallingGmail() {
    var existing = attempt(EmailSendStatus.UNKNOWN);
    when(dao.lockOwnedDraft(DRAFT_ID, LOGIN_ID)).thenReturn(draft());
    when(dao.findAttempt(DRAFT_ID)).thenReturn(existing);
    var gmail = mock(GmailSendService.class);

    assertThat(new EmailDraftSendService(transactions, gmail).send(LOGIN_ID, DRAFT_ID, "new-key"))
        .isSameAs(existing);

    verifyNoInteractions(gmail, connections);
  }

  @Test
  void uncertainGmailResponseIsPersistedAsUnknown() {
    var tx = mock(EmailDraftTransactionService.class);
    var gmail = mock(GmailSendService.class);
    var sending = attempt(EmailSendStatus.SENDING);
    sending.setMessageIdHeader("<gre@example.test>");
    var claim = new SendClaim(sending, draft(), new GmailConnection(), List.of());
    var unknown = attempt(EmailSendStatus.UNKNOWN);
    when(tx.claimSend(LOGIN_ID, DRAFT_ID, null)).thenReturn(claim);
    when(gmail.send(
            LOGIN_ID,
            claim.connection(),
            claim.draft(),
            claim.attachments(),
            sending.getMessageIdHeader()))
        .thenThrow(new BusinessException(ErrorCode.EMAIL_SEND_UNCONFIRMED));
    when(tx.recordSendFailure(claim, ErrorCode.EMAIL_SEND_UNCONFIRMED)).thenReturn(unknown);

    assertThat(new EmailDraftSendService(tx, gmail).send(LOGIN_ID, DRAFT_ID, null))
        .isSameAs(unknown);
  }

  private EmailDraftRow draft() {
    var draft = new EmailDraftRow();
    draft.setId(DRAFT_ID);
    draft.setCaseId(10);
    draft.setStatus(EmailDraftStatus.DRAFT);
    draft.setRecipientEmail("seller@example.test");
    draft.setSubject("Refund request");
    draft.setBody("Please refund this order.");
    return draft;
  }

  private EmailSendAttempt attempt(EmailSendStatus status) {
    var attempt = new EmailSendAttempt();
    attempt.setDraftId(DRAFT_ID);
    attempt.setStatus(status);
    return attempt;
  }
}
