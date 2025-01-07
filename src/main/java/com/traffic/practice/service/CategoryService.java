package com.traffic.practice.service;

import com.traffic.practice.dto.CategoryDTO;

public interface CategoryService {

    void register(String accountId, CategoryDTO categoryDTO);
    void updateCategory(CategoryDTO categoryDTO);
    void deleteCategory(Long id);
}
