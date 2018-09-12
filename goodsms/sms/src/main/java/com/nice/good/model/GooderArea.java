package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "n_gooder_area")
public class GooderArea {
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
     * 区类型
     */
    @Column(name = "area_type")
    private String areaType;

    /**
     * 区名称
     */
    @Column(name = "area_name")
    private String areaName;

    /**
     * 区编码
     */
    @Column(name = "area_code")
    private String areaCode;

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 数量下限
     */
    @Column(name = "min_num")
    private Integer minNum;

    /**
     * 数量上限
     */
    @Column(name = "max_num")
    private Integer maxNum;

    /**
     * 首选区 0否,1是
     */
    @Column(name = "fist_area")
    private Integer fistArea;

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
     * 货主档案表id
     */
    @Column(name = "gooder_id")
    private String gooderId;

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
     * 获取区类型
     *
     * @return area_type - 区类型
     */
    public String getAreaType() {
        return areaType;
    }

    /**
     * 设置区类型
     *
     * @param areaType 区类型
     */
    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }

    /**
     * 获取区名称
     *
     * @return area_name - 区名称
     */
    public String getAreaName() {
        return areaName;
    }

    /**
     * 设置区名称
     *
     * @param areaName 区名称
     */
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    /**
     * 获取数量下限
     *
     * @return min_num - 数量下限
     */
    public Integer getMinNum() {
        return minNum;
    }

    /**
     * 设置数量下限
     *
     * @param minNum 数量下限
     */
    public void setMinNum(Integer minNum) {
        this.minNum = minNum;
    }

    /**
     * 获取数量上限
     *
     * @return max_num - 数量上限
     */
    public Integer getMaxNum() {
        return maxNum;
    }

    /**
     * 设置数量上限
     *
     * @param maxNum 数量上限
     */
    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    /**
     * 获取首选区 0否,1是
     *
     * @return fist_area - 首选区 0否,1是
     */
    public Integer getFistArea() {
        return fistArea;
    }

    /**
     * 设置首选区 0否,1是
     *
     * @param fistArea 首选区 0否,1是
     */
    public void setFistArea(Integer fistArea) {
        this.fistArea = fistArea;
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
     * 获取货主档案表id
     *
     * @return gooder_id - 货主档案表id
     */
    public String getGooderId() {
        return gooderId;
    }

    /**
     * 设置货主档案表id
     *
     * @param gooderId 货主档案表id
     */
    public void setGooderId(String gooderId) {
        this.gooderId = gooderId;
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