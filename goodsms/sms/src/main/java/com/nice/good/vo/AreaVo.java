package com.nice.good.vo;

public class AreaVo {


    /**
     * 序号id
     */

    private Integer id;


    /**
     * 展厅编码
     */
    private String placeNumber;

    /**
     * 展厅名称
     */
    private String exhibition;

    public String getExhibition() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    /**
     * 库区类型
     */
    private String areaType;


    /**
     * 库区名称
     */
    private String areaName;

    /**
     * 库区编码
     */
    private String areaCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
}
