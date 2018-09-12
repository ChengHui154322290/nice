package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OutSender;
import org.apache.ibatis.annotations.Param;

public interface OutSenderMapper extends Mapper<OutSender> {

    void updateByBaseId(@Param(value = "baseId") String baseId, @Param(value = "hangUp") Integer hangUp);

    void  deleteByBaseId(String baseId);

     OutSender selectByBaseId(String baseId);
}