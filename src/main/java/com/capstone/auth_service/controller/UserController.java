package com.capstone.auth_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.auth_service.entity.UserEntity;
import com.capstone.auth_service.pojo.UserInputDataPojo;
import com.capstone.auth_service.pojo.UserOutputDataPojo;
import com.capstone.auth_service.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/users")
    public ResponseEntity<List<UserOutputDataPojo>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/active")
    public ResponseEntity<List<UserOutputDataPojo>> getAllActiveUsers() {
        return new ResponseEntity<>(userService.getAllActiveUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/deleted")
    public ResponseEntity<List<UserOutputDataPojo>> getAllDeletedUsers() {
        return new ResponseEntity<>(userService.getAllDeletedUsers(), HttpStatus.OK);
    }

    @GetMapping("/users/id/{userId}")
    public ResponseEntity<UserOutputDataPojo> getUserById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/users/email/{email}")
    public ResponseEntity<UserOutputDataPojo> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/users/phoneNo/{phoneNo}")
    public ResponseEntity<UserOutputDataPojo> getUserByPhoneNo(@PathVariable String phoneNo) {
        return new ResponseEntity<>(userService.getUserByPhoneNo(phoneNo), HttpStatus.OK);
    }

    @GetMapping("/users/adhaarNo/{adhaarNo}")
    public ResponseEntity<UserOutputDataPojo> getUserByAdhaarNo(@PathVariable String adhaarNo) {
        return new ResponseEntity<>(userService.getUserByAdhaarNo(adhaarNo), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> pseudoDeleteUser(@PathVariable Long userId) {
        userService.pseudoDeleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/users/permenentDelete/{userId}")
    public ResponseEntity<Void> actualDeleteUser(@PathVariable Long userId) {
        userService.actualDeleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/auth")
    public ResponseEntity<UserOutputDataPojo> registerNewUser(@RequestBody UserInputDataPojo newUser) {
        return new ResponseEntity<>(userService.registerNewUser(newUser), HttpStatus.OK);
    }

    @GetMapping("/auth/validate/token")
    public String validateToken(@RequestParam String token) {
        return userService.verifyToken(token);
    }

    @PostMapping("/auth/validate/user")
    public String getToken(@RequestBody UserEntity user) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPasswordHash()));
        if (authenticate.isAuthenticated()) {
            return userService.generateToken(user.getEmail());
        }
        return null;
    }

}
