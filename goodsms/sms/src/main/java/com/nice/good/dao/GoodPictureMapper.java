package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.GoodPicture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodPictureMapper extends Mapper<GoodPicture> {

    /**
     * 删除图片
     * -- 2018/05/26 11:45  rk
     * @param imgUrl
     */
    void deletePicture(String imgUrl);

    List<String> selectImgsByGoodId(String goodId);

    void deleteByGoodId(String goodId);

    List<String> selectImgsByGoodCode(@Param(value = "gooderCode") String gooderCode,@Param(value = "goodCode") String goodCode);

    List<GoodPicture> getByGoodId(String goodId) ;
}