package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.Gooder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GooderMapper extends Mapper<Gooder> {
    String findIdByGooderCode(String gooderCode);


    /**
     * 在 n_gooder 表中， 通过 gooder_name 查询 gooder_code   2018/05/21  16:47
     *
     * @param gooder_name
     * @return
     */
    String findGooderCodeByGooderName(@Param(value = "gooder_name") String gooder_name);

    /**
     * 查询 g_good表 中的 gooder_code -- 除去重复的编码
     *
     * @return
     */
    List<String> findGooderCodes();

    /**
     * 查询 n_gooder表 中的所有 gooder_name
     *
     * @return
     */
    List<String> findAllGooderNames();

    /**
     * 查询 n_gooder表 中的所有 gooder_code
     *
     * @return
     */
//    List<String> findAllGooderCodes();

    String  selectGooderCodeByAreaId(String areaId);


    String selectGooderIdByGooderName(String gooderName);


}