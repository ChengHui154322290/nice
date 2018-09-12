package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GooderTransport;

import java.util.List;

public interface GooderTransportMapper extends Mapper<GooderTransport> {
    void deleteByGooderId(String gooderId);
    List<String> selectTransportByCarrierId(String carrierId);

    List<GooderTransport> selectTransByGooderId(String gooderId);
}