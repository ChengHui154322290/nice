package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OutBase;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OutBaseMapper extends Mapper<OutBase> {

    List<String>  selectBaseByCarrierCode(String carrierCode);
    Integer selectMaxId();
    String selectSendCodeByBaseId(String baseId);
    void updateStatus(@Param(value = "baseId") String baseId, @Param(value = "status") Integer status);

    /**
     *  通过最大 id 值查询 OutBase.java中的 SendCode(发货编号)  --  rk  2018/05/02
     * @return
     */
    OutBase findSendCodeByMaxId();

    String selectBaseIdBySendCode(String sendCode);

    OutBase selectBySenCode(String sendCode);

	List<OutBase> selectByConditionMap(Map<Object, Object> conditionMap);
}