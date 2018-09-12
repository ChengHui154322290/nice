package com.nice.good.service;
import com.nice.good.model.StoreArea;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface StoreAreaService extends Service<StoreArea> {

      String storeAreaAdd(StoreArea storeArea, String placeId,String userId) throws Exception;

      String storeAreaUpdate(StoreArea storeArea, String placeId,String userId);

      String deleteByAreaId(List<String> areaIds,String placeId);
}
