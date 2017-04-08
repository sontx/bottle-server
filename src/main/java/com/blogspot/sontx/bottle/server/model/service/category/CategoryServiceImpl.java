package com.blogspot.sontx.bottle.server.model.service.category;

import com.blogspot.sontx.bottle.server.model.bean.Category;
import com.blogspot.sontx.bottle.server.model.entity.CategoryEntity;
import com.blogspot.sontx.bottle.server.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories(int page, int pageSize) {
        if (page < 0)
            page = 0;
        if (pageSize <= 0)
            pageSize = 10;

        Pageable pageable = new PageRequest(page, pageSize);
        Page<CategoryEntity> roomEntities = categoryRepository.findAll(pageable);

        List<Category> categories = new ArrayList<>(roomEntities.getSize());
        for (CategoryEntity categoryEntity : roomEntities) {
            Category category = new Category();
            category.setId(categoryEntity.getId());
            category.setName(categoryEntity.getName());
            category.setDescription(categoryEntity.getDescription());
            categories.add(category);
        }

        return categories;
    }

    @Override
    public Category getCategory(int categoryId) {
        if (categoryId >= 0) {
            CategoryEntity categoryEntity = categoryRepository.findOne(categoryId);

            Category category = new Category();
            category.setId(categoryEntity.getId());
            category.setName(categoryEntity.getName());
            category.setDescription(categoryEntity.getDescription());

            return category;
        }
        return null;
    }
}
