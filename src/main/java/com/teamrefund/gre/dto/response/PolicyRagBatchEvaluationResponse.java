package com.teamrefund.gre.dto.response;

public record PolicyRagBatchEvaluationResponse(
    int total,
    int relevancyPassed,
    int factCheckingPassed,
    int bothPassed,
    double relevancyPassRate,
    double factCheckingPassRate,
    double bothPassRate) {}
