package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "n_gooder_config")
public class  GooderConfig {
    /**
     * 主键货主配置id,后台生成
     */
    @Id
    @Column(name = "config_id")
    private String configId;

    /**
     * 序号id,自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 无PO收获
     */
    @Column(name = "no_po_receive")
    private String noPoReceive;

    /**
     * 超量验证 0否,1是
     */
    @Column(name = "beyond_verify")
    private Integer beyondVerify;

    /**
     * 超量比例
     */
    @Column(name = "beyond_ratio")
    private Double beyondRatio;

    /**
     * 是否质检 0否,1是
     */
    @Column(name = "is_quality")
    private Integer isQuality;

    /**
     * rfid采集 0否,1是
     */
    @Column(name = "rfid_gather")
    private Integer rfidGather;

    /**
     * 进货质检库位
     */
    @Column(name = "in_quality_seat")
    private String inQualitySeat;

    /**
     * 退货接收库位
     */
    @Column(name = "out_accept_seat")
    private String outAcceptSeat;

    /**
     * 次品接收库位
     */
    @Column(name = "bad_accept_seat")
    private String badAcceptSeat;

    /**
     * 出库待选库位
     */
    @Column(name = "out_wait_seat")
    private String outWaitSeat;

    /**
     * 上架策略
     */
    @Column(name = "put_strategy")
    private String putStrategy;

    /**
     * 分配策略
     */
    @Column(name = "allot_strategy")
    private String allotStrategy;

    /**
     * 周转规则 1先进先出, 2后进先出
     */
    @Column(name = "run_rule")
    private Integer runRule;

    /**
     * 库存周转
     */
    @Column(name = "stock_run")
    private String stockRun;

    /**
     * 盘点自动处理 0否,1是
     */
    @Column(name = "auto_deal")
    private Integer autoDeal;

    /**
     * 混放箱号 
     */
    @Column(name = "mix_box_num")
    private Integer mixBoxNum;

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
     * 获取主键货主配置id,后台生成
     *
     * @return config_id - 主键货主配置id,后台生成
     */
    public String getConfigId() {
        return configId;
    }

    /**
     * 设置主键货主配置id,后台生成
     *
     * @param configId 主键货主配置id,后台生成
     */
    public void setConfigId(String configId) {
        this.configId = configId;
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
     * 获取无PO收获
     *
     * @return no_po_receive - 无PO收获
     */
    public String getNoPoReceive() {
        return noPoReceive;
    }

    /**
     * 设置无PO收获
     *
     * @param noPoReceive 无PO收获
     */
    public void setNoPoReceive(String noPoReceive) {
        this.noPoReceive = noPoReceive;
    }

    /**
     * 获取超量验证 0否,1是
     *
     * @return beyond_verify - 超量验证 0否,1是
     */
    public Integer getBeyondVerify() {
        return beyondVerify;
    }

    /**
     * 设置超量验证 0否,1是
     *
     * @param beyondVerify 超量验证 0否,1是
     */
    public void setBeyondVerify(Integer beyondVerify) {
        this.beyondVerify = beyondVerify;
    }

    /**
     * 获取超量比例
     *
     * @return beyond_ratio - 超量比例
     */
    public Double getBeyondRatio() {
        return beyondRatio;
    }

    /**
     * 设置超量比例
     *
     * @param beyondRatio 超量比例
     */
    public void setBeyondRatio(Double beyondRatio) {
        this.beyondRatio = beyondRatio;
    }

    /**
     * 获取是否质检 0否,1是
     *
     * @return is_quality - 是否质检 0否,1是
     */
    public Integer getIsQuality() {
        return isQuality;
    }

    /**
     * 设置是否质检 0否,1是
     *
     * @param isQuality 是否质检 0否,1是
     */
    public void setIsQuality(Integer isQuality) {
        this.isQuality = isQuality;
    }

    /**
     * 获取rfid采集 0否,1是
     *
     * @return rfid_gather - rfid采集 0否,1是
     */
    public Integer getRfidGather() {
        return rfidGather;
    }

    /**
     * 设置rfid采集 0否,1是
     *
     * @param rfidGather rfid采集 0否,1是
     */
    public void setRfidGather(Integer rfidGather) {
        this.rfidGather = rfidGather;
    }

    /**
     * 获取进货质检库位
     *
     * @return in_quality_seat - 进货质检库位
     */
    public String getInQualitySeat() {
        return inQualitySeat;
    }

    /**
     * 设置进货质检库位
     *
     * @param inQualitySeat 进货质检库位
     */
    public void setInQualitySeat(String inQualitySeat) {
        this.inQualitySeat = inQualitySeat;
    }

    /**
     * 获取退货接收库位
     *
     * @return return_receive_seat - 退货接收库位
     */
    public String getOutAcceptSeat() {
        return outAcceptSeat;
    }

    /**
     * 设置退货接收库位
     *
     * @param outAcceptSeat 退货接收库位
     */
    public void setOutAcceptSeat(String outAcceptSeat) {
        this.outAcceptSeat = outAcceptSeat;
    }

    /**
     * 获取次品接收库位
     *
     * @return bad_accept_seat - 次品接收库位
     */
    public String getBadAcceptSeat() {
        return badAcceptSeat;
    }


    /**
     * 设置次品接收库位
     *
     * @param badAcceptSeat 次品接收库位
     */
    public void setBadAcceptSeat(String badAcceptSeat) {
        this.badAcceptSeat = badAcceptSeat;
    }



    /**
     * 获取出库待选库位
     *
     * @return out_wait_seat - 出库待选库位
     */
    public String getOutWaitSeat() {
        return outWaitSeat;
    }

    /**
     * 设置出库待选库位
     *
     * @param outWaitSeat 出库待选库位
     */
    public void setOutWaitSeat(String outWaitSeat) {
        this.outWaitSeat = outWaitSeat;
    }

    /**
     * 获取上架策略
     *
     * @return put_strategy - 上架策略
     */
    public String getPutStrategy() {
        return putStrategy;
    }

    /**
     * 设置上架策略
     *
     * @param putStrategy 上架策略
     */
    public void setPutStrategy(String putStrategy) {
        this.putStrategy = putStrategy;
    }

    /**
     * 获取分配策略
     *
     * @return allot_strategy - 分配策略
     */
    public String getAllotStrategy() {
        return allotStrategy;
    }

    /**
     * 设置分配策略
     *
     * @param allotStrategy 分配策略
     */
    public void setAllotStrategy(String allotStrategy) {
        this.allotStrategy = allotStrategy;
    }

    /**
     * 获取周转规则
     *
     * @return run_rule - 周转规则
     */
    public Integer getRunRule() {
        return runRule;
    }

    /**
     * 设置周转规则
     *
     * @param runRule 周转规则
     */
    public void setRunRule(Integer runRule) {
        this.runRule = runRule;
    }

    /**
     * 获取库存周转
     *
     * @return stock_run - 库存周转
     */
    public String getStockRun() {
        return stockRun;
    }

    /**
     * 设置库存周转
     *
     * @param stockRun 库存周转
     */
    public void setStockRun(String stockRun) {
        this.stockRun = stockRun;
    }

    /**
     * 获取盘点自动处理 0否,1是
     *
     * @return auto_deal - 盘点自动处理 0否,1是
     */
    public Integer getAutoDeal() {
        return autoDeal;
    }

    /**
     * 设置盘点自动处理 0否,1是
     *
     * @param autoDeal 盘点自动处理 0否,1是
     */
    public void setAutoDeal(Integer autoDeal) {
        this.autoDeal = autoDeal;
    }

    /**
     * 获取混放箱号 
     *
     * @return mix_box_num - 混放箱号 
     */
    public Integer getMixBoxNum() {
        return mixBoxNum;
    }

    /**
     * 设置混放箱号 
     *
     * @param mixBoxNum 混放箱号 
     */
    public void setMixBoxNum(Integer mixBoxNum) {
        this.mixBoxNum = mixBoxNum;
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