package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.MeasureMapper;
import com.nice.good.model.Measure;
import com.nice.good.service.MeasureService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/01.
 */
@Service
@Transactional
public class MeasureServiceImpl extends AbstractService<Measure> implements MeasureService {
    @Resource
    private MeasureMapper measureMapper;

    @Override
    public  void measureAdd(Measure measure,String userId) throws Exception {

        measure.setMeasureId(IdsUtils.getOrderId());
        measure.setCreater(userId);
        measure.setModifier(userId);
        measure.setCreatetime(TimeStampUtils.getTimeStamp());
        measure.setModifytime(TimeStampUtils.getTimeStamp());

        measureMapper.insert(measure);

    }


   @Override
   public void  measureUpdate(Measure measure,String userId){

        measure.setModifier(userId);
        measure.setModifytime(TimeStampUtils.getTimeStamp());
        measureMapper.updateByPrimaryKey(measure);
   }

}
