package com.blogspot.sontx.bottle.server.model.service.category;

import com.blogspot.sontx.bottle.server.model.bean.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories(int page, int pageSize);
}
