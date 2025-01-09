package com.traffic.practice.service;

import com.traffic.practice.dto.CommentDTO;
import com.traffic.practice.dto.PostDTO;
import com.traffic.practice.dto.TagDTO;

import java.util.List;

public interface PostService {

    void register(String id, PostDTO postDTO);
    List<PostDTO> getMyPostList(Long userId);
    void updatePost(PostDTO postDTO);
    void deletePost(Long userId, Long id);

    // ** commnet ** //
    void registerComment(Long userId, CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deletePostComment(Long userId, Long commentId);

    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deletePostTag(Long userId, Long tagId);


}
