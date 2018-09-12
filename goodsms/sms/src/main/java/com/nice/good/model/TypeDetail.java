package com.nice.good.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "t_type_detail")
public class TypeDetail {
    /**
     * 主键id
     */
    @Id
    @Column(name = "detail_id")
    private String detailId;

    /**
     * 序号id
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 类型明细编码
     */
    @Column(name = "detail_code")
    private String detailCode;

    /**
     * 类型编码
     */
    @Column(name = "type_code")
    private String typeCode;

    /**
     * 类型明细名称
     */
    @Column(name = "detail_name")
    private String detailName;

    /**
     * 说明
     */
    private String remark;

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
     * 系统预置( 是 : 0 , 否 : 1)
     */
    private Integer preset;

    /**
     * 获取主键id
     *
     * @return detail_id - 主键id
     */
    public String getDetailId() {
        return detailId;
    }

    /**
     * 设置主键id
     *
     * @param detailId 主键id
     */
    public void setDetailId(String detailId) {
        this.detailId = detailId;
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
     * 获取类型明细编码
     *
     * @return detail_code - 类型明细编码
     */
    public String getDetailCode() {
        return detailCode;
    }

    /**
     * 设置类型明细编码
     *
     * @param detailCode 类型明细编码
     */
    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    /**
     * 获取类型编码
     *
     * @return type_code - 类型编码
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 设置类型编码
     *
     * @param typeCode 类型编码
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取类型明细名称
     *
     * @return detail_name - 类型明细名称
     */
    public String getDetailName() {
        return detailName;
    }

    /**
     * 设置类型明细名称
     *
     * @param detailName 类型明细名称
     */
    public void setDetailName(String detailName) {
        this.detailName = detailName;
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
     * 获取系统预置( 是 : 0 , 否 : 1)
     *
     * @return preset - 系统预置( 是 : 0 , 否 : 1)
     */
    public Integer getPreset() {
        return preset;
    }

    /**
     * 设置系统预置( 是 : 0 , 否 : 1)
     *
     * @param preset 系统预置( 是 : 0 , 否 : 1)
     */
    public void setPreset(Integer preset) {
        this.preset = preset;
    }
}