package com.nice.good.service;
import com.nice.good.model.StoreSeatPicture;
import com.nice.good.core.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/13.
 */
public interface StoreSeatPictureService extends Service<StoreSeatPicture> {

    List<String> goodPictureAdd(MultipartFile[] files, String userId);

    void deletePicture(List<String> imgIds);
}
