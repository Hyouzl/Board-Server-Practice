package com.traffic.practice.service.impl;

import com.traffic.practice.dto.UserDTO;
import com.traffic.practice.exception.DuplicatedIdException;
import com.traffic.practice.mapper.UserProfileMapper;
import com.traffic.practice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.traffic.practice.util.SHA256Util.encryptSHA256;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserProfileMapper userProfileMapper;

    public UserServiceImpl(UserProfileMapper userProfileMapper) {
        this.userProfileMapper = userProfileMapper;
    }

    @Override
    public void register(UserDTO userProfile) {
        boolean duplicatedResult = isDuplicatiedId(userProfile.getUserId());
        if (duplicatedResult) {
            throw new DuplicatedIdException("중복된 아이디입니다.");
        }
        userProfile.setCreatedTime(new Date());
        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));

        int insertCount = userProfileMapper.register(userProfile);

        if (insertCount != 1) {
            log.error("insertMember error! {}", userProfile);
            throw new RuntimeException("insertMember error! 회원가입 메서드를 확인해주세요 \n " + "Param: " + userProfile);
        }


    }

    @Override
    public UserDTO login(String id, String password) {
        String cryptPassword = encryptSHA256(password);
        UserDTO userDTO = userProfileMapper.findByIdAndPassword(id, cryptPassword);

        return userDTO;
    }

    @Override
    public boolean isDuplicatiedId(String id) {
        return userProfileMapper.idCheck(id) > 0;
    }

    @Override
    public UserDTO getUserProfile(String id) {
        return userProfileMapper.getUserProfile(id);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String afterPassword) {

        UserDTO userInfo = userProfileMapper.findByIdAndPassword(id, encryptSHA256(beforePassword));

        if (userInfo != null) {
           userInfo.setPassword(encryptSHA256(afterPassword));
           int insertCount = userProfileMapper.updatePassword(userInfo);
        } else {
            log.error("updatePassword error! {}", userInfo);
            throw new RuntimeException("updatePassword error! 비밀번호 변경 메서드를 확인해주세요 \n " + "Param: " + userInfo);
        }

    }

    @Override
    public void deleteId(String id, String password) {
        UserDTO userInfo = userProfileMapper.findByIdAndPassword(id, encryptSHA256(password));

        if (userInfo != null) {
            int deleteCount = userProfileMapper.deleteUserProfile(id);
        } else {
            log.error("deleteId error! {}", userInfo);
            throw new RuntimeException("deleteId error! 회원탈퇴 메서드를 확인해주세요 \n " + "Param: " + userInfo);
        }

    }


}
