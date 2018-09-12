package com.nice.good.wx_model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "x_show_count")
public class ShowCount {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 直播间id
     */
    @Column(name = "show_room_id")
    private Integer showRoomId;

    /**
     * 展厅id（库位id）
     */
    @Column(name = "exhibition_Room_Id")
    private Integer exhibitionRoomId;

    /**
     * 类型（0:直播间；1:展厅）
     */
    private Integer type;

    /**
     * 点击次数
     */
    @Column(name = "click_num")
    private Integer clickNum;

    /**
     * 预约次数
     */
    @Column(name = "bespeak_num")
    private Integer bespeakNum;

}