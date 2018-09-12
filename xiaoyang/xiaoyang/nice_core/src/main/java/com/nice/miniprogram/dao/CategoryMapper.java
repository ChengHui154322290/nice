package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.Category;
import io.swagger.models.auth.In;

import java.util.List;

public interface CategoryMapper extends CoreMapper<Category> {
    List<Category> getCategories();
}