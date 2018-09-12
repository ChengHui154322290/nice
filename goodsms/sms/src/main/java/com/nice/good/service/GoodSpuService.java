package com.nice.good.service;

import com.nice.good.core.Service;
import com.nice.good.wx_model.GoodSpu;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
public interface GoodSpuService extends Service<GoodSpu> {

    GoodSpu getBySpu(String spuCode);

}
