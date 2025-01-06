package com.traffic.practice.service;

import com.traffic.practice.dto.UserDTO;
import org.springframework.stereotype.Service;

public interface UserService {

    void register(UserDTO userProfile);

    UserDTO login(String id, String password);

    boolean isDuplicatiedId(String id);

    UserDTO getUserProfile(String id);

    void updatePassword(String id, String beforePassword, String afterPassword);

    void deleteId(String id, String password);
}
