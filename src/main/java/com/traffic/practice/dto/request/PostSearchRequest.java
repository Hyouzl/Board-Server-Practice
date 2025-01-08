package com.traffic.practice.dto.request;

import com.traffic.practice.dto.CategoryDTO;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchRequest {
    private Long id;
    private String name;
    private String content;
    private int views;
    private int categoryId;
    private CategoryDTO.SortStatus sortStatus;

}
