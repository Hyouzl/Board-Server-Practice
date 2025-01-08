package com.traffic.practice.mapper;

import com.traffic.practice.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    public int register(PostDTO postDTO);
    public List<PostDTO> selectMyPostList(Long userId);
    public void updatePost(PostDTO postDTO);
    public void deletePost(Long userId, Long id);
}
