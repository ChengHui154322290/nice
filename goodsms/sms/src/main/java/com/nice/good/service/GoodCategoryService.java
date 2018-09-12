package com.nice.good.service;
import com.nice.good.model.GoodCategory;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/07/12.
 */
public interface GoodCategoryService extends Service<GoodCategory> {
      void goodCategoryAdd(GoodCategory goodCategory,String userId);
      void goodCategoryUpdate(GoodCategory goodCategory,String userId);
}
