package com.nice.miniprogram.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "直播间信息")
public class ShowRoomInfoVo {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "直播场地id")
    private Integer showPlaceId;

    @ApiModelProperty(value = "直播场地编号")
    private String showPlaceCode;

    @ApiModelProperty(value = "直播场地名称")
    private String showPlaceName;

    @ApiModelProperty(value = "直播场地类型（内场：0，外场：1）")
    private Integer type;

    @ApiModelProperty(value = "省id")
    private String province;
    @ApiModelProperty(value = "省")
    private String provinceName;

    @ApiModelProperty(value = "市id")
    private String city;
    @ApiModelProperty(value = "市")
    private String cityName;

    @ApiModelProperty(value = "区id")
    private String district;
    @ApiModelProperty(value = "区")
    private String districtName;

    @ApiModelProperty(value = "详细地址")
    private String address;
    
    @ApiModelProperty(value = "直播间编号")
    private String code;

    @ApiModelProperty(value = "直播间名称")
    private String name;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建人")
    private String creater;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createtime;

    @ApiModelProperty(value = "修改人")
    private String modifier;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date modifytime;

    @ApiModelProperty(value = "库位编号")
    private String seatCode;
    @ApiModelProperty(value = "图片URL")
    private String picture;
}
