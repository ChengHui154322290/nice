package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.Provider;

import java.util.List;

public interface ProviderMapper extends Mapper<Provider> {
    String findIdByProviderCode(String providerCode);

    /**
     * 查询 e_provider表 中的 provider_code
     * @return
     */
    List<String> findProviderCodes();

}