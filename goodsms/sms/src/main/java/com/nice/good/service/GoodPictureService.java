package com.nice.good.service;

import com.nice.good.model.GoodPicture;
import com.nice.good.core.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface GoodPictureService extends Service<GoodPicture> {
    List<String> goodPictureAdd(MultipartFile[] files, String userId);

    void goodPictureUpdate(GoodPicture goodPicture, String userId);

    /**
     * 删除图片
     * -- 2018/05/26 11:45  rk
     *
     * @param imgIds
     */
    void deletePicture(List<String> imgIds);

    List<GoodPicture> getByGoodId(String goodId);
}
