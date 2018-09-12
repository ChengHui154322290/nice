package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.ReceiveDetailTemp;

import java.util.List;

public interface ReceiveDetailTempMapper extends Mapper<ReceiveDetailTemp> {
    void deleteByReceiveId(String receiveId);
    List<ReceiveDetailTemp> selectAllByReceiveId(String receiveId);
}