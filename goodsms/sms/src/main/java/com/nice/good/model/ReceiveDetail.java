package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "o_receive_detail")
public class ReceiveDetail {
    /**
     * 主键收货单明细id,后台生成
     */
    @Id
    @Column(name = "detail_id")
    private String detailId;

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
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 供应商编码
     */
    @Column(name = "provider_code")
    private String providerCode;

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
     * 货品规格
     */
    @Column(name = "good_model")
    private String goodModel;


    /**
     * 采购单号
     */
    @Column(name = "purchase_code")
    private String purchaseCode;

    /**
     * 采购单行号
     */
    @Column(name = "purchase_line_code")
    private Integer purchaseLineCode;

    /**
     * 外部单行号
     */
    @Column(name = "outside_line_code")
    private String outsideLineCode;

    /**
     * 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     */
    private Integer status;

    /**
     * 质检
     */
    @Column(name = "is_quality")
    private Integer isQuality;

    /**
     * 毛重
     */
    private Double rweight;

    /**
     * 体积
     */
    private Double bulk;

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
     * 拒收量
     */
    @Column(name = "refuse_num")
    private Integer refuseNum;

    /**
     * 质检量
     */
    @Column(name = "quality_num")
    private Integer qualityNum;

    /**
     * 次品量
     */
    @Column(name = "second_num")
    private Integer secondNum;

    /**
     * 上架量
     */
    @Column(name = "shelf_num")
    private Integer shelfNum;

    /**
     * 库位编码
     */
    @Column(name = "seat_code")
    private String seatCode;

    /**
     * rfid采集量
     */
    private Integer rfid;

    /**
     * 操作人
     */
    private String operator;

    /**
     * LPN
     */
    private String lpn;

    /**
     * 接收时间
     */
    @Column(name = "receive_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date receiveTime;

    /**
     * 质检时间
     */
    @Column(name = "quality_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date qualityTime;

    /**
     * 上架时间
     */
    @Column(name = "shelf_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date shelfTime;

    /**
     * asn收货单表id
     */
    @Column(name = "receive_id")
    private String receiveId;



    /**
     * 订单挂起 0表示未挂起 1表示挂起
     */
    @Column(name = "hang_up")
    private Integer hangUp;

    /**
     * 创建人id
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
     * 获取主键收货单明细id,后台生成
     *
     * @return detail_id - 主键收货单明细id,后台生成
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置主键收货单明细id,后台生成
     *
     * @param detailId 主键收货单明细id,后台生成
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
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

    public String getGoodModel() {
        return goodModel;
    }

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
     * 获取采购单号
     *
     * @return purchase_code - 采购单号
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    /**
     * 设置采购单号
     *
     * @param purchaseCode 采购单号
     */
    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    /**
     * 获取采购单行号
     *
     * @return purchase_line_code - 采购单行号
     */
    public Integer getPurchaseLineCode() {
        return purchaseLineCode;
    }

    /**
     * 设置采购单行号
     *
     * @param purchaseLineCode 采购单行号
     */
    public void setPurchaseLineCode(Integer purchaseLineCode) {
        this.purchaseLineCode = purchaseLineCode;
    }

    /**
     * 获取外部单行号
     *
     * @return outside_line_code - 外部单行号
     */
    public String getOutsideLineCode() {
        return outsideLineCode;
    }

    /**
     * 设置外部单行号
     *
     * @param outsideLineCode 外部单行号
     */
    public void setOutsideLineCode(String outsideLineCode) {
        this.outsideLineCode = outsideLineCode;
    }

    /**
     * 获取单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     *
     * @return status - 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     *
     * @param status 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     */
    public void setStatus(Integer status) {
        this.status = status;
    }


    public Integer getIsQuality() {
        return isQuality;
    }

    public void setIsQuality(Integer isQuality) {
        this.isQuality = isQuality;
    }

    /**
     * 获取毛重
     *
     * @return rweight - 毛重
     */
    public Double getRweight() {
        return rweight;
    }

    /**
     * 设置毛重
     *
     * @param rweight 毛重
     */
    public void setRweight(Double rweight) {
        this.rweight = rweight;
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
     * @return receive_num
     */
    public Integer getReceiveNum() {
        return receiveNum;
    }

    /**
     * @param receiveNum
     */
    public void setReceiveNum(Integer receiveNum) {
        this.receiveNum = receiveNum;
    }

    /**
     * 获取拒收量
     *
     * @return refuse_num - 拒收量
     */
    public Integer getRefuseNum() {
        return refuseNum;
    }

    /**
     * 设置拒收量
     *
     * @param refuseNum 拒收量
     */
    public void setRefuseNum(Integer refuseNum) {
        this.refuseNum = refuseNum;
    }

    /**
     * 获取质检量
     *
     * @return quality_num - 质检量
     */
    public Integer getQualityNum() {
        return qualityNum;
    }

    /**
     * 设置质检量
     *
     * @param qualityNum 质检量
     */
    public void setQualityNum(Integer qualityNum) {
        this.qualityNum = qualityNum;
    }

    /**
     * 获取次品量
     *
     * @return second_num - 次品量
     */
    public Integer getSecondNum() {
        return secondNum;
    }

    /**
     * 设置次品量
     *
     * @param secondNum 次品量
     */
    public void setSecondNum(Integer secondNum) {
        this.secondNum = secondNum;
    }

    /**
     * 获取上架量
     *
     * @return shelf_num - 上架量
     */
    public Integer getShelfNum() {
        return shelfNum;
    }

    /**
     * 设置上架量
     *
     * @param shelfNum 上架量
     */
    public void setShelfNum(Integer shelfNum) {
        this.shelfNum = shelfNum;
    }

    /**
     * 获取库位编码
     *
     * @return seat_code - 编码
     */
    public String getSeatCode() {
        return seatCode;
    }

    /**
     * 设置编码
     *
     * @param seatCode 编码
     */
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /**
     * 获取rfid采集量
     *
     * @return rfid - rfid采集量
     */
    public Integer getRfid() {
        return rfid;
    }

    /**
     * 设置rfid采集量
     *
     * @param rfid rfid采集量
     */
    public void setRfid(Integer rfid) {
        this.rfid = rfid;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
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
     * @param lnp LPN
     */
    public void setLpn(String lnp) {
        this.lpn = lpn;
    }

    /**
     * 获取接收时间
     *
     * @return receive_time - 接收时间
     */
    public Date getReceiveTime() {
        return receiveTime;
    }

    /**
     * 设置接收时间
     *
     * @param receiveTime 接收时间
     */
    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    /**
     * 获取质检时间
     *
     * @return quality_time - 质检时间
     */
    public Date getQualityTime() {
        return qualityTime;
    }

    /**
     * 设置质检时间
     *
     * @param qualityTime 质检时间
     */
    public void setQualityTime(Date qualityTime) {
        this.qualityTime = qualityTime;
    }

    /**
     * 获取上架时间
     *
     * @return shelf_time - 上架时间
     */
    public Date getShelfTime() {
        return shelfTime;
    }

    /**
     * 设置上架时间
     *
     * @param shelfTime 上架时间
     */
    public void setShelfTime(Date shelfTime) {
        this.shelfTime = shelfTime;
    }

    /**
     * 获取asn收货单表id
     *
     * @return receive_id - asn收货单表id
     */
    public String getReceiveId() {
        return receiveId;
    }

    /**
     * 设置asn收货单表id
     *
     * @param receiveId asn收货单表id
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
    }



    /**
     * 获取订单挂起 0表示未挂起 1表示挂起
     *
     * @return hang_up - 订单挂起 0表示未挂起 1表示挂起
     */
    public Integer getHangUp() {
        return hangUp;
    }

    /**
     * 设置订单挂起 0表示未挂起 1表示挂起
     *
     * @param hangUp 订单挂起 0表示未挂起 1表示挂起
     */
    public void setHangUp(Integer hangUp) {
        this.hangUp = hangUp;
    }

    /**
     * 获取创建人id
     *
     * @return create_id - 创建人id
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建人id
     *
     * @param createId 创建人id
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

}