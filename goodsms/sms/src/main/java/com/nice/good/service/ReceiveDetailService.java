package com.nice.good.service;
import com.nice.good.dto.RfidGatherDto;
import com.nice.good.model.Good;
import com.nice.good.model.ReceiveDetail;
import com.nice.good.core.Service;
import com.nice.good.model.RfidLabel;
import com.nice.good.vo.ChooseOrderVo;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/28.
 */
public interface ReceiveDetailService extends Service<ReceiveDetail> {
      void receiveDetailAdd(ReceiveDetail receiveDetail,String userId) throws Exception;
      void receiveDetailUpdate(ReceiveDetail receiveDetail,String userId);

      List<ChooseOrderVo>  chooseOrderVo(ChooseOrderVo chooseOrderVo);

    String getRfidGather(RfidGatherDto  rfidGatherDto, String userId);


}
