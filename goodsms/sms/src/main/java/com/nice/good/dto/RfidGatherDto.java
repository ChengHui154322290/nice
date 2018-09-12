package com.nice.good.dto;

import javax.persistence.Transient;
import java.util.List;

public class RfidGatherDto {

    /**
     * 收货明细主键id
     */
    private String detailId;

    /**
     * 货主编码
     */
    private String gooderCode;


    /**
     * 货品编码
     */
    private String goodCode;


    /**
     * 货品名称
     */
    private String goodName;


    /**
     * 货品规格
     */
    private String goodModel;


    /**
     * 状态
     */
    private Integer status;


    /**
     * 收货量
     */
    private Integer receiveNum;


    /**
     * 采集量
     */
    private Integer rfidGather;


    /**
     * rfid编码
     * @return
     */
    List<String> rfidCodes;

    public List<String> getRfidCodes() {
        return rfidCodes;
    }

    public void setRfidCodes(List<String> rfidCodes) {
        this.rfidCodes = rfidCodes;
    }

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReceiveNum() {
        return receiveNum;
    }

    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    public Integer getRfidGather() {
        return rfidGather;
    }

    public void setRfidGather(Integer rfidGather) {
        this.rfidGather = rfidGather;
    }
}
