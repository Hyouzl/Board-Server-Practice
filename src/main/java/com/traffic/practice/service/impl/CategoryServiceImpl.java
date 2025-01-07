package com.traffic.practice.service.impl;

import com.traffic.practice.dto.CategoryDTO;
import com.traffic.practice.mapper.CategoryMapper;
import com.traffic.practice.service.CategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {


    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {
        if (accountId != null) {
            categoryMapper.register(categoryDTO);
        } else {
            log.error("register Error! {}", categoryDTO);
            throw new RuntimeException("register Error! 카테고리 등록 메서드를 확인해주세요 \n " + "Param: " + categoryDTO);
        }
    }

    @Override
    public void updateCategory(CategoryDTO categoryDTO) {
        if (categoryDTO != null) {
            categoryMapper.updateCategory(categoryDTO);
        } else {
            log.error("updateCategory Error! {}", categoryDTO);
            throw new RuntimeException("updateCategory Error! 카테고리 수정 메서드를 확인해주세요 \n " + "Param: " + categoryDTO);
        }

    }

    @Override
    public void deleteCategory(Long id) {
        if (id != 0) {
            categoryMapper.deleteCategory(id);
        } else {
            log.error("deleteCategory Error! {}", id);
            throw new RuntimeException("deleteCategory Error! 카테고리 삭제 메서드를 확인해주세요 \n " + "Param: " + id);
        }

    }
}
