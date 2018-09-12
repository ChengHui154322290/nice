package com.nice.good.model;

import javax.persistence.*;

@Table(name = "g_good_excel")
public class GoodExcel {
    /**
     * 主键id,系统后台生成
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
     * 货主名称
     */
    @Column(name = "gooder_name")
    private String gooderName;

    /**
     * 商品编码
     */
    @Column(name = "commodity_code")
    private String commodityCode;

    /**
     * 货品编码
     */
    @Column(name = "good_code")
    private String goodCode;

    /**
     * 货品规格
     */
    @Column(name = "good_model")
    private String goodModel;

    /**
     * 货品名称
     */
    @Column(name = "good_name")
    private String goodName;

    /**
     * 体积
     */
    private Double bulk;

    /**
     * 净重
     */
    @Column(name = "net_weight")
    private Double netWeight;

    /**
     * 皮重
     */
    @Column(name = "tare_weight")
    private Double tareWeight;

    /**
     * 毛重
     */
    @Column(name = "rough_weight")
    private Double roughWeight;

    /**
     * 属性
     */
    private String property;

    /**
     * 有效期
     */
    private Double period;

    /**
     * 期效单位
     */
    @Column(name = "period_unit")
    private String periodUnit;

    /**
     * 说明
     */
    private String statement;

    /**
     * 获取主键id,系统后台生成
     *
     * @return excel_id - 主键id,系统后台生成
     */
    public String getExcelId() {
        return excelId;
    }

    /**
     * 设置主键id,系统后台生成
     *
     * @param excelId 主键id,系统后台生成
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
     * 获取货主名称
     *
     * @return gooder_name - 货主名称
     */
    public String getGooderName() {
        return gooderName;
    }

    /**
     * 设置货主名称
     *
     * @param gooderName 货主名称
     */
    public void setGooderName(String gooderName) {
        this.gooderName = gooderName;
    }

    /**
     * 获取商品编码
     *
     * @return commodity_code - 商品编码
     */
    public String getCommodityCode() {
        return commodityCode;
    }

    /**
     * 设置商品编码
     *
     * @param commodityCode 商品编码
     */
    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    /**
     * 获取货品编码
     *
     * @return good_code - 货品编码
     */
    public String getGoodCode() {
        return goodCode;
    }

    /**
     * 设置货品编码
     *
     * @param goodCode 货品编码
     */
    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    /**
     * 获取货品规格
     *
     * @return good_model - 货品规格
     */
    public String getGoodModel() {
        return goodModel;
    }

    /**
     * 设置货品规格
     *
     * @param goodModel 货品规格
     */
    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    /**
     * 获取货品名称
     *
     * @return good_name - 货品名称
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * 设置货品名称
     *
     * @param goodName 货品名称
     */
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /**
     * 获取体积
     *
     * @return bulk - 体积
     */
    public Double getBulk() {
        return bulk;
    }

    /**
     * 设置体积
     *
     * @param bulk 体积
     */
    public void setBulk(Double bulk) {
        this.bulk = bulk;
    }

    /**
     * 获取净重
     *
     * @return net_weight - 净重
     */
    public Double getNetWeight() {
        return netWeight;
    }

    /**
     * 设置净重
     *
     * @param netWeight 净重
     */
    public void setNetWeight(Double netWeight) {
        this.netWeight = netWeight;
    }

    /**
     * 获取皮重
     *
     * @return tare_weight - 皮重
     */
    public Double getTareWeight() {
        return tareWeight;
    }

    /**
     * 设置皮重
     *
     * @param tareWeight 皮重
     */
    public void setTareWeight(Double tareWeight) {
        this.tareWeight = tareWeight;
    }

    /**
     * 获取毛重
     *
     * @return rough_weight - 毛重
     */
    public Double getRoughWeight() {
        return roughWeight;
    }

    /**
     * 设置毛重
     *
     * @param roughWeight 毛重
     */
    public void setRoughWeight(Double roughWeight) {
        this.roughWeight = roughWeight;
    }

    /**
     * 获取属性
     *
     * @return property - 属性
     */
    public String getProperty() {
        return property;
    }

    /**
     * 设置属性
     *
     * @param property 属性
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * 获取有效期
     *
     * @return period - 有效期
     */
    public Double getPeriod() {
        return period;
    }

    /**
     * 设置有效期
     *
     * @param period 有效期
     */
    public void setPeriod(Double period) {
        this.period = period;
    }

    /**
     * 获取期效单位
     *
     * @return period_unit - 期效单位
     */
    public String getPeriodUnit() {
        return periodUnit;
    }

    /**
     * 设置期效单位
     *
     * @param periodUnit 期效单位
     */
    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
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
    /*===================================企业id=====================================*/
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