package com.nice.good.service;

import com.nice.good.vo.PdaDetailVo;

public interface PdaReceiveGoodService {

    String receiveGood(PdaDetailVo pdaDetailVo,String placeId,String userId) throws Exception;
}
