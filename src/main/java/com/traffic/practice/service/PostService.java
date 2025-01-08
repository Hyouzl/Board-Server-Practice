package com.traffic.practice.service;

import com.traffic.practice.dto.PostDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO);
    List<PostDTO> getMyPostList(Long userId);
    void updatePost(PostDTO postDTO);
    void deletePost(Long userId, Long id);

}
