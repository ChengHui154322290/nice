package com.nice.miniprogram.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "a_sys_place")
public class SysPlace {
    /**
     * 场地ID，主键id
     */
    @Id
    @Column(name = "place_id")
    private String placeId;

    /**
     * 场地名称
     */
    @Id
    private String exhibition;

    /**
     * 数据行号
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场地编号
     */
    @Column(name = "place_number")
    private String placeNumber;

    /**
     * 场地类型
     */
    private String type;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 行政区
     */
    private String district;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 地址
     */
    private String address;

    /**
     * 说明
     */
    private String remark;

    /**
     * 修改人
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 修改时间
     */
    @Column(name = "modify_date")
    private Date modifyDate;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 获取场地ID，主键id
     *
     * @return place_id - 场地ID，主键id
     */
    public String getPlaceId() {
        return placeId;
    }

    /**
     * 设置场地ID，主键id
     *
     * @param placeId 场地ID，主键id
     */
    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    /**
     * 获取场地名称
     *
     * @return exhibition - 场地名称
     */
    public String getExhibition() {
        return exhibition;
    }

    /**
     * 设置场地名称
     *
     * @param exhibition 场地名称
     */
    public void setExhibition(String exhibition) {
        this.exhibition = exhibition;
    }

    /**
     * 获取数据行号
     *
     * @return id - 数据行号
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置数据行号
     *
     * @param id 数据行号
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取场地编号
     *
     * @return place_number - 场地编号
     */
    public String getPlaceNumber() {
        return placeNumber;
    }

    /**
     * 设置场地编号
     *
     * @param placeNumber 场地编号
     */
    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    /**
     * 获取场地类型
     *
     * @return type - 场地类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置场地类型
     *
     * @param type 场地类型
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取省份
     *
     * @return province - 省份
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置省份
     *
     * @param province 省份
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
     * 获取行政区
     *
     * @return district - 行政区
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置行政区
     *
     * @param district 行政区
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * 获取邮编
     *
     * @return postcode - 邮编
     */
    public String getPostcode() {
        return postcode;
    }

    /**
     * 设置邮编
     *
     * @param postcode 邮编
     */
    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    /**
     * 获取地址
     *
     * @return address - 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置地址
     *
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取说明
     *
     * @return remark - 说明
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置说明
     *
     * @param remark 说明
     */
    public void setRemark(String remark) {
        this.remark = remark;
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