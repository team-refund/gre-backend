package com.teamrefund.gre.dto.query.gmail;

import java.util.List;
import lombok.Value;

@Value
public class CaseEmailMessagePageResult {
  List<CaseEmailMessageResult> items;
  long total;
}
