package com.capstone.auth_service.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.capstone.auth_service.entity.UserEntity;
import com.capstone.auth_service.pojo.UserInputDataPojo;
import com.capstone.auth_service.pojo.UserOutputDataPojo;
import com.capstone.auth_service.pojo.UserSaveSessionDataOutput;
import com.capstone.auth_service.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    @Autowired
    JwtService jwtService;

    @Autowired
    PasswordEncoder passwordEncoder;

    UserOutputDataPojo convertEntityToPojo1(UserEntity userEntity) {
        UserOutputDataPojo userOutputDataPojo = new UserOutputDataPojo();
        BeanUtils.copyProperties(userEntity, userOutputDataPojo);
        userOutputDataPojo.setRoleName(userEntity.getRole().getRoleName());
        return userOutputDataPojo;
    }

    public List<UserOutputDataPojo> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        List<UserOutputDataPojo> userOutputDataPojos = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userOutputDataPojos.add(convertEntityToPojo1(userEntity));
        }
        return userOutputDataPojos;
    }

    public List<UserOutputDataPojo> getAllActiveUsers() {
        List<UserEntity> userEntities = userRepository.findAllByIsDeleted(false);
        List<UserOutputDataPojo> userOutputDataPojos = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userOutputDataPojos.add(convertEntityToPojo1(userEntity));
        }
        return userOutputDataPojos;
    }

    public List<UserOutputDataPojo> getAllDeletedUsers() {
        List<UserEntity> userEntities = userRepository.findAllByIsDeleted(true);
        List<UserOutputDataPojo> userOutputDataPojos = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            userOutputDataPojos.add(convertEntityToPojo1(userEntity));
        }
        return userOutputDataPojos;
    }

    public UserOutputDataPojo getUserById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            UserOutputDataPojo userOutputDataPojo = convertEntityToPojo1(userEntity);
            return userOutputDataPojo;
        }
        return null;
    }

    public UserOutputDataPojo getUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email).orElse(null);
        if (userEntity != null) {
            UserOutputDataPojo userOutputDataPojo = convertEntityToPojo1(userEntity);
            return userOutputDataPojo;
        }
        return null;
    }

    public UserOutputDataPojo getUserByPhoneNo(String phoneNo) {
        UserEntity userEntity = userRepository.findByPhoneNo(phoneNo).orElse(null);
        if (userEntity != null) {
            UserOutputDataPojo userOutputDataPojo = convertEntityToPojo1(userEntity);
            return userOutputDataPojo;
        }
        return null;
    }

    public UserOutputDataPojo getUserByAdhaarNo(String adhaarNo) {
        UserEntity userEntity = userRepository.findByAdhaarNo(adhaarNo).orElse(null);
        if (userEntity != null) {
            UserOutputDataPojo userOutputDataPojo = convertEntityToPojo1(userEntity);
            return userOutputDataPojo;
        }
        return null;
    }

    public UserOutputDataPojo registerNewUser(UserInputDataPojo newUser) {
        UserEntity newUserEntity = new UserEntity();
        UserOutputDataPojo userOutputDataPojo = new UserOutputDataPojo();
        if (getUserByEmail(newUser.getEmail()) != null) {
            // Todo Exception email Already Exists
            userOutputDataPojo.setEmail("Email Already Exists");
            return userOutputDataPojo;
        } else if (getUserByPhoneNo(newUser.getPhoneNo()) != null) {
            // Todo Exception phoneNo Already Exists
            userOutputDataPojo.setEmail("Phone Number Already Exists");
            return userOutputDataPojo;
        } else if (getUserByAdhaarNo(newUser.getAdhaarNo()) != null) {
            // Todo Exception adhaarNo Already Exists
            userOutputDataPojo.setEmail("Adhaar Already Exists");
            return userOutputDataPojo;
        }
        BeanUtils.copyProperties(newUser, newUserEntity);
        newUserEntity.setPasswordHash(passwordEncoder.encode(newUser.getPasswordHash()));
        newUserEntity.setRole(roleService.getRoleById(newUser.getRoleId()));
        newUserEntity.setDeleted(false);
        userOutputDataPojo = convertEntityToPojo1(userRepository.saveAndFlush(newUserEntity));
        return userOutputDataPojo;
    }

    public void pseudoDeleteUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            userEntity.setDeleted(true);
            userRepository.save(userEntity);
        }
    }

    public void actualDeleteUser(Long userId) {
        UserEntity userEntity = userRepository.findById(userId).orElse(null);
        if (userEntity != null) {
            userRepository.deleteById(userId);
        }
    }

    public UserSaveSessionDataOutput generateToken(String email) {
        UserSaveSessionDataOutput userSaveSessionDataOutput = new UserSaveSessionDataOutput();
        userSaveSessionDataOutput.setEmail(email);
        userSaveSessionDataOutput.setRoleId(userRepository.findByEmail(email).get().getRole().getRoleId());
        userSaveSessionDataOutput.setJwtToken(jwtService.generateToken(email));
        return userSaveSessionDataOutput;
    }

    public String verifyToken(String token) {
        return jwtService.validateToken(token);
    }
}
