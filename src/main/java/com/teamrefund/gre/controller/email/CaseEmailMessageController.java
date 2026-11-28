package com.teamrefund.gre.controller.email;

import com.teamrefund.gre.dto.response.gmail.CaseEmailMessagePageResponse;
import com.teamrefund.gre.dto.response.gmail.CaseEmailMessageResponse;
import com.teamrefund.gre.service.email.CaseEmailMessageQueryService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cases/{caseId}/email-messages")
@RequiredArgsConstructor
public class CaseEmailMessageController {
  private final CaseEmailMessageQueryService caseEmailMessageQueryService;

  @GetMapping
  public CaseEmailMessagePageResponse listMessages(
      Principal principal,
      @PathVariable("caseId") long caseId,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "20") int size) {
    var result = caseEmailMessageQueryService.listMessages(principal.getName(), caseId, page, size);
    var items = result.getItems().stream().map(CaseEmailMessageResponse::new).toList();
    return new CaseEmailMessagePageResponse(items, page, size, result.getTotal());
  }

  @GetMapping("/{messageId}")
  public CaseEmailMessageResponse getMessage(
      Principal principal,
      @PathVariable("caseId") long caseId,
      @PathVariable("messageId") long messageId) {
    return new CaseEmailMessageResponse(
        caseEmailMessageQueryService.getMessage(principal.getName(), caseId, messageId));
  }
}
