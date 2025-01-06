package com.traffic.practice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class UserDTO {

    public boolean hashNullDateBeforeRegister() {
        return (userId == null || password == null || nickname == null);
    }


    public enum Status {
        DEFAULT, ADMIN, DELETED
    }

    private Long id;
    private String userId;
    private String password;
    private String nickname;
    private boolean isAdmin;
    private Date createdTime;
    private boolean isWithDraw;
    private Status status;

}
