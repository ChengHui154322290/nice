package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;


@Table(name = "v_exhibition_room")
@Data
public class ExhibitionRoom {
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
     * 展厅id
     */
    @Column(name = "exhibition_id")
    private Integer exhibitionId;

    /**
     * 库位编码
     */
    @Column(name = "seat_code")
    private String seatCode;

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

}