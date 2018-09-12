package com.nice.good.service;
import com.nice.good.wx_model.RoomPicture;
import com.nice.good.core.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/27.
 */
public interface RoomPictureService extends Service<RoomPicture> {
      List<String> roomPictureAdd(MultipartFile[] files, String userId);
      void roomPictureUpdate(RoomPicture roomPicture,String userId);

	void deletePicture(List<String> imgIds);
}
