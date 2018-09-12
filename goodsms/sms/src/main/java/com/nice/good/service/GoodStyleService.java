package com.nice.good.service;
import com.nice.good.model.GoodStyle;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/07/12.
 */
public interface GoodStyleService extends Service<GoodStyle> {
      void goodStyleAdd(GoodStyle goodStyle,String userId);
      void goodStyleUpdate(GoodStyle goodStyle,String userId);
}
