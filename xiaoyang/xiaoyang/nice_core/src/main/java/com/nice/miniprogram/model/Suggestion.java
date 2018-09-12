package com.nice.miniprogram.model;

import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_suggestion")
public class Suggestion {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 建议内容
     */
    private String suggestion;
    /**
     * 投诉内容
     */
    private String complain;
    /**
     * 操作人
     */
    private Integer operator;

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