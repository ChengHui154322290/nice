package com.nice.good.vo;

public class ReceiveDetailVo {

    private Integer expectSum;

    private Integer receiveSum;

    private Integer refuseSum;

    private Integer qualitySum;

    private Integer secondSum;

    public Integer getReceiveSum() {
        return receiveSum;
    }

    public Integer getExpectSum() {
        return expectSum;
    }

    public void setExpectSum(Integer expectSum) {
        this.expectSum = expectSum;
    }

    public void setReceiveSum(Integer receiveSum) {
        this.receiveSum = receiveSum;
    }

    public Integer getRefuseSum() {
        return refuseSum;
    }

    public void setRefuseSum(Integer refuseSum) {
        this.refuseSum = refuseSum;
    }

    public Integer getQualitySum() {
        return qualitySum;
    }

    public void setQualitySum(Integer qualitySum) {
        this.qualitySum = qualitySum;
    }

    public Integer getSecondSum() {
        return secondSum;
    }

    public void setSecondSum(Integer secondSum) {
        this.secondSum = secondSum;
    }
}
