package com.nice.good.service;

import com.nice.good.model.PackManage;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/15.
 */
public interface PackManageService extends Service<PackManage> {
    String packManageAdd(PackManage packManage,String placeId, String userId) throws Exception;

    String deleteByPackId(List<String> packIds);

}
