package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_category")
public class Category {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 级别
     */
    private Integer level;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 父级类目的id
     */
    @Column(name = "parent_id")
    private Integer parentId;

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