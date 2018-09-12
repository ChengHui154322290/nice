package com.nice.good.dto;



import java.util.List;

public class GoodDto<T> {

    private List<String> placeNumbers;

    private List<String> gooderCodes;

    private  List<String> packCodes;


    /**
     * 所属类目
     */
    private List<T> categoryList;

    /**
     *货品风格
     */
    private List<T> styleList;

    public List<String> getPlaceNumbers() {
        return placeNumbers;
    }

    public void setPlaceNumbers(List<String> placeNumbers) {
        this.placeNumbers = placeNumbers;
    }

    public List<String> getGooderCodes() {
        return gooderCodes;
    }

    public void setGooderCodes(List<String> gooderCodes) {
        this.gooderCodes = gooderCodes;
    }

    public List<String> getPackCodes() {
        return packCodes;
    }

    public void setPackCodes(List<String> packCodes) {
        this.packCodes = packCodes;
    }

    public List<T> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<T> categoryList) {
        this.categoryList = categoryList;
    }

    public List<T> getStyleList() {
        return styleList;
    }

    public void setStyleList(List<T> styleList) {
        this.styleList = styleList;
    }
}
