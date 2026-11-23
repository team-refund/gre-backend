package com.teamrefund.gre.service.gmail;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.util.Base64;
import org.junit.jupiter.api.Test;

class GmailTokenCipherTest {
  private final GmailTokenCipher cipher =
      new GmailTokenCipher(Base64.getEncoder().encodeToString(new byte[32]));

  @Test
  void recoversTokenWithItsOriginalContext() {
    var encrypted = cipher.encrypt("test-token", "gre-user:1:access");

    assertThat(cipher.decrypt(encrypted, "gre-user:1:access")).isEqualTo("test-token");
    assertThat(cipher.encrypt("test-token", "gre-user:1:access")).isNotEqualTo(encrypted);
  }

  @Test
  void rejectsTokenFromAnotherUserContext() {
    var encrypted = cipher.encrypt("test-token", "gre-user:1:access");

    assertThatThrownBy(() -> cipher.decrypt(encrypted, "gre-user:2:access"))
        .isInstanceOfSatisfying(
            BusinessException.class,
            exception ->
                assertThat(exception.getErrorCode()).isEqualTo(ErrorCode.GMAIL_REAUTH_REQUIRED));
  }
}
