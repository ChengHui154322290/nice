package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "s_stock")
public class Stock {
    /**
     * 库存id,后台生成
     */
    @Id
    @Column(name = "stock_id")
    private String stockId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;


    /**
     * 组织编码
     */
    @Column(name = "org_code")
    private String orgCode;


    /**
     * 供应商编码
     */
    @Column(name = "provider_Code")
    private String providerCode;


    /**
     * 货品编码
     */
    @Column(name = "good_code")
    private String goodCode;



    /**
     * 商品编码
     */
    @Column(name = "commodity_code")
    private String commodityCode;

    /**
     * 货品名称
     */
    @Column(name = "good_name")
    private String goodName;

    /**
     * 现有量
     */
    @Column(name = "now_num")
    private Integer nowNum;

    /**
     * 可用量
     */
    @Column(name = "use_num")
    private Integer useNum;

    /**
     * 分配量
     */
    @Column(name = "allot_num")
    private Integer allotNum;

    /**
     * 拣货量
     */
    @Column(name = "pick_num")
    private Integer pickNum;

    /**
     * 冻结量
     */
    @Column(name = "freeze_num")
    private Integer freezeNum;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 修改人
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 修改时间
     */
    @Column(name = "modify_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifyDate;

    /**
     * 获取库存id,后台生成
     *
     * @return stock_id - 库存id,后台生成
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * 设置库存id,后台生成
     *
     * @param stockId 库存id,后台生成
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
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
     * 获取货主code
     *
     * @return gooder_code - 货主code
     */
    public String getGooderCode() {
        return gooderCode;
    }

    /**
     * 设置货主code
     *
     * @param gooderCode 货主code
     */
    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
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
     * 获取现有量
     *
     * @return now_num - 现有量
     */
    public Integer getNowNum() {
        return nowNum;
    }

    /**
     * 设置现有量
     *
     * @param nowNum 现有量
     */
    public void setNowNum(Integer nowNum) {
        this.nowNum = nowNum;
    }

    /**
     * 获取可用量
     *
     * @return use_num - 可用量
     */
    public Integer getUseNum() {
        return useNum;
    }

    /**
     * 设置可用量
     *
     * @param useNum 可用量
     */
    public void setUseNum(Integer useNum) {
        this.useNum = useNum;
    }

    /**
     * 获取分配量
     *
     * @return allot_num - 分配量
     */
    public Integer getAllotNum() {
        return allotNum;
    }

    /**
     * 设置分配量
     *
     * @param allotNum 分配量
     */
    public void setAllotNum(Integer allotNum) {
        this.allotNum = allotNum;
    }

    /**
     * 获取拣货量
     *
     * @return pick_num - 拣货量
     */
    public Integer getPickNum() {
        return pickNum;
    }

    /**
     * 设置拣货量
     *
     * @param pickNum 拣货量
     */
    public void setPickNum(Integer pickNum) {
        this.pickNum = pickNum;
    }

    /**
     * 获取冻结量
     *
     * @return freeze_num - 冻结量
     */
    public Integer getFreezeNum() {
        return freezeNum;
    }

    /**
     * 设置冻结量
     *
     * @param freezeNum 冻结量
     */
    public void setFreezeNum(Integer freezeNum) {
        this.freezeNum = freezeNum;
    }

    /**
     * 获取创建人
     *
     * @return create_id - 创建人
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建人
     *
     * @param createId 创建人
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改人
     *
     * @return modify_id - 修改人
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * 设置修改人
     *
     * @param modifyId 修改人
     */
    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    /**
     * 获取修改时间
     *
     * @return modify_date - 修改时间
     */
    public Date getModifyDate() {
        return modifyDate;
    }

    /**
     * 设置修改时间
     *
     * @param modifyDate 修改时间
     */
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    /**
     * 场地id
     */
    @Column(name = "place_id")
    private String placeId ;


    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
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

    /**
     * 剔除空库为
     */
    @Transient
    private Integer removeEmpty;

    public Integer getRemoveEmpty() {
        return removeEmpty;
    }

    public void setRemoveEmpty(Integer removeEmpty) {
        this.removeEmpty = removeEmpty;
    }


    /**
     * 数据来源，0商品上新，1采购收货
     */
    @Transient
    private Integer flag;

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}