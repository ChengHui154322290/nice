package com.nice.good.dto;


/**
* @Description:   rfid出库采集
* @Author:   fqs
* @Date:  2018/5/18 16:02
* @Version:   1.0
*/
public class GatherDto {

    /**
     * 出库明细单id
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
     * 状态 0未开始,1部分分配,2已分配,3部分拣货,4拣货完成,5部分发货,6已发货
     */
    private Integer status;


    /**
     * 分配量
     */
    private Integer allotNum;

    /**
     * 拣货量
     */

    private Integer pickNum;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAllotNum() {
        return allotNum;
    }

    public void setAllotNum(Integer allotNum) {
        this.allotNum = allotNum;
    }

    public Integer getPickNum() {
        return pickNum;
    }

    public void setPickNum(Integer pickNum) {
        this.pickNum = pickNum;
    }




    /**
     * 组织机构编码
     */
    private String orgCode;

    /**
     * 供应商编码
     */
    private String providerCode;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }
}
