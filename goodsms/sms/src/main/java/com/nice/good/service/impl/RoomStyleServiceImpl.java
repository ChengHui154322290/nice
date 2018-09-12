package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.RoomStyleMapper;
import com.nice.good.wx_model.RoomStyle;
import com.nice.good.service.RoomStyleService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/27.
 */
@Service
@Transactional
public class RoomStyleServiceImpl extends AbstractService<RoomStyle> implements RoomStyleService {
    @Resource
    private RoomStyleMapper roomStyleMapper;

    @Override
    public  void roomStyleAdd(RoomStyle roomStyle,String userId){



        roomStyleMapper.insert(roomStyle);

    }


   @Override
   public void  roomStyleUpdate(RoomStyle roomStyle,String userId){

        roomStyleMapper.updateByPrimaryKey(roomStyle);
   }

}
