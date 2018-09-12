package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "o_receive_order_temp")
public class ReceiveOrderTemp {
    /**
     * 主键id,后台生成
     */
    @Id
    @Column(name = "receive_id")
    private String receiveId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 收货单编号
     */
    @Column(name = "receive_code")
    private String receiveCode;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 货主名称
     */
    @Transient
    private String gooderName;

    /**
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 组织机构名称
     */
    @Transient
    private String orgName;


    /**
     * 收货类型
     */
    @Column(name = "receive_type")
    private String receiveType;

    /**
     * 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 预约时间
     */
    @Column(name = "appoint_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date appointTime;

    /**
     * 供应商编码
     */
    @Column(name = "provider_code")
    private String providerCode;

    /**
     * 供应商名称
     */

    @Transient
    private String providerName;


    /**
     * 采购订单号
     */
    @Column(name = "purchase_code")
    private String purchaseCode;

    /**
     * 外部单号
     */
    @Column(name = "outside_code")
    private String outsideCode;

    /**
     * 月台编号
     */
    @Column(name = "platform_code")
    private String platformCode;

    /**
     * 承运商编码
     */
    @Column(name = "carrier_code")
    private String carrierCode;

    /**
     * 承运商名称
     */
    @Transient
    private  String carrierName;


    /**
     * 车牌号
     */
    @Column(name = "car_num")
    private String carNum;

    /**
     * 司机
     */
    private String driver;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 月台类型
     */
    @Column(name = "platform_type")
    private String platformType;

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
     * 上架量
     */
    @Column(name = "shelf_num")
    private Integer shelfNum;

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
     * 结算时间
     */
    @Column(name = "clear_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date clearTime;

    /**
     * 预计体积
     */
    @Column(name = "predict_bulk")
    private Double predictBulk;

    /**
     * 预计重量
     */
    @Column(name = "predict_weight")
    private Double predictWeight;

    /**
     * 上次上架时间
     */
    @Column(name = "last_shelf_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastShelfTime;

    /**
     * 上次接收时间
     */
    @Column(name = "last_receive_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastReceiveTime;

    /**
     * 上次质检时间
     */
    @Column(name = "last_quality_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastQualityTime;

    /**
     * 说明
     */
    private String statement;

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
     * 收货表明细对象
     */
    @Transient
    private List<ReceiveDetailTemp> receiveDetailTemps;

    public List<ReceiveDetailTemp> getReceiveDetailTemps() {
        return receiveDetailTemps;
    }

    public void setReceiveDetailTemps(List<ReceiveDetailTemp> receiveDetailTemps) {
        this.receiveDetailTemps = receiveDetailTemps;
    }

    /**
     * 获取主键id,后台生成
     *
     * @return receive_id - 主键id,后台生成
     */
    public String getReceiveId() {
        return receiveId;
    }

    /**
     * 设置主键id,后台生成
     *
     * @param receiveId 主键id,后台生成
     */
    public void setReceiveId(String receiveId) {
        this.receiveId = receiveId;
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
     * 获取ASN收货单
     *
     * @return recerive_code - ASN收货单
     */
    public String getReceiveCode() {
        return receiveCode;
    }

    /**
     * 设置ASN收货单
     *
     * @param receiveCode ASN收货单
     */
    public void setReceiveCode(String receiveCode) {
        this.receiveCode = receiveCode;
    }


    /**
     * 获取收货类型
     *
     * @return receive_type - 收货类型
     */
    public String getReceiveType() {
        return receiveType;
    }

    /**
     * 设置收货类型
     *
     * @param receiveType 收货类型
     */
    public void setReceiveType(String receiveType) {
        this.receiveType = receiveType;
    }

    /**
     * 获取单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     *
     * @return order_status - 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     *
     * @param orderStatus 单据状态 0未开始 1收货中 2 已收货 3 质检中 4 已质检 5上架中 6已上架 7已结算
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     * 获取预约时间
     *
     * @return appoint_time - 预约时间
     */
    public Date getAppointTime() {
        return appointTime;
    }

    /**
     * 设置预约时间
     *
     * @param appointTime 预约时间
     */
    public void setAppointTime(Date appointTime) {
        this.appointTime = appointTime;
    }



    /**
     * 获取采购订单号
     *
     * @return purchase_code - 采购订单号
     */
    public String getPurchaseCode() {
        return purchaseCode;
    }

    /**
     * 设置采购订单号
     *
     * @param purchaseCode 采购订单号
     */
    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    /**
     * 获取外部单号
     *
     * @return outside_code - 外部单号
     */
    public String getOutsideCode() {
        return outsideCode;
    }

    /**
     * 设置外部单号
     *
     * @param outsideCode 外部单号
     */
    public void setOutsideCode(String outsideCode) {
        this.outsideCode = outsideCode;
    }



    /**
     * 获取月台编号
     *
     * @return platform_code - 月台编号
     */
    public String getPlatformCode() {
        return platformCode;
    }

    /**
     * 设置月台编号
     *
     * @param platformCode 月台编号
     */
    public void setPlatformCode(String platformCode) {
        this.platformCode = platformCode;
    }



    /**
     * 获取车牌号
     *
     * @return car_num - 车牌号
     */
    public String getCarNum() {
        return carNum;
    }

    /**
     * 设置车牌号
     *
     * @param carNum 车牌号
     */
    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    /**
     * 获取司机
     *
     * @return driver - 司机
     */
    public String getDriver() {
        return driver;
    }

    /**
     * 设置司机
     *
     * @param driver 司机
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * 获取电话
     *
     * @return telephone - 电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置电话
     *
     * @param telephone 电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取月台类型
     *
     * @return platform_type - 月台类型
     */
    public String getPlatformType() {
        return platformType;
    }

    /**
     * 设置月台类型
     *
     * @param platformType 月台类型
     */
    public void setPlatformType(String platformType) {
        this.platformType = platformType;
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
     * 获取结算时间
     *
     * @return clear_time - 结算时间
     */
    public Date getClearTime() {
        return clearTime;
    }

    /**
     * 设置结算时间
     *
     * @param clearTime 结算时间
     */
    public void setClearTime(Date clearTime) {
        this.clearTime = clearTime;
    }

    /**
     * 获取预计体积
     *
     * @return predict_bulk - 预计体积
     */
    public Double getPredictBulk() {
        return predictBulk;
    }

    /**
     * 设置预计体积
     *
     * @param predictBulk 预计体积
     */
    public void setPredictBulk(Double predictBulk) {
        this.predictBulk = predictBulk;
    }

    /**
     * 获取预计重量
     *
     * @return predict_weight - 预计重量
     */
    public Double getPredictWeight() {
        return predictWeight;
    }

    /**
     * 设置预计重量
     *
     * @param predictWeight 预计重量
     */
    public void setPredictWeight(Double predictWeight) {
        this.predictWeight = predictWeight;
    }

    /**
     * 获取上次上架时间
     *
     * @return last_shelf_time - 上次上架时间
     */
    public Date getLastShelfTime() {
        return lastShelfTime;
    }

    /**
     * 设置上次上架时间
     *
     * @param lastShelfTime 上次上架时间
     */
    public void setLastShelfTime(Date lastShelfTime) {
        this.lastShelfTime = lastShelfTime;
    }

    /**
     * 获取上次接收时间
     *
     * @return last_receive_time - 上次接收时间
     */
    public Date getLastReceiveTime() {
        return lastReceiveTime;
    }

    /**
     * 设置上次接收时间
     *
     * @param lastReceiveTime 上次接收时间
     */
    public void setLastReceiveTime(Date lastReceiveTime) {
        this.lastReceiveTime = lastReceiveTime;
    }

    /**
     * 获取上次质检时间
     *
     * @return last_quality_time - 上次质检时间
     */
    public Date getLastQualityTime() {
        return lastQualityTime;
    }

    /**
     * 设置上次质检时间
     *
     * @param lastQualityTime 上次质检时间
     */
    public void setLastQualityTime(Date lastQualityTime) {
        this.lastQualityTime = lastQualityTime;
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

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getGooderName() {
        return gooderName;
    }

    public void setGooderName(String gooderName) {
        this.gooderName = gooderName;
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

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
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