package com.nice.good.service;
import com.nice.good.model.ShelfGood;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/09.
 */
public interface ShelfGoodService extends Service<ShelfGood> {
      void shelfGoodAdd(ShelfGood shelfGood,String userId);
      void shelfGoodUpdate(ShelfGood shelfGood,String userId);
}
