package com.traffic.practice.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    private Long id;
    private String name;
    private int isAdmin;
    private String contents;
    private int views;
    private Long categoryId;
    private Long userId;
    private Long fileId;
    private Date createdTime;
    private Date updateTime;
}
