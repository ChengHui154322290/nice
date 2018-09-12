/**
 * Copyright(c) 2014-2016 Basung Information Technology (Shanghai) Co., Ltd.
 * <p>
 * 　　　　　　　　┏┓　　　┏┓+ +
 * 　　　　　　　┏┛┻━━━┛┻┓ + +
 * 　　　　　　　┃　　　　　　　┃
 * 　　　　　　　┃　　　━　　　┃ ++ + + +
 * 　　　　　　 ████━████ ┃+
 * 　　　　　　　┃　　　　　　　┃ +
 * 　　　　　　　┃　　　┻　　　┃
 * 　　　　　　　┃　　　　　　　┃ + +
 * 　　　　　　　┗━┓　　　┏━┛
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃ + + + +
 * 　　　　　　　　　┃　　　┃　　　　Code is far away from bug with the animal protecting
 * 　　　　　　　　　┃　　　┃ + 　　　　神兽保佑,代码无bug
 * 　　　　　　　　　┃　　　┃
 * 　　　　　　　　　┃　　　┃　　+
 * 　　　　　　　　　┃　 　　┗━━━┓ + +
 * 　　　　　　　　　┃ 　　　　　　　┣┓
 * 　　　　　　　　　┃ 　　　　　　　┏┛
 * 　　　　　　　　　┗┓┓┏━┳┓┏┛ + + + +
 * 　　　　　　　　　　┃┫┫　┃┫┫
 * 　　　　　　　　　　┗┻┛　┗┻┛+ + + +
 * <p>
 * History:
 * 2016/11/3 下午5:42 Created by leijinghan
 */
package com.nice.miniprogram.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.Set;

/**
 * Explain: 
 * Description:
 *
 * @Author : <a href="mailto:jinghan.lei@basung.com">leijinghan</a>.
 * @Version: 1.0
 * @Date : 2016/11/3.
 */
@Data
@ApiModel(value = "企业-认证")
public class CertificationVo {
    @ApiModelProperty(value = "编号")
    private long id;
    @ApiModelProperty(value = "企业标识", required = true)
//    @Min(value = 1, message = "valid.min", groups = {Admin.class})
    private long enterpriseId;

    @ApiModelProperty(value = "企业名称")
//    @NotBlank(message = "valid.notBlank", groups = {Admin.class, Business.class})
    private String name;
    @ApiModelProperty(value = "地区")
    private String region;
    @ApiModelProperty(value = "企业营业执照注册号")
    private String regCode;
    @ApiModelProperty(value = "企业经营范围")
    private String scope;
    @ApiModelProperty(value = "营业执照图片")
    private String licensePic;
    @ApiModelProperty(value = "企业成立日期")
    private Date setUpDate;
    @ApiModelProperty(value = "对公帐户")
    private String bankAccount;
    @ApiModelProperty(value = "开户银行")
    private String bank;
    @ApiModelProperty(value = "开户行地点")
    private String bankRegion;
    @ApiModelProperty(value = "运营者姓名")
    private String operatingName;
    @ApiModelProperty(value = "运营者身份证")
    private String operatingCard;
    @ApiModelProperty(value = "运营者手机号")
    private String operatingMobile;
    @ApiModelProperty(value = "身份证正面")
    private String positive;
    @ApiModelProperty(value = "身份证反面")
    private String inverse;
    @ApiModelProperty(value = "企业图片-多张")
    private Set<String> picList;
    @ApiModelProperty(value = "认证信息状态:0-未审核|1-通过")
    private int status;

    @ApiModelProperty(value = "信息所有者类型")
    private String ownerType;
    @ApiModelProperty(value = "信息所有者id")
    private String ownerId;

    @ApiModelProperty(value = "创建时间")
    private Date creationTime = new Date();
    @ApiModelProperty(value = "修改时间")
    private Date lastModify = new Date();
    @ApiModelProperty(value = "创建者")
    private String creator;
    @ApiModelProperty(value = "修改者")
    private String mender;
}
