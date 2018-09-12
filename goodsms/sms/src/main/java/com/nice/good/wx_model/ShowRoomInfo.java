package com.nice.good.wx_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Table(name = "v_show_room_info")
public class ShowRoomInfo {
    /**
     * 主键PK
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
     * 图片imgId
     *
     * @return
     */
    @Transient
    private List<String> imgIds;
    /**
     * 直播间编号
     */
    private String code;

    /**
     * 直播间名称
     */
    private String name;
    /**
     * 直播间设备
     */
    private String equipment;
    /**
     * 直播间风格
     */
    private String style;
    /**
     * 直播间面积
     */
    private String area;

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
     * 库位编号
     */
    private String seatCode;

    private String picture;
}