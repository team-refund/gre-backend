package com.teamrefund.gre.entity.cases;

import java.util.Date;
import lombok.Data;

// 사건에 첨부한 증빙 파일의 저장 정보와 분석 상태를 저장합니다. DB 테이블은 {@code case_evidences}
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
  private Date createdAt;
  private Date updatedAt;
}
