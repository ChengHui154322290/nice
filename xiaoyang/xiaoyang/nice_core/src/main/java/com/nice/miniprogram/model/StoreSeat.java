package com.nice.miniprogram.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "i_store_seat")
public class StoreSeat {
    /**
     * 主键库位id,后台生成
     */
    @Id
    @Column(name = "seat_id")
    private String seatId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 场地编号
     */
    @Column(name = "place_number")
    private String placeNumber;

    /**
     * 库区编码
     */
    @Column(name = "area_code")
    private String areaCode;

    /**
     * 库位名称
     */
    @Column(name = "seat_name")
    private String seatName;

    /**
     * 库位编号
     */
    @Column(name = "seat_code")
    private String seatCode;

    /**
     * 混放货品 0 否,1是
     */
    @Column(name = "mix_good")
    private Integer mixGood;

    /**
     * 混放批次 0否,1是
     */
    @Column(name = "mix_batch")
    private Integer mixBatch;

    /**
     * 库位类型
     */
    @Column(name = "seat_type")
    private String seatType;

    /**
     * 层级
     */
    private String level;

    /**
     * 库位标记
     */
    @Column(name = "seat_tag")
    private String seatTag;

    /**
     * 库位状态 0未使用,1使用中
     */
    @Column(name = "seat_status")
    private Integer seatStatus;

    /**
     * 库位容量
     */
    @Column(name = "seat_capacity")
    private Integer seatCapacity;

    /**
     * 说明
     */
    private String statement;

    /**
     * 长
     */
    private Double length;

    /**
     * 宽
     */
    private Double width;

    /**
     * 高
     */
    private Double height;

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
     * 获取主键库位id,后台生成
     *
     * @return seat_id - 主键库位id,后台生成
     */
    public String getSeatId() {
        return seatId;
    }

    /**
     * 设置主键库位id,后台生成
     *
     * @param seatId 主键库位id,后台生成
     */
    public void setSeatId(String seatId) {
        this.seatId = seatId;
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
     * 获取库位名称
     *
     * @return seat_name - 库位名称
     */
    public String getSeatName() {
        return seatName;
    }

    /**
     * 设置库位名称
     *
     * @param seatName 库位名称
     */
    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }

    /**
     * 获取库位编号
     *
     * @return seat_code - 库位编号
     */
    public String getSeatCode() {
        return seatCode;
    }

    /**
     * 设置库位编号
     *
     * @param seatCode 库位编号
     */
    public void setSeatCode(String seatCode) {
        this.seatCode = seatCode;
    }

    /**
     * 获取混放货品 0 否,1是
     *
     * @return mix_good - 混放货品 0 否,1是
     */
    public Integer getMixGood() {
        return mixGood;
    }

    /**
     * 设置混放货品 0 否,1是
     *
     * @param mixGood 混放货品 0 否,1是
     */
    public void setMixGood(Integer mixGood) {
        this.mixGood = mixGood;
    }

    /**
     * 获取混放批次 0否,1是
     *
     * @return mix_batch - 混放批次 0否,1是
     */
    public Integer getMixBatch() {
        return mixBatch;
    }

    /**
     * 设置混放批次 0否,1是
     *
     * @param mixBatch 混放批次 0否,1是
     */
    public void setMixBatch(Integer mixBatch) {
        this.mixBatch = mixBatch;
    }

    /**
     * 获取库位类型
     *
     * @return seat_type - 库位类型
     */
    public String getSeatType() {
        return seatType;
    }

    /**
     * 设置库位类型
     *
     * @param seatType 库位类型
     */
    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    /**
     * 获取层级
     *
     * @return level - 层级
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置层级
     *
     * @param level 层级
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取库位标记
     *
     * @return seat_tag - 库位标记
     */
    public String getSeatTag() {
        return seatTag;
    }

    /**
     * 设置库位标记
     *
     * @param seatTag 库位标记
     */
    public void setSeatTag(String seatTag) {
        this.seatTag = seatTag;
    }

    /**
     * 获取库位状态 0未使用,1使用中
     *
     * @return seat_status - 库位状态 0未使用,1使用中
     */
    public Integer getSeatStatus() {
        return seatStatus;
    }

    /**
     * 设置库位状态 0未使用,1使用中
     *
     * @param seatStatus 库位状态 0未使用,1使用中
     */
    public void setSeatStatus(Integer seatStatus) {
        this.seatStatus = seatStatus;
    }

    /**
     * 获取库位容量
     *
     * @return seat_capacity - 库位容量
     */
    public Integer getSeatCapacity() {
        return seatCapacity;
    }

    /**
     * 设置库位容量
     *
     * @param seatCapacity 库位容量
     */
    public void setSeatCapacity(Integer seatCapacity) {
        this.seatCapacity = seatCapacity;
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
     * 获取长
     *
     * @return length - 长
     */
    public Double getLength() {
        return length;
    }

    /**
     * 设置长
     *
     * @param length 长
     */
    public void setLength(Double length) {
        this.length = length;
    }

    /**
     * 获取宽
     *
     * @return width - 宽
     */
    public Double getWidth() {
        return width;
    }

    /**
     * 设置宽
     *
     * @param width 宽
     */
    public void setWidth(Double width) {
        this.width = width;
    }

    /**
     * 获取高
     *
     * @return height - 高
     */
    public Double getHeight() {
        return height;
    }

    /**
     * 设置高
     *
     * @param height 高
     */
    public void setHeight(Double height) {
        this.height = height;
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