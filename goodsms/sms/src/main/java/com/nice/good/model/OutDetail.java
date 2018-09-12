package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "d_out_detail")
public class OutDetail {
    /**
     * 主键id,后台系统生成
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
     * 行号生成
     */
    @Column(name = "line_code")
    private String lineCode;


    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 货品编码
     */
    @Column(name = "good_code")
    private String goodCode;

    /**
     * 供应商编码
     */
    @Column(name = "provider_code")
    private String providerCode;


    /**
     * 组织机构编码
     */
    @Column(name = "org_code")
    private String orgCode;

    /**
     * 货品名称
     */
    @Column(name = "good_name")
    private String goodName;

    /**
     * 包装编码
     */
    @Column(name = "pack_code")
    private String packCode;


    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    /**
     * 单位
     */
    private String unite;

    /**
     *  单据状态 0未开始,1部分分配,2已分配,3部分拣货,4已拣货,5部分验货,6已验货,7部分打包,8已打包,9部分称重,10已称重,11部分发货,12已发货,13已取消
     */
    private Integer status;

    /**
     * 订单量
     */
    @Column(name = "order_num")
    private Integer orderNum;

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
     * 发送量
     */
    @Column(name = "send_num")
    private Integer sendNum;

    /**
     * 剩余量
     */
    @Column(name = "surplus_num")
    private Integer surplusNum;

    /**
     * 原因
     */
    private String reason;

    /**
     * 快递单号
     */
    @Column(name = "express_code")
    private String expressCode;

    /**
     * 挂起  0否,1是
     */
    @Column(name = "hang_up")
    private Integer hangUp;

    /**
     * 基本信息表id
     */
    @Column(name = "base_id")
    private String baseId;

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
     * 获取主键id,后台系统生成
     *
     * @return detail_id - 主键id,后台系统生成
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置主键id,后台系统生成
     *
     * @param detailId 主键id,后台系统生成
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
     * @return gooder_code- 货主code
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
     * 获取货品编码
     *
     * @return good_name - 货品编码
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * 设置货品编码
     *
     * @param goodName 货品编码
     */
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }


    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * 获取单位
     *
     * @return unite - 单位
     */
    public String getUnite() {
        return unite;
    }

    /**
     * 设置单位
     *
     * @param unite 单位
     */
    public void setUnite(String unite) {
        this.unite = unite;
    }

    /**
     * 获取状态 0未开始,1部分分配,2已分配,3部分拣货,4拣货完成,5部分发货,6已发货
     *
     * @return status - 状态 0未开始,1部分分配,2已分配,3部分拣货,4拣货完成,5部分发货,6已发货
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0未开始,1部分分配,2已分配,3部分拣货,4拣货完成,5部分发货,6已发货
     *
     * @param status 状态 0未开始,1部分分配,2已分配,3部分拣货,4拣货完成,5部分发货,6已发货
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取订单量
     *
     * @return order_num - 订单量
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 设置订单量
     *
     * @param orderNum 订单量
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
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
     * 获取发送量
     *
     * @return send_num - 发送量
     */
    public Integer getSendNum() {
        return sendNum;
    }

    /**
     * 设置发送量
     *
     * @param sendNum 发送量
     */
    public void setSendNum(Integer sendNum) {
        this.sendNum = sendNum;
    }

    /**
     * 获取剩余量
     *
     * @return surplus_num - 剩余量
     */
    public Integer getSurplusNum() {
        return surplusNum;
    }

    /**
     * 设置剩余量
     *
     * @param surplusNum 剩余量
     */
    public void setSurplusNum(Integer surplusNum) {
        this.surplusNum = surplusNum;
    }

    /**
     * 获取原因
     *
     * @return reason - 原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置原因
     *
     * @param reason 原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取快递单号
     *
     * @return express_code - 快递单号
     */
    public String getExpressCode() {
        return expressCode;
    }

    /**
     * 设置快递单号
     *
     * @param expressCode 快递单号
     */
    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    /**
     * 获取挂起  0否,1是
     *
     * @return hang_up - 挂起  0否,1是
     */
    public Integer getHangUp() {
        return hangUp;
    }

    /**
     * 设置挂起  0否,1是
     *
     * @param hangUp 挂起  0否,1是
     */
    public void setHangUp(Integer hangUp) {
        this.hangUp = hangUp;
    }

    /**
     * 获取基本信息表id
     *
     * @return base_id - 基本信息表id
     */
    public String getBaseId() {
        return baseId;
    }

    /**
     * 设置基本信息表id
     *
     * @param baseId 基本信息表id
     */
    public void setBaseId(String baseId) {
        this.baseId = baseId;
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