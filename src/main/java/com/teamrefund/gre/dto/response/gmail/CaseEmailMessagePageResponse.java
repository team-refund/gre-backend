package com.teamrefund.gre.dto.response.gmail;

import java.util.List;
import lombok.Value;

@Value
public class CaseEmailMessagePageResponse {
  List<CaseEmailMessageResponse> items;
  int page;
  int size;
  long total;
}
