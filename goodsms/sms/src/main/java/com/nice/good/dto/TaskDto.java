package com.nice.good.dto;


public class TaskDto {


    /**
     * 任务id
     */
    private Integer id;

    /**
     * 货主编码
     */

    private String gooderCode;

    /**
     * 货品编码
     */
    private String goodCode;

    /**
     * 组织编码
     */
    private String orgCode;

    /**
     * 供应商编码
     */
    private String providerCode;


    /**
     * 库位
     */
    private String seatCode;

    /**
     * 目标库位
     */
    private String goalSeat;


    /**
     * 拣货量
     */
    private Integer pickNum;


    /**
     * rfid
     */
    private Integer rfid;


    /**
     * 发货单号
     */
    private String sendCode;


    /**
     * 发货明细单号
     */
    private String detailId;

    /**
     * 任务号
     */
    private String taskCode;


    /**
     * 图片img
     */
    private String imgId;


    /**
     * lpn
     * @return
     */

    private String lpn;

    /**
     * 备注
     * @return
     */

    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSendCode() {
        return sendCode;
    }

    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }


    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
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

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getGoalSeat() {
        return goalSeat;
    }

    public void setGoalSeat(String goalSeat) {
        this.goalSeat = goalSeat;
    }

    public Integer getPickNum() {
        return pickNum;
    }

    public void setPickNum(Integer pickNum) {
        this.pickNum = pickNum;
    }

    public Integer getRfid() {
        return rfid;
    }

    public void setRfid(Integer rfid) {
        this.rfid = rfid;
    }

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
