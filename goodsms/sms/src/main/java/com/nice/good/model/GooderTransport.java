package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "n_gooder_transport")
public class GooderTransport {
    /**
     * 主键物流模板id,后台生成
     */
    @Id
    @Column(name = "trans_id")
    private String transId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 承运商编号
     */
    @Column(name = "carrier_code")
    private String carrierCode;

    /**
     * 承运商名称
     */
    @Column(name = "carrier_name")
    private String carrierName;

    /**
     * 承运商id
     */
    @Column(name = "carrier_id")
    private String carrierId;

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    /**
     * 普通打印
     */
    @Column(name = "ord_print")
    private String ordPrint;

    /**
     * 热敏打印
     */
    @Column(name = "hot_print")
    private String hotPrint;

    /**
     * 热敏账号
     */
    @Column(name = "hot_account")
    private String hotAccount;

    /**
     * 热敏编码
     */
    @Column(name = "hot_code")
    private String hotCode;

    /**
     * 热敏授权码1
     */
    @Column(name = "autcode_one")
    private String autcodeOne;

    /**
     * 热敏授权码2
     */
    @Column(name = "autcode_two")
    private String autcodeTwo;

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
     * 货主id
     */
    @Column(name = "gooder_id")
    private String gooderId;

    /**
     * 获取主键物流模板id,后台生成
     *
     * @return trans_id - 主键物流模板id,后台生成
     */
    public String getTransId() {
        return transId;
    }

    /**
     * 设置主键物流模板id,后台生成
     *
     * @param transId 主键物流模板id,后台生成
     */
    public void setTransId(String transId) {
        this.transId = transId;
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
     * 获取承运商编号
     *
     * @return carrier_code - 承运商编号
     */
    public String getCarrierCode() {
        return carrierCode;
    }

    /**
     * 设置承运商编号
     *
     * @param carrierCode 承运商编号
     */
    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    /**
     * 获取承运商名称
     *
     * @return carrier_name - 承运商名称
     */
    public String getCarrierName() {
        return carrierName;
    }

    /**
     * 设置承运商名称
     *
     * @param carrierName 承运商名称
     */
    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    /**
     * 获取普通打印
     *
     * @return ord_print - 普通打印
     */
    public String getOrdPrint() {
        return ordPrint;
    }

    /**
     * 设置普通打印
     *
     * @param ordPrint 普通打印
     */
    public void setOrdPrint(String ordPrint) {
        this.ordPrint = ordPrint;
    }

    /**
     * 获取热敏打印
     *
     * @return hot_print - 热敏打印
     */
    public String getHotPrint() {
        return hotPrint;
    }

    /**
     * 设置热敏打印
     *
     * @param hotPrint 热敏打印
     */
    public void setHotPrint(String hotPrint) {
        this.hotPrint = hotPrint;
    }

    /**
     * 获取热敏账号
     *
     * @return hot_account - 热敏账号
     */
    public String getHotAccount() {
        return hotAccount;
    }

    /**
     * 设置热敏账号
     *
     * @param hotAccount 热敏账号
     */
    public void setHotAccount(String hotAccount) {
        this.hotAccount = hotAccount;
    }

    /**
     * 获取热敏编码
     *
     * @return hot_code - 热敏编码
     */
    public String getHotCode() {
        return hotCode;
    }

    /**
     * 设置热敏编码
     *
     * @param hotCode 热敏编码
     */
    public void setHotCode(String hotCode) {
        this.hotCode = hotCode;
    }

    /**
     * 获取热敏授权码1
     *
     * @return autcode_one - 热敏授权码1
     */
    public String getAutcodeOne() {
        return autcodeOne;
    }

    /**
     * 设置热敏授权码1
     *
     * @param autcodeOne 热敏授权码1
     */
    public void setAutcodeOne(String autcodeOne) {
        this.autcodeOne = autcodeOne;
    }

    /**
     * 获取热敏授权码2
     *
     * @return autcode_two - 热敏授权码2
     */
    public String getAutcodeTwo() {
        return autcodeTwo;
    }

    /**
     * 设置热敏授权码2
     *
     * @param autcodeTwo 热敏授权码2
     */
    public void setAutcodeTwo(String autcodeTwo) {
        this.autcodeTwo = autcodeTwo;
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

    /**
     * 设置修改时间
     *
     * @param modifytime 修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * 获货主id
     *
     * @return gooder_id - 货主表id
     */
    public String getGooderId() {
        return gooderId;
    }

    /**
     * 设置货主表id
     *
     * @param gooderId 货主表id
     */
    public void setGooderId(String gooderId) {
        this.gooderId = gooderId;
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