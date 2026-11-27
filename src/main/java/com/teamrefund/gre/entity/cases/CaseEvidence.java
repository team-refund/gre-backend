package com.teamrefund.gre.entity.cases;

import java.time.Instant;
import lombok.Data;

@Data
public class CaseEvidence {
  private Long id;
  private Long caseId;
  private String evidenceType;
  private String description;
  private String originalFilename;
  private String storedFilename;
  private String filePath;
  private Long fileSize;
  private String contentType;
  private String caseEvidencesAnalysisStatus;
  private Instant createdAt;
  private Instant updatedAt;
}
