package com.nice.good.vo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class PlaceSeatVo {


    /**
     * 库位主键seatId
     */

    private String seatId;

    public String getSeatId() {
        return seatId;
    }

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    /**

     * 序号id
     */

    private Integer id;

    /**
     * 展厅名称
     */
    private String exhibition;

    /**

     * 展厅编号
     */

    private String placeNumber;

    /**
     * 库区名称
     */

    private String areaName;

    /**
     * 库区编号
     */

    private String areaCode;

    /**
     * 库位名称
     */

    private String seatName;


    /**
     * 库位类型
     */

    private String seatType;

    /**
     * 库位编号
     */

    private String seatCode;

    /**
     * 混放货品 0 否,1是
     */

    private Integer mixGood;

    /**
     * 混放批次 0否,1是
     */

    private Integer mixBatch;



    /**
     * 说明
     */
    private String statement;

    /**
     * 库位容量
     */
    private Integer seatCapacity;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createtime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifytime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getexhibition() {
        return exhibition;
    }

    public void setexhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public Integer getMixGood() {
        return mixGood;
    }

    public void setMixGood(Integer mixGood) {
        this.mixGood = mixGood;
    }

    public Integer getMixBatch() {
        return mixBatch;
    }

    public void setMixBatch(Integer mixBatch) {
        this.mixBatch = mixBatch;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }
}
