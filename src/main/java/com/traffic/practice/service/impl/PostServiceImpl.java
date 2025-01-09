package com.traffic.practice.service.impl;

import com.traffic.practice.dto.CommentDTO;
import com.traffic.practice.dto.PostDTO;
import com.traffic.practice.dto.TagDTO;
import com.traffic.practice.dto.UserDTO;
import com.traffic.practice.mapper.CommentMapper;
import com.traffic.practice.mapper.PostMapper;
import com.traffic.practice.mapper.TagMapper;
import com.traffic.practice.mapper.UserProfileMapper;
import com.traffic.practice.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;
    private final UserProfileMapper userProfileMapper;
    private final CommentMapper commentMapper;
    private final TagMapper tagMapper;

    public PostServiceImpl(PostMapper postMapper, UserProfileMapper userProfileMapper, CommentMapper commentMapper, TagMapper tagMapper) {
        this.postMapper = postMapper;
        this.userProfileMapper = userProfileMapper;
        this.commentMapper = commentMapper;
        this.tagMapper = tagMapper;
    }


    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO userDTO = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(userDTO.getId());
        postDTO.setCreatedTime(new Date());

        if(userDTO != null) {
            Long postId = postMapper.register(postDTO);
            if(postDTO.getTagDTOList() != null) {
                for(TagDTO tagDTO : postDTO.getTagDTOList()) {
                    tagDTO.setPostId(postId);
                    Long tagId = tagMapper.register(tagDTO);
                    tagMapper.createPostTags(postId, tagId);
                }
            }
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

    @Override
    public void registerComment(Long userId, CommentDTO commentDTO) {
        if(commentDTO.getPostId() != null) {
            commentMapper.register(userId, commentDTO);
        } else {
            // DB가 닫혀있거나, 네트워크 오류가 있을 때 개발자한테 이슈를 알려주기 위해 설정.
            log.error("register Comment Error! {}", commentDTO);
            throw new RuntimeException("register Comment Error! {}");
        }

    }


    @Override
    public void updateComment(CommentDTO commentDTO) {
        if (commentDTO != null) {
            commentMapper.updateComment(commentDTO);
        } else {
            log.error("update Comment Error! {}");
            throw new RuntimeException("update ERROR! 댓글 수정 메서드를 확인해주세요.");
        }
    }

    @Override
    public void deletePostComment(Long userId, Long commentId) {
        if (userId != 0 && commentId != 0) {
            commentMapper.deletePostComment(commentId);
        } else {
            log.error("delete Comment Error! userId: {}, commentId: {}", userId, commentId);
            throw new RuntimeException("delete ERROR! 댓글 삭제 메서드를 확인해주세요.");
        }

    }


    @Override
    public void registerTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            tagMapper.register(tagDTO);
        } else {
            log.error("register Tag Error! {}", tagDTO);
            throw new RuntimeException("register ERROR! 태그 등록 메서드를 확인해주세요.");
        }

    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            tagMapper.updateTags(tagDTO);
        } else {
            log.error("update Tag Error! {}", tagDTO);
            throw new RuntimeException("update ERROR! 태그 수정 메서드를 확인해주세요.");
        }

    }

    @Override
    public void deletePostTag(Long userId, Long tagId) {
        if(userId != 0 && tagId != 0) {
            tagMapper.deletePostTags(tagId);
        } else {
            log.error("delete Tag Error! userId: {}, tagId: {}", userId, tagId);
            throw new RuntimeException("delete ERROR! 태그 삭제 메서드를 확인해주세요.");
        }

    }
}
