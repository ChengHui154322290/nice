package com.nice.good.vo;

public class SendCodeVo {

    /**
     * 拣货任务id
     */
    private Integer id;

    /**
     * 发货单号
     */
    private String sendCode;


    /**
     * 明细单号
     */
    private String detailId;

    /**
     * 任务号
     */
    private String taskCode;

    /**
     * lpn
     * @return
     */

    private String lpn;


    /**
     * 货主编码
     */

    private String gooderCode;

    /**
     * 货品编码
     */
    private String goodCode;

    /**
     * 拣货量
     */
    private Integer pickNum;


    /**
     * 库位
     * @return
     */

    public Integer getPickNum() {
        return pickNum;
    }

    public void setPickNum(Integer pickNum) {
        this.pickNum = pickNum;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
