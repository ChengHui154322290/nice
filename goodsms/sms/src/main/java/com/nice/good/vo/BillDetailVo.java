package com.nice.good.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Transient;
import java.util.Date;

public class BillDetailVo {

    /**
     *  序号 id
     */
    private int id;

    /**
     *  货主名称
     */
    private String gooderName;

    /**
     *  货主编码
     */
    private String gooderCode;

    /**
     *  库区编码
     */
    private String areaCode;

    /**
     *  库位编码
     */
    private String seatCode;

    /**
     *  供应商编码
     */
    private String providerCode;

    /**
     *  组织机构编码
     */
    private String orgCode;

    /**
     *  货品编码
     */
    private String goodCode;

    /**
     *  货品规格
     */
    private String goodModel;

    /**
     *  货品名称
     */
    private String goodName;


    /**
     *  现有量
     */
    private int nowNum;

    /**
     *  可用量
     */
    private int useNum;

    /**
     *  分配量
     */
    private int allotNum;

    /**
     *  拣货量
     */
    private int pickNum;

    /**
     *  冻结量
     */
    private int freezeNum;

    /**
     * 判断是否剔除空库位   -- 2018/06/09  17:20  rk  用于 查询生成盘点单明细 信息
     */

    private Integer isNull;

    /**
     * 修改开始时间   -- 2018/06/09  17:20  rk  用于 查询生成盘点单明细 信息
     */

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDateStart;

    /**
     * 修改结束时间   -- 2018/06/09  17:20  rk  用于 查询生成盘点单明细 信息
     */

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDateEnd;


    public Integer getIsNull() {
        return isNull;
    }

    public void setIsNull(Integer isNull) {
        this.isNull = isNull;
    }

    public Date getModifyDateStart() {
        return modifyDateStart;
    }

    public void setModifyDateStart(Date modifyDateStart) {
        this.modifyDateStart = modifyDateStart;
    }

    public Date getModifyDateEnd() {
        return modifyDateEnd;
    }

    public void setModifyDateEnd(Date modifyDateEnd) {
        this.modifyDateEnd = modifyDateEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGooderName() {
        return gooderName;
    }

    public void setGooderName(String gooderName) {
        this.gooderName = gooderName;
    }

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public int getNowNum() {
        return nowNum;
    }

    public void setNowNum(int nowNum) {
        this.nowNum = nowNum;
    }

    public int getUseNum() {
        return useNum;
    }

    public void setUseNum(int useNum) {
        this.useNum = useNum;
    }

    public int getAllotNum() {
        return allotNum;
    }

    public void setAllotNum(int allotNum) {
        this.allotNum = allotNum;
    }

    public int getPickNum() {
        return pickNum;
    }

    public void setPickNum(int pickNum) {
        this.pickNum = pickNum;
    }

    public int getFreezeNum() {
        return freezeNum;
    }

    public void setFreezeNum(int freezeNum) {
        this.freezeNum = freezeNum;
    }

    /**
     * 移动类型:0-正常移动 ,1借领,2归还
     */
    @Transient
    private Integer moveType;


    public Integer getMoveType() {
        return moveType;
    }

    public void setMoveType(Integer moveType) {
        this.moveType = moveType;
    }
}
