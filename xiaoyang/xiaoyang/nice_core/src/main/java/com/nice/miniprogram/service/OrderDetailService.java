package com.nice.miniprogram.service;
import com.nice.miniprogram.model.OrderDetail;
import com.nice.miniprogram.core.Service;
import com.nice.miniprogram.vo.OrderDetailVo;

import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/07/27.
 */
public interface OrderDetailService extends Service<OrderDetail> {

    List<OrderDetail> selectDetailListByOrderId(int orderId);

    void updateStatus(Integer orderId,Integer id,Integer status,String userName);

    void batchUpdateStatusByOrderId(Map<String,Object> params);

    List<OrderDetail> selectByParams(Map<String ,Object> params);

    void batchInsert(List<OrderDetail> orderDetails);

//    void updateFeedback(Integer detailId,Integer isFeedback);

//    List<ScoreAndEvaluationVo> getScoreAndEvaluation(Map<String,Object> params);

    List<OrderDetail> freezeStock(List<OrderDetail> details,String placeNumber);
    List<OrderDetail> freezeStock(OrderDetail detail,String placeNumber);
    List<OrderDetail> setSourceSeatCode(OrderDetail detail, String placeNumber);
    void catchStock(OrderDetail orderDetail);

    void returnStock(OrderDetail orderDetail);

//    OrderDetail getOneById(Integer id);

    List<OrderDetail> queryByParams(Map<String,Object> params);

    void updateDetStatus(Integer orderId);
}
