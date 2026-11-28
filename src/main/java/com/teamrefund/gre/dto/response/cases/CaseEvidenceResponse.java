package com.teamrefund.gre.dto.response.cases;

import com.teamrefund.gre.entity.cases.CaseEvidence;
import java.time.Instant;
import lombok.Value;

@Value
public class CaseEvidenceResponse {
  long id;
  long caseId;
  String evidenceType;
  String description;
  String originalFilename;
  Long fileSize;
  String contentType;
  String analysisStatus;
  Instant createdAt;
  Instant updatedAt;

  public CaseEvidenceResponse(CaseEvidence evidence) {
    id = evidence.getId();
    caseId = evidence.getCaseId();
    evidenceType = evidence.getEvidenceType();
    description = evidence.getDescription();
    originalFilename = evidence.getOriginalFilename();
    fileSize = evidence.getFileSize();
    contentType = evidence.getContentType();
    analysisStatus = evidence.getCaseEvidencesAnalysisStatus();
    createdAt = evidence.getCreatedAt();
    updatedAt = evidence.getUpdatedAt();
  }
}
