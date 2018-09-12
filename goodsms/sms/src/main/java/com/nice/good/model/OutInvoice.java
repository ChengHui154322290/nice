package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "d_out_invoice")
public class OutInvoice {
    /**
     * 主键发票id,后台生成
     */
    @Id
    @Column(name = "invoice_id")
    private String invoiceId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 发票类型
     */
    @Column(name = "invoice_type")
    private String invoiceType;

    /**
     * 发票抬头
     */
    @Column(name = "invoice_head")
    private String invoiceHead;

    /**
     * 开票金额
     */
    @Column(name = "invoicel_amount")
    private Double invoicelAmount;

    /**
     * 电话
     */
    private String phone;

    /**
     * 纳税人识别号
     */
    @Column(name = "tax_code")
    private String taxCode;

    /**
     * 银行
     */
    private String bank;

    /**
     * 开户行
     */
    @Column(name = "open_bank")
    private String openBank;

    /**
     * 发票编号
     */
    @Column(name = "invoice_code")
    private String invoiceCode;

    /**
     * 备注
     */
    private String remark;

    /**
     * 是否打印
     */
    @Column(name = "is_print")
    private Integer isPrint;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 挂起 0否,1是
     */
    @Column(name = "hang_up")
    private Integer hangUp;
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
     * 基本信息表id
     */
    @Column(name = "base_id")
    private String baseId;

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
     * 修改人id
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
     * 获取主键发票id,后台生成
     *
     * @return invoice_id - 主键发票id,后台生成
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * 设置主键发票id,后台生成
     *
     * @param invoiceId 主键发票id,后台生成
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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
     * 获取发票类型
     *
     * @return invoice_type - 发票类型
     */
    public String getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型
     *
     * @param invoiceType 发票类型
     */
    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 获取发票抬头
     *
     * @return invoice_head - 发票抬头
     */
    public String getInvoiceHead() {
        return invoiceHead;
    }

    /**
     * 设置发票抬头
     *
     * @param invoiceHead 发票抬头
     */
    public void setInvoiceHead(String invoiceHead) {
        this.invoiceHead = invoiceHead;
    }

    /**
     * 获取开票金额
     *
     * @return invoicel_amount - 开票金额
     */
    public Double getInvoicelAmount() {
        return invoicelAmount;
    }

    /**
     * 设置开票金额
     *
     * @param invoicelAmount 开票金额
     */
    public void setInvoicelAmount(Double invoicelAmount) {
        this.invoicelAmount = invoicelAmount;
    }

    /**
     * 获取电话
     *
     * @return phone - 电话
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置电话
     *
     * @param phone 电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取纳税人识别号
     *
     * @return tax_code - 纳税人识别号
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * 设置纳税人识别号
     *
     * @param taxCode 纳税人识别号
     */
    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    /**
     * 获取银行
     *
     * @return bank - 银行
     */
    public String getBank() {
        return bank;
    }

    /**
     * 设置银行
     *
     * @param bank 银行
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     * 获取开户行
     *
     * @return open_bank - 开户行
     */
    public String getOpenBank() {
        return openBank;
    }

    /**
     * 设置开户行
     *
     * @param openBank 开户行
     */
    public void setOpenBank(String openBank) {
        this.openBank = openBank;
    }

    /**
     * 获取发票编号
     *
     * @return invoice_code - 发票编号
     */
    public String getInvoiceCode() {
        return invoiceCode;
    }

    /**
     * 设置发票编号
     *
     * @param invoiceCode 发票编号
     */
    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
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
     * 获取是否打印
     *
     * @return is_print - 是否打印
     */
    public Integer getIsPrint() {
        return isPrint;
    }

    /**
     * 设置是否打印
     *
     * @param isPrint 是否打印
     */
    public void setIsPrint(Integer isPrint) {
        this.isPrint = isPrint;
    }

    /**
     * 获取国家
     *
     * @return country - 国家
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置国家
     *
     * @param country 国家
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取区
     *
     * @return district - 区
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置区
     *
     * @param district 区
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address;
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
     * 获取修改人id
     *
     * @return modify_id - 修改人id
     */
    public String getModifyId() {
        return modifyId;
    }

    /**
     * 设置修改人id
     *
     * @param modifyId 修改人id
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