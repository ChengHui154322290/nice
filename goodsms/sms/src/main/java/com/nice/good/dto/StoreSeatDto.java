package com.nice.good.dto;


public class StoreSeatDto {

    //场地编号
    private String placeNumber;
    //库区编码
    private String areaCode;
    //库位名称
    private String seatName;
    //库位编号
    private String seatCode;
    //混放货品
    private String mixGood;
    private int mixGoodInt;
    //混放批次
    private String mixBatch;
    private int mixBatchInt;
    //库位类型
    private String seatType;
    //层级
    private String level;
    //库位标记
    private String seatTag;
    //库位状态 0未使用,1使用中
    private String seatStatus;
    private int seatStatusInt;
    //库位容量
    private String seatCapacity;
    //说明
    private String statement;
    //长
    private String length;
    private String error;

    public void setError(String error) {
        this.error = error;
    }

    public void setMixGoodInt(int mixGoodInt) {
        this.mixGoodInt = mixGoodInt;
    }

    public String getError() {

        return error;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    private String width;
    //高
    private String height;
    //文件名
    private String fileName;

    public String getPlaceNumber() {
        return placeNumber;
    }

    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    public String getSeatCode() {
        return seatCode;
    }

    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    public String getMixGood() {
        return mixGood;
    }

    public void setMixGood(String mixGood) {
        this.mixGood = mixGood;
    }

    public String getMixBatch() {
        return mixBatch;
    }

    public void setMixBatch(String mixBatch) {
        this.mixBatch = mixBatch;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSeatTag() {
        return seatTag;
    }

    public void setSeatTag(String seatTag) {
        this.seatTag = seatTag;
    }

    public String getSeatStatus() {
        return seatStatus;
    }

    public void setSeatStatus(String seatStatus) {
        this.seatStatus = seatStatus;
    }

    public String getSeatCapacity() {
        return seatCapacity;
    }

    public void setSeatCapacity(String seatCapacity) {
        this.seatCapacity = seatCapacity;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public int getMixGoodInt() {
        if("是".equals(mixGood)){
            mixGoodInt = 1;
        }else if("否".equals(mixGood)){
            mixGoodInt = 0;
        }
        return mixGoodInt;
    }
    public int getMixBatchInt() {
        if("是".equals(mixBatch)){
            mixBatchInt = 1;
        }else if("否".equals(mixBatch)){
            mixBatchInt = 0;
        }
        return mixBatchInt;
    }
    public int getSeatStatusInt() {
        if("使用中".equals(seatStatus)){
            seatStatusInt = 1;
        }else if("未使用".equals(seatStatus)){
            seatStatusInt = 0;
        }
        return seatStatusInt;
    }
}
