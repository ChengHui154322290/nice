package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "o_shelf_good")
public class ShelfGood {
    /**
     * 主键id,后台生成
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
     * 货主名称
     */
    @Column(name = "gooder_name")
    private String gooderName;

    /**
     * 货主id
     */
    @Column(name = "gooder_id")
    private String gooderId;

    /**
     * 商品编码
     */
    @Column(name = "commodity_code")
    private String commodityCode;

    /**
     * 货品规格
     */
    @Column(name = "good_model")
    private String goodModel;

    /**
     * 货品编码
     */
    @Column(name = "good_code")
    private String goodCode;

    /**
     * 货品名称
     */
    @Column(name = "good_name")
    private String goodName;

    /**
     * 状态 1表示质检中,2表示已质检,3表示上架中
     */
    private Integer status;

    /**
     * 预期量
     */
    @Column(name = "expect_num")
    private Integer expectNum;

    /**
     * 接收量
     */
    @Column(name = "receive_num")
    private Integer receiveNum;

    /**
     * LPN
     */
    private String lpn;

    /**
     * 库位名称
     */
    @Column(name = "seate_name")
    private String seateName;

    /**
     * 货品标记  1过检 2 次品
     */
    @Column(name = "good_sign")
    private Integer goodSign;

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
     * 获取主键id,后台生成
     *
     * @return stock_id - 主键id,后台生成
     */
    public String getStockId() {
        return stockId;
    }

    /**
     * 设置主键id,后台生成
     *
     * @param stockId 主键id,后台生成
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
     * 获取货主id
     *
     * @return gooder_id - 货主id
     */
    public String getGooderId() {
        return gooderId;
    }

    /**
     * 设置货主id
     *
     * @param gooderId 货主id
     */
    public void setGooderId(String gooderId) {
        this.gooderId = gooderId;
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
     * 获取状态 1表示质检中,2表示已质检,3表示上架中
     *
     * @return status - 状态 1表示质检中,2表示已质检,3表示上架中
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1表示质检中,2表示已质检,3表示上架中
     *
     * @param status 状态 1表示质检中,2表示已质检,3表示上架中
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取预期量
     *
     * @return expect_num - 预期量
     */
    public Integer getExpectNum() {
        return expectNum;
    }

    /**
     * 设置预期量
     *
     * @param expectNum 预期量
     */
    public void setExpectNum(Integer expectNum) {
        this.expectNum = expectNum;
    }

    /**
     * 获取接收量
     *
     * @return receive_num - 接收量
     */
    public Integer getReceiveNum() {
        return receiveNum;
    }

    /**
     * 设置接收量
     *
     * @param receiveNum 接收量
     */
    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    /**
     * 获取LPN
     *
     * @return lpn - LPN
     */
    public String getLpn() {
        return lpn;
    }

    /**
     * 设置LPN
     *
     * @param lpn LPN
     */
    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    /**
     * 获取库位名称
     *
     * @return seate_name - 库位名称
     */
    public String getSeateName() {
        return seateName;
    }

    /**
     * 设置库位名称
     *
     * @param seateName 库位名称
     */
    public void setSeateName(String seateName) {
        this.seateName = seateName;
    }

    /**
     * 获取货品标记  1过检 2 次品
     *
     * @return good_sign - 货品标记  1过检 2 次品
     */
    public Integer getGoodSign() {
        return goodSign;
    }

    /**
     * 设置货品标记  1过检 2 次品
     *
     * @param goodSign 货品标记  1过检 2 次品
     */
    public void setGoodSign(Integer goodSign) {
        this.goodSign = goodSign;
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