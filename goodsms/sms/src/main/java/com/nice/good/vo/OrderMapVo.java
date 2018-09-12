package com.nice.good.vo;


public class OrderMapVo {


    /**
     * 序号id
     */
    private Integer id;

    /**
     * 货主名称
     */

    private String gooderName;


    /**
     * 场地名称
     */

    private String exhibition;

    /**
     * 商品编码
     */
    private String commodityCode;


    /**
     * 货品规格
     */

    private String goodModel;


    /**
     * 货品名称
     */

    private String goodName;


    /**
     * 货品编码
     */

    private String goodCode;

    /**
     * 包装编码
     */
    private String packCode;


    /**
     *
     * 有效期
     */
    private String period;


    /**
     * 期效单位
     */
    private String periodUnite;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGooderName() {
        return gooderName;
    }

    public void setGooderName(String gooderName) {
        this.gooderName = gooderName;
    }

    public String getExhibition() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getGoodModel() {
        return goodModel;
    }

    public void setGoodModel(String goodModel) {
        this.goodModel = goodModel;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPeriodUnite() {
        return periodUnite;
    }

    public void setPeriodUnite(String periodUnite) {
        this.periodUnite = periodUnite;
    }
    /*===================================企业id=====================================*/
//    /**
//     * 企业id
//     */
////    @Column(name = "company_id")
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
//    /*===================================场地id=====================================*/
//    /**
//     * 场地id
//     */
////    @Column(name = "place_id")
//    private String placeId ;
//
//
//    public String getPlaceId() {
//        return placeId;
//    }
//
//    public void setPlaceId(String placeId) {
//        this.placeId = placeId;
//    }
}
