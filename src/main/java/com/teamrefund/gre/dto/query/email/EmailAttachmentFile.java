package com.teamrefund.gre.dto.query.email;

import lombok.Data;

@Data
public class EmailAttachmentFile {
  private Long evidenceId;
  private String originalFilename;
  private String filePath;
  private String contentType;
}
