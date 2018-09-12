package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.dao.StoreSeatMapper;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.StoreAreaMapper;
import com.nice.good.model.StoreArea;
import com.nice.good.service.StoreAreaService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


/**
 * @Description: 展厅档案-库区档案
 * @Author: fqs
 * @Date: 2018/3/23 10:32
 * @Version: 1.0
 */
@Service
@Transactional
public class StoreAreaServiceImpl extends AbstractService<StoreArea> implements StoreAreaService {
    @Resource
    private StoreAreaMapper storeAreaMapper;
    @Resource
    private StoreSeatMapper storeSeatMapper;


    //出库暂存区
    private static  final  String OUTAREA="KQZC001";

    //收货暂存区
    private static  final  String RECEIVEAREA="SHZCKQ001";

    //借领暂存区
    private static  final  String BORROWAREA="JLZCKQ001";

    //上架库区
    private static  final  String SHELFAREA="SJKQ001";

    @Override
    public String storeAreaAdd(StoreArea storeArea, String placeId, String userId) throws Exception {

        String areaId = IdsUtils.getOrderId();
        //库区编号,库区名称不可重复
        String errorMsg = "";
        errorMsg = CheckUniqueFiled(storeArea, areaId,placeId, errorMsg);
        if (StringUtils.isBlank(errorMsg)) {
            storeArea.setAreaId(areaId);
            storeArea.setCreater(userId);
            storeArea.setModifier(userId);
            storeArea.setCreatetime(TimeStampUtils.getTimeStamp());
            storeArea.setModifytime(TimeStampUtils.getTimeStamp());

            //关联场地
            storeArea.setPlaceId(placeId);

            storeAreaMapper.insert(storeArea);
        }
        return errorMsg;

    }

    private String CheckUniqueFiled(StoreArea storeArea, String id,String placeId, String errorMsg) {
        String areaCode = storeArea.getAreaCode();
        if (areaCode != null) {
            String areaId = storeAreaMapper.findIdByAreaCode(areaCode,placeId);
            if (areaId != null && !id.equals(areaId)) {
                errorMsg = "库区编码不能重复!";
                return errorMsg;
            }
        }

        return errorMsg;
    }


    @Override
    public String storeAreaUpdate(StoreArea storeArea,String placeId, String userId) {

        String id = storeArea.getAreaId();

        //库区编号,库区名称不可重复
        String errorMsg = "";
        errorMsg = CheckUniqueFiled(storeArea, id, placeId,errorMsg);
        if (StringUtils.isBlank(errorMsg)) {

            storeArea.setModifier(userId);
            storeArea.setModifytime(TimeStampUtils.getTimeStamp());
            storeAreaMapper.updateByPrimaryKey(storeArea);
        }
        return errorMsg;
    }

    /**
     * 库区删除操作
     *
     * @param areaIds
     * @return
     */
    @Override
    public String deleteByAreaId(List<String> areaIds,String placeId) {

        String errorMsg = "";
        for (String areaId:areaIds) {
            if (areaId==null){
                continue;
            }

            StoreArea storeArea = storeAreaMapper.selectByPrimaryKey(areaId);
            String areaCode = storeArea.getAreaCode();

            //库区下面如果有库位不能删除
            List<String> seatCodes = storeAreaMapper.findAllSeatCode(areaId,placeId);
            if (seatCodes != null && seatCodes.size()>0) {
                errorMsg += "库区"+areaCode+"占用中,删除失败!\n";
                continue;
            }

            //当前库区下所有库位
            List<String> list1 = storeSeatMapper.selectByAreaCode(areaCode, placeId);

            //收货单和库位库存中的库位
            List<String> list2 = storeAreaMapper.findSeatCode(placeId);

            if (list2!=null && list2!=null && list1.retainAll(list2)) {
                errorMsg += "库区"+areaCode+"占用中,删除失败!\n";
                continue;
            }

            //如果是系统内置则无法删除
            if (OUTAREA.equals(areaCode) || RECEIVEAREA.equals(areaCode) ||BORROWAREA.equals(areaCode) ||SHELFAREA.equals(areaCode)){

                errorMsg += "库区"+areaCode+"为系统内置,删除失败!\n";

                continue;
            }


            storeAreaMapper.deleteByPrimaryKey(areaId);

        }
        return errorMsg;

    }

}
