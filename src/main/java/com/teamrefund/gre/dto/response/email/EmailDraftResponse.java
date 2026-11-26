package com.teamrefund.gre.dto.response.email;

import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.entity.email.EmailDraftStatus;
import java.time.Instant;
import java.util.List;
import lombok.Getter;

@Getter
public class EmailDraftResponse {
  private final long draftId;
  private final long caseId;
  private final String purpose;
  private final String to;
  private final String subject;
  private final String body;
  private final List<Long> attachmentEvidenceIds;
  private final EmailDraftStatus status;
  private final Instant updatedAt;

  public EmailDraftResponse(EmailDraftRow draft, List<Long> attachmentEvidenceIds) {
    this.draftId = draft.getId();
    this.caseId = draft.getCaseId();
    this.purpose = draft.getPurpose();
    this.to = draft.getRecipientEmail();
    this.subject = draft.getSubject();
    this.body = draft.getBody();
    this.attachmentEvidenceIds = attachmentEvidenceIds;
    this.status = draft.getStatus();
    this.updatedAt = draft.getUpdatedAt();
  }
}
