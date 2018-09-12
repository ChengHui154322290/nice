package com.nice.good.model;

import javax.persistence.*;

@Table(name = "z_module_region")
public class ModuleRegion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 中文简称
     */
    @Column(name = "abbr_cn_name")
    private String abbrCnName;

    /**
     * 中文名称
     */
    @Column(name = "cn_name")
    private String cnName;

    /**
     * 区号/代码
     */
    private String code;

    /**
     * 英文名称
     */
    @Column(name = "en_name")
    private String enName;

    /**
     * 中文名称简称
     */
    @Column(name = "first_spell")
    private String firstSpell;

    /**
     * 中文名称全称
     */
    @Column(name = "full_spell")
    private String fullSpell;

    /**
     * 上级地区标识
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 地区类型
     */
    private String type;

    /**
     * 邮政编码
     */
    private String zipcode;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取中文简称
     *
     * @return abbr_cn_name - 中文简称
     */
    public String getAbbrCnName() {
        return abbrCnName;
    }

    /**
     * 设置中文简称
     *
     * @param abbrCnName 中文简称
     */
    public void setAbbrCnName(String abbrCnName) {
        this.abbrCnName = abbrCnName;
    }

    /**
     * 获取中文名称
     *
     * @return cn_name - 中文名称
     */
    public String getCnName() {
        return cnName;
    }

    /**
     * 设置中文名称
     *
     * @param cnName 中文名称
     */
    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    /**
     * 获取区号/代码
     *
     * @return code - 区号/代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置区号/代码
     *
     * @param code 区号/代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取英文名称
     *
     * @return en_name - 英文名称
     */
    public String getEnName() {
        return enName;
    }

    /**
     * 设置英文名称
     *
     * @param enName 英文名称
     */
    public void setEnName(String enName) {
        this.enName = enName;
    }

    /**
     * 获取中文名称简称
     *
     * @return first_spell - 中文名称简称
     */
    public String getFirstSpell() {
        return firstSpell;
    }

    /**
     * 设置中文名称简称
     *
     * @param firstSpell 中文名称简称
     */
    public void setFirstSpell(String firstSpell) {
        this.firstSpell = firstSpell;
    }

    /**
     * 获取中文名称全称
     *
     * @return full_spell - 中文名称全称
     */
    public String getFullSpell() {
        return fullSpell;
    }

    /**
     * 设置中文名称全称
     *
     * @param fullSpell 中文名称全称
     */
    public void setFullSpell(String fullSpell) {
        this.fullSpell = fullSpell;
    }

    /**
     * 获取上级地区标识
     *
     * @return parent_id - 上级地区标识
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 设置上级地区标识
     *
     * @param parentId 上级地区标识
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取地区类型
     *
     * @return type - 地区类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置地区类型
     *
     * @param type 地区类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取邮政编码
     *
     * @return zipcode - 邮政编码
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * 设置邮政编码
     *
     * @param zipcode 邮政编码
     */
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    /*===================================企业id=====================================*/
//    /**
//     * 企业id
//     */
//    @Column(name = "company_id")
//    private String companyId;
//
//
//    public String getCompanyId() {
//        return companyId;
//    }
//
//    public void setCompanyId(String companyId) {
//        this.companyId = companyId;
//    }
}