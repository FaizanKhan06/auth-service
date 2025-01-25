package com.capstone.auth_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveSessionDataOutput {

    String email;
    int roleId;
    String jwtToken;

}
