package com.teamrefund.gre.dto.query.cases;

import com.teamrefund.gre.dto.query.email.EmailDraftSummary;
import com.teamrefund.gre.entity.cases.Case;
import com.teamrefund.gre.entity.cases.CaseEvidence;
import com.teamrefund.gre.entity.orchestrator.OrchestratorRun;
import java.util.List;
import lombok.Value;

@Value
public class CaseDetailResult {
  Case refundCase;
  String requirement;
  OrchestratorRun latestRun;
  List<EmailDraftSummary> emailDrafts;
  List<CaseEvidence> evidences;
}
