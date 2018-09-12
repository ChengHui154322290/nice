package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "g_good_alias")
public class GoodAlias {

    /**
     * 主键别名id,后台生成
     */
    @Id
    @Column(name = "alias_id")
    private String aliasId;

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
     * 货品编码
     */
    @Column(name = "good_code")
    private String goodCode;

    /**
     * 货品别名编码
     */
    @Column(name = "alias_code")
    private String aliasCode;

    /**
     * 别名类型
     */
    @Column(name = "alias_type")
    private String aliasType;

    /**
     * 备注
     */
    private String remark;

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

    @Column(name = "good_id")
    private String goodId;

    /**
     * @return alias_id
     */
    public String getAliasId() {
        return aliasId;
    }

    /**
     * @param aliasId
     */
    public void setAliasId(String aliasId) {
        this.aliasId = aliasId;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    public String getGooderCode() {
        return gooderCode;
    }

    public void setGooderCode(String gooderCode) {
        this.gooderCode = gooderCode;
    }

    /**
     * 获取货主编码
     *
     * @return good_code - 货主编码
     */
    public String getGoodCode() {
        return goodCode;
    }

    /**
     * 设置货主编码
     *
     * @param goodCode 货主编码
     */
    public void setGoodCode(String goodCode) {
        this.goodCode = goodCode;
    }

    /**
     * 获取货品别名编码
     *
     * @return alias_code - 货品别名编码
     */
    public String getAliasCode() {
        return aliasCode;
    }

    /**
     * 设置货品别名编码
     *
     * @param aliasCode 货品别名编码
     */
    public void setAliasCode(String aliasCode) {
        this.aliasCode = aliasCode;
    }

    /**
     * 获取别名类型
     *
     * @return alias_type - 别名类型
     */
    public String getAliasType() {
        return aliasType;
    }

    /**
     * 设置别名类型
     *
     * @param aliasType 别名类型
     */
    public void setAliasType(String aliasType) {
        this.aliasType = aliasType;
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
     * @return good_id
     */
    public String getGoodId() {
        return goodId;
    }

    /**
     * @param goodId
     */
    public void setGoodId(String goodId) {
        this.goodId = goodId;
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