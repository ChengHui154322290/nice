package com.nice.good.service;


import com.nice.good.core.Service;
import com.nice.good.wx_model.GoodSku;
import com.nice.good.wx_model.GoodSkuVo;

import java.util.List;

/**
 * Created by CodeGenerator on 2018/08/06.
 */
public interface GoodSkuService extends Service<GoodSku> {


	List<GoodSkuVo> findBygoods(GoodSkuVo good);

	GoodSku getBySku(String skuCode);
}
