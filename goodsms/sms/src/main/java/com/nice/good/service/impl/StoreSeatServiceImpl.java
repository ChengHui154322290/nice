package com.nice.good.service.impl;


import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.dao.SeatStockMapper;
import com.nice.good.dao.StoreSeatPictureMapper;
import com.nice.good.dto.StoreSeatDto;

import com.nice.good.dao.StoreSeatMapper;
import com.nice.good.model.Good;
import com.nice.good.model.GoodPicture;
import com.nice.good.model.StoreSeat;
import com.nice.good.model.StoreSeatPicture;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.service.StoreSeatService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 展厅档案-库位
 * @Author: fqs
 * @Date: 2018/3/23 10:33
 * @Version: 1.0
 */
@Service
@Transactional
public class StoreSeatServiceImpl extends AbstractService<StoreSeat> implements StoreSeatService {

    private static Logger log = LoggerFactory.getLogger(StoreSeatServiceImpl.class);

    @Resource
    private StoreSeatMapper storeSeatMapper;

    @Resource
    private SeatStockMapper seatStockMapper;


    @Resource
    private ReceiveDetailMapper receiveDetailMapper;


    //出库暂存位
    private static  final  String OUTSEAT="KWZC001";

    //收货暂存位
    private static  final  String RECEIVESEAT="SHZCKW001";

    //借领暂存位
    private static  final  String BORROWSEAT="JLZCKQ";

    //上架库位
    private static  final  String SHELFSEA="SJKW001";


    @Resource
    private StoreSeatPictureMapper storeSeatPictureMapper;




    @Override
    public String storeSeatAdd(StoreSeat storeSeat,String placeId, String userId) throws Exception {


        String seatId = IdsUtils.getOrderId();
        //判断字段是否重复

        String errorMsg = "";
        errorMsg = CheckUniqueFileds(storeSeat, seatId,placeId, errorMsg);
        if (StringUtils.isBlank(errorMsg)) {

            //图片绑定库位
            updateImg(storeSeat, userId, seatId);

            storeSeat.setSeatId(seatId);
            storeSeat.setCreater(userId);
            storeSeat.setModifier(userId);
            storeSeat.setCreatetime(TimeStampUtils.getTimeStamp());
            storeSeat.setModifytime(TimeStampUtils.getTimeStamp());


            storeSeat.setPlaceId(placeId);

            storeSeatMapper.insert(storeSeat);
        }
        return errorMsg;

    }

    private void updateImg(StoreSeat storeSeat, String userId, String seatId) {
        List<String> imgIds = storeSeat.getImgIds();
        if (imgIds != null && imgIds.size() > 0) {
            for (String imgId : imgIds) {
                if (imgId != null) {
                    Long newImgId = Long.valueOf(imgId.substring(0, imgId.lastIndexOf(".")));
                    //继续新增图片
                    StoreSeatPicture seatPicture = storeSeatPictureMapper.selectByPrimaryKey(newImgId);
                    if (seatPicture == null) {
                        continue;
                    }

                    String seatId1 = seatPicture.getSeatId();
                    if (seatId1!=null){
                        continue;
                    }

                    seatPicture.setSeatId(seatId);
                    seatPicture.setModifyId(userId);
                    seatPicture.setModifyDate(TimeStampUtils.getTimeStamp());
                    storeSeatPictureMapper.updateByPrimaryKey(seatPicture);
                }
            }
        }
    }


    @Override
    public String storeSeatUpdate(StoreSeat storeSeat,String placeId, String userId) {

        String errorMsg = "";
        String seatId = storeSeat.getSeatId();
//        errorMsg = CheckUniqueFileds(storeSeat,seatId , placeId,errorMsg);
        if (StringUtils.isBlank(errorMsg)) {

            String seatCode = storeSeat.getSeatCode();

            //获取库位标记之前的状态
            String seatTag = storeSeatMapper.selectSeatTagBySeatCode(seatCode,placeId);

            //库位标价暂不让修改
            //TODO
            //只有正常可以切换到冻结和无,冻结和无不可以切换到正常,无和冻结不可以切换
            if (!seatTag.equals(storeSeat.getSeatTag())){
                storeSeat.setSeatTag(seatTag);
            }

            //库位类型在使用时,则无法进行修改
            String seatType = storeSeatMapper.selectSeatTypeBySeatCode(seatCode,placeId);
            List<String> list1 = receiveDetailMapper.selectAllSeatCodes();
            List<String> list2 = seatStockMapper.selectSeatCodes(placeId);
            if (!seatType.equals(storeSeat.getSeatType())) {
                if (list1.contains(seatCode) || list2.contains(seatCode)){
                    errorMsg="库位占用中,库位类型修改失败!";
                    return errorMsg;
                }
            }

            storeSeat.setModifier(userId);
            storeSeat.setModifytime(TimeStampUtils.getTimeStamp());
            storeSeatMapper.updateByPrimaryKey(storeSeat);


            //图片绑定库位
            updateImg(storeSeat, userId, seatId);
        }
        return errorMsg;
    }

    /**
     * 库位删除操作
     *
     * @param seatIds
     * @return
     */
    @Override
    public String deleteBySeatId(List<String> seatIds, String placeId) {

        String errorMsg = "";

        for (String seatId : seatIds) {

            String seatCode = storeSeatMapper.findSeatCode(seatId);
            if (seatCode == null) {
                continue;
            }

            List<String> list = storeSeatMapper.findAllSeatCode(placeId);
            if (list.contains(seatCode)) {
                errorMsg += "库位" + seatCode + "占用中,删除失败!\n";
                continue ;
            }

            //如果是系统内置则无法删除
            if (OUTSEAT.equals(seatCode) || RECEIVESEAT.equals(seatCode) || seatCode.startsWith(BORROWSEAT) ||SHELFSEA.equals(seatCode)){

                errorMsg += "库位"+seatCode+"为系统内置,删除失败!\n";

                continue;
            }

            //删除图片
            storeSeatPictureMapper.deleteBySeatId(seatId);



            storeSeatMapper.deleteByPrimaryKey(seatId);
        }
        return errorMsg;

    }

    private String CheckUniqueFileds(StoreSeat storeSeat, String seatId,String placeId, String errorMsg) {

        String seatCode = storeSeat.getSeatCode();

        if (seatCode != null) {
            String id = storeSeatMapper.findIdBySeatCode(seatCode,placeId);
            if (id != null && !seatId.equals(id)) {
                errorMsg = "库位编码不能重复!";
                return errorMsg;
            }
        }
        return errorMsg;

    }

    /**
     * 通过excel导入库位信息
     *
     * @param seatList
     * @Author: ch
     */
    @Override
    @Transactional
    public void uploadExcelForAddStoreSeat(List<StoreSeatDto> seatList,String placeId, String userId) {
        StoreSeat storeSeat;
        List<StoreSeat> storeSeatList = new ArrayList<StoreSeat>();
        try {
            for (StoreSeatDto storeSeatDto : seatList) {
                storeSeat = new StoreSeat();
                BeanUtils.copyProperties(storeSeatDto, storeSeat);
                if(StringUtils.isNotBlank(storeSeatDto.getHeight())){
                    storeSeat.setHeight(Double.valueOf(storeSeatDto.getHeight()));
                }
                if(StringUtils.isNotBlank(storeSeatDto.getLength())){
                    storeSeat.setLength(Double.valueOf(storeSeatDto.getLength()));
                }
                if(StringUtils.isNotBlank(storeSeatDto.getWidth())){
                    storeSeat.setWidth(Double.valueOf(storeSeatDto.getWidth()));
                }
                if(StringUtils.isNotBlank(storeSeatDto.getSeatCapacity())){
                    storeSeat.setSeatCapacity(Math.round(Float.valueOf(storeSeatDto.getSeatCapacity())));
                }
                String seatId = IdsUtils.getOrderId();
                storeSeat.setMixGood(storeSeatDto.getMixGoodInt());
                storeSeat.setMixBatch(storeSeatDto.getMixBatchInt());
                storeSeat.setSeatStatus(storeSeatDto.getSeatStatusInt());
                storeSeat.setSeatId(seatId);
                storeSeat.setCreater(userId);
                storeSeat.setModifier(userId);

                //关联场地id
                storeSeat.setPlaceId(placeId);
                storeSeatMapper.insert(storeSeat);
            }
        } catch (Exception e) {
            log.error("导入库位数据出错：" + e.getMessage().toString());
            e.printStackTrace();
        }

    }


}
