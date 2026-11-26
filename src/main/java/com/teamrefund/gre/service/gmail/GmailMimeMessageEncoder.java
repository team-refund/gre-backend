package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.dto.query.email.EmailDraftRow;
import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class GmailMimeMessageEncoder {
  private static final String UTF_8 = StandardCharsets.UTF_8.name();
  private static final long MAX_ATTACHMENT_BYTES = 18 * 1024 * 1024;

  private final Path evidenceDirectory;

  GmailMimeMessageEncoder(@Value("${gre.evidence.directory:uploads}") String evidenceDirectory) {
    this.evidenceDirectory = Path.of(evidenceDirectory).toAbsolutePath().normalize();
  }

  /** 첨부파일 없이 Gmail 초안용 MIME 메시지를 인코딩합니다. */
  String encode(String to, String subject, String body) {
    return encode(to, subject, body, null, null, List.of());
  }

  /** 발신 정보와 첨부파일을 포함한 MIME 메시지를 Gmail의 base64url 형식으로 변환합니다. */
  String encode(
      String to,
      String subject,
      String body,
      String from,
      String messageId,
      List<EmailDraftRow.Attachment> attachments) {
    try {
      var message = new MimeMessage(Session.getInstance(new Properties()));
      message.setRecipient(Message.RecipientType.TO, parseAddress(to));
      if (from != null) {
        message.setFrom(parseAddress(from));
      }
      message.setSubject(subject, UTF_8);
      if (attachments.isEmpty()) {
        message.setText(body, UTF_8);
      } else {
        message.setContent(createMultipart(body, attachments));
      }
      message.saveChanges();
      if (messageId != null) {
        message.setHeader("Message-ID", messageId);
      }
      var output = new ByteArrayOutputStream();
      message.writeTo(output);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(output.toByteArray());
    } catch (MessagingException | IOException | IllegalArgumentException exception) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
  }

  /** 표시명이나 그룹 없이 단일 이메일 주소가 전달됐는지 검증합니다. */
  private InternetAddress parseAddress(String address) throws MessagingException {
    var parsed = new InternetAddress(address, true);
    parsed.validate();
    if (parsed.isGroup() || !address.equals(parsed.getAddress())) {
      throw new BusinessException(ErrorCode.INVALID_REQUEST);
    }
    return parsed;
  }

  /** 텍스트 본문과 저장소 범위 및 크기를 검증한 첨부파일을 하나의 메시지로 구성합니다. */
  private MimeMultipart createMultipart(String body, List<EmailDraftRow.Attachment> attachments)
      throws IOException, MessagingException {
    var multipart = new MimeMultipart();
    var text = new MimeBodyPart();
    text.setText(body, UTF_8);
    multipart.addBodyPart(text);
    var root = evidenceDirectory.toRealPath();
    long totalBytes = 0;
    for (var attachment : attachments) {
      var storedPath = Path.of(attachment.getFilePath());
      if (!storedPath.isAbsolute()) {
        var workingPath = storedPath.toAbsolutePath().normalize();
        storedPath =
            workingPath.startsWith(evidenceDirectory)
                ? workingPath
                : evidenceDirectory.resolve(storedPath);
      }
      var path = storedPath.toRealPath();
      if (!path.startsWith(root) || !Files.isRegularFile(path)) {
        throw new BusinessException(ErrorCode.INVALID_REQUEST);
      }
      totalBytes += Files.size(path);
      if (totalBytes > MAX_ATTACHMENT_BYTES) {
        throw new BusinessException(ErrorCode.INVALID_REQUEST);
      }
      var part = new MimeBodyPart();
      part.attachFile(path.toFile());
      part.setFileName(attachment.getOriginalFilename());
      multipart.addBodyPart(part);
    }
    return multipart;
  }
}
