package com.traffic.practice.mapper;

import com.traffic.practice.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {

    public Long register(TagDTO tagDTO);
    public void deletePostTags(Long tagId);
    public void updateTags(TagDTO tagDTO);
    public void createPostTags(Long postId, Long tagId);

}
