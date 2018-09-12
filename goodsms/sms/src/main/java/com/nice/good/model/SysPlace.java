package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

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
     * 场地名称
     */
    @Column(name = "exhibition")
    private String exhibition;

    /**
     * 场地类型
     */
    private String type;

    /**
     * 修改人
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 修改时间
     */
    @Column(name = "modify_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDate;

    /**
     * 说明
     */
    private String remark;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date createDate;

    /**
     * 邮编
     */
    private String postcode;

    /**
     * 地址
     */
    private String address;

    // 添加字段：国家、省、市、区
    /**
     * 国家
     */
    @Column(name = "country")
    private String country;

    /**
     * 省
     */
    @Column(name = "province")
    private String province;
    @Transient
    private String provinceName;

    /**
     * 市
     */
    @Column(name = "city")
    private String city;
    @Transient
    private String cityName;

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * 区
     */
    @Column(name = "district")

    private String district;

    public String getProvinceName() {
        return provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    @Transient
    private String districtName;


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
     * 获取国家
     * @return country
     */
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 获取省份
     * @return province
     */
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 获取“市”
     * @return city
     */
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 获取“区域”
     * @return
     */
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    //无参构造函数
    public SysPlace() {
    }

    //全参构造函数
    public SysPlace(String placeId, String placeNumber, String exhibition, String type,
                    String modifyId, Date modifyDate, String remark, String createId,
                    Date createDate, String postcode, String address, String country,
                    String province, String city, String district) {
        this.placeId = placeId;
        this.placeNumber = placeNumber;
        this.exhibition = exhibition;
        this.type = type;
        this.modifyId = modifyId;
        this.modifyDate = modifyDate;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
        this.postcode = postcode;
        this.address = address;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
    }

    public SysPlace(String placeId, Integer id, String placeNumber, String exhibition,
                    String type, String country, String province, String city, String district,
                    String postcode, String address, String remark, String createId, Date createDate) {
        this.placeId = placeId;
        this.id = id;
        this.placeNumber = placeNumber;
        this.exhibition = exhibition;
        this.type = type;
        this.country = country;
        this.province = province;
        this.city = city;
        this.district = district;
        this.postcode = postcode;
        this.address = address;
        this.remark = remark;
        this.createId = createId;
        this.createDate = createDate;
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
     * 冗余字段，0表示新增，1表示修改
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