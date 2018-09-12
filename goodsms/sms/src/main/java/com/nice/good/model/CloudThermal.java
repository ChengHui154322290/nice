package com.nice.good.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "c_cloud_thermal")
public class CloudThermal {
    /**
     * 主键id
     */
    @Id
    @Column(name = "cloud_id")
    private String cloudId;

    /**
     * 序号id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 热敏编码
     */
    @Column(name = "thermal_code")
    private String thermalCode;

    /**
     * 承运商编码
     */
    @Column(name = "carrier_code")
    private String carrierCode;

    /**
     * 货主编码
     */
    @Column(name = "gooder_code")
    private String gooderCode;

    /**
     * 展厅编码
     */
    @Column(name = "place_number")
    private String placeNumber;

    /**
     * 是否淘宝云栈 0否,1是
     */
    @Column(name = "taobao_status")
    private Integer taobaoStatus;

    /**
     * 打印用户名
     */
    @Column(name = "print_user")
    private String printUser;

    /**
     * 第 三方账号
     */
    @Column(name = "third_account")
    private String thirdAccount;

    /**
     * 打印密码
     */
    @Column(name = "third_password")
    private String thirdPassword;

    /**
     * 授权码2
     */
    @Column(name = "authorization_code2")
    private String authorizationCode2;

    /**
     * 授权码3
     */
    @Column(name = "authorization_code3")
    private String authorizationCode3;

    /**
     * 网店名称
     */
    @Column(name = "shop_name")
    private String shopName;

    /**
     * 快件种类
     */
    @Column(name = "parcel_kind")
    private String parcelKind;

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
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifytime;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 获取主键id
     *
     * @return cloud_id - 主键id
     */
    public String getCloudId() {
        return cloudId;
    }

    /**
     * 设置主键id
     *
     * @param cloudId 主键id
     */
    public void setCloudId(String cloudId) {
        this.cloudId = cloudId;
    }

    /**
     * 获取序号id
     *
     * @return id - 序号id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号id
     *
     * @param id 序号id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取热敏编码
     *
     * @return thermal_code - 热敏编码
     */
    public String getThermalCode() {
        return thermalCode;
    }

    /**
     * 设置热敏编码
     *
     * @param thermalCode 热敏编码
     */
    public void setThermalCode(String thermalCode) {
        this.thermalCode = thermalCode;
    }

    /**
     * 获取承运商编码
     *
     * @return carrier_code - 承运商编码
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * 设置承运商编码
     *
     * @param carrierCode 承运商编码
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
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
     * 获取展厅编码
     *
     * @return place_number - 展厅编码
     */
    public String getPlaceNumber() {
        return placeNumber;
    }

    /**
     * 设置展厅编码
     *
     * @param placeNumber 展厅编码
     */
    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    /**
     * 获取是否淘宝云栈 0否,1是
     *
     * @return taobao_status - 是否淘宝云栈 0否,1是
     */
    public Integer getTaobaoStatus() {
        return taobaoStatus;
    }

    /**
     * 设置是否淘宝云栈 0否,1是
     *
     * @param taobaoStatus 是否淘宝云栈 0否,1是
     */
    public void setTaobaoStatus(Integer taobaoStatus) {
        this.taobaoStatus = taobaoStatus;
    }

    /**
     * 获取打印用户名
     *
     * @return print_user - 打印用户名
     */
    public String getPrintUser() {
        return printUser;
    }

    /**
     * 设置打印用户名
     *
     * @param printUser 打印用户名
     */
    public void setPrintUser(String printUser) {
        this.printUser = printUser;
    }

    /**
     * 获取第 三方账号
     *
     * @return third_account - 第 三方账号
     */
    public String getThirdAccount() {
        return thirdAccount;
    }

    /**
     * 设置第 三方账号
     *
     * @param thirdAccount 第 三方账号
     */
    public void setThirdAccount(String thirdAccount) {
        this.thirdAccount = thirdAccount;
    }

    /**
     * 获取打印密码
     *
     * @return third_password - 打印密码
     */
    public String getThirdPassword() {
        return thirdPassword;
    }

    /**
     * 设置打印密码
     *
     * @param thirdPassword 打印密码
     */
    public void setThirdPassword(String thirdPassword) {
        this.thirdPassword = thirdPassword;
    }

    /**
     * 获取授权码2
     *
     * @return authorization_code2 - 授权码2
     */
    public String getAuthorizationCode2() {
        return authorizationCode2;
    }

    /**
     * 设置授权码2
     *
     * @param authorizationCode2 授权码2
     */
    public void setAuthorizationCode2(String authorizationCode2) {
        this.authorizationCode2 = authorizationCode2;
    }

    /**
     * 获取授权码3
     *
     * @return authorization_code3 - 授权码3
     */
    public String getAuthorizationCode3() {
        return authorizationCode3;
    }

    /**
     * 设置授权码3
     *
     * @param authorizationCode3 授权码3
     */
    public void setAuthorizationCode3(String authorizationCode3) {
        this.authorizationCode3 = authorizationCode3;
    }

    /**
     * 获取网店名称
     *
     * @return shop_name - 网店名称
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 设置网店名称
     *
     * @param shopName 网店名称
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 获取快件种类
     *
     * @return parcel_kind - 快件种类
     */
    public String getParcelKind() {
        return parcelKind;
    }

    /**
     * 设置快件种类
     *
     * @param parcelKind 快件种类
     */
    public void setParcelKind(String parcelKind) {
        this.parcelKind = parcelKind;
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

    /**
     * 设置修改时间
     *
     * @param modifytime 修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
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