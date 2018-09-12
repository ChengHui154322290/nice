package com.nice.miniprogram.service;
import com.nice.miniprogram.model.Category;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/07/07.
 */
public interface CategoryService extends Service<Category> {

    List<Category> getSecondLevelCategories();

}
