package com.traffic.practice.mapper;

import com.traffic.practice.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    public void register(Long userId, CommentDTO commentDTO);
    public void updateComment(CommentDTO commentDTO);
    public void deletePostComment(Long commentId);
}
