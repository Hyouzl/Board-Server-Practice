package com.traffic.practice.service.impl;

import com.traffic.practice.dto.PostDTO;
import com.traffic.practice.dto.request.PostSearchRequest;
import com.traffic.practice.mapper.PostSearchMapper;
import com.traffic.practice.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    private final PostSearchMapper postSearchMapper;

    public PostSearchServiceImpl(PostSearchMapper postSearchMapper) {
        this.postSearchMapper = postSearchMapper;
    }


    @Cacheable(value = "post", key = "'getPosts' + #PostSearchRequest.getName() + #PostSearchRequest.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.selectPosts(postSearchRequest);
        } catch (RuntimeException e) {
            log.error("selectPosts 메서드 실패 {}", e.getMessage());
        }
        return null;
    }

    @Override
    public List<PostDTO> getPostsByTag(String tagName) {
        List<PostDTO> postDTOList = null;
        try {
            postDTOList = postSearchMapper.getPostsByTag(tagName);
        } catch (RuntimeException e) {
            log.error("selectPostsByTag 메서드 실패 {}", e.getMessage());
        }
        return null;
    }
}
