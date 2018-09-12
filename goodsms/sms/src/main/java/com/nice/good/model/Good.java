package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "g_good")
public class Good {
    @Id
    @Column(name = "good_id")
    private String goodId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 货主名称
     */
    @Transient
    private String gooderName;

    /**
     * 货主编码
     */

    @Column(name = "gooder_code")
    private String gooderCode;

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

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
     * 场地名称
     */
    @Transient
    private String exhibition;

    public String getExhibition() {
        return exhibition;
    }

    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    /**
     * 备注
     */
    private String remark;


    /**
     * 产品链接
     */
    @Column(name = "good_link")
    private String goodLink;


    /**
     * 颜色
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
     * 优惠方式
     */
    @Column(name = "discount_method")
    private String discountMethod;


    /**
     * 优惠链接
     */
    @Column(name = "discount_link")
    private String discountLink;

    /**
     * 优惠券额度
     */
    @Column(name = "coupon_limit")
    private Double couponLimit;

    /**
     * 优惠内容
     */
    @Column(name = "discount_content")
    private String discountContent;


    public Integer getStyleId() {
        return styleId;
    }

    public void setStyleId(Integer styleId) {
        this.styleId = styleId;
    }

    public String getGoodLink() {
        return goodLink;
    }

    public void setGoodLink(String goodLink) {
        this.goodLink = goodLink;
    }

    public String getGoodColor() {
        return goodColor;
    }

    public void setGoodColor(String goodColor) {
        this.goodColor = goodColor;
    }

    public String getGoodSize() {
        return goodSize;
    }

    public void setGoodSize(String goodSize) {
        this.goodSize = goodSize;
    }

    public String getBrokerageLink() {
        return brokerageLink;
    }

    public void setBrokerageLink(String brokerageLink) {
        this.brokerageLink = brokerageLink;
    }

    public Double getBrokerageRatio() {
        return brokerageRatio;
    }

    public void setBrokerageRatio(Double brokerageRatio) {
        this.brokerageRatio = brokerageRatio;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Double getNormalPrice() {
        return normalPrice;
    }

    public void setNormalPrice(Double normalPrice) {
        this.normalPrice = normalPrice;
    }

    public Double getSeckillPrice() {
        return seckillPrice;
    }

    public void setSeckillPrice(Double seckillPrice) {
        this.seckillPrice = seckillPrice;
    }

    public String getDiscountMethod() {
        return discountMethod;
    }

    public void setDiscountMethod(String discountMethod) {
        this.discountMethod = discountMethod;
    }

    public String getDiscountLink() {
        return discountLink;
    }

    public void setDiscountLink(String discountLink) {
        this.discountLink = discountLink;
    }

    public String getDiscountContent() {
        return discountContent;
    }

    public void setDiscountContent(String discountContent) {
        this.discountContent = discountContent;
    }

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    /**
     * 货品库区设置
     *
     * @return
     */

    @Transient
    public List<GoodArea> getGoodAreas() {
        return goodAreas;
    }

    public void setGoodAreas(List<GoodArea> goodAreas) {
        this.goodAreas = goodAreas;
    }


    /**
     * 修改人
     */

    private String modifier;

    /**
     * 修改时间
     */

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifytime;

    /**
     * 货品别名
     */
    @Transient
    private String aliasCode;


    public String getAliasCode() {
        return aliasCode;
    }

    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    /**
     * 货品配置对象
     */
    @Transient
    private GoodConfig goodConfig;

    /**
     * 货品库区配置对象
     */

    @Transient
    private List<GoodArea> goodAreas;

    /**
     * 货品别名对象
     */
    @Transient
    private List<GoodAlias> goodAlias;

    /**
     * 图片imgId
     *
     * @return
     */
    @Transient
    private List<String> imgIds;

    public List<String> getImgIds() {
        return imgIds;
    }

    public void setImgIds(List<String> imgIds) {
        this.imgIds = imgIds;
    }

    public GoodConfig getGoodConfig() {
        return goodConfig;
    }

    public void setGoodConfig(GoodConfig goodConfig) {
        this.goodConfig = goodConfig;
    }


    public List<GoodAlias> getGoodAlias() {
        return goodAlias;
    }

    public void setGoodAlias(List<GoodAlias> goodAlias) {
        this.goodAlias = goodAlias;
    }


    /**
     * @return good_id
     */
    public String getGoodId() {
        return goodId;
    }

    /**
     * @param goodId
     */
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
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


    public String getPackCode() {
        return packCode;
    }

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


    public String getBrand() {
        return brand;
    }

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
     * 获取期效单位年
     *
     * @return period_unite - 期效单位年
     */
    public String getPeriodUnite() {
        return periodUnite;
    }

    /**
     * 设置期效单位年
     *
     * @param periodUnite 期效单位
     */
    public void setPeriodUnite(String periodUnite) {
        this.periodUnite = periodUnite;
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
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


}