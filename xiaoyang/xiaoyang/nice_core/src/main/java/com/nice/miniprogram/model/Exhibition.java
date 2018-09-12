package com.nice.miniprogram.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "v_exhibition")
public class Exhibition {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场地编号
     */
    @Column(name = "place_number")
    private String placeNumber;

    /**
     * 展厅编号
     */
    private String code;

    /**
     * 展厅名称
     */
    private String name;

    /**
     * 库区编码
     */
    @Column(name = "area_code")
    private String areaCode;

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
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
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
     * 获取展厅编号
     *
     * @return code - 展厅编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置展厅编号
     *
     * @param code 展厅编号
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取展厅名称
     *
     * @return name - 展厅名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置展厅名称
     *
     * @param name 展厅名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取库区编码
     *
     * @return area_code - 库区编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /**
     * 设置库区编码
     *
     * @param areaCode 库区编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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
}