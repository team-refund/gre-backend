package com.teamrefund.gre.dto.response.email;

import com.teamrefund.gre.entity.email.EmailDraftStatus;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DraftApprovalResponse {
  private final long draftId;
  private final EmailDraftStatus status;
  private final Instant approvedAt;
}
