package com.nice.good.wx_model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class GoodSkuVoNew {
    private Integer id;
    private String spuCode;
    private String skuCode;
    private String skuName;
    private Integer status;
    private String goodColor;
    private String goodSize;
    private String brokerageLink;
    private Double brokerageRatio;
    private Double normalPrice;
    private Double seckillPrice;
    private String discountMethod;
    private String discountLink;
    private String discountContent;
    private String remark;
    private String creater;
    private Date createtime;
    private String modifier;
    private Date modifytime;
    private String companyId;

    private Integer inventory;

}