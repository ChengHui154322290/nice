package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.core.Result;
import com.nice.good.dao.GoodMapper;
import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.dao.SeatStockMapper;
import com.nice.good.model.Good;
import com.nice.good.model.GooderArea;
import com.nice.good.service.ReceiveDetailService;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GoodAreaMapper;
import com.nice.good.model.GoodArea;
import com.nice.good.service.GoodAreaService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.AreaVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: 货品档案-库区设置
 * @Author: fqs
 * @Date: 2018/3/23 10:39
 * @Version: 1.0
 */
@Service
@Transactional
public class GoodAreaServiceImpl extends AbstractService<GoodArea> implements GoodAreaService {
    @Resource
    private GoodAreaMapper goodAreaMapper;


    @Override
    public void goodAreaAdd(GoodArea goodArea, String userId) throws Exception {

        goodArea.setAreaId(IdsUtils.getOrderId());
        goodArea.setCreater(userId);
        goodArea.setModifier(userId);
        goodArea.setCreatetime(TimeStampUtils.getTimeStamp());
        goodArea.setModifytime(TimeStampUtils.getTimeStamp());

        goodAreaMapper.insert(goodArea);

    }


    @Override
    public void goodAreaUpdate(GoodArea goodArea, String userId) {

        goodArea.setModifier(userId);
        goodArea.setModifytime(TimeStampUtils.getTimeStamp());
        goodAreaMapper.updateByPrimaryKey(goodArea);
    }


    /**
     * 修改新增库区
     * @param goodArea
     * @param userId
     */


    /**
     * 修改删除库区
     *
     * @param areaIds
     * @return
     */
    @Override
    public String deleteByGoodAreaId(List<String> areaIds,String placeId) {

        String errorMsg = "";

       for (String areaId: areaIds) {

//           //如果该库区在使用中则不能删除
//           GoodArea goodArea = goodAreaMapper.selectByPrimaryKey(areaId);
//           if (goodArea == null) {
//               continue;
//           }
//
//           //首选区不能删除
//           Integer fistArea = goodArea.getFistArea();
//           if (fistArea!=null && fistArea == 1) {
//               errorMsg += "首选区不能删除!\n";
//               continue;
//           }

           goodAreaMapper.deleteAreaById(areaId,placeId);
       }

        return errorMsg;
    }

    /**
     * 修改新增库区
     *
     * @param areaVos
     * @param userId
     * @return
     */
    @Override
    public String addGoodArea(List<AreaVo> areaVos, String goodId,String placeId, String userId) throws Exception {

        String errorMsg = "";

        //如果没有首选区,默认第 一个为首选区
        List<GoodArea> goodAreas = goodAreaMapper.selectAreaByGoodIdAndPlaceId(goodId,placeId);

        Set<String> set = new HashSet<>();
        if (goodAreas!=null && goodAreas.size()>0){
            for(GoodArea GoodArea:goodAreas) {
                set.add(GoodArea.getAreaCode());
            }
        }

        Boolean flag=true;
        if (goodAreas==null || goodAreas.size()==0){
            flag=false;
        }

        int num=0;
        for (AreaVo areaVo : areaVos) {

            //不能重复添加同一个库区
            if (set.contains(areaVo.getAreaCode())){
                errorMsg+="库区"+areaVo.getAreaCode()+"已存在,添加失败!\n";
                continue;
            }

            GoodArea goodArea = new GoodArea();
            //关联主表
            goodArea.setGoodId(goodId);
            goodArea.setAreaId(IdsUtils.getOrderId());
            goodArea.setAreaCode(areaVo.getAreaCode());
            goodArea.setAreaName(areaVo.getAreaName());
            goodArea.setAreaType(areaVo.getAreaType());
            goodArea.setCreatetime(TimeStampUtils.getTimeStamp());
            goodArea.setCreater(userId);
            goodArea.setModifier(userId);
            goodArea.setModifytime(TimeStampUtils.getTimeStamp());


            if (flag==false && num==0){
                goodArea.setFistArea(1);
                flag=true;
            }else {
                goodArea.setFistArea(0);
            }

            //关联场地id
            goodArea.setPlaceId(placeId);

            goodAreaMapper.insert(goodArea);
            num=++num;

        }

        return errorMsg;
    }


    /**
     * 修改库区以后刷新操作
     * @param goodId
     * @return
     */
    @Override
    public Result listGoodArea(String goodId,String placeId) {
        Result result = new Result();
        List<GoodArea> goodAreas = goodAreaMapper.selectAreaByGoodIdAndPlaceId(goodId,placeId);
        result.setData(goodAreas);
        return result;
    }

}
