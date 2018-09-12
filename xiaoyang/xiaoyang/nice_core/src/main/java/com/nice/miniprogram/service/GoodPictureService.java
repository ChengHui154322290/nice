package com.nice.miniprogram.service;
import com.nice.miniprogram.model.GoodPicture;
import com.nice.miniprogram.core.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/07/19.
 */
public interface GoodPictureService extends Service<GoodPicture> {
    List<GoodPicture> getByGoodId(String goodId);
}
