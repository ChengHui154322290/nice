package com.nice.good.model;

import javax.persistence.*;

@Table(name = "i_store_sexcel")
public class StoreSexcel {
    /**
     * 主键id,后台生成
     */
    @Id
    @Column(name = "excel_id")
    private String excelId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 校验
     */
    private String checkout;

    /**
     * 展厅名称
     */
    private String exhibition;

    /**
     * 库区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 库位编号
     */
    @Column(name = "seat_code")
    private String seatCode;

    /**
     * 库位名称
     */
    @Column(name = "seat_name")
    private String seatName;

    /**
     * 库位类型
     */
    @Column(name = "seat_type")
    private String seatType;

    /**
     * 库位种类
     */
    @Column(name = "seat_kind")
    private String seatKind;

    /**
     * 混放货品
     */
    @Column(name = "mix_good")
    private String mixGood;

    /**
     * 混放批次
     */
    @Column(name = "mix_batch")
    private String mixBatch;

    /**
     * 线路顺序
     */
    @Column(name = "route_order")
    private String routeOrder;

    /**
     * 说明
     */
    private String statement;

    /**
     * 长
     */
    private Double length;

    /**
     * 宽
     */
    private Double width;

    /**
     * 高
     */
    private Double height;

    /**
     * 获取主键id,后台生成
     *
     * @return excel_id - 主键id,后台生成
     */
    public String getExcelId() {
        return excelId;
    }

    /**
     * 设置主键id,后台生成
     *
     * @param excelId 主键id,后台生成
     */
    public void setExcelId(String excelId) {
        this.excelId = excelId;
    }

    /**
     * 获取序号id,自动增长
     *
     * @return id - 序号id,自动增长
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号id,自动增长
     *
     * @param id 序号id,自动增长
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取校验
     *
     * @return checkout - 校验
     */
    public String getCheckout() {
        return checkout;
    }

    /**
     * 设置校验
     *
     * @param checkout 校验
     */
    public void setCheckout(String checkout) {
        this.checkout = checkout;
    }

    /**
     * 获取展厅名称
     *
     * @return exhibition - 展厅名称
     */
    public String getExhibition() {
        return exhibition;
    }

    /**
     * 设置展厅名称
     *
     * @param exhibition 展厅名称
     */
    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    /**
     * 获取库区名称
     *
     * @return area_name - 库区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置库区名称
     *
     * @param areaName 库区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取库位编号
     *
     * @return seat_code - 库位编号
     */
    public String getSeatCode() {
        return seatCode;
    }

    /**
     * 设置库位编号
     *
     * @param seatCode 库位编号
     */
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /**
     * 获取库位名称
     *
     * @return seat_name - 库位名称
     */
    public String getSeatName() {
        return seatName;
    }

    /**
     * 设置库位名称
     *
     * @param seatName 库位名称
     */
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    /**
     * 获取库位类型
     *
     * @return seat_type - 库位类型
     */
    public String getSeatType() {
        return seatType;
    }

    /**
     * 设置库位类型
     *
     * @param seatType 库位类型
     */
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    /**
     * 获取库位种类
     *
     * @return seat_kind - 库位种类
     */
    public String getSeatKind() {
        return seatKind;
    }

    /**
     * 设置库位种类
     *
     * @param seatKind 库位种类
     */
    public void setSeatKind(String seatKind) {
        this.seatKind = seatKind;
    }

    /**
     * 获取混放货品
     *
     * @return mix_good - 混放货品
     */
    public String getMixGood() {
        return mixGood;
    }

    /**
     * 设置混放货品
     *
     * @param mixGood 混放货品
     */
    public void setMixGood(String mixGood) {
        this.mixGood = mixGood;
    }

    /**
     * 获取混放批次
     *
     * @return mix_batch - 混放批次
     */
    public String getMixBatch() {
        return mixBatch;
    }

    /**
     * 设置混放批次
     *
     * @param mixBatch 混放批次
     */
    public void setMixBatch(String mixBatch) {
        this.mixBatch = mixBatch;
    }

    /**
     * 获取线路顺序
     *
     * @return route_order - 线路顺序
     */
    public String getRouteOrder() {
        return routeOrder;
    }

    /**
     * 设置线路顺序
     *
     * @param routeOrder 线路顺序
     */
    public void setRouteOrder(String routeOrder) {
        this.routeOrder = routeOrder;
    }

    /**
     * 获取说明
     *
     * @return statement - 说明
     */
    public String getStatement() {
        return statement;
    }

    /**
     * 设置说明
     *
     * @param statement 说明
     */
    public void setStatement(String statement) {
        this.statement = statement;
    }

    /**
     * 获取长
     *
     * @return length - 长
     */
    public Double getLength() {
        return length;
    }

    /**
     * 设置长
     *
     * @param length 长
     */
    public void setLength(Double length) {
        this.length = length;
    }

    /**
     * 获取宽
     *
     * @return width - 宽
     */
    public Double getWidth() {
        return width;
    }

    /**
     * 设置宽
     *
     * @param width 宽
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    /**
     * 获取高
     *
     * @return height - 高
     */
    public Double getHeight() {
        return height;
    }

    /**
     * 设置高
     *
     * @param height 高
     */
    public void setHeight(Double height) {
        this.height = height;
    }
//    /*===================================企业id=====================================*/
//    /**
//     * 企业id
//     */
//    @Column(name = "company_id")
//    private String companyId;
//
//
//    public String getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(String companyId) {
//        this.companyId = companyId;
//    }
}