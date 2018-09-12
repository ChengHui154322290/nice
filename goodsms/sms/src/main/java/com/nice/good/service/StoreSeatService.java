package com.nice.good.service;
import com.nice.good.core.Service;
import com.nice.good.dto.StoreSeatDto;
import com.nice.good.model.StoreSeat;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface StoreSeatService extends Service<StoreSeat> {
      String storeSeatAdd(StoreSeat storeSeat,String placeId, String userId) throws Exception;

      String storeSeatUpdate(StoreSeat storeSeat,String placeId,String userId);

      String deleteBySeatId(List<String> seatIds, String placeId);

      void uploadExcelForAddStoreSeat(List<StoreSeatDto> seatList,String placeId,String userId);
}
