package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.Carrier;

import java.util.List;

public interface CarrierMapper extends Mapper<Carrier> {
    String findIdByCarrierCode(String carrierCode);

    /**
     * 查询 c_carrier 表 中的 carrier_code
     * @return
     */
    List<String> findCarrierCodes();


}