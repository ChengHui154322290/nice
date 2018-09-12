package com.nice.good.service.impl;


import com.nice.good.dao.BespeakOrderMapper;
import com.nice.good.wx_model.BespeakOrder;
import com.nice.good.service.BespeakOrderService;
import com.nice.good.core.AbstractService;
import com.nice.good.wx_model.BespeakOrderVo;
import com.nice.good.wx_model.BookingSlipVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/03.
 */
@Service
@Transactional
public class BespeakOrderServiceImpl extends AbstractService<BespeakOrder> implements BespeakOrderService {
    @Resource
    private BespeakOrderMapper bespeakOrderMapper;

    @Override
    public  void bespeakOrderAdd(BespeakOrder bespeakOrder,String userId){



        bespeakOrderMapper.insert(bespeakOrder);

    }


   @Override
   public void  bespeakOrderUpdate(BespeakOrder bespeakOrder,String userId){

        bespeakOrderMapper.updateByPrimaryKey(bespeakOrder);
   }

    @Override
    public Integer selectMaxId() {
        return bespeakOrderMapper.selectMaxId();
    }

    @Override
    public List<BookingSlipVo> selectBespeakAll(BookingSlipVo bookingSlipVo) {


        return bespeakOrderMapper.selectBespeakAll(bookingSlipVo);
    }

    @Override
    public List<BookingSlipVo> selectCollarAll(BookingSlipVo bookingSlipVo) {
        return bespeakOrderMapper.selectCollarAll(bookingSlipVo);
    }


    @Override
    public BespeakOrderVo selectBespeak(String orderCode) {
        return bespeakOrderMapper.selectBespeak(orderCode);
    }

    @Override
    public void deleteByOrderCode(String orderCode) {
         bespeakOrderMapper.deleteByOrderCode(orderCode);
    }

    @Override
    public BespeakOrder getByOrderCode(String orderCode) {
        return bespeakOrderMapper.getByOrderCode(orderCode);
    }

    @Override
    public List<Integer> findStatusByOwnId(Integer ownId) {
        return bespeakOrderMapper.findStatusByOwnId(ownId);
    }


}
