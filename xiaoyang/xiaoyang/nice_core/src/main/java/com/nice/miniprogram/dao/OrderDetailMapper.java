package com.nice.miniprogram.dao;

import com.nice.miniprogram.core.CoreMapper;
import com.nice.miniprogram.model.OrderDetail;
import com.nice.miniprogram.vo.OrderDetailVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDetailMapper extends CoreMapper<OrderDetail> {
    void batchInsert(List<OrderDetail> orderDetails);

//    @Select("select * from x_order_detail where id=#{id}")
//    OrderDetail getOneById(Integer id);

    @Update("update x_order_detail set status=#{status},modifier=#{userName},modifytime=#{current} where order_id=#{orderId}")
    void batchUpdateStatusByOrderId(Map<String,Object> params);

    List<OrderDetail> selectByParams(Map<String,Object> params);

    @Update("update x_order_detail set status=#{status},modifier=#{userName},modifytime=#{current} where id=#{id}")
    void updateStatusById(Integer status,Integer id,String userName,Date current);

    //    @Update("update x_order_detail set score=#{score},homogeneity=#{homogeneity},fans_praise=#{fansPraise},evaluation=#{evaluation} where id=#{detailId}")
//    void updateFeedback(Integer score,Integer homogeneity,Integer fansPraise,String evaluation,Integer detailId);
//    @Update("update x_order_detail set is_feedback=#{isFeedback} where id=#{detailId}")
//    void updateFeedback(Integer detailId, Integer isFeedback);

//    List<ScoreAndEvaluationVo> getScoreAndEvaluation(Map<String,Object> params);

    List<OrderDetail> queryByParams(Map<String, Object> params);

	void updateDetStatus(Integer orderId);
}