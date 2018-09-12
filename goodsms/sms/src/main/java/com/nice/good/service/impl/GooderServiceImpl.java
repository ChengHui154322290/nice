package com.nice.good.service.impl;

import java.util.Date;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.dao.*;
import com.nice.good.model.*;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.service.GooderService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description: 货主档案
 * @Author: fqs
 * @Date: 2018/3/23 10:36
 * @Version: 1.0
 */
@Service
@Transactional
public class GooderServiceImpl extends AbstractService<Gooder> implements GooderService {
    @Resource
    private GooderMapper gooderMapper;

    @Resource
    private GoodMapper goodMapper;


    @Resource
    private GooderConfigMapper gooderConfigMapper;


    @Resource
    private GooderAreaMapper gooderAreaMapper;


    @Resource
    private GooderTransportMapper gooderTransportMapper;


    @Resource
    private StockMapper stockMapper;

    @Resource
    private SysPlaceMapper sysPlaceMapper;


    @Resource
    private UserGooderMapper userGooderMapper;


    private static final String seatCode = "KWZC001";


    /**
     * 在 n_gooder 表中， 通过 gooder_name 查询 gooder_code   2018/05/21  16:47
     *
     * @param gooder_name
     * @return
     */
    @Override
    public String findGooderCodeByGooderName(String gooder_name) {
        return gooderMapper.findGooderCodeByGooderName(gooder_name);
    }

    /**
     * 查询 n_gooder表 中的所有 gooder_code
     *
     * @return
     */
    @Override
    public List<String> selectGooderCodes(String placeId) {
        return stockMapper.selectGooderCodes(placeId);
    }


    /**
     * 查询 n_gooder表 中的所有 gooder_name     -- 2018/05/21  11:31 rk
     *
     * @return
     */
    @Override
    public List<String> findAllGooderNames() {
        return gooderMapper.findAllGooderNames();
    }

    @Override
    @Transactional
    public String gooderAdd(Gooder gooder, String placeId, String userId, HttpServletRequest request) throws Exception {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();

        String gooderId = gooder.getGooderId();

//        String gooderId=gooder.getGooderCode();

        String errorMsg = null;


        //配置表
        GooderConfig gooderConfig = gooder.getGooderConfig();
        if (gooderConfig != null) {
            if (StringUtils.isBlank(gooderConfig.getOutWaitSeat())) {
                errorMsg = "出库待选库位不能为空!";
                return errorMsg;
            }
        }


        List<GooderArea> gooderAreas = gooder.getGooderAreas();
//        String putStrategy = gooderConfig.getPutStrategy();
//        if ("查找货品设置库区中可用库位".equals(putStrategy)) {
//            if (gooderAreas == null || gooderAreas.size() == 0) {
//                errorMsg = "库区设置不能为空!";
//                return errorMsg;
//            }
//        }


        if (gooderId == null) {
            //新增操作
//            gooderId = IdsUtils.getOrderId();

            gooderId = gooder.getGooderCode();
            //货主编码不允许重复
            errorMsg = CheckFieldUnique(gooder, gooderId, errorMsg);
            if (errorMsg != null) {
                return errorMsg;
            }

            //货主名称不能重复
            String gooderName = gooder.getGooderName();
            if (StringUtils.isBlank(gooderName)) {
                errorMsg = "货主名称不能为空!";
                return errorMsg;
            }


            //货主名称不可以重复
            String gooderId2 = gooderMapper.selectGooderIdByGooderName(gooderName);
            if (gooderId2 != null) {
                errorMsg = "货主名称不能重复!";
                return errorMsg;
            }

            gooder.setGooderId(gooderId);
            gooder.setCreater(userId);
            gooder.setModifier(userId);
            gooder.setCreatetime(timeStamp);
            gooder.setModifytime(timeStamp);

            gooderMapper.insert(gooder);

            //如果当前用户是超级管理员，则同步权限，同步session
            synchronizeAdmin(gooder, userId, request);


            if (gooderConfig != null) {

                gooderConfig.setConfigId(IdsUtils.getOrderId());
                gooderConfig.setCreater(userId);
                gooderConfig.setCreatetime(timeStamp);
                gooderConfig.setModifier(userId);
                gooderConfig.setModifytime(timeStamp);


                //关联场地Id
                gooderConfig.setPlaceId(placeId);

                //关联主表
                gooderConfig.setGooderId(gooderId);

                gooderConfigMapper.insert(gooderConfig);


                //根据场地个数生成默认配置
                generateGooderConfig(placeId, userId, gooderId);

            }


            //关联库区设置表
            if (gooderAreas != null && gooderAreas.size() > 0) {

                int num = 0;
                for (GooderArea gooderArea : gooderAreas) {
                    if (gooderArea != null) {
                        if (num == 0) {
                            gooderArea.setFistArea(1);
                            ++num;
                        } else {
                            gooderArea.setFistArea(0);
                        }

                        gooderArea.setAreaId(IdsUtils.getOrderId());
                        gooderArea.setCreater(userId);
                        gooderArea.setCreatetime(timeStamp);
                        gooderArea.setModifier(userId);
                        gooderArea.setModifytime(timeStamp);


                        //关联场地Id
                        gooderArea.setPlaceId(placeId);

                        //关联主表
                        gooderArea.setGooderId(gooderId);
                        gooderAreaMapper.insert(gooderArea);
                    }
                }
            }


            //关联物流模版表
            List<GooderTransport> transports = gooder.getGooderTransports();
            if (transports != null && transports.size() > 0) {
                for (GooderTransport transport : transports) {
                    if (transport != null) {
                        transport.setTransId(IdsUtils.getOrderId());
                        transport.setCreater(userId);
                        transport.setCreatetime(timeStamp);
                        transport.setModifier(userId);
                        transport.setModifytime(timeStamp);

                        //关联主表
                        transport.setGooderId(gooderId);
                        gooderTransportMapper.insert(transport);
                    }
                }
            }


        } else {
            //修改操作
            //货主编码不允许重复,
//            errorMsg = CheckFieldUnique(gooder, gooderId, errorMsg);
//            if (errorMsg != null) {
//                return errorMsg;
//            }

            //货主名称不能重复
            String gooderName = gooder.getGooderName();
            if (StringUtils.isBlank(gooderName)) {
                errorMsg = "货主名称不能为空!";
                return errorMsg;
            }

            String gooderId1 = gooder.getGooderId();

            //货主名称不可以重复
            String gooderId2 = gooderMapper.selectGooderIdByGooderName(gooderName);
            if (gooderId2 != null && !gooderId2.equals(gooderId1)) {
                errorMsg = "货主名称不能重复!";
                return errorMsg;
            }

            gooder.setModifier(userId);
            gooder.setModifytime(timeStamp);
            gooderMapper.updateByPrimaryKey(gooder);


            //配置表更新操作
            if (gooderConfig != null) {
                if (StringUtils.isBlank(gooderConfig.getOutWaitSeat())) {
                    errorMsg = "出库待选库位不能为空!";
                    return errorMsg;
                }
            }

            if (gooderConfig != null) {
                gooderConfig.setModifier(userId);
                gooderConfig.setModifytime(timeStamp);

                gooderConfigMapper.updateByPrimaryKey(gooderConfig);

            }


            //库区设置表更新
            for (GooderArea gooderArea : gooderAreas) {
                if (gooderArea != null) {
                    if (gooderArea.getAreaId() == null) {
                        //新增操作
                        gooderArea.setAreaId(IdsUtils.getOrderId());
                        gooderArea.setCreater(userId);
                        gooderArea.setCreatetime(timeStamp);
                        gooderArea.setModifier(userId);
                        gooderArea.setModifytime(timeStamp);

                        //关联主表
                        gooderArea.setGooderId(gooderId);
                        gooderAreaMapper.insert(gooderArea);
                    } else {
                        //修改操作
                        gooderArea.setModifier(userId);
                        gooderArea.setModifytime(timeStamp);
                        gooderAreaMapper.updateByPrimaryKey(gooderArea);
                    }
                }
            }


            //更新物流模版表
            List<GooderTransport> transports = gooder.getGooderTransports();
            if (transports != null && transports.size() > 0) {
                for (GooderTransport transport : transports) {
                    if (transport != null) {
                        if (transport.getTransId() != null) {

                            transport.setModifier(userId);
                            transport.setModifytime(timeStamp);

                            gooderTransportMapper.updateByPrimaryKey(transport);
                        } else {
                            //新增操作
                            transport.setTransId(IdsUtils.getOrderId());
                            transport.setCreater(userId);
                            transport.setCreatetime(timeStamp);
                            transport.setModifier(userId);
                            transport.setModifytime(timeStamp);

                            //关联主表
                            transport.setGooderId(gooderId);
                            gooderTransportMapper.insert(transport);
                        }
                    }
                }
            }

        }
        return errorMsg;


    }

    private void synchronizeAdmin(Gooder gooder, String userId, HttpServletRequest request) {
        if ("admin".equals(userId)) {

            //同步货主权限
            UserGooder userGooder = new UserGooder();
            userGooder.setKeyId(IdsUtils.getOrderId());
            userGooder.setUsername("admin");
            userGooder.setGooderCode(gooder.getGooderCode());
            userGooder.setRemark("系统设置");
            userGooder.setCreateId("system");
            userGooder.setCreateDate(new Date());
            userGooder.setModifyId("system");
            userGooder.setModifyDate(new Date());

            userGooderMapper.insert(userGooder);


            List<String> gooderCodeList = userGooderMapper.selectGooderCodeByUserName(userId);

            if (gooderCodeList != null && gooderCodeList.size() != 0) {


                request.getSession().setAttribute("gooderCodes", gooderCodeList);

                System.out.println("我是所有货主的权限: " + gooderCodeList.toString());

            } else {
                request.getSession().setAttribute("gooderCodes", null);
            }


            // 设置 session 超时机制
            HttpSession sessionOutTime = request.getSession(true);

            // 将sessionOutTime 的值设置为 12小时
            sessionOutTime.setMaxInactiveInterval(12 * 60 * 60);

        }
    }

    private void generateGooderConfig(String placeId, String userId, String gooderId) {
        List<SysPlace> places = sysPlaceMapper.selectAll();
        if (places != null && places.size() > 0) {
            for (SysPlace place : places) {
                String placeId1 = place.getPlaceId();
                if (!placeId.equals(placeId1)) {

                    GooderConfig gooderConfig1 = new GooderConfig();
                    gooderConfig1.setConfigId(IdsUtils.getOrderId());
                    gooderConfig1.setNoPoReceive("允许");
                    gooderConfig1.setBeyondVerify(0);
                    gooderConfig1.setIsQuality(1);
                    gooderConfig1.setRfidGather(1);
                    gooderConfig1.setOutWaitSeat(seatCode);
                    gooderConfig1.setPutStrategy("查找货品设置库区中可用库位");
                    gooderConfig1.setAllotStrategy("根据库位名称顺序分配库位");
                    gooderConfig1.setRunRule(1);
                    gooderConfig1.setStockRun("批号");
                    gooderConfig1.setAutoDeal(0);
                    gooderConfig1.setMixBoxNum(0);
                    gooderConfig1.setCreater(userId);
                    gooderConfig1.setCreatetime(new Date());
                    gooderConfig1.setModifier(userId);
                    gooderConfig1.setModifytime(new Date());
                    gooderConfig1.setGooderId(gooderId);
                    gooderConfig1.setPlaceId(placeId1);

                    gooderConfigMapper.insert(gooderConfig1);

                }
            }
        }
    }

    /**
     * 删除操作
     *
     * @param gooderIds
     * @return
     */

    @Override
    public String deleteByGooderId(List<String> gooderIds) {

        String errorMsg = "";

        for (String gooderId : gooderIds) {
            if (gooderId == null) {
                continue;
            }
            Gooder gooder = gooderMapper.selectByPrimaryKey(gooderId);

            if (gooder == null) {
                continue;
            }
            //如果在货品档案中有关联则不允许删除

            String gooderCode = gooder.getGooderCode();
            List<String> goodIds = goodMapper.selectGoodByGooderCode(gooderCode);
            if (goodIds != null && goodIds.size() > 0) {
                errorMsg += "货主" + gooderCode + "和货品档案有关联,删除失败!\n";
                continue;
            }

            //如果货主权限被分配则无法删除
            List<String> list = userGooderMapper.selectUserNameByGooderCode(gooderCode);
            if (list != null && list.size() > 0) {
                errorMsg += "货主" + gooderCode + "和货主权限有关联,删除失败!\n";
                continue;
            }

            //删除和商品表关联的明细表记录
            //删除配置表
            gooderConfigMapper.deleteByGooderId(gooderId);
            //删除库区设置表
            gooderAreaMapper.deleteByGooderId(gooderId);
            //删除物流模板配置表
            gooderTransportMapper.deleteByGooderId(gooderId);

            //删除主表
            gooderMapper.deleteByPrimaryKey(gooderId);

        }
        return errorMsg;
    }


    private String CheckFieldUnique(Gooder gooder, String gooderId, String errorMsg) {
        String gooderCode = gooder.getGooderCode();

        if (StringUtils.isNotBlank(gooderCode)) {
            String gooderId1 = gooderMapper.findIdByGooderCode(gooderCode);
            if (gooderId1 != null && gooderId.equals(gooderId1)) {

                errorMsg = "货主编码不允许重复!";

                return errorMsg;
            }
        }
        return errorMsg;
    }

    /**
     * 查询 g_good表 中的 gooder_code -- 除去重复的编码
     *
     * @return
     */
    @Override
    public List<String> findGooderCodes() {
        return gooderMapper.findGooderCodes();
    }
}
