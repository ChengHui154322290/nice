package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "b_measure")
public class Measure {
    /**
     * 主键id 后台生成
     */
    @Id
    @Column(name = "measure_id")
    private String measureId;

    /**
     * 序号id 默认自增
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 计量单位组编号
     */
    @Column(name = "m_group_code")
    private String mGroupCode;

    /**
     * 计量单位组名称
     */
    @Column(name = "m_group_name")
    private String mGroupName;

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
     * 获取主键id 后台生成
     *
     * @return measure_id - 主键id 后台生成
     */
    public String getMeasureId() {
        return measureId;
    }

    /**
     * 设置主键id 后台生成
     *
     * @param measureId 主键id 后台生成
     */
    public void setMeasureId(String measureId) {
        this.measureId = measureId;
    }

    /**
     * 获取序号id 默认自增
     *
     * @return id - 序号id 默认自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号id 默认自增
     *
     * @param id 序号id 默认自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取计量单位组编号
     *
     * @return m_group_code - 计量单位组编号
     */
    public String getmGroupCode() {
        return mGroupCode;
    }

    /**
     * 设置计量单位组编号
     *
     * @param mGroupCode 计量单位组编号
     */
    public void setmGroupCode(String mGroupCode) {
        this.mGroupCode = mGroupCode;
    }

    /**
     * 获取计量单位组名称
     *
     * @return m_group_name - 计量单位组名称
     */
    public String getmGroupName() {
        return mGroupName;
    }

    /**
     * 设置计量单位组名称
     *
     * @param mGroupName 计量单位组名称
     */
    public void setmGroupName(String mGroupName) {
        this.mGroupName = mGroupName;
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