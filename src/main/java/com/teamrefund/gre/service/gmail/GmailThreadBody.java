package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import jakarta.mail.internet.ContentType;
import jakarta.mail.internet.ParseException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.jsoup.Jsoup;
import tools.jackson.databind.JsonNode;

final class GmailThreadBody {
  private GmailThreadBody() {}

  static String readText(JsonNode payload) {
    var text = findBody(payload, "text/plain");
    if (text != null) {
      return text.replace("\u0000", "");
    }
    var html = findBody(payload, "text/html");
    return html == null ? null : Jsoup.parse(html).text().replace("\u0000", "");
  }

  private static String findBody(JsonNode part, String mimeType) {
    var filename = part.path("filename").asString("");
    if (!filename.isBlank()) {
      return null;
    }
    if (mimeType.equalsIgnoreCase(part.path("mimeType").asString(null))) {
      var data = part.path("body").path("data").asString(null);
      if (data != null && !data.isEmpty()) {
        try {
          return new String(Base64.getUrlDecoder().decode(data), charset(part));
        } catch (IllegalArgumentException exception) {
          throw new BusinessException(ErrorCode.GMAIL_SEARCH_FAILED);
        }
      }
    }
    for (var child : part.path("parts")) {
      var text = findBody(child, mimeType);
      if (text != null) {
        return text;
      }
    }
    return null;
  }

  private static Charset charset(JsonNode part) {
    var header = GmailThreadService.header(part, "Content-Type");
    if (header != null) {
      try {
        var charset = new ContentType(header).getParameter("charset");
        if (charset != null) {
          return Charset.forName(charset);
        }
      } catch (IllegalArgumentException | ParseException ignored) {
        return StandardCharsets.UTF_8;
      }
    }
    return StandardCharsets.UTF_8;
  }
}
