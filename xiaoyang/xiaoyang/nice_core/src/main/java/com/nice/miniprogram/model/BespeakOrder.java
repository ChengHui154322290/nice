package com.nice.miniprogram.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_bespeak_order")
public class BespeakOrder {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 预约单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 预约的开始时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "bespeak_time_start")
    private Date bespeakTimeStart;

    /**
     * 预约的结束时间
     */
    @Column(name = "bespeak_time_end")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date bespeakTimeEnd;

    /**
     * 预约的主播间id
     */
    @Column(name = "show_room_id")
    private Integer showRoomId;

    /**
     * 订单所属用户id
     */
    @Column(name = "own_id")
    private Integer ownId;

    /**
     * 操作人
     */
    @Column(name = "operator_id")
    private String operatorId;

    /**
     * 操作类型（订单由主播创建：0，运营创建：1）
     */
    @Column(name = "operator_type")
    private Integer operatorType;

    /**
     * 预约单状态（已预约：0；已完成：1；已取消：2）
     */
    private Integer status;

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

    /**
     *  展厅id
     */
    private Integer exhibitionRoomId;
    /**
     *  展厅名称
     */
    private String exhibitionRoomName;
}