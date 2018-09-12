package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Table(name = "n_gooder")
public class Gooder {
    /**
     * 主键货主id,后台生成
     */
    @Id
    @Column(name = "gooder_id")
    private String gooderId;



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
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 公司
     */
    private String company;

    /**
     * 邮编
     */
    private Integer postcode;

    /**
     * 说明
     */
    private String remark;

    /**
     * 返还周期
     */
    @Column(name = "return_cycle")
    private Integer returnCycle;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;
    @Transient
    private String provinceName;

    /**
     * 市
     */
    private String city;
    @Transient
    private String cityName;

    /**
     * 区
     */
    private String district;
    @Transient
    private String districtName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系人1
     */
    @Column(name = "m_linker")
    private String mLinker;

    /**
     * 联系人1电话
     */
    @Column(name = "m_phone")
    private String mPhone;

    /**
     * 联系人1传真
     */
    @Column(name = "m_fax")
    private String mFax;

    /**
     * 联系人1手机
     */
    @Column(name = "m_telephone")
    private String mTelephone;

    /**
     * 联系人1邮箱
     */
    @Column(name = "m_mail")
    private String mMail;

    /**
     * 联系人2
     */
    @Column(name = "s_linker")
    private String sLinker;

    /**
     * 联系人2电话
     */
    @Column(name = "s_phone")
    private String sPhone;

    /**
     * 联系人2传真
     */
    @Column(name = "s_fax")
    private String sFax;

    /**
     * 联系人2手机
     */
    @Column(name = "s_telephone")
    private String sTelephone;

    /**
     * 联系人2邮箱
     */
    @Column(name = "s_mail")
    private String sMail;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifytime;


    /**
     * 货主配置对象
     */
    @Transient
    private GooderConfig gooderConfig;

    /**
     * 库区设置对象
     */
    @Transient
    private List<GooderArea> gooderAreas;


    /**
     * 物流模板配置对象
     */

    @Transient
    private List<GooderTransport> gooderTransports;

    public GooderConfig getGooderConfig() {
        return gooderConfig;
    }

    public void setGooderConfig(GooderConfig gooderConfig) {
        this.gooderConfig = gooderConfig;
    }

    public List<GooderArea> getGooderAreas() {
        return gooderAreas;
    }

    public void setGooderAreas(List<GooderArea> gooderAreas) {
        this.gooderAreas = gooderAreas;
    }

    public List<GooderTransport> getGooderTransports() {
        return gooderTransports;
    }

    public void setGooderTransports(List<GooderTransport> gooderTransports) {
        this.gooderTransports = gooderTransports;
    }

    /**
     * 获取主键货主id,后台生成
     *
     * @return gooder_id - 主键货主id,后台生成
     */
    public String getGooderId() {
        return gooderId;
    }

    /**
     * 设置主键货主id,后台生成
     *
     * @param gooderId 主键货主id,后台生成
     */
    public void setGooderId(String gooderId) {
        this.gooderId = gooderId;
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
     * 获取货主编码
     *
     * @return gooder_code - 货主编码
     */
    public String getGooderCode() {
        return gooderCode;
    }

    /**
     * 设置货主编码
     *
     * @param gooderCode 货主编码
     */
    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    /**
     * 获取公司
     *
     * @return company - 公司
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置公司
     *
     * @param company 公司
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * 获取邮编
     *
     * @return postcode - 邮编
     */
    public Integer getPostcode() {
        return postcode;
    }

    /**
     * 设置邮编
     *
     * @param postcode 邮编
     */
    public void setPostcode(Integer postcode) {
        this.postcode = postcode;
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
     * 获取返还周期
     *
     * @return return_cycle - 返还周期
     */
    public Integer getReturnCycle() {
        return returnCycle;
    }

    /**
     * 设置返还周期
     *
     * @param returnCycle 返还周期
     */
    public void setReturnCycle(Integer returnCycle) {
        this.returnCycle = returnCycle;
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
     * 获取联系人1
     *
     * @return m_linker - 联系人1
     */
    public String getmLinker() {
        return mLinker;
    }

    /**
     * 设置联系人1
     *
     * @param mLinker 联系人1
     */
    public void setmLinker(String mLinker) {
        this.mLinker = mLinker;
    }

    /**
     * 获取联系人1电话
     *
     * @return m_phone - 联系人1电话
     */
    public String getmPhone() {
        return mPhone;
    }

    /**
     * 设置联系人1电话
     *
     * @param mPhone 联系人1电话
     */
    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    /**
     * 获取联系人1传真
     *
     * @return m_fax - 联系人1传真
     */
    public String getmFax() {
        return mFax;
    }

    /**
     * 设置联系人1传真
     *
     * @param mFax 联系人1传真
     */
    public void setmFax(String mFax) {
        this.mFax = mFax;
    }

    /**
     * 获取联系人1手机
     *
     * @return m_telephone - 联系人1手机
     */
    public String getmTelephone() {
        return mTelephone;
    }

    /**
     * 设置联系人1手机
     *
     * @param mTelephone 联系人1手机
     */
    public void setmTelephone(String mTelephone) {
        this.mTelephone = mTelephone;
    }

    /**
     * 获取联系人1邮箱
     *
     * @return m_mail - 联系人1邮箱
     */
    public String getmMail() {
        return mMail;
    }

    /**
     * 设置联系人1邮箱
     *
     * @param mMail 联系人1邮箱
     */
    public void setmMail(String mMail) {
        this.mMail = mMail;
    }

    /**
     * 获取联系人2
     *
     * @return s_linker - 联系人2
     */
    public String getsLinker() {
        return sLinker;
    }

    /**
     * 设置联系人2
     *
     * @param sLinker 联系人2
     */
    public void setsLinker(String sLinker) {
        this.sLinker = sLinker;
    }

    /**
     * 获取联系人2电话
     *
     * @return s_phone - 联系人2电话
     */
    public String getsPhone() {
        return sPhone;
    }

    /**
     * 设置联系人2电话
     *
     * @param sPhone 联系人2电话
     */
    public void setsPhone(String sPhone) {
        this.sPhone = sPhone;
    }

    /**
     * 获取联系人2传真
     *
     * @return s_fax - 联系人2传真
     */
    public String getsFax() {
        return sFax;
    }

    /**
     * 设置联系人2传真
     *
     * @param sFax 联系人2传真
     */
    public void setsFax(String sFax) {
        this.sFax = sFax;
    }

    /**
     * 获取联系人2手机
     *
     * @return s_telephone - 联系人2手机
     */
    public String getsTelephone() {
        return sTelephone;
    }

    /**
     * 设置联系人2手机
     *
     * @param sTelephone 联系人2手机
     */
    public void setsTelephone(String sTelephone) {
        this.sTelephone = sTelephone;
    }

    /**
     * 获取联系人2邮箱
     *
     * @return s_mail - 联系人2邮箱
     */
    public String getsMail() {
        return sMail;
    }

    /**
     * 设置联系人2邮箱
     *
     * @param sMail 联系人2邮箱
     */
    public void setsMail(String sMail) {
        this.sMail = sMail;
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

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 设置修改时间
     *
     * @param modifytime 修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
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