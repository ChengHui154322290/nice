package com.nice.good.dto;


import java.util.List;

public class SeatDto {

    /**
     * 展厅编码
     */
    private String placeNumber;

    /**
     * 所在展厅的库区编码
     */
    private List<String> areaCodes;


    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    public List<String> getAreaCodes() {
        return areaCodes;
    }

    public void setAreaCodes(List<String> areaCodes) {
        this.areaCodes = areaCodes;
    }
}
