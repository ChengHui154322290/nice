package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OutInvoiceMapper;
import com.nice.good.model.OutInvoice;
import com.nice.good.service.OutInvoiceService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
@Service
@Transactional
public class OutInvoiceServiceImpl extends AbstractService<OutInvoice> implements OutInvoiceService {
    @Resource
    private OutInvoiceMapper outInvoiceMapper;

    @Override
    public  void outInvoiceAdd(OutInvoice outInvoice,String userId){


        outInvoice.setCreateId(userId);
        outInvoice.setModifyId(userId);
        outInvoice.setCreateDate(TimeStampUtils.getTimeStamp());
        outInvoice.setModifyDate(TimeStampUtils.getTimeStamp());

        outInvoiceMapper.insert(outInvoice);

    }


   @Override
   public void  outInvoiceUpdate(OutInvoice outInvoice,String userId){

        outInvoice.setModifyId(userId);
        outInvoice.setModifyDate(TimeStampUtils.getTimeStamp());
        outInvoiceMapper.updateByPrimaryKey(outInvoice);
   }

}
