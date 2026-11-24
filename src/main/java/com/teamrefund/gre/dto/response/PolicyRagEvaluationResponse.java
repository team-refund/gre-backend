package com.teamrefund.gre.dto.response;

public record PolicyRagEvaluationResponse(
    PolicyRagResult result,
    int retrievedDocumentCount,
    boolean relevancyPassed,
    boolean factCheckingPassed) {}
