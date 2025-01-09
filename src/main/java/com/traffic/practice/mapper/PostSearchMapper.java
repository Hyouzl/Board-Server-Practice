package com.traffic.practice.mapper;

import com.traffic.practice.dto.PostDTO;
import com.traffic.practice.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
    public List<PostDTO> getPostsByTag(String tagName);
}
