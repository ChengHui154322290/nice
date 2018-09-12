package com.nice.miniprogram.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "主播用户信息")
public class UserVo {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    private Integer userId;
    @ApiModelProperty(value = "账号")
    private String account;
    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "微信openId")
    private String openId;//微信openId

    @ApiModelProperty(value = "微信昵称")
    private String nickName;//微信昵称

    @ApiModelProperty(value = "微信头像")
    private String picture;//微信头像

    @ApiModelProperty(value = "运营用户id")
    private String sysUserId;//运营用户id

    @ApiModelProperty(value = "直播平台")
    private String anchorPlatform;//直播平台

    @ApiModelProperty(value = "平台主播uid")
    private String anchorUid;//平台主播uid

    @ApiModelProperty(value = "主播真实姓名")
    private String anchorTrueName;//主播真实姓名

    @ApiModelProperty(value = "手机号码")
    private String anchorPhone;//手机号码

    @ApiModelProperty(value = "主播风格")
    private String anchorStyle;//主播风格

    @ApiModelProperty(value = "粉丝年龄段")
    private String fansAgeGroup;//粉丝年龄段

    @ApiModelProperty(value = "粉丝消费能力")
    private String fansConsumingAbility;//粉丝消费能力

    @ApiModelProperty(value = "备注")
    private String remark;//备注

    @ApiModelProperty(value = "创建人")
    private String creater;//创建人

    @ApiModelProperty(value = "创建时间")
    private Date createtime;//创建时间

    @ApiModelProperty(value = "修改人")
    private String modifier;//修改人

    @ApiModelProperty(value = "修改时间")
    private Date modifytime;//修改时间

    @ApiModelProperty(value = "用户类型 0：未审核，1：已审核")
    private Integer userType;
    @ApiModelProperty(value = "主播 0：是，1：否")
    private Integer ifAnchor;
    @ApiModelProperty(value = "登录次数")
    private Integer loginNum;

    @ApiModelProperty(value = "主播性别")
    private Integer anchorSex;
    @ApiModelProperty(value = "组织机构")
    private String orgName;
}
