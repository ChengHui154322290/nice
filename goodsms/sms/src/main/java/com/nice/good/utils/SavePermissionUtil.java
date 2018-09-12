package com.nice.good.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SavePermissionUtil {

    private static Logger log = LoggerFactory.getLogger(SavePermissionUtil.class);

    /**
     * 通过获取 Map<"checked", V>中的 V 值是否‘等于’true ，进而累计自增  Map<K, V> 中的 V 值。 K 值为：gooderCode
     *
     * @param listBean
     * @return
     */
    public static List<String> getGooderCode(List<Map<String, String>> listBean) {

        // 设置 List<String> 接收为 checked=true 所对应的 gooderCode 值
        List<String> listGooderCode = new ArrayList<String>();
        try {
            for (Map<String, String> mapStr : listBean) {

                if (mapStr.get("checked").equals("true")) {
                    listGooderCode.add(mapStr.get("gooderCode"));
                }
            }

        } catch (Exception e) {
            log.error("获取List<String> gooderCode 货主编码异常!");
        }
        return listGooderCode;
    }


    /**
     * -- 2018/05/21  16:26 rk
     * 通过获取 Map<"checked", V>中的 V 值是否‘等于’true ，进而累计自增  Map<K, V> 中的 V 值。 K 值为：gooderCode
     *
     * @param listBean
     * @return
     */
    public static List<String> getGooderName(List<Map<String, String>> listBean) {

        // 设置 List<String> 接收为 checked=true 所对应的 gooderCode 值
        List<String> listGooderName = new ArrayList<String>();
        try {
            for (Map<String, String> mapStr : listBean) {

                if (mapStr.get("checked").equals("true")) {
                    listGooderName.add(mapStr.get("gooderName"));
                }
            }

        } catch (Exception e) {
            log.error("获取List<String> gooderName 货主名称异常!");
        }
        return listGooderName;
    }


    /**
     * 通过获取 Map<"checked", V>中的 V 值是否‘等于’true ，进而累计自增  Map<K, V> 中的 V 值。 K 值为：placeNumber
     *
     * @param listBean
     * @return
     */
    public static List<String> getPlaceNumber(List<Map<String, String>> listBean) {

        // 设置 List<String> 接收为 checked=true 所对应的 gooderCode 值
        List<String> listPlaceNumber = new ArrayList<String>();
        try {
            for (Map<String, String> mapStr : listBean) {

                if (mapStr.get("checked").equals("true")) {
                    listPlaceNumber.add(mapStr.get("placeNumber"));
                }
            }

        } catch (Exception e) {
            log.error("获取List<String> placeNumber 场地异常!");
        }
        return listPlaceNumber;
    }


    /**
     * -- 2018/05/21  16:26 rk
     * 通过获取 Map<"checked", V>中的 V 值是否‘等于’true ，进而累计自增  Map<K, V> 中的 V 值。 K 值为：placeNumber
     *
     * @param listBean
     * @return
     */
    public static List<String> getPlaceName(List<Map<String, String>> listBean) {

        // 设置 List<String> 接收为 checked=true 所对应的 gooderCode 值
        List<String> listPlaceName = new ArrayList<String>();
        try {
            for (Map<String, String> mapStr : listBean) {

                if (mapStr.get("checked").equals("true")) {
                    listPlaceName.add(mapStr.get("placeName"));
                }
            }

        } catch (Exception e) {
            log.error("获取List<String> placeName 场地名称异常!");
        }
        return listPlaceName;
    }


    /**
     * 通过获取 Map<"checked", V>中的 V 值是否‘等于’true ，进而累计自增  Map<K, V> 中的 V 值。 K 值为：roleId
     *
     * @param listBean
     * @return
     */
    public static List<String> getRoleId(List<Map<String, String>> listBean) {

        // 设置 List<String> 接收为 checked=true 所对应的 gooderCode 值
        List<String> listRoleId = new ArrayList<String>();
        try {
            for (Map<String, String> mapStr : listBean) {

                if (mapStr.get("checked").equals("true")) {
                    listRoleId.add(mapStr.get("roleId"));
                }
            }

        } catch (Exception e) {
            log.error("获取List<String> listRoleId 角色ID异常!");
        }
        return listRoleId;
    }


    /**
     * -- 2018/05/21  16:26 rk
     * 通过获取 Map<"checked", V>中的 V 值是否‘等于’true ，进而累计自增  Map<K, V> 中的 V 值。 K 值为：roleId
     *
     * @param listBean
     * @return
     */
    public static List<String> getRoleName(List<Map<String, String>> listBean) {

        // 设置 List<String> 接收为 checked=true 所对应的 gooderCode 值
        List<String> listRoleName = new ArrayList<String>();
        try {
            for (Map<String, String> mapStr : listBean) {

                if (mapStr.get("checked").equals("true")) {
                    listRoleName.add(mapStr.get("roleName"));
                }
            }

        } catch (Exception e) {
            log.error("获取List<String> listRoleName 角色名称异常!");
        }
        return listRoleName;
    }


}
