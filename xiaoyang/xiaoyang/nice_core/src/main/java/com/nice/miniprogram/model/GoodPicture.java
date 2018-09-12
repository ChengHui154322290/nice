package com.nice.miniprogram.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "g_good_picture")
public class GoodPicture {
    /**
     * 主键id,后台生成
     */
    @Id
    @Column(name = "img_id")
    private Long imgId;

    /**
     * 序号id 自动增长
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图片名字
     */
    @Column(name = "img_name")
    private String imgName;

    /**
     * 图片url
     */
    @Column(name = "img_url")
    private String imgUrl;

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

    /**
     * 货品档案表id
     */
    @Column(name = "good_id")
    private String goodId;

    /**
     * 企业id
     */
    @Column(name = "company_id")
    private String companyId;

    /**
     * 获取主键id,后台生成
     *
     * @return img_id - 主键id,后台生成
     */
    public Long getImgId() {
        return imgId;
    }

    /**
     * 设置主键id,后台生成
     *
     * @param imgId 主键id,后台生成
     */
    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    /**
     * 获取序号id 自动增长
     *
     * @return id - 序号id 自动增长
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置序号id 自动增长
     *
     * @param id 序号id 自动增长
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取图片名字
     *
     * @return img_name - 图片名字
     */
    public String getImgName() {
        return imgName;
    }

    /**
     * 设置图片名字
     *
     * @param imgName 图片名字
     */
    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    /**
     * 获取图片url
     *
     * @return img_url - 图片url
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置图片url
     *
     * @param imgUrl 图片url
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取创建人
     *
     * @return creater - 创建人
     */
    public String getCreater() {
        return creater;
    }

    /**
     * 设置创建人
     *
     * @param creater 创建人
     */
    public void setCreater(String creater) {
        this.creater = creater;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取修改人
     *
     * @return modifier - 修改人
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置修改人
     *
     * @param modifier 修改人
     */
    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    /**
     * 获取修改时间
     *
     * @return modifytime - 修改时间
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * 设置修改时间
     *
     * @param modifytime 修改时间
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * 获取货品档案表id
     *
     * @return good_id - 货品档案表id
     */
    public String getGoodId() {
        return goodId;
    }

    /**
     * 设置货品档案表id
     *
     * @param goodId 货品档案表id
     */
    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    /**
     * 获取企业id
     *
     * @return company_id - 企业id
     */
    public String getCompanyId() {
        return companyId;
    }

    /**
     * 设置企业id
     *
     * @param companyId 企业id
     */
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}