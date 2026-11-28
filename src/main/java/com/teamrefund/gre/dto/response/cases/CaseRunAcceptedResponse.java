package com.teamrefund.gre.dto.response.cases;

import lombok.Value;

@Value
public class CaseRunAcceptedResponse {
  long runId;
  String status;
}
