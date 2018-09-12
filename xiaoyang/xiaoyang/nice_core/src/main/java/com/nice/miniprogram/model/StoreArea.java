package com.nice.miniprogram.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "i_store_area")
public class StoreArea {
    /**
     * 主键库区id,后台生成
     */
    @Id
    @Column(name = "area_id")
    private String areaId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 展厅名称
     */
    @Column(name = "place_number")
    private String placeNumber;

    /**
     * 库区编号
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 库区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 库区类型
     */
    @Column(name = "area_type")
    private String areaType;

    /**
     * 说明
     */
    private String statement;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private Date createtime;

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
     * 获取主键库区id,后台生成
     *
     * @return area_id - 主键库区id,后台生成
     */
    public String getAreaId() {
        return areaId;
    }

    /**
     * 设置主键库区id,后台生成
     *
     * @param areaId 主键库区id,后台生成
     */
    public void setAreaId(String areaId) {
        this.areaId = areaId;
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
     * 获取展厅名称
     *
     * @return place_number - 展厅名称
     */
    public String getPlaceNumber() {
        return placeNumber;
    }

    /**
     * 设置展厅名称
     *
     * @param placeNumber 展厅名称
     */
    public void setPlaceNumber(String placeNumber) {
        this.placeNumber = placeNumber;
    }

    /**
     * 获取库区编号
     *
     * @return area_code - 库区编号
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置库区编号
     *
     * @param areaCode 库区编号
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 获取库区名称
     *
     * @return area_name - 库区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置库区名称
     *
     * @param areaName 库区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取库区类型
     *
     * @return area_type - 库区类型
     */
    public String getAreaType() {
        return areaType;
    }

    /**
     * 设置库区类型
     *
     * @param areaType 库区类型
     */
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    /**
     * 获取说明
     *
     * @return statement - 说明
     */
    public String getStatement() {
        return statement;
    }

    /**
     * 设置说明
     *
     * @param statement 说明
     */
    public void setStatement(String statement) {
        this.statement = statement;
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