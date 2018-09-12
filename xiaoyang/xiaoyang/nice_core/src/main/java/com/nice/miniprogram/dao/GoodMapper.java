package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.Good;

public interface GoodMapper extends CoreMapper<Good> {
    Good getOneByGoodCode(String goodCode);
}