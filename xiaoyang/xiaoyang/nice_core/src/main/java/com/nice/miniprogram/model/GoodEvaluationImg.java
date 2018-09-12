package com.nice.miniprogram.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "x_good_evaluation_img")
public class GoodEvaluationImg {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图片url
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 评价id
     */
    @Column(name = "evaluation_id")
    private Integer evaluationId;


}