package com.capstone.auth_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.capstone.auth_service.pojo.DateTimePojo;
import com.capstone.auth_service.pojo.CronJobPojos.CommunityMembershipWithUserDetailsPojo;
import com.capstone.auth_service.pojo.CronJobPojos.CommunityWithRulesPojo;
import com.capstone.auth_service.pojo.CronJobPojos.TransactionEntity;

@Service
public class CronService {
    private CommunityWithRulesPojo updateActiveStatus(CommunityWithRulesPojo community, LocalDate currentDate,
            boolean status) {
        LocalDateTime communityStartDateTime = community.getRule().getCommunityStartDate();
        LocalDate communityStartDate = communityStartDateTime.toLocalDate();
        if (communityStartDate.isEqual(currentDate) || communityStartDate.isBefore(currentDate)) {
            community.setActive(status);
            return community;
        } else {
            return null;
        }
    }

    @SuppressWarnings("null")
    private LocalDateTime getCurrentDate(){
        RestClient restClient = RestClient.create();
        DateTimePojo dateTimePojo = restClient.put().uri("http://localhost:5010/api/time")
                .body(new DateTimePojo(0, LocalDateTime.now())).retrieve().body(DateTimePojo.class);

        return dateTimePojo.getDateTime();
    }

    public void UpdateCommunityStatusToActive() {
        RestClient restClient = RestClient.create();

        // DateTimePojo dateTimePojo = restClient.put().uri("http://localhost:5010/api/time")
        //         .body(new DateTimePojo(0, LocalDateTime.now())).retrieve().body(DateTimePojo.class);
        // @SuppressWarnings("null")
        LocalDate currentDate = getCurrentDate().toLocalDate();

        List<CommunityWithRulesPojo> allCommunities = restClient.get()
                .uri("http://localhost:5002/api/communities/notActive")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommunityWithRulesPojo>>() {
                });
        
        List<CommunityWithRulesPojo> toSetToActiveCommunities = new ArrayList<>();
        if (allCommunities != null) {
            for (CommunityWithRulesPojo community : allCommunities) {
                CommunityWithRulesPojo comm = updateActiveStatus(community, currentDate, true);
                toSetToActiveCommunities.add(comm);
            }
        }

        // System.out.println("\n\n\n\n"+allCommunities+"\n\n\n\n"+toSetToActiveCommunities+"\n\n\n\n");

        if(toSetToActiveCommunities.size() <= 0 || toSetToActiveCommunities.get(0) == null){
            return;
        }

        for (CommunityWithRulesPojo toSetToActiveCommunity : toSetToActiveCommunities) {
            // communities/activate/{communityId}
            restClient.put()
                    .uri("http://localhost:5002/api/communities/activate/" + toSetToActiveCommunity.getCommunityId())
                    .retrieve()
                    .body(Void.class);
        }

    }

    public void updateAllCommunitiesNextContributionDate(){
        // http://localhost:5002/api/communities/active
        RestClient restClient = RestClient.create();
        List<CommunityWithRulesPojo> allCommunities = restClient.get()
                .uri("http://localhost:5002/api/communities/active")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommunityWithRulesPojo>>() {
                });
       
        if(allCommunities == null || allCommunities.size() <= 0 || allCommunities.get(0) == null){
            return;
        }
        // System.out.println("\n\n\n\n"+allCommunities+"\n\n\n\n");
        
        for (CommunityWithRulesPojo communityWithRulesPojo : allCommunities) {
            long daysBetween = ChronoUnit.DAYS.between(communityWithRulesPojo.getRule().getCommunityStartDate(), communityWithRulesPojo.getRule().getContributionDeadline());
            
            LocalDateTime newContributionDate = communityWithRulesPojo.getNextContributionDate().plusDays(daysBetween + 1);
            LocalDate currentDate = getCurrentDate().toLocalDate();
            if(currentDate.equals(newContributionDate.toLocalDate())){
                communityWithRulesPojo.setNextContributionDate(communityWithRulesPojo.getNextContributionDate().plusMonths(1));
                communityWithRulesPojo.setRemainingTermPeriod(communityWithRulesPojo.getRemainingTermPeriod() - 1);
                List<TransactionEntity> allTransactions = restClient.get().uri("localhost:5006/api/transactions/communities/dateRange?communityId="+communityWithRulesPojo.getCommunityId()+"&startDate="+communityWithRulesPojo.getNextContributionDate()+"&endDate="+communityWithRulesPojo.getNextContributionDate().plusMonths(1)).retrieve().body(new ParameterizedTypeReference<List<TransactionEntity>>() {});
                List<CommunityMembershipWithUserDetailsPojo> allMemberships = restClient.get().uri("localhost:5005/api/CommunityMembership/community/accepted/"+communityWithRulesPojo.getCommunityId()).retrieve().body(new ParameterizedTypeReference<List<CommunityMembershipWithUserDetailsPojo>>() {});

                System.out.println("\n\n\n\n"+allTransactions+"\n"+allMemberships+"\n\n\n\n");
            }  
        }
        
    }

}
