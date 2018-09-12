package com.nice.good.service;

import com.nice.good.model.SysPlace;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
public interface SysPlaceService extends Service<SysPlace> {
    void sysPlaceAdd(SysPlace sysPlace, String userId) throws Exception;

    void sysPlaceUpdate(SysPlace sysPlace, String userId);


    /**
     * 通过 exhibition(场地名称) 查询 place_number(场地编号)， 在 a_sys_place 表中   -- 2018/05/21  17:18
     *
     * @param exhibition
     * @return
     */
    String findPlaceNumberByExhibition(String exhibition);


    /**
     * 通过 placeNumber 删除 a_sys_place 中的场地信息
     * 2018/05/12 11:11 rk
     *
     * @param placeNumber
     */
    void deleteByPlaceNumber(String placeNumber);

    /**
     * 通过 placeNumber(场地编码) 查询 i_store_area 表中 placeNumber(场地编码) 下是否有 area_code(库区编号)
     * 2018/05/12  10:15  rk
     *
     * @param placeNumber
     * @return
     */
    List<String> findAreaCodeByPlaceNumber(String placeNumber);

    // 检查place_number是否重复
    String checkSysPlaceNumber(String placeNumber);

    /**
     * 模糊匹配，查询 a_sys_place
     *
     * @param placeNumber
     * @param exhibition
     * @param type
     * @param country
     * @param province
     * @param city
     * @param district
     * @return
     */
    List<SysPlace> findBySevenParameters(String placeNumber, String exhibition, String type, String country, String province, String city, String district);


    /**
     * 查询 a_sys_place 表 中的 exhibition(场地名称)
     *
     * @return
     */
    List<String> findExhibitions();


    /**
     * 查询 a_sys_place 表 中的 place_number
     *
     * @return
     */
    List<String> findPlaceNumbers();



}
