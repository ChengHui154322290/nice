package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "p_pack_manage")
public class PackManage {
    /**
     * 主键包装id,后台生成
     */
    @Id
    @Column(name = "pack_id")
    private String packId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 包装代码
     */
    @Column(name = "pack_code")
    private String packCode;

    /**
     * 包装名称
     */
    @Column(name = "pack_name")
    private String packName;

    /**
     * 是否启用
     */

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 主单位数量
     */
    @Column(name = "main_unite_num")
    private Integer mainUniteNum;

    /**
     * 主单位单位
     */
    @Column(name = "main_unite_nite")
    private String mainUniteNite;

    /**
     * 内包装数量
     */
    @Column(name = "inner_pack_num")
    private Integer innerPackNum;

    /**
     * 内包装单位
     */
    @Column(name = "inner_pack_unite")
    private String innerPackUnite;

    /**
     * 箱子数量
     */
    @Column(name = "case_num")
    private Integer caseNum;

    /**
     * 箱子单位
     */
    @Column(name = "case_unite")
    private String caseUnite;

    /**
     * 托盘数量
     */
    @Column(name = "tray_num")
    private Integer trayNum;

    /**
     * 托盘单位
     */
    @Column(name = "tray_unite")
    private String trayUnite;

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
     * 获取主键包装id,后台生成
     *
     * @return pack_id - 主键包装id,后台生成
     */
    public String getPackId() {
        return packId;
    }

    /**
     * 设置主键包装id,后台生成
     *
     * @param packId 主键包装id,后台生成
     */
    public void setPackId(String packId) {
        this.packId = packId;
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
     * 获取包装代码
     *
     * @return pack_code - 包装代码
     */
    public String getPackCode() {
        return packCode;
    }

    /**
     * 设置包装代码
     *
     * @param packCode 包装代码
     */
    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    /**
     * 获取包装名称
     *
     * @return pack_name - 包装名称
     */
    public String getPackName() {
        return packName;
    }

    /**
     * 设置包装名称
     *
     * @param packName 包装名称
     */
    public void setPackName(String packName) {
        this.packName = packName;
    }

    /**
     * 获取主单位数量
     *
     * @return main_unite_num - 主单位数量
     */
    public Integer getMainUniteNum() {
        return mainUniteNum;
    }

    /**
     * 设置主单位数量
     *
     * @param mainUniteNum 主单位数量
     */
    public void setMainUniteNum(Integer mainUniteNum) {
        this.mainUniteNum = mainUniteNum;
    }

    /**
     * 获取主单位单位
     *
     * @return main_unite_nite - 主单位单位
     */
    public String getMainUniteNite() {
        return mainUniteNite;
    }

    /**
     * 设置主单位单位
     *
     * @param mainUniteNite 主单位单位
     */
    public void setMainUniteNite(String mainUniteNite) {
        this.mainUniteNite = mainUniteNite;
    }

    /**
     * 获取内包装数量
     *
     * @return inner_pack_num - 内包装数量
     */
    public Integer getInnerPackNum() {
        return innerPackNum;
    }

    /**
     * 设置内包装数量
     *
     * @param innerPackNum 内包装数量
     */
    public void setInnerPackNum(Integer innerPackNum) {
        this.innerPackNum = innerPackNum;
    }

    /**
     * 获取内包装单位
     *
     * @return inner_pack_unite - 内包装单位
     */
    public String getInnerPackUnite() {
        return innerPackUnite;
    }

    /**
     * 设置内包装单位
     *
     * @param innerPackUnite 内包装单位
     */
    public void setInnerPackUnite(String innerPackUnite) {
        this.innerPackUnite = innerPackUnite;
    }

    /**
     * 获取箱子数量
     *
     * @return case_num - 箱子数量
     */
    public Integer getCaseNum() {
        return caseNum;
    }

    /**
     * 设置箱子数量
     *
     * @param caseNum 箱子数量
     */
    public void setCaseNum(Integer caseNum) {
        this.caseNum = caseNum;
    }

    /**
     * 获取箱子单位
     *
     * @return case_unite - 箱子单位
     */
    public String getCaseUnite() {
        return caseUnite;
    }

    /**
     * 设置箱子单位
     *
     * @param caseUnite 箱子单位
     */
    public void setCaseUnite(String caseUnite) {
        this.caseUnite = caseUnite;
    }

    /**
     * 获取托盘数量
     *
     * @return tray_num - 托盘数量
     */
    public Integer getTrayNum() {
        return trayNum;
    }

    /**
     * 设置托盘数量
     *
     * @param trayNum 托盘数量
     */
    public void setTrayNum(Integer trayNum) {
        this.trayNum = trayNum;
    }

    /**
     * 获取托盘单位
     *
     * @return tray_unite - 托盘单位
     */
    public String getTrayUnite() {
        return trayUnite;
    }

    /**
     * 设置托盘单位
     *
     * @param trayUnite 托盘单位
     */
    public void setTrayUnite(String trayUnite) {
        this.trayUnite = trayUnite;
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