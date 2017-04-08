package com.blogspot.sontx.bottle.server.controller;

import com.blogspot.sontx.bottle.server.model.bean.Category;
import com.blogspot.sontx.bottle.server.model.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/categories")
public class CategoryRestController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    ResponseEntity getCategories(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        List<Category> categories = categoryService.getCategories(page, pageSize);
        return categories != null ? ResponseEntity.ok(categories) : ResponseEntity.status(400).build();
    }

    @GetMapping("{categoryId}")
    ResponseEntity getCategory(@PathVariable int categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.status(400).build();
    }
}
