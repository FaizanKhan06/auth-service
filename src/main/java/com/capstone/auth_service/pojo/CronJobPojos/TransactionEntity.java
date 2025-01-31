package com.capstone.auth_service.pojo.CronJobPojos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionEntity {

    private int transactionId;

    private String email;

    private int communityId;

    private String transactionType;

    private double amount;

    private double interestAmount;

    private LocalDateTime transactionDateTime;

}
