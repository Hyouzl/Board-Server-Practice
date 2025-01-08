package com.traffic.practice.service.impl;

import com.traffic.practice.dto.PostDTO;
import com.traffic.practice.dto.UserDTO;
import com.traffic.practice.mapper.PostMapper;
import com.traffic.practice.mapper.UserProfileMapper;
import com.traffic.practice.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;

    public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper) {
        this.postMapper = postMapper;
        this.userProfileMapper = userProfileMapper;
    }


    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO userDTO = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(userDTO.getId());
        postDTO.setCreatedTime(new Date());

        if(userDTO != null) {
            postMapper.register(postDTO);
        } else {
            throw new RuntimeException("regiset ERROR! 게시글 등록 메서드를 확인해주세요.");
        }

        postMapper.register(postDTO);
    }

    @Override
    public List<PostDTO> getMyPostList(Long userId) {
        List<PostDTO> postDTOList = postMapper.selectMyPostList(userId);

        return postDTOList;
    }

    @Override
    public void updatePost(PostDTO postDTO) {

        if (postDTO != null && postDTO.getId() != 0) {
            postMapper.updatePost(postDTO);
        } else {
            log.error("update Posts Error! {}", postDTO);
            throw new RuntimeException("update ERROR! 게시글 수정 메서드를 확인해주세요.");
        }

    }

    @Override
    public void deletePost(Long userId, Long id) {
        if (userId != 0 && id != 0) {
            postMapper.deletePost(userId, id);
        } else {
            log.error("delete Posts Error! userId: {}, id: {}", userId, id);
            throw new RuntimeException("delete ERROR! 게시글 삭제 메서드를 확인해주세요.");
        }

    }
}
