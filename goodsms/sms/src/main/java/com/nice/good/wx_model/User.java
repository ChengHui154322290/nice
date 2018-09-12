package com.nice.good.wx_model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "x_user")
public class User {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 微信头像
     */
    private String picture;

    /**
     * 运营用户id
     */
    @Column(name = "sys_user_id")
    private String sysUserId;

    /**
     * 直播平台
     */
    @Column(name = "anchor_platform")
    private String anchorPlatform;

    /**
     * 直播平台主播uid
     */
    @Column(name = "anchor_uid")
    private String anchorUid;

    /**
     * 主播姓名
     */
    @Column(name = "anchor_true_name")
    private String anchorTrueName;

    /**
     * 主播手机号
     */
    @Column(name = "anchor_phone")
    private String anchorPhone;

    /**
     * 主播风格
     */
    @Column(name = "anchor_style")
    private String anchorStyle;

    /**
     * 粉丝年龄段
     */
    @Column(name = "fans_age_group")
    private String fansAgeGroup;

    /**
     * 粉丝消费能力
     */
    @Column(name = "fans_consuming_ability")
    private String fansConsumingAbility;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createtime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date modifytime;

    /**
     * 用户类型 0：未审核，1：已审核
     */
    @Column(name = "user_type")
    private Byte userType;

    /**
     * 主播:是0,否1
     */
    @Column(name = "if_anchor")
    private Integer ifAnchor;

    /**
     * 登陆次数
     */
    @Column(name = "login_num")
    private Integer loginNum;

    /**
     * 主播性别， 男：1，女：0
     */
    @Column(name = "anchor_sex")
    private Byte anchorSex;

    /**
     * 微信昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 所属机构
     */
    private String orgName;

}