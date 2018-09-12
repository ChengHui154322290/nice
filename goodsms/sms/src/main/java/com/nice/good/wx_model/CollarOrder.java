package com.nice.good.wx_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_collar_order")
public class CollarOrder {
    /**
     * 主键PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 领用单编号
     */
    @Column(name = "order_code")
    private String orderCode;

    /**
     * 领用单状态()
     */
    private int status;

    /**
     * 直播间id
     */
    @Column(name = "show_room_id")
    private Integer showRoomId;

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
     * 订单所属用户id
     */
    private Integer ownId;

    /**
     * 操作人
     */
    private String operatorId;

    /**
     * 操作类型（订单由主播创建：0，运营创建：1）
     */
    private Integer operatorType;

    /**
     *  配送方式（自提：0，快递：1 ）
     */
    private Integer distributionMode;

    /**
     *  借领人
     */
    private String recipient;

    /**
     *  库位id
     */
    private Integer exhibitionRoomId;
    /**
     *  库位名称
     */
    private String exhibitionRoomName;

    private String seatCode;
}