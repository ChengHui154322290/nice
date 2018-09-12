package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GooderTransportMapper;
import com.nice.good.model.GooderTransport;
import com.nice.good.service.GooderTransportService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
@Service
@Transactional
public class GooderTransportServiceImpl extends AbstractService<GooderTransport> implements GooderTransportService {
    @Resource
    private GooderTransportMapper gooderTransportMapper;

    @Override
    public  void gooderTransportAdd(GooderTransport gooderTransport,String userId){


        gooderTransport.setCreater(userId);
        gooderTransport.setModifier(userId);
        gooderTransport.setCreatetime(TimeStampUtils.getTimeStamp());
        gooderTransport.setModifytime(TimeStampUtils.getTimeStamp());

        gooderTransportMapper.insert(gooderTransport);

    }


   @Override
   public void  gooderTransportUpdate(GooderTransport gooderTransport,String userId){

       gooderTransport.setModifier(userId);
       gooderTransport.setModifytime(TimeStampUtils.getTimeStamp());
        gooderTransportMapper.updateByPrimaryKey(gooderTransport);
   }

}
