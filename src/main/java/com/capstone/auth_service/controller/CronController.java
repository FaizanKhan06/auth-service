package com.capstone.auth_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.auth_service.service.CronService;

@RestController
@RequestMapping("/api")
public class CronController {

    @Autowired
    CronService cronService;

    @PutMapping("/auth/cronJob")
    public ResponseEntity<Void> cronJob() {
        cronService.UpdateCommunityStatusToActive();
        cronService.updateAllCommunitiesNextContributionDate();
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
