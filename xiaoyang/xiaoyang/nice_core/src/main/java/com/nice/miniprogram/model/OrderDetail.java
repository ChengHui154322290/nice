package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "x_order_detail")
@Data
public class OrderDetail {
    /**
     * 主键PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单类型（0：领用单，1：预约单）
     */
    @Column(name = "order_type")
    private Integer orderType;

    /**
     * 主表id
     */
    @Column(name = "order_id")
    private Integer orderId;

    /**
     * 订单状态(待发货:0,待收货:1,已收货:3,已归还:5,归还中:6,已取消:7, 借领中:8 , 未开始:9)
     */
    private Integer status;

    /**
     * 样品id
     */
    @Column(name = "sku_code")
    private String skuCode;

    /**
     * 已选数量
     */
    @Column(name = "chose_num")
    private Integer choseNum;

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
     * 库位编码
     */
    @Column(name = "seat_code")
    private String seatCode;

    /**
     * 目标库位
     */
    @Column(name = "target_seat_code")
    private String targetSeatCode;

    /**
     * 来源库位
     */
    @Column(name = "source_seat_code")
    private String sourceSeatCode;

    /**
     * 是否已评价（否：0，是：1）
     */
    @Column(name = "is_feedback")
    private Integer isFeedback;

}