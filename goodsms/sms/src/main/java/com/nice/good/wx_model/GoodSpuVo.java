package com.nice.good.wx_model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
public class GoodSpuVo {

    private Integer id;
    private String spuCode;
    private String spuName;
    private Integer status;
    private String gooderCode;
    private Integer styleId;
    private Integer categoryId;
    private Integer brandId;
    private String priceSection;
    private String packCode;
    private Double bulk;
    private Double netWeight;
    private Double tareWeight;
    private Double roughWeight;
    private String property;
    private String period;
    private String periodUnite;
    private String goodLink;
    private String brokerageLink;
    private Double brokerageRatio;
    private String remark;
    private String creater;
    private Date createtime;
    private String modifier;
    private Date modifytime;
    private String companyId;

    private List<GoodSkuVoNew> skuList;
    private List<GoodImg> pictureList;
    private List<GoodImg> detailPictureList;

}