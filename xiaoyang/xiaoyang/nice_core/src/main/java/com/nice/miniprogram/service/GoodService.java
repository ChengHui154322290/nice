package com.nice.miniprogram.service;
import com.nice.miniprogram.model.Good;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/08/21.
 */
public interface GoodService extends Service<Good> {

    Good getOneByGoodCode(String goodCode);
}
