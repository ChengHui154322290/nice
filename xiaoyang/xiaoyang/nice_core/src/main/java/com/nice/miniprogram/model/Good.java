package com.nice.miniprogram.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "g_good")
public class Good {
    /**
     * 主键id
     */
    @Id
    @Column(name = "good_id")
    private String goodId;

    /**
     * 序号id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

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
     * 是否启用 0否,1是
     */
    private Integer status;

    /**
     * 包装编码
     */
    @Column(name = "pack_code")
    private String packCode;

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
     * 品牌
     */
    private String brand;

    /**
     * 有效期
     */
    private String period;

    /**
     * 期效单位
     */
    @Column(name = "period_unite")
    private String periodUnite;

    /**
     * 产品链接
     */
    @Column(name = "good_link")
    private String goodLink;

    /**
     * 货品颜色
     */
    @Column(name = "good_color")
    private String goodColor;

    /**
     * 尺码
     */
    @Column(name = "good_size")
    private String goodSize;

    /**
     * 佣金链接
     */
    @Column(name = "brokerage_link")
    private String brokerageLink;

    /**
     * 佣金比例
     */
    @Column(name = "brokerage_ratio")
    private Double brokerageRatio;

    /**
     * 所属类目id
     */
    @Column(name = "category_id")
    private Integer categoryId;

    /**
     * 货品风格id
     */
    @Column(name = "style_id")
    private Integer styleId;

    /**
     * 正常售价
     */
    @Column(name = "normal_price")
    private Double normalPrice;

    /**
     * 秒杀价
     */
    @Column(name = "seckill_price")
    private Double seckillPrice;

    /**
     * 优惠形式
     */
    @Column(name = "discount_method")
    private String discountMethod;

    /**
     * 优惠链接
     */
    @Column(name = "discount_link")
    private String discountLink;

    /**
     * discount_content
     */
    @Column(name = "discount_content")
    private String discountContent;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 获取主键id
     *
     * @return good_id - 主键id
     */
    public String getGoodId() {
        return goodId;
    }

    /**
     * 设置主键id
     *
     * @param goodId 主键id
     */
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    /**
     * 获取序号id
     *
     * @return id - 序号id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号id
     *
     * @param id 序号id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取货主编码
     *
     * @return gooder_code - 货主编码
     */
    public String getGooderCode() {
        return gooderCode;
    }

    /**
     * 设置货主编码
     *
     * @param gooderCode 货主编码
     */
    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
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
     * 获取是否启用 0否,1是
     *
     * @return status - 是否启用 0否,1是
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否启用 0否,1是
     *
     * @param status 是否启用 0否,1是
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取包装编码
     *
     * @return pack_code - 包装编码
     */
    public String getPackCode() {
        return packCode;
    }

    /**
     * 设置包装编码
     *
     * @param packCode 包装编码
     */
    public void setPackCode(String packCode) {
        this.packCode = packCode;
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
     * 获取品牌
     *
     * @return brand - 品牌
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 设置品牌
     *
     * @param brand 品牌
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 获取有效期
     *
     * @return period - 有效期
     */
    public String getPeriod() {
        return period;
    }

    /**
     * 设置有效期
     *
     * @param period 有效期
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * 获取期效单位
     *
     * @return period_unite - 期效单位
     */
    public String getPeriodUnite() {
        return periodUnite;
    }

    /**
     * 设置期效单位
     *
     * @param periodUnite 期效单位
     */
    public void setPeriodUnite(String periodUnite) {
        this.periodUnite = periodUnite;
    }

    /**
     * 获取产品链接
     *
     * @return good_link - 产品链接
     */
    public String getGoodLink() {
        return goodLink;
    }

    /**
     * 设置产品链接
     *
     * @param goodLink 产品链接
     */
    public void setGoodLink(String goodLink) {
        this.goodLink = goodLink;
    }

    /**
     * 获取货品颜色
     *
     * @return good_color - 货品颜色
     */
    public String getGoodColor() {
        return goodColor;
    }

    /**
     * 设置货品颜色
     *
     * @param goodColor 货品颜色
     */
    public void setGoodColor(String goodColor) {
        this.goodColor = goodColor;
    }

    /**
     * 获取尺码
     *
     * @return good_size - 尺码
     */
    public String getGoodSize() {
        return goodSize;
    }

    /**
     * 设置尺码
     *
     * @param goodSize 尺码
     */
    public void setGoodSize(String goodSize) {
        this.goodSize = goodSize;
    }

    /**
     * 获取佣金链接
     *
     * @return brokerage_link - 佣金链接
     */
    public String getBrokerageLink() {
        return brokerageLink;
    }

    /**
     * 设置佣金链接
     *
     * @param brokerageLink 佣金链接
     */
    public void setBrokerageLink(String brokerageLink) {
        this.brokerageLink = brokerageLink;
    }

    /**
     * 获取佣金比例
     *
     * @return brokerage_ratio - 佣金比例
     */
    public Double getBrokerageRatio() {
        return brokerageRatio;
    }

    /**
     * 设置佣金比例
     *
     * @param brokerageRatio 佣金比例
     */
    public void setBrokerageRatio(Double brokerageRatio) {
        this.brokerageRatio = brokerageRatio;
    }

    /**
     * 获取所属类目id
     *
     * @return category_id - 所属类目id
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置所属类目id
     *
     * @param categoryId 所属类目id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取货品风格id
     *
     * @return style_id - 货品风格id
     */
    public Integer getStyleId() {
        return styleId;
    }

    /**
     * 设置货品风格id
     *
     * @param styleId 货品风格id
     */
    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    /**
     * 获取正常售价
     *
     * @return normal_price - 正常售价
     */
    public Double getNormalPrice() {
        return normalPrice;
    }

    /**
     * 设置正常售价
     *
     * @param normalPrice 正常售价
     */
    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }

    /**
     * 获取秒杀价
     *
     * @return seckill_price - 秒杀价
     */
    public Double getSeckillPrice() {
        return seckillPrice;
    }

    /**
     * 设置秒杀价
     *
     * @param seckillPrice 秒杀价
     */
    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    /**
     * 获取优惠形式
     *
     * @return discount_method - 优惠形式
     */
    public String getDiscountMethod() {
        return discountMethod;
    }

    /**
     * 设置优惠形式
     *
     * @param discountMethod 优惠形式
     */
    public void setDiscountMethod(String discountMethod) {
        this.discountMethod = discountMethod;
    }

    /**
     * 获取优惠链接
     *
     * @return discount_link - 优惠链接
     */
    public String getDiscountLink() {
        return discountLink;
    }

    /**
     * 设置优惠链接
     *
     * @param discountLink 优惠链接
     */
    public void setDiscountLink(String discountLink) {
        this.discountLink = discountLink;
    }

    /**
     * 获取discount_content
     *
     * @return discount_content - discount_content
     */
    public String getDiscountContent() {
        return discountContent;
    }

    /**
     * 设置discount_content
     *
     * @param discountContent discount_content
     */
    public void setDiscountContent(String discountContent) {
        this.discountContent = discountContent;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取创建人
     *
     * @return creater - 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建人
     *
     * @param creater 创建人
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取修改时间
     *
     * @return modifytime - 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * 设置修改时间
     *
     * @param modifytime 修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * 获取企业id
     *
     * @return company_id - 企业id
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置企业id
     *
     * @param companyId 企业id
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}