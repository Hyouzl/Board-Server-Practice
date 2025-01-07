package com.traffic.practice.controller;

import com.traffic.practice.aop.LoginCheck;
import com.traffic.practice.dto.CategoryDTO;
import com.traffic.practice.service.CategoryService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@Log4j2
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void register(String accountId,@RequestBody CategoryDTO categoryDTO) {
        categoryService.register(accountId, categoryDTO);
    }

    @PatchMapping("/{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void updateCategories(String accountId, @PathVariable(name = "categoryId") Long categoryId,
                                 @RequestBody CategoryRequest categoryRequest) {
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), CategoryDTO.SortStatus.NEWEST, 10, 1);
        categoryService.updateCategory(categoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    @LoginCheck(type = LoginCheck.UserType.ADMIN)
    public void deleteCategory(String accountId, @PathVariable(name = "categoryId") Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }


    // -------------- request 객체 --------------

    @Setter
    @Getter
    private static class CategoryRequest {
        private Long id;
        private String name;
    }






}
