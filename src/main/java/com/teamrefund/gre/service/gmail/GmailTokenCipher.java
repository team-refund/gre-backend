package com.teamrefund.gre.service.gmail;

import com.teamrefund.gre.exception.BusinessException;
import com.teamrefund.gre.exception.ErrorCode;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GmailTokenCipher {
  private static final byte FORMAT_VERSION = 1;
  private static final int KEY_BYTES = 32;
  private static final int NONCE_BYTES = 12;
  private static final int TAG_BITS = 128;

  private final String encodedKey;
  private final SecureRandom random = new SecureRandom();

  public GmailTokenCipher(@Value("${gre.gmail.token-encryption-key}") String encodedKey) {
    this.encodedKey = encodedKey;
  }

  byte[] encrypt(String token, String context) {
    try {
      var key = Base64.getDecoder().decode(encodedKey);
      if (key.length != KEY_BYTES) {
        throw new BusinessException(ErrorCode.GMAIL_OAUTH_UNAVAILABLE);
      }
      var nonce = new byte[NONCE_BYTES];
      random.nextBytes(nonce);
      var cipher = Cipher.getInstance("AES/GCM/NoPadding");
      cipher.init(
          Cipher.ENCRYPT_MODE,
          new SecretKeySpec(key, "AES"),
          new GCMParameterSpec(TAG_BITS, nonce));
      cipher.updateAAD(context.getBytes(StandardCharsets.UTF_8));
      var encrypted = cipher.doFinal(token.getBytes(StandardCharsets.UTF_8));
      // Persist the format version, nonce, and authenticated ciphertext in that order.
      return ByteBuffer.allocate(Byte.BYTES + nonce.length + encrypted.length)
          .put(FORMAT_VERSION)
          .put(nonce)
          .put(encrypted)
          .array();
    } catch (IllegalArgumentException | GeneralSecurityException exception) {
      throw new BusinessException(ErrorCode.GMAIL_OAUTH_UNAVAILABLE);
    }
  }

  String decrypt(byte[] storedToken, String context) {
    if (storedToken == null
        || storedToken.length <= Byte.BYTES + NONCE_BYTES + TAG_BITS / Byte.SIZE
        || storedToken[0] != FORMAT_VERSION) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
    try {
      var key = Base64.getDecoder().decode(encodedKey);
      if (key.length != KEY_BYTES) {
        throw new BusinessException(ErrorCode.GMAIL_OAUTH_UNAVAILABLE);
      }
      var nonce = new byte[NONCE_BYTES];
      var payload = ByteBuffer.wrap(storedToken);
      payload.get();
      payload.get(nonce);
      var cipher = Cipher.getInstance("AES/GCM/NoPadding");
      cipher.init(
          Cipher.DECRYPT_MODE,
          new SecretKeySpec(key, "AES"),
          new GCMParameterSpec(TAG_BITS, nonce));
      cipher.updateAAD(context.getBytes(StandardCharsets.UTF_8));
      return new String(
          cipher.doFinal(storedToken, payload.position(), payload.remaining()),
          StandardCharsets.UTF_8);
    } catch (IllegalArgumentException exception) {
      throw new BusinessException(ErrorCode.GMAIL_OAUTH_UNAVAILABLE);
    } catch (GeneralSecurityException exception) {
      throw new BusinessException(ErrorCode.GMAIL_REAUTH_REQUIRED);
    }
  }
}
