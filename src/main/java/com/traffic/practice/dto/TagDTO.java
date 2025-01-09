package com.traffic.practice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagDTO {
    private Long id;
    private String name;
    private String url; // 실제 url 정보, 웹에서 사용할 때 활용할 수 있도록
    private Long postId;
}
