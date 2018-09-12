package com.nice.good.service;

import com.nice.good.core.Result;
import com.nice.good.dto.ReceiveDetailDto;
import com.nice.good.model.Good;
import com.nice.good.model.ReceiveDetail;
import com.nice.good.model.ReceiveOrder;
import com.nice.good.core.Service;
import com.nice.good.dto.ShelfPojo;
import com.nice.good.vo.GoodVo;

import java.util.List;


/**
* @Description:   java类作用描述
* @Author:   fqs
* @Date:  2018/4/16 10:10
* @Version:   1.0
*/
public interface ReceiveOrderService extends Service<ReceiveOrder> {

    String receiveOrderAdd(ReceiveOrder receiveOrder,String placeId, String userId) throws Exception;

    String receiveOrderHangUp(List<String> receiveIds,Integer hangUp,Integer flag,String userId);

    String deleteByReceiveId(List<String> receiveIds);

    String receiveGoods(ReceiveOrder receiveOrder,String placeId, String userId) throws Exception;

    String receiveMainGoods(List<ReceiveOrder> receiveOrders,String placeId, String userId) throws Exception;

    String cancelGoods(List<ReceiveDetail> receiveDetails,String placeId, String userId) throws Exception;

    String cancelMainGoods(List<ReceiveOrder> receiveOrders,String placeId, String userId) throws Exception;

    Result shelfGoods(List<ReceiveDetail> receiveDetails, String placeId, String userId);

    Result selectAllotSeat(List<ShelfPojo> shelfPojos,String placeId);

    String shelfGoodSave(List<ShelfPojo> shelfPojos, String placeId,String userId) throws Exception;

    Result  clearGoods(List<ReceiveDetail> receiveDetails,Boolean forceClear, String userId);

    Result clearMainGoods(List<ReceiveOrder> receiveOrders,Boolean forceClear, String userId);

    List<ReceiveOrder> findByConditions(ReceiveOrder receiveOrder,String placeId, List<String> gooderCodes);

    String addReceiveDetail(List<GoodVo> goods, String receiveId,String placeId, String userId) throws Exception;

    String delReceiveDetail(List<String> detailIds,String userId);

    Result listReceiveDetail(String receiveId);

    /**
     *  通过最大 id 值查询 ReceiveOrder.java  --  rk  2018/05/02
     * @return
     */
    ReceiveOrder findReceiveOrderByMaxId();

	void uploadExcelForAddStoreSeat(List<ReceiveDetailDto> success, String userId, ReceiveOrder receiveOrder) throws Exception;
}
