package com.teamrefund.gre.dto.request.cases;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Data
public class CaseRegistrationRequest {
  @NotNull private CaseType caseType;

  @NotBlank
  @Size(max = 255)
  private String merchantName;

  @NotBlank
  @Email
  @Size(max = 255)
  private String merchantEmail;

  @NotBlank
  @Size(max = 500)
  private String purchaseSite;

  @NotBlank
  @Size(max = 100)
  private String orderNumber;

  @NotNull private LocalDate purchaseDate;

  @NotNull
  @DecimalMin("1")
  @Digits(integer = 13, fraction = 2)
  private BigDecimal paymentAmount;

  @NotBlank
  @Size(max = 10000)
  private String requirement;

  public enum CaseType {
    REFUND_DELAY,
    REFUND_NOT_PROCESSED,
    DUPLICATE_CHARGE,
    PAYMENT_CORRECTION
  }
}
