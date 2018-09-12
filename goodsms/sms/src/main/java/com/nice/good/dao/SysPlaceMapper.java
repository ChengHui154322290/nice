package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.SysPlace;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysPlaceMapper extends Mapper<SysPlace> {


    /**
     * 通过 exhibition(场地名称) 查询 place_number(场地编号)， 在 a_sys_place 表中   -- 2018/05/21  17:18
     *
     * @param exhibition
     * @return
     */
    String findPlaceNumberByExhibition(@Param(value = "exhibition") String exhibition);


    /**
     * 通过 placeNumber 删除 a_sys_place 中的场地信息
     * 2018/05/12 11:11 rk
     * @param place_number
     */
    void deleteByPlaceNumber(@Param(value = "place_number")String place_number);

    /**
     * 通过 placeNumber(场地编码) 查询 i_store_area 表中 placeNumber(场地编码) 下是否有 area_code(库区编号)
     * 2018/05/12  10:15  rk
     *
     * @return
     *
     */

    List<String> findAreaCodeByPlaceNumber(@Param(value = "place_number") String place_number);

    /**
     * 检查placeNumber(场地编号)是否重复
     *
     * @param placeNumber
     * @return
     */
    String checkSysPlaceNumber(String placeNumber);

    List<String> findPlaceName();

    /**
     * 模糊匹配，查询 a_sys_place
     *
     * @param place_number
     * @param exhibition
     * @param type
     * @param country
     * @param province
     * @param city
     * @param district
     * @return
     */
    List<SysPlace> findBySevenParameters(@Param(value = "place_number") String place_number,
                                         @Param(value = "exhibition") String exhibition,
                                         @Param(value = "type") String type,
                                         @Param(value = "country") String country,
                                         @Param(value = "province") String province,
                                         @Param(value = "city") String city,
                                         @Param(value = "district") String district);


    List<String> selectPlaceNumbers();

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


    SysPlace selectPlaceByPlaceNumber(@Param(value = "username") String username);


    String findPlaceNumberById(String placeId);

    String selectPlaceIdByExhibition(String exhibition);
}