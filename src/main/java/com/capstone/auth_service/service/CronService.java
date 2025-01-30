package com.capstone.auth_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import com.capstone.auth_service.pojo.DateTimePojo;
import com.capstone.auth_service.pojo.CronJobPojos.CommunityWithRulesPojo;

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

    public void UpdateStatusToActive() {
        RestClient restClient = RestClient.create();

        DateTimePojo dateTimePojo = restClient.put().uri("http://localhost:5010/api/time")
                .body(new DateTimePojo(0, LocalDateTime.now())).retrieve().body(DateTimePojo.class);
        @SuppressWarnings("null")
        LocalDate currentDate = dateTimePojo.getDateTime().toLocalDate();

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

        for (CommunityWithRulesPojo toSetToActiveCommunity : toSetToActiveCommunities) {
            // communities/activate/{communityId}
            restClient.put()
                    .uri("http://localhost:5002/api/communities/activate/" + toSetToActiveCommunity.getCommunityId())
                    .retrieve()
                    .body(Void.class);
        }

    }

}
