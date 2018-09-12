package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.GooderMapper;
import com.nice.good.dao.ReceiveDetailMapper;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.GoodArea;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GooderAreaMapper;
import com.nice.good.model.GooderArea;
import com.nice.good.service.GooderAreaService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.AreaVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
@Service
@Transactional
public class GooderAreaServiceImpl extends AbstractService<GooderArea> implements GooderAreaService {

    @Resource
    private GooderAreaMapper gooderAreaMapper;


    /**
     * 货主档案,库区删除
     * @param areaIds
     * @return
     */

    @Override
    public String deleteByAreaId(List<String> areaIds,String placeId) {

        String errorMsg="";

        for (String areaId:areaIds) {

            GooderArea gooderArea = gooderAreaMapper.selectByPrimaryKey(areaId);
            if (gooderArea == null) {
                continue;
            }

//            //首选区不能删除
//            Integer fistArea = gooderArea.getFistArea();
//
//            System.out.println("我是首选区 "+fistArea);
//            if (fistArea!=null && fistArea == 1) {
//                errorMsg += "首选区不能删除!\n";
//                continue;
//            }

            gooderAreaMapper.deleteAreaById(areaId,placeId);
        }

        return errorMsg;
    }

    /**
     * 修改新增库区
     * @param areaVos
     * @param gooderId
     * @param userId
     * @return
     */
    @Override
    public String addGooderArea(List<AreaVo> areaVos, String gooderId, String placeId,String userId) throws Exception {

        String errorMsg="";

        //如果没有首选区,默认第 一个为首选区
        List<GooderArea> gooderAreas = gooderAreaMapper.selectAreaByGooderId(gooderId,placeId);

        Set<String> set = new HashSet<>();
        if (gooderAreas!=null && gooderAreas.size()>0){
            for(GooderArea gooderArea:gooderAreas) {
                set.add(gooderArea.getAreaCode());
            }
        }


            //如果当前库区已存在,则不能重复添加
        Boolean flag=true;
        if (gooderAreas==null || gooderAreas.size()==0){
            flag=false;
        }


        int num=0;
        for (AreaVo areaVo : areaVos) {

            if (set.contains(areaVo.getAreaCode())){
                errorMsg+="库区"+areaVo.getAreaCode()+"已存在,添加失败!\n";
                continue;
            }

            GooderArea gooderArea = new GooderArea();
            //关联主表
            gooderArea.setGooderId(gooderId);
            gooderArea.setAreaId(IdsUtils.getOrderId());
            gooderArea.setAreaCode(areaVo.getAreaCode());
            gooderArea.setAreaName(areaVo.getAreaName());
            gooderArea.setAreaType(areaVo.getAreaType());
            gooderArea.setCreatetime(TimeStampUtils.getTimeStamp());
            gooderArea.setCreater(userId);
            gooderArea.setModifier(userId);
            gooderArea.setModifytime(TimeStampUtils.getTimeStamp());

            //关联场地id
            gooderArea.setPlaceId(placeId);

            if (flag==false && num==0){
                gooderArea.setFistArea(1);
                flag=true;
            }else {
                gooderArea.setFistArea(0);
            }

            gooderAreaMapper.insert(gooderArea);
            num=++num;

        }
        
        return errorMsg;
    }


    /**
     * 修改库区以后刷新操作
     * @param gooderId
     * @return
     */
    @Override
    public Result listGooderArea(String gooderId,String placeId) {
        Result result = new Result();
        List<GooderArea> gooderAreas = gooderAreaMapper.selectAreaByGooderId(gooderId,placeId);
        result.setData(gooderAreas);
        return result;
    }


}
