package com.capstone.auth_service.pojo.CronJobPojos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RulesPojo {
    private int ruleId;
    private String termsAndConditions;
    private double contributionPerMonth;
    private int termPeriod;
    private double interestRate;
    private LocalDateTime contributionDeadline;
    private LocalDateTime communityStartDate;
}
