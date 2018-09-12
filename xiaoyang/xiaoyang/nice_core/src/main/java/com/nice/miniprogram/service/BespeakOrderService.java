package com.nice.miniprogram.service;
import com.nice.miniprogram.model.BespeakOrder;
import com.nice.miniprogram.core.Service;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/06/13.
 */
public interface BespeakOrderService extends Service<BespeakOrder> {
    BespeakOrder getById(int id);

    List<BespeakOrder> selectListByParams(Map<String,Object> map);

    Integer selectMaxId();

    List<BespeakOrder> getListByStartTime(Map<String,Object> params);
}
