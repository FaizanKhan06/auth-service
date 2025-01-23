package com.capstone.auth_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInputDataPojo {

    String email;
    String passwordHash;
    String firstName;
    String lastName;
    String phoneNo;
    String adhaarNo;
    int roleId;

}
