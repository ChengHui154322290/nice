package com.nice.good.service;
import com.nice.good.model.GoodAlias;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface GoodAliasService extends Service<GoodAlias> {
      String goodAliasAdd(GoodAlias goodAlias,String userId) throws Exception;

}
