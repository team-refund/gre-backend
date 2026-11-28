package com.teamrefund.gre.dto.query.email;

import java.util.List;
import lombok.Value;

@Value
public class EmailDraftDetailResult {
  EmailDraftRow draft;
  List<Long> attachmentEvidenceIds;
}
