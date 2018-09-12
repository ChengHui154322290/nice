package com.nice.good.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import javax.persistence.*;

@Table(name = "i_store_seat_picture")
public class StoreSeatPicture {
    /**
     * 图片id
     */
    @Id
    @Column(name = "img_id")
    private Long imgId;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图片名字
     */
    @Column(name = "img_name")
    private String imgName;

    /**
     * 图片链接
     */
    @Column(name = "img_url")
    private String imgUrl;

    /**
     * 库位id
     */
    @Column(name = "seat_id")
    private String seatId;

    @Column(name = "create_date")
    private Date createDate;

    /**
     * 创建人
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 获取图片id
     *
     * @return img_id - 图片id
     */
    public Long getImgId() {
        return imgId;
    }

    /**
     * 设置图片id
     *
     * @param imgId 图片id
     */
    public void setImgId(Long imgId) {
        this.imgId = imgId;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
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
     * 获取图片链接
     *
     * @return img_url - 图片链接
     */
    public String getImgUrl() {
        return imgUrl;
    }

    /**
     * 设置图片链接
     *
     * @param imgUrl 图片链接
     */
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * 获取库位id
     *
     * @return seat_id - 库位id
     */
    public String getSeatId() {
        return seatId;
    }

    /**
     * 设置库位id
     *
     * @param seatId 库位id
     */
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取创建人
     *
     * @return create_id - 创建人
     */
    public String getCreateId() {
        return createId;
    }

    /**
     * 设置创建人
     *
     * @param createId 创建人
     */
    public void setCreateId(String createId) {
        this.createId = createId;
    }


    /**
     * 修改人
     */
    @Column(name = "modify_id")
    private String modifyId;

    /**
     * 修改时间
     */
    @Column(name = "modify_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",  timezone = "GMT+8")
    private Date modifyDate;

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}