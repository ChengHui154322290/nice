package com.nice.good.service.impl;


import com.nice.good.core.RedisService;
import com.nice.good.dao.RoomPictureMapper;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.ShowRoomInfoMapper;
import com.nice.good.wx_model.RoomPicture;
import com.nice.good.wx_model.ShowRoomInfo;
import com.nice.good.service.ShowRoomInfoService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
@Service
@Transactional
public class ShowRoomInfoServiceImpl extends AbstractService<ShowRoomInfo> implements ShowRoomInfoService {
    @Resource
    private ShowRoomInfoMapper showRoomInfoMapper;

    @Resource
    private RedisService redisService;

    @Resource
    private RoomPictureMapper roomPictureMapper;
    @Override
    public  void showRoomInfoAdd(ShowRoomInfo showRoomInfo,String userId){
        List<String> imgIds =   showRoomInfo.getImgIds();
        if(imgIds!=null && imgIds.size()>0){
            showRoomInfo.setPicture(imgIds.get(0));
        }
        showRoomInfoMapper.insert(showRoomInfo);
        Integer roomId = showRoomInfo.getId();
        updateImg(showRoomInfo, userId, roomId);

    }

    private void updateImg(ShowRoomInfo showRoomInfo, String userId, Integer roomId) {
        List<String> imgIds = showRoomInfo.getImgIds();
        if (imgIds != null && imgIds.size() > 0) {
            for (String imgId : imgIds) {
                if (imgId != null) {
                    Long newImgId = Long.valueOf(imgId.substring(0, imgId.lastIndexOf(".")));
                    //继续新增图片
                    RoomPicture roomPicture = roomPictureMapper.selectByPrimaryKey(newImgId);
                    if (roomPicture == null) {
                        continue;
                    }

                    if (roomPicture.getRoomId() != null) {
                        continue;
                    }
                    roomPicture.setRoomId(roomId);
                    roomPicture.setModifier(userId);
                    roomPicture.setModifytime(TimeStampUtils.getTimeStamp());
                    roomPictureMapper.updateByPrimaryKey(roomPicture);
                }
            }
        }
    }


   @Override
   public void  showRoomInfoUpdate(ShowRoomInfo showRoomInfo,String userId){

        showRoomInfoMapper.updateByPrimaryKey(showRoomInfo);
       Integer roomId = showRoomInfo.getId();
       updateImg(showRoomInfo, userId, roomId);
   }

    @Override
    public Boolean checkIsChose(Integer showRoomId, int startDay, int endDay, int startHour, int endHour) {
        if(startDay == endDay) {
            Set choseHours = redisService.setMembers(showRoomId + "_" + startDay);
            for (int i=startHour;i<=endHour;i++){
                if(choseHours.contains(i)){
                    return true;
                }
            }
        }else{
            Set choseHours1 = redisService.setMembers(showRoomId + "_" + startDay);
            Set choseHours2 = redisService.setMembers(showRoomId + "_" + endDay);
            for(int i=startHour;i<=endHour;i++){
                if(choseHours1.contains(i) || choseHours2.contains(i)){
                    return true;
                }
            }

        }
        return false;
    }

}
