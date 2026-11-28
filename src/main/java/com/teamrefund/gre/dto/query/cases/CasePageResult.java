package com.teamrefund.gre.dto.query.cases;

import com.teamrefund.gre.entity.cases.Case;
import java.util.List;
import lombok.Value;

@Value
public class CasePageResult {
  List<Case> cases;
  long totalElements;
}
