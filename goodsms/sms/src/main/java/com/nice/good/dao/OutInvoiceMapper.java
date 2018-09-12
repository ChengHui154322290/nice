package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OutInvoice;
import org.apache.ibatis.annotations.Param;

public interface OutInvoiceMapper extends Mapper<OutInvoice> {
    void updateByBaseId(@Param(value = "baseId") String baseId, @Param(value = "hangUp") Integer hangUp);

    void deleteByBaseId(String baseId);

    OutInvoice selectByBaseId(String baseId);
}