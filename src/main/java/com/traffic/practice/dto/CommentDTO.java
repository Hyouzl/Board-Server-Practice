package com.traffic.practice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommentDTO {
    private Long id;
    private Long postId;
    private String contents;
    private Long subCommentId;
}
