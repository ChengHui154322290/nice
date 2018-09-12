package com.nice.good.vo;

public class StockNumVo {

    /**
     当前库存总量
     *
     */
    private Integer totalNowNum;

    /**
     * 当前总可用量
     */

    private Integer totalUseNum;

    /**
     * 当前总的分配量
     */
    private Integer totalAllotNum;

    /**
     * 当前总的拣货量
     */

    private Integer totalPickNum;

    /**当前总的冻结量
     *
     */
    private Integer totalFreezeNum;

    public Integer getTotalNowNum() {
        return totalNowNum;
    }

    public void setTotalNowNum(Integer totalNowNum) {
        this.totalNowNum = totalNowNum;
    }

    public Integer getTotalUseNum() {
        return totalUseNum;
    }

    public void setTotalUseNum(Integer totalUseNum) {
        this.totalUseNum = totalUseNum;
    }


    public Integer getTotalAllotNum() {
        return totalAllotNum;
    }

    public void setTotalAllotNum(Integer totalAllotNum) {
        this.totalAllotNum = totalAllotNum;
    }

    public Integer getTotalPickNum() {
        return totalPickNum;
    }

    public void setTotalPickNum(Integer totalPickNum) {
        this.totalPickNum = totalPickNum;
    }

    public Integer getTotalFreezeNum() {
        return totalFreezeNum;
    }

    public void setTotalFreezeNum(Integer totalFreezeNum) {
        this.totalFreezeNum = totalFreezeNum;
    }
}
