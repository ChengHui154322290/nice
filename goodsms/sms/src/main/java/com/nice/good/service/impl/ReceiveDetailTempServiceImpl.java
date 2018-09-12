package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.ReceiveDetailTempMapper;
import com.nice.good.model.ReceiveDetailTemp;
import com.nice.good.service.ReceiveDetailTempService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/05.
 */
@Service
@Transactional
public class ReceiveDetailTempServiceImpl extends AbstractService<ReceiveDetailTemp> implements ReceiveDetailTempService {
    @Resource
    private ReceiveDetailTempMapper receiveDetailTempMapper;

    @Override
    public  void receiveDetailTempAdd(ReceiveDetailTemp receiveDetailTemp,String userId){


        receiveDetailTemp.setCreateId(userId);
        receiveDetailTemp.setModifyId(userId);
        receiveDetailTemp.setCreateDate(TimeStampUtils.getTimeStamp());
        receiveDetailTemp.setModifyDate(TimeStampUtils.getTimeStamp());

        receiveDetailTempMapper.insert(receiveDetailTemp);

    }


   @Override
   public void  receiveDetailTempUpdate(ReceiveDetailTemp receiveDetailTemp,String userId){

        receiveDetailTemp.setModifyId(userId);
        receiveDetailTemp.setModifyDate(TimeStampUtils.getTimeStamp());
        receiveDetailTempMapper.updateByPrimaryKey(receiveDetailTemp);
   }

}
