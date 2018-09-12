package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "x_consignee_info")
public class ConsigneeInfo {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "own_id")
    private Integer ownId;

    /**
     * 联系人
     */
    private String name;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 省id
     */
    private Integer province;

    /**
     * 市id
     */
    private Integer city;

    /**
     * 区id
     */
    private Integer district;

    /**
     * 详细地址
     */
    private String adress;

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