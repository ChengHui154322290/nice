package com.nice.good.dto;

public class ShelfSeatDto {

    /**
     * 库区编码
     */
    private String areaCode;

    /**
     * 库位编码
     */
    private String seatCode;


    /**
     * 货品标记 2正品,3次品
     */
    private Integer goodSign;

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

    public Integer getGoodSign() {
        return goodSign;
    }

    public void setGoodSign(Integer goodSign) {
        this.goodSign = goodSign;
    }
}
