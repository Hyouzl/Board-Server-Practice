package com.traffic.practice.service;

import com.traffic.practice.dto.PostDTO;
import com.traffic.practice.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {

    List<PostDTO> getPosts(PostSearchRequest postSearchRequest);
    List<PostDTO> getPostsByTag(String tagName);

}
