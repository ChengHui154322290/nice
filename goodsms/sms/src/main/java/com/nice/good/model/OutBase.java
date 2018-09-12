package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "d_out_base")
public class OutBase {
    /**
     * 主键id,后台生成
     */
    @Id
    @Column(name = "base_id")
    private String baseId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 发货编号
     */
    @Column(name = "send_code")
    private String sendCode;

    /**
     * 订单编号
     */
    @Column(name = "order_code")
    private String orderCode;


    /**
     * 供应商名称
     */
    @Transient
    private String providerName;


    /**
     * 供应商编码
     */
    @Transient
    private String providerCode;


    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    /**
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;



    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private Integer orderType;

    /**
     * 类型名称
     */
    @Column(name = "type_name")
    private String typeName;

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 优先级
     */
    private String priority;

    /**
     * 波次编号
     */
    @Column(name = "wave_code")
    private String waveCode;

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

    /**
     * 货品编码
     */
    @Transient
    private String goodCode;

    public String getGoodCode() {
        return goodCode;
    }

    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    /**
     * 单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
     */
    @Column(name = "order_status")
    private Integer orderStatus;

    /**
     * 操作员
     */
    private String operator;

    /**
     * 开票方式
     */
    @Column(name = "invoice_way")
    private String invoiceWay;

    /**
     * 承运商名称
     */
    @Transient
    private String carrierName;

    /**
     * 承运商id
     */
    @Column(name = "carrier_code")
    private String carrierCode;



    /**
     * 运单编码
     */
    @Column(name = "waybill_code")
    private String waybillCode;

    /**
     * 打印时间
     */
    @Column(name = "print_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date printTime;

    /**
     * 下单时间
     */
    @Column(name = "order_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;


    /**
     * 下单开始时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTimeStart;

    /**
     * 下单结束时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTimeEnd;

    public Date getOrderTimeStart() {
        return orderTimeStart;
    }

    public void setOrderTimeStart(Date orderTimeStart) {
        this.orderTimeStart = orderTimeStart;
    }

    public Date getOrderTimeEnd() {
        return orderTimeEnd;
    }

    public void setOrderTimeEnd(Date orderTimeEnd) {
        this.orderTimeEnd = orderTimeEnd;
    }

    /**
     * 导入时间
     */
    @Column(name = "import_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date importTime;

    /**
     * 支付时间
     */
    @Column(name = "pay_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;

    /**
     * 付款开始时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTimeStart;

    /**
     * 付款结束时间
     */
    @Transient
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTimeEnd;


    public Date getPayTimeStart() {
        return payTimeStart;
    }

    public void setPayTimeStart(Date payTimeStart) {
        this.payTimeStart = payTimeStart;
    }

    public Date getPayTimeEnd() {
        return payTimeEnd;
    }

    public void setPayTimeEnd(Date payTimeEnd) {
        this.payTimeEnd = payTimeEnd;
    }

    /**
     * 发货时间
     */
    @Column(name = "deliver_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deliverTime;

    /**
     * 挂起 0否,1是
     */
    @Column(name = "hang_up")
    private Integer hangUp;

    /**
     * 打印次数
     */
    @Column(name = "print_num")
    private Integer printNum;

    /**
     * 说明
     */
    private String statement;

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

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
     * 出库明细单对象
     */
    @Transient
    private List<OutDetail>  outDetails;

    /**
     * 收货人对象
     */
    @Transient
    private OutReceiver outReceiver;
    /**
     * 发货人对象
     */
    @Transient
    private OutSender outSender;
    /**
     * 发票对象
     */
    @Transient
    private OutInvoice outInvoice;

    public List<OutDetail> getOutDetails() {
        return outDetails;
    }

    public void setOutDetails(List<OutDetail> outDetails) {
        this.outDetails = outDetails;
    }

    public List<OutPick> getOutPicks() {
        return outPicks;
    }

    public void setOutPicks(List<OutPick> outPicks) {
        this.outPicks = outPicks;
    }

    /**

     * 拣货订单对象
     */
    @Transient
    private List<OutPick> outPicks;

    @Transient
    private List<OutTask> outTasks;


    public List<OutTask> getOutTasks() {
        return outTasks;
    }

    public void setOutTasks(List<OutTask> outTasks) {
        this.outTasks = outTasks;
    }

    public OutReceiver getOutReceiver() {
        return outReceiver;
    }

    public void setOutReceiver(OutReceiver outReceiver) {
        this.outReceiver = outReceiver;
    }

    public OutSender getOutSender() {
        return outSender;
    }

    public void setOutSender(OutSender outSender) {
        this.outSender = outSender;
    }

    public OutInvoice getOutInvoice() {
        return outInvoice;
    }

    public void setOutInvoice(OutInvoice outInvoice) {
        this.outInvoice = outInvoice;
    }




    /**
     * 获取主键id,后台生成
     *
     * @return base_id - 主键id,后台生成
     */
    public String getBaseId() {
        return baseId;
    }

    /**
     * 设置主键id,后台生成
     *
     * @param baseId 主键id,后台生成
     */
    public void setBaseId(String baseId) {
        this.baseId = baseId;
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
     * 获取发货编号
     *
     * @return send_code - 发货编号
     */
    public String getSendCode() {
        return sendCode;
    }

    /**
     * 设置发货编号
     *
     * @param sendCode 发货编号
     */
    public void setSendCode(String sendCode) {
        this.sendCode = sendCode;
    }

    /**
     * 获取订单编号
     *
     * @return order_code - 订单编号
     */
    public String getOrderCode() {
        return orderCode;
    }

    /**
     * 设置订单编号
     *
     * @param orderCode 订单编号
     */
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }


    /**
     * 获取订单类型
     *
     * @return  1正常出库,2次品出库,3换货出库
     */
    public Integer getOrderType() {
        return orderType;
    }

    /**
     * 设置订单类型
     *
     * @param orderType 订单类型
     */
    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    /**
     * 获取优先级
     *
     * @return priority - 优先级
     */
    public String getPriority() {
        return priority;
    }

    /**
     * 设置优先级
     *
     * @param priority 优先级
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * 获取波次编号
     *
     * @return wave_code - 波次编号
     */
    public String getWaveCode() {
        return waveCode;
    }

    /**
     * 设置波次编号
     *
     * @param waveCode 波次编号
     */
    public void setWaveCode(String waveCode) {
        this.waveCode = waveCode;
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
     * 获取单据状态
     *
     * @return orderStatus - 单据状态
     */
    public Integer getOrderStatus() {
        return orderStatus;
    }

    /**
     * 设置单据状态
     *
     * @param orderStatus 单据状态
     */
    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    /**
     * 获取操作员
     *
     * @return operator - 操作员
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作员
     *
     * @param operator 操作员
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取开票方式
     *
     * @return invoice_way - 开票方式
     */
    public String getInvoiceWay() {
        return invoiceWay;
    }

    /**
     * 设置开票方式
     *
     * @param invoiceWay 开票方式
     */
    public void setInvoiceWay(String invoiceWay) {
        this.invoiceWay = invoiceWay;
    }

    /**
     * 获取承运商
     *
     * @return carrier_name - 承运商
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * 设置承运商
     *
     * @param carrierName 承运商
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * 获取运单编码
     *
     * @return waybill_code - 运单编码
     */
    public String getWaybillCode() {
        return waybillCode;
    }

    /**
     * 设置运单编码
     *
     * @param waybillCode 运单编码
     */
    public void setWaybillCode(String waybillCode) {
        this.waybillCode = waybillCode;
    }

    /**
     * 获取打印时间
     *
     * @return print_time - 打印时间
     */
    public Date getPrintTime() {
        return printTime;
    }

    /**
     * 设置打印时间
     *
     * @param printTime 打印时间
     */
    public void setPrintTime(Date printTime) {
        this.printTime = printTime;
    }

    /**
     * 获取下单时间
     *
     * @return order_time - 下单时间
     */
    public Date getOrderTime() {
        return orderTime;
    }

    /**
     * 设置下单时间
     *
     * @param orderTime 下单时间
     */
    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * 获取导入时间
     *
     * @return import_time - 导入时间
     */
    public Date getImportTime() {
        return importTime;
    }

    /**
     * 设置导入时间
     *
     * @param importTime 导入时间
     */
    public void setImportTime(Date importTime) {
        this.importTime = importTime;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPayTime() {
        return payTime;
    }


    /**
     * 设置支付时间
     *
     * @param payTime 支付时间
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取发货时间
     *
     * @return deliver_time - 发货时间
     */
    public Date getDeliverTime() {
        return deliverTime;
    }

    /**
     * 设置发货时间
     *
     * @param deliverTime 发货时间
     */
    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    /**
     * 获取挂起0否,1是
     *
     * @return hang_up - 挂起 0否,1是
     */
    public Integer getHangUp() {
        return hangUp;
    }

    /**
     * 设置挂起 0否,1是
     *
     * @param hangUp 挂起 0否,1是
     */
    public void setHangUp(Integer hangUp) {
        this.hangUp = hangUp;
    }

    /**
     * 获取打印次数
     *
     * @return print_num - 打印次数
     */
    public Integer getPrintNum() {
        return printNum;
    }

    /**
     * 设置打印次数
     *
     * @param printNum 打印次数
     */
    public void setPrintNum(Integer printNum) {
        this.printNum = printNum;
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


    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
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