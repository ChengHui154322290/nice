package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OutReceiver;
import org.apache.ibatis.annotations.Param;

public interface OutReceiverMapper extends Mapper<OutReceiver> {

    void updateByBaseId(@Param(value = "baseId") String baseId, @Param(value = "hangUp") Integer hangUp);

    void deleteByBaseId(String baseId);

    OutReceiver selectByBaseId(String baseId);
}