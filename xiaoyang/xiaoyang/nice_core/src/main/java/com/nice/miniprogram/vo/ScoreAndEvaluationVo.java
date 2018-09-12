package com.nice.miniprogram.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "样品评分评价信息")
public class ScoreAndEvaluationVo {
    @ApiModelProperty(value = "spu")
    private String spuCode;
    @ApiModelProperty(value = "sku")
    private String skuCode;
    @ApiModelProperty(value = "评分")
    private Double score;
    @ApiModelProperty(value = "实物描述一致性")
    private Double homogeneity;
    @ApiModelProperty(value = "粉丝口碑")
    private Double fansPraise;
    @ApiModelProperty(value = "评价")
    private String evaluation;
    @ApiModelProperty(value = "明细id")
    private Integer detailId;
    @ApiModelProperty(value = "图片集合")
    private List<Integer> imgIds;
}
