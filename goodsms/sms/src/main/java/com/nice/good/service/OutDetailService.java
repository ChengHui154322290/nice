package com.nice.good.service;

import com.nice.good.core.Result;
import com.nice.good.model.OutDetail;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
public interface OutDetailService extends Service<OutDetail> {
    void outDetailAdd(OutDetail outDetail, String userId);

    void outDetailUpdate(OutDetail outDetail, String userId);



}
