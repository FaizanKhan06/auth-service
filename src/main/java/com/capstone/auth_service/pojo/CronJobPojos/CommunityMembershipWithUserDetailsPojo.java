package com.capstone.auth_service.pojo.CronJobPojos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityMembershipWithUserDetailsPojo {
    private int communityMembershipId;
    private CommunityPojo community;
    private UserOutputDataPojo user;
    private double amount;
    private boolean isAccepted;
    private boolean isLoanTaken;
    private boolean isLoanDefaulter;
}