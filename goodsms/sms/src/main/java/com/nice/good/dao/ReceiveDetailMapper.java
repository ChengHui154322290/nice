package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.ReceiveDetail;
import com.nice.good.vo.ChooseOrderVo;
import com.nice.good.vo.ReceiveDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReceiveDetailMapper extends Mapper<ReceiveDetail> {

    List<String> selectByReceiveId(String receiveId);

    /**
     * 通过 receive_Id(ASN收货单标id) 获取 o_receive_detail 表中所有数据
     *
     * @param receiveId
     * @return
     */
    List<ReceiveDetail> selectReceiveDetailByReceiveId(String receiveId);

    List<ReceiveDetail> findDetailByReceiveId(String receiveId);


    List<String> selectByGooderAndCode(@Param(value = "gooderCode") String gooderCode,
                                       @Param(value = "goodCode") String goodCode);

    List<ReceiveDetail> selectListByReceiveId(String receiveId);


    List<ChooseOrderVo> chooseOrderVo(ChooseOrderVo chooseOrderVo);

    Integer selectStatus(String detailId);


    Integer selectTotalExpectNum(@Param(value = "purchaseCode") String purchaseCode,
                                 @Param(value = "purchaseLineCode") Integer purchaseLineCode);

    Integer selectTotalReceiveNum(@Param(value = "purchaseCode") String purchaseCode,
                                  @Param(value = "purchaseLineCode") Integer purchaseLineCode);


    Integer selectCountByPurchaseCode(String purchaseCode);


    Integer selectCountByReceiveId(String receiveId);

    ReceiveDetailVo selectByPurchaseCode(String purchaseCode);

    List<ReceiveDetail> selectByPurchaseLineCode(@Param(value = "purchaseCode") String purchaseCode,
                                                 @Param(value = "purchaseLineCode") Integer purchaseLineCode);

    ReceiveDetailVo selectOrderMapByPurchase(@Param(value = "purchaseCode") String purchaseCode,
                                             @Param(value = "purchaseLineCode") Integer purchaseLineCode);

    Integer selectSumByLineAndCode(@Param(value = "purchaseCode") String purchaseCode,
                                   @Param(value = "purchaseLineCode") Integer purchaseLineCode);

    Integer selectCountByLineAndCode(@Param(value = "purchaseCode") String purchaseCode,
                                     @Param(value = "purchaseLineCode") Integer purchaseLineCode);

    Integer selectSumOfReceiveNum(@Param(value = "gooderCode") String gooderCode,
                                  @Param(value = "goodCode") String goodCode,
                                  @Param(value = "receiveId") String receiveId);

    Integer selectSumOfRefuseNum(@Param(value = "gooderCode") String gooderCode,
                                 @Param(value = "goodCode") String goodCode,
                                 @Param(value = "receiveId") String receiveId);

    Integer selectSumOfExpectNum(@Param(value = "gooderCode") String gooderCode,
                                 @Param(value = "goodCode") String goodCode,
                                 @Param(value = "receiveId") String receiveId);



    String selectSeatCode(@Param(value = "gooderCode") String gooderCode,
                          @Param(value = "goodCode") String goodCode,
                          @Param(value = "receiveId") String receiveId);

    List<Integer> selectRfidByGooderAndCode(@Param(value = "gooderCode") String gooderCode,
                                            @Param(value = "goodCode") String goodCode);

    Integer selectExpectNum(@Param(value = "purchaseCode") String purchaseCode,
                            @Param(value = "purchaseLineCode") Integer purchaseLineCode);


    Integer selectReceiveNum(@Param(value = "purchaseCode") String purchaseCode,
                             @Param(value = "purchaseLineCode") Integer purchaseLineCode);

    Integer selectRefuseNum(@Param(value = "purchaseCode") String purchaseCode,
                            @Param(value = "purchaseLineCode") Integer purchaseLineCode);


    List<Integer> selectDetailStatusByReceiveId(String receiveId);


    void updateSameGoodStatus(@Param(value = "gooderCode") String gooderCode,
                              @Param(value = "goodCode") String goodCode,
                              @Param(value = "receiveId") String receiveId,
                              @Param(value = "status") Integer status);


    void updateSamePurchaseStatus(@Param(value = "purchaseCode") String purchaseCode,
                                  @Param(value = "purchaseLineCode") Integer purchaseLineCode,
                                  @Param(value = "status") Integer status);

    void updateOtherStatus(@Param(value = "gooderCode") String gooderCode,
                           @Param(value = "goodCode") String goodCode,
                           @Param(value = "receiveId") String receiveId);

    void updateOtherPurchaseStatus(@Param(value = "purchaseCode") String purchaseCode,
                                   @Param(value = "purchaseLineCode") Integer purchaseLineCode);


    List<ReceiveDetail> selectReceiveDetailByPurchaseCode(String purchaseCode);


    List<String> selectAllSeatCode(@Param(value = "gooderCode") String gooderCode,
                                   @Param(value = "goodCode") String goodCode,
                                   @Param(value = "placeId") String placeId);


    List<String> selectSeatCodeByGooderCode(@Param(value = "gooderCode") String gooderCode,
                                            @Param(value = "placeId") String placeId);


    List<String> selectAllSeatCodes();

    List<ReceiveDetail> selectDetailByReceiveIdAndCode(@Param(value = "receiveId") String receiveId,
                                           @Param(value = "gooderCode") String gooderCode,
                                           @Param(value = "goodCode") String goodCode);


}