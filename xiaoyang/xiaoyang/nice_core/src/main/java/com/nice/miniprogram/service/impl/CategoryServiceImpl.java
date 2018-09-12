package com.nice.miniprogram.service.impl;


import com.nice.miniprogram.dao.CategoryMapper;
import com.nice.miniprogram.model.Category;
import com.nice.miniprogram.service.CategoryService;
import com.nice.miniprogram.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/07/07.
 */
@Service
@Transactional
public class CategoryServiceImpl extends AbstractService<Category> implements CategoryService {
    @Resource
    private CategoryMapper categoryMapper;


    @Override
    public List<Category> getSecondLevelCategories() {
        return categoryMapper.getCategories();
    }
}
