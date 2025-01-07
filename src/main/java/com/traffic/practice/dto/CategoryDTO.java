package com.traffic.practice.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    public enum SortStatus {
        CATECORYIES, NEWEST, OLDEST
    }

    private Long id;
    private String name;
    private SortStatus sortStatus;
    private int searchCount;
    private int pagingStartOffset;

}
