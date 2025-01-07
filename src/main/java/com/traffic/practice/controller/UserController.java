package com.traffic.practice.controller;

import com.traffic.practice.aop.LoginCheck;
import com.traffic.practice.dto.UserDTO;
import com.traffic.practice.dto.request.UserDeleteId;
import com.traffic.practice.dto.request.UserLoginRequest;
import com.traffic.practice.dto.request.UserUpdatePasswordRequest;
import com.traffic.practice.dto.response.LoginResponse;
import com.traffic.practice.dto.response.UserInfoResponse;
import com.traffic.practice.service.UserService;
import com.traffic.practice.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserService userService;
    private static LoginResponse loginResponse;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
       if (userDTO.hashNullDateBeforeRegister()) {
           log.error("회원가입 실패: 필수 정보가 누락되었습니다.");
           throw new RuntimeException("회원가입 실패: 필수 정보가 누락되었습니다.");
       }

       userService.register(userDTO);
    }

    @PostMapping("/sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest userLoginRequest,
                            HttpSession httpSession) {

        ResponseEntity<LoginResponse> responseEntity = null;
        String id = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();
        LoginResponse loginResponse;
        UserDTO userInfo = userService.login(id, password);

        if(userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null) {
            loginResponse = LoginResponse.success(userInfo);
            if (userInfo.getStatus() == (UserDTO.Status.ADMIN)) {
                SessionUtil.setLoginAdminId(httpSession, userInfo.getUserId());
            } else {
                SessionUtil.setLoginMemberId(httpSession, userInfo.getUserId());
            }

            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login  Error! 유저정보가 없거나 지원하지 않는 유저입니다");
        }
        return HttpStatus.OK;
    }


    @GetMapping("my-info")
    public UserInfoResponse getMemberInfo(HttpSession httpSession) {
        String loginMemberId = SessionUtil.getLoginMemberId(httpSession);
        String loginAdminId = SessionUtil.getLoginAdminId(httpSession);
        String id;
        if (loginMemberId == null && loginAdminId == null) {
            throw new RuntimeException("로그인이 필요합니다.");
        } else {
            id = (loginMemberId != null) ? loginMemberId : loginAdminId;
        }

        UserDTO userDTO = userService.getUserProfile(id);

        return new UserInfoResponse(userDTO);
    }


    // put 은 전체 수정
    @PutMapping("/logout")
    public void logout(HttpSession httpSession) {
        SessionUtil.clear(httpSession);
    }

    @PatchMapping("/password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public  ResponseEntity<LoginResponse> updateUserPassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
                                                             HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;

        String id = accountId;
        LoginResponse loginResponse = null;
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(id, beforePassword, afterPassword);
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("update password error! {}", e.getMessage());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/withdrawal")
    public ResponseEntity<LoginResponse> deleteId (@RequestBody UserDeleteId userDeleteId,
                                                   HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            log.error("deleteId error! {}", e.getMessage());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

}
