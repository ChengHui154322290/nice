package com.nice.miniprogram.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "购物车")
public class AlternativeVo {

    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "图片id")
    private String imgId;
    @ApiModelProperty(value = "货品编码")
    private String skuCode;
    @ApiModelProperty(value = "已选数量")
    private Integer checkedNum;
    @ApiModelProperty(value = "创建人")
    private String creater;
    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    @ApiModelProperty(value = "修改人")
    private String modifier;
    @ApiModelProperty(value = "修改时间")
    private Date modifytime;
    @ApiModelProperty(value = "企业id")
    private String companyId;
    @ApiModelProperty(value = "所属用户id")
    private Integer ownId;
    @ApiModelProperty(value = "可用库存")
    private Integer useNum;


}
