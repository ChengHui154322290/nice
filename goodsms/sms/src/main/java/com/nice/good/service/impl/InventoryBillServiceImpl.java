package com.nice.good.service.impl;


import com.nice.good.dao.*;
import com.nice.good.model.AdjustBill;
import com.nice.good.model.AdjustBillDetail;
import com.nice.good.model.InventoryBillDetail;
import com.nice.good.utils.ExcelImportUtils;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.model.InventoryBill;
import com.nice.good.service.InventoryBillService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.CodeAndDateVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/06.
 */
@Service
public class InventoryBillServiceImpl extends AbstractService<InventoryBill> implements InventoryBillService {

    @Resource
    private AdjustBillMapper adjustBillMapper;


    @Resource
    private AdjustBillDetailMapper adjustBillDetailMapper;

    @Resource
    private InventoryBillMapper inventoryBillMapper;


    @Resource
    private InventoryBillDetailMapper inventoryBillDetailMapper;



    @Resource
    private SeatStockMapper seatStockMapper;


    @Override
    @Transactional
    public String generateAdjustBills(List<InventoryBill> inventoryBills, String placeId, String userId) {

        String errorMsg = "";


        for (InventoryBill inventoryBill : inventoryBills) {
            // 通过 isDifferent(是否有差异: 0-无差异， 1-有差异) 属性判断 盘点单是否可生成调整单.
            if (inventoryBill.getIsDifferent() != 1) {
                errorMsg += "盘点单" + inventoryBill.getInventoryBillId() + "无差异量,生成调整单失败!";
                continue;
            }

            if (inventoryBill.getInventoryStatus() == 2) {
                errorMsg += "盘点单" + inventoryBill.getInventoryBillId() + "单据状态不对,操作失败!";
                continue;
            }

            Timestamp timeStamp = TimeStampUtils.getTimeStamp();

            // 设置调整单编号
            String adjustBillCode = getAdjustCode();

            String inventoryBillId = inventoryBill.getInventoryBillId();

            // 根据 盘点单编号 查询 盘点明细数据 List<T>
            List<InventoryBillDetail> inventoryBillDetails = inventoryBillDetailMapper.selectInventoryBillDetailByInventoryBillId(inventoryBillId);

            Boolean flag = false;
            String adjustBillId = IdsUtils.getOrderId();

            for (InventoryBillDetail inventoryBillDetail : inventoryBillDetails) {
                // 通过 status(盘点明细表中--盘点状态：0-未开始,1-盘点中,2-已完成')  判断 盘点明细 是否可以生成 调整明细 .
                if (inventoryBillDetail.getStatus() != 0) {
                    continue;
                }

                //现有量
                Integer existingQuantity = inventoryBillDetail.getExistingQuantity();
                if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                    continue;
                }

                if (existingQuantity == 0) {

                    continue;
                }

                //盘点量
                Integer inventoryQuantity = inventoryBillDetail.getInventoryQuantity();

                if (inventoryQuantity == null || StringUtils.isBlank(inventoryQuantity.toString())) {
                    continue;
                }

                if (inventoryQuantity == 0) {
                    continue;
                }

                //差异量不能为0
                Integer diffQuantity = inventoryBillDetail.getDiffQuantity();
                if (diffQuantity == 0) {
                    continue;
                }


                //库存有变动不能操作
                String gooderCode = inventoryBillDetail.getGooderCode();
                String goodCode = inventoryBillDetail.getGoodCode();
                String orgCode = inventoryBillDetail.getOrgCode();
                String providerCode = inventoryBillDetail.getProviderCode();
                String seatCode = inventoryBillDetail.getSeatCode();


                Integer nowNum = seatStockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode, seatCode, placeId);
                if (nowNum == null || !nowNum.equals(existingQuantity)) {
                    continue;
                }

                // 初始化 AdjustBillDetail.java(调整明细实体类) 对象，将 盘点明细 转成 调整明细
                AdjustBillDetail adjustBillDetail = new AdjustBillDetail();
                adjustBillDetail.setAdjustBillId(adjustBillId);
                adjustBillDetail.setDetailId(IdsUtils.getOrderId());
                adjustBillDetail.setProviderCode(inventoryBillDetail.getProviderCode());
                adjustBillDetail.setOrgCode(inventoryBillDetail.getOrgCode());
                adjustBillDetail.setGooderCode(inventoryBillDetail.getGooderCode());
                adjustBillDetail.setGooderName(inventoryBillDetail.getGooderName());
                adjustBillDetail.setGoodCode(inventoryBillDetail.getGoodCode());
                adjustBillDetail.setGoodName(inventoryBillDetail.getGoodName());
                adjustBillDetail.setSeatCode(inventoryBillDetail.getSeatCode());
                adjustBillDetail.setStatus(0);
                adjustBillDetail.setCreateId(userId);
                adjustBillDetail.setCreateDate(TimeStampUtils.getTimeStamp());
                adjustBillDetail.setModifyId(userId);
                adjustBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());

                //差异量等于调整量
                adjustBillDetail.setAdjustQuantity(diffQuantity);

                //调整单现有量等于盘点单现有量
                adjustBillDetail.setExistingQuantity(inventoryBillDetail.getExistingQuantity());

                //关联场地id
                adjustBillDetail.setPlaceId(placeId);

                // 新增 调整明细单
                adjustBillDetailMapper.insert(adjustBillDetail);


                //盘点单状态变更
                inventoryBillDetail.setStatus(2);
                inventoryBillDetail.setModifyId(userId);
                inventoryBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());
                inventoryBillDetailMapper.updateByPrimaryKey(inventoryBillDetail);

                flag = true;


            }

            if (flag) {

                // 初始化 AdjustBill.java(调整单) 对象, 将盘点单转化成调整单.
                AdjustBill adjustBill = new AdjustBill();

                adjustBill.setAdjustBillId(adjustBillId);
                adjustBill.setAdjustBillCode(adjustBillCode);
                adjustBill.setPlaceId(placeId);
                adjustBill.setModifyDate(timeStamp);
                adjustBill.setModifyId(userId);
                adjustBill.setAdjustStatus(0);
                adjustBill.setAdjustType(1);
                adjustBill.setCreateDate(timeStamp);
                adjustBill.setCreateId(userId);
                adjustBill.setId(null);
                adjustBill.setSourceBillCode(inventoryBill.getInventoryBillCode());

                //关联场地id
                adjustBill.setPlaceId(placeId);

                // 新增调整单
                adjustBillMapper.insert(adjustBill);

                updateInventory(userId, inventoryBillId);
            }
        }

        return errorMsg;
    }

    /**
     * 新增盘点单
     *
     * @param inventoryBill
     * @param placeId
     * @param userId
     * @return
     */

    @Override
    @Transactional
    public String saveInventoryBill(InventoryBill inventoryBill, String placeId, String userId) {
        String errorMsg = "";

        List<InventoryBillDetail> inventoryBillDetails = inventoryBill.getInventoryBillDetails();

        if (inventoryBillDetails == null || inventoryBillDetails.size() == 0) {
            errorMsg = "盘点明细单不能为空!";
            return errorMsg;
        }


        List<String> list = new ArrayList<>();


        for (InventoryBillDetail detail : inventoryBillDetails) {
            //现有量
            Integer existingQuantity = detail.getExistingQuantity();
            //盘点量
            Integer inventoryQuantity = detail.getInventoryQuantity();

            //差异量
            Integer diffQuantity = detail.getDiffQuantity();

            if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                errorMsg = "盘点明细现有量不能为空!";
                return errorMsg;
            }

            if (existingQuantity == 0) {
                errorMsg = "盘点明细现有量不能为0!";
                return errorMsg;
            }


            if (inventoryQuantity == null || StringUtils.isBlank(inventoryQuantity.toString())) {
                errorMsg = "盘点明细盘点量不能为空!";
                return errorMsg;
            }

//            if (inventoryQuantity == 0) {
//                errorMsg = "盘点明细盘点量不能为0!";
//                return errorMsg;
//            }

//            if (diffQuantity == 0) {
//                errorMsg = "盘点明细差异量不能为0!";
//                return errorMsg;
//            }
//
//
//            if (inventoryQuantity < 0) {
//                errorMsg = "盘点明细盘点量不能小于0!";
//                return errorMsg;
//            }


            String gooderCode = detail.getGooderCode();
            String goodCode = detail.getGoodCode();
            String orgCode = detail.getOrgCode();
            String providerCode = detail.getProviderCode();
            String seatCode = detail.getSeatCode();

            String goodMessage = gooderCode + goodCode + orgCode + providerCode + seatCode;

            if (list.contains(goodMessage)) {
                errorMsg = "新增的明细中存在重复的记录，添加失败!";
                return errorMsg;
            }

            list.add(goodMessage);

        }


        //新增操作
        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        String inventoryBillId = inventoryBill.getInventoryBillId();

        if (inventoryBillId == null) {

            String inventoryBillCode = inventoryBill.getInventoryBillCode();

            // 通过 inventoryBillId 查询 InventoryBill.java 实体类， 判断其是否为 null
            InventoryBill inventoryBillIsNull = inventoryBillMapper.selectInventoryBillCode(inventoryBillCode);
            // 判断 inventoryBillId 是否为 null
            if (inventoryBillIsNull != null) {
                inventoryBillCode = getInventoryCode();
            }

            inventoryBillId = IdsUtils.getOrderId();
            inventoryBill.setInventoryBillId(inventoryBillId);
            inventoryBill.setInventoryBillCode(inventoryBillCode);
            inventoryBill.setInventoryStatus(0);

            //关联场地id
            inventoryBill.setPlaceId(placeId);

            inventoryBillMapper.insert(inventoryBill);

            for (InventoryBillDetail detail : inventoryBillDetails) {

                // 设置 盘点单明细 的 盘点单编号
                detail.setInventoryBillId(inventoryBillId);
                detail.setStatus(0);
                detail.setDetailId(IdsUtils.getOrderId());
                detail.setCreateId(userId);
                detail.setCreateDate(timeStamp);
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);


                //关联场地id
                detail.setPlaceId(placeId);

                // 新增 InventoryBillDetail.java 类 --- 盘点明细单
                inventoryBillDetailMapper.insert(detail);
            }
        } else {
            //修改操作
            inventoryBillMapper.updateByPrimaryKey(inventoryBill);
            for (InventoryBillDetail detail : inventoryBillDetails) {
                if (detail.getStatus() != 0) {
                    continue;
                }
                detail.setModifyId(userId);
                detail.setModifyDate(timeStamp);
                inventoryBillDetailMapper.updateByPrimaryKey(detail);
            }

        }

        return errorMsg;
    }

    /**
     * 复盘操作
     *
     * @param inventoryBills
     * @param placeId
     * @param userId
     * @return
     */

    @Override
    @Transactional
    public String reInventory(List<InventoryBill> inventoryBills, String placeId, String userId) {

        String errorMsg = "";

        for (InventoryBill inventoryBill : inventoryBills) {

            //只有已完成的单据才能复盘
            Integer inventoryStatus = inventoryBill.getInventoryStatus();
            if (inventoryStatus != 2) {
                errorMsg += "盘点单" + inventoryBill.getInventoryBillId() + "单据状态不对,操作失败!";
                continue;
            }


            String newInventoryBillId = IdsUtils.getOrderId();

            String oldInventoryBillId = inventoryBill.getInventoryBillId();

            List<InventoryBillDetail> inventoryBillDetails = inventoryBillDetailMapper.selectInventoryBillDetailByInventoryBillId(oldInventoryBillId);

            if (inventoryBillDetails == null || inventoryBillDetails.size() == 0) {
                continue;
            }

            // 循环获取 inventoryBillDetails 中元素，进行二次盘点.

            int num = 0;
            for (InventoryBillDetail inventoryBillDetail : inventoryBillDetails) {

                //现有量
                Integer existingQuantity = inventoryBillDetail.getExistingQuantity();
                if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                    continue;
                }

                if (existingQuantity == 0) {
                    continue;
                }

                //盘点量
                Integer inventoryQuantity = inventoryBillDetail.getInventoryQuantity();

                if (inventoryQuantity == null || StringUtils.isBlank(inventoryQuantity.toString())) {
                    continue;
                }

                if (inventoryQuantity == 0) {
                    continue;
                }

                //库存有变动不能操作
                String gooderCode = inventoryBillDetail.getGooderCode();
                String goodCode = inventoryBillDetail.getGoodCode();
                String orgCode = inventoryBillDetail.getOrgCode();
                String providerCode = inventoryBillDetail.getProviderCode();
                String seatCode = inventoryBillDetail.getSeatCode();


                Integer nowNum = seatStockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode, seatCode, placeId);
                if (nowNum == null || !nowNum.equals(existingQuantity)) {
                    continue;
                }


                Timestamp timeStamp = TimeStampUtils.getTimeStamp();
                inventoryBillDetail.setId(null);
                // 设置明细表中的：盘点单编号
                inventoryBillDetail.setInventoryBillId(newInventoryBillId);
                // 设置明细表中的：盘点状态 0-未开始
                inventoryBillDetail.setStatus(0);
                // 新增 盘点明细单
                inventoryBillDetail.setDetailId(IdsUtils.getOrderId());
                inventoryBillDetail.setCreateId(userId);
                inventoryBillDetail.setModifyId(userId);
                inventoryBillDetail.setModifyDate(timeStamp);

                //盘点量置空
                inventoryBillDetail.setInventoryQuantity(0);
                //差异量置空
                inventoryBillDetail.setDiffQuantity(0);

                //关联场地id
                inventoryBillDetail.setPlaceId(placeId);

                inventoryBillDetailMapper.insert(inventoryBillDetail);
                ++num;
            }


            if (num > 0) {
                inventoryBill.setId(null);

                // 获取 inventoryBillId 盘点单号
                String newInventoryBillCode = getInventoryCode();
                // 获取 原有的 inventoryBillId 盘点单号
                String oldInventoryBillCode = inventoryBill.getInventoryBillCode();

                // 设置 源盘点单编号： 将 盘点单编号 赋值给 源盘点单号
                inventoryBill.setSourceBillCode(oldInventoryBillCode);
                // 设置 盘点单编号： 将 Result类中 date属性 的值赋值给 盘点单编号
                inventoryBill.setInventoryBillCode(newInventoryBillCode);
                // 设置 盘点状态：0-未开始
                inventoryBill.setInventoryStatus(0);
                // 设置 盘点类型：0-二次盘点
                inventoryBill.setInventoryType(0);
                // 新增 盘点单
                Timestamp timeStamp = TimeStampUtils.getTimeStamp();
                inventoryBill.setCreateId(userId);
                inventoryBill.setCreateDate(timeStamp);
                inventoryBill.setModifyId(userId);
                inventoryBill.setModifyDate(timeStamp);

                inventoryBill.setInventoryBillId(newInventoryBillId);

                //关联场地id
                inventoryBill.setPlaceId(placeId);

                inventoryBillMapper.insert(inventoryBill);
            }

        }

        return errorMsg;
    }


    /**
     * 生成调整单
     *
     * @param inventoryBillDetails
     * @param userId
     * @return
     */
    @Override
    public String updateInventoryBillDetails(List<InventoryBillDetail> inventoryBillDetails, String placeId, String userId) {


        String errorMsg = "";

        Boolean flag = false;
        String inventoryBillId = null;
        String adjustBillId = IdsUtils.getOrderId();
        for (InventoryBillDetail inventoryBillDetail : inventoryBillDetails) {
            // 通过 status(盘点明细表中--盘点状态：0-未开始,1-盘点中,2-已完成')  判断 盘点明细 是否可以生成 调整明细 .
            if (inventoryBillDetail.getStatus() != 0) {
                errorMsg += "第" + inventoryBillDetail.getId() + "条记录单据状态不对,操作失败!\n";
                continue;
            }

            //现有量
            Integer existingQuantity = inventoryBillDetail.getExistingQuantity();
            if (existingQuantity == null || StringUtils.isBlank(existingQuantity.toString())) {
                errorMsg += "第 " + inventoryBillDetail.getId() + "条记录现有量不能为空,操作失败!\n";
                continue;
            }

            if (existingQuantity == 0) {
                errorMsg += "第 " + inventoryBillDetail.getId() + "条记录现有量不能为0,操作失败!\n";
                continue;
            }

            Integer inventoryQuantity = inventoryBillDetail.getInventoryQuantity();

            if (inventoryQuantity == null || StringUtils.isBlank(inventoryQuantity.toString())) {
                errorMsg += "第" + inventoryBillDetail.getId() + "条记录盘点量为空,操作失败!\n";
                continue;
            }

//            if (inventoryQuantity == 0) {
//                errorMsg += "第" + inventoryBillDetail.getId() + "条记录盘点量为0,操作失败!\n";
//                continue;
//            }

            //如果现有量和盘点量相同,则差异量为0,不可生成调整单
//            if (existingQuantity.equals(inventoryQuantity)) {
//                errorMsg += "第" + inventoryBillDetail.getId() + "条记录差异量为0,操作失败!\n";
//                continue;
//            }

            //盘点之前先执行更新操作，同步数据
            inventoryBillDetailMapper.updateByPrimaryKey(inventoryBillDetail);


            //库存有变动不能操作
            String gooderCode = inventoryBillDetail.getGooderCode();
            String goodCode = inventoryBillDetail.getGoodCode();
            String orgCode = inventoryBillDetail.getOrgCode();
            String providerCode = inventoryBillDetail.getProviderCode();
            String seatCode = inventoryBillDetail.getSeatCode();


            Integer nowNum = seatStockMapper.selectNowNumByCode(gooderCode, goodCode, orgCode, providerCode, seatCode, placeId);
            if (nowNum == null || !nowNum.equals(existingQuantity)) {
                errorMsg += "第 " + inventoryBillDetail.getId() + "条记录货品库存有变动,操作失败!\n";
                continue;
            }


            // 初始化 AdjustBillDetail.java(调整明细单)， 将 inventoryBillDetail(盘点明细单) 转换成 调整明细单.
            AdjustBillDetail adjustBillDetail = new AdjustBillDetail();

            adjustBillDetail.setAdjustBillId(adjustBillId);

            adjustBillDetail.setDetailId(IdsUtils.getOrderId());
            adjustBillDetail.setProviderCode(inventoryBillDetail.getProviderCode());
            adjustBillDetail.setOrgCode(inventoryBillDetail.getOrgCode());
            adjustBillDetail.setGooderCode(inventoryBillDetail.getGooderCode());
            adjustBillDetail.setGooderName(inventoryBillDetail.getGooderName());
            adjustBillDetail.setGoodCode(inventoryBillDetail.getGoodCode());
            adjustBillDetail.setGoodName(inventoryBillDetail.getGoodName());
            adjustBillDetail.setSeatCode(inventoryBillDetail.getSeatCode());
            adjustBillDetail.setStatus(0);
            adjustBillDetail.setCreateId(userId);
            adjustBillDetail.setCreateDate(TimeStampUtils.getTimeStamp());
            adjustBillDetail.setModifyId(userId);
            adjustBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());

            //调整单现有量
            adjustBillDetail.setExistingQuantity(inventoryBillDetail.getExistingQuantity());

            //调整量等于差异量
            adjustBillDetail.setAdjustQuantity(inventoryBillDetail.getDiffQuantity());

            // 新增 调整明细单
            adjustBillDetailMapper.insert(adjustBillDetail);

            //盘点单明细状态更新
            inventoryBillDetail.setStatus(2);

            //关联场地id
            inventoryBillDetail.setPlaceId(placeId);

            inventoryBillDetail.setModifyId(userId);

            inventoryBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());

            inventoryBillDetailMapper.updateByPrimaryKey(inventoryBillDetail);

            //盘点单状态更新
            flag = true;
            inventoryBillId = inventoryBillDetail.getInventoryBillId();
        }


        if (flag) {

            AdjustBill adjustBill = new AdjustBill();

            adjustBill.setAdjustBillId(adjustBillId);

            Timestamp timeStamp = TimeStampUtils.getTimeStamp();

            adjustBill.setAdjustBillCode(getAdjustCode());
            adjustBill.setPlaceId(placeId);
            adjustBill.setModifyDate(timeStamp);
            adjustBill.setModifyId(userId);
            adjustBill.setAdjustStatus(0);
            adjustBill.setAdjustType(1);
            adjustBill.setCreateDate(timeStamp);
            adjustBill.setCreateId(userId);
            adjustBill.setId(null);

            InventoryBill inventoryBill = inventoryBillMapper.selectByPrimaryKey(inventoryBillId);

            adjustBill.setSourceBillCode(inventoryBill.getInventoryBillCode());

            //关联场地id
            adjustBill.setPlaceId(placeId);

            // 新增调整单
            adjustBillMapper.insert(adjustBill);

            updateInventory(userId, inventoryBillId);
        }
        return errorMsg;
    }

    /**
     * 盘点单导出
     *
     * @param inventoryBills
     * @return
     */

    @Override
    public String inventoryBillExport(List<InventoryBill> inventoryBills, HttpServletRequest
            request, HttpServletResponse response) {
        String errorMsg = "";
        //用IO将数据导出
        OutputStream out = null;

        try {

            if (inventoryBills != null && inventoryBills.size() > 0) {
                // 导出文件的标题
                String title = "盘点单" + ".xls";
                // 设置表格标题行
                String[] headers = new String[]{"序号", "盘点单号", "源盘点单", "盘点状态", "盘点类型", "是否差异", "是否盲盘", "创建人", "创建时间", "操作人", "操作时间", "说明"};

                List<Object[]> dataList = new ArrayList<Object[]>();
                Object[] objs = null;
                for (InventoryBill inventoryBill : inventoryBills) {
                    objs = new Object[headers.length];
                    // 序号
                    objs[0] = inventoryBill.getId();
                    // 盘点单编号
                    objs[1] = inventoryBill.getInventoryBillId();
                    // 源盘点单
                    objs[2] = inventoryBill.getSourceBillCode();


                    // 盘点状态
                    switch (inventoryBill.getInventoryStatus()) {
                        case 0:
                            objs[3] = "未开始";
                            break;
                        case 1:
                            objs[3] = "盘点中";
                            break;
                        case 2:
                            objs[3] = "已完成";
                            break;
                        default:
                            objs[3] = "";
                            break;
                    }

                    // 盘点类型
                    switch (inventoryBill.getInventoryType()) {
                        case 0:
                            objs[4] = "二次盘点";
                            break;
                        case 1:
                            objs[4] = "RF盘点";
                            break;
                        default:
                            objs[4] = "";
                            break;
                    }

                    // objs[5] = inventoryBill.getIsDifferent();  // 是否差异
                    switch (inventoryBill.getIsDifferent()) {
                        case 0:
                            objs[5] = "无差异";
                            break;
                        case 1:
                            objs[5] = "有差异";
                            break;
                        default:
                            objs[5] = "";
                            break;
                    }

                    // objs[6] = inventoryBill.getIsBlindInventory();  // 是否盲盘
                    switch (inventoryBill.getIsBlindInventory()) {
                        case 0:
                            objs[6] = "否";
                            break;
                        case 1:
                            objs[6] = "是";
                            break;
                        default:
                            objs[6] = "";
                            break;
                    }

                    // 创建人
                    objs[7] = inventoryBill.getCreateId();
                    // 创建时间
                    objs[8] = inventoryBill.getCreateDate();
                    // 操作人
                    objs[9] = inventoryBill.getModifyId();
                    // 操作时间
                    objs[10] = inventoryBill.getModifyDate();
                    // 说明
                    objs[11] = inventoryBill.getRemark();
                    //将数据添加到excel表格中
                    dataList.add(objs);
                }

                //防止中文乱码
                String headStr = "attachment; filename=\"" + new String(title.getBytes("gb2312"), "ISO8859-1") + "\"";
                response.setContentType("octets/stream");
                response.setContentType("APPLICATION/OCTET-STREAM");
                response.setHeader("Content-Disposition", headStr);

                out = response.getOutputStream();

                //ExportExcel ex = new ExportExcel(title, headers, dataList);//有标题
                //没有标题
                ExcelImportUtils.ExportExcelSeedBack ex = new ExcelImportUtils.ExportExcelSeedBack(title, headers, dataList);
                ex.export(out);

            }
        } catch (Exception e) {
            e.printStackTrace();
            errorMsg = "导出失败!";
        }


        return errorMsg;
    }

    private void updateInventory(String userId, String inventoryBillId) {

        Integer status = getStatus(inventoryBillId);

        InventoryBill inventoryBill = inventoryBillMapper.selectByPrimaryKey(inventoryBillId);
        inventoryBill.setInventoryStatus(status);
        inventoryBill.setModifyDate(TimeStampUtils.getTimeStamp());
        inventoryBill.setModifyId(userId);

        inventoryBillMapper.updateByPrimaryKey(inventoryBill);
    }


    private String getInventoryCode() {
        Integer id = inventoryBillMapper.selectMaxId();
        if (id == null || id == 0) {
            id = 1;
        } else {
            id++;
        }

        return "CC" + getBody(id);

    }


    public String getAdjustCode() {
        Integer id = adjustBillMapper.selectMaxId();
        if (id == null) {
            id = 1;
        } else {
            id++;
        }

        return "RD" + getBody(id);
    }


    /**
     * 8位日期 + 6位流水号
     *
     * @param id
     * @return
     */
    private String getBody(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()) + String.format("%06d", id);
    }


    /**
     * 获取 status 的 Set<Integer> 集合   -- 2018/06/13  17:58  rk
     *
     * @param inventoryBillId
     * @return
     */
    public Integer getStatus(String inventoryBillId) {

        List<Integer> counts = inventoryBillDetailMapper.selectStatusByInventoryBillId(inventoryBillId);
        if (counts==null || counts.size() == 0) {
            return 0;
        }

        if (counts.size() > 1) {
            return 1;
        } else {
            return counts.get(0);
        }
    }


    /**
     * 通过 inventoryBillId(盘点单编号) 查询 k_inventory_bill 表中对应的数据    -- 2018/05/12  15:13  rk
     *
     * @param inventoryBillId
     * @return
     */
    @Override
    public InventoryBill selectInventoryBillByKeyId(String inventoryBillId) {
        return inventoryBillMapper.selectInventoryBillByKeyId(inventoryBillId);
    }


    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 k_inventory_bill 表中对应的 inventory_status(盘点状态)   -- 2018/06/11  15:04  rk
     *
     * @param inventoryBillId
     * @return
     */
    @Override
    public Integer selectInventoryStatus(String inventoryBillId) {
        return inventoryBillMapper.selectInventoryStatus(inventoryBillId);
    }


    /**
     * 查询 k_inventory_bill 表中最大的 id 值    -- 2018/06/06  16:05 rk
     *
     * @return
     */
    @Override
    public Integer selectMaxId() {
        return inventoryBillMapper.selectMaxId();
    }

    @Override
    public CodeAndDateVo getInventoryIdAndDate(String userId) {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        CodeAndDateVo codeAndDateVo = new CodeAndDateVo();
        codeAndDateVo.setWorkCode(getInventoryCode());
        codeAndDateVo.setCreateDate(timeStamp);
        codeAndDateVo.setModifyDate(timeStamp);

        codeAndDateVo.setCreateId(userId);
        codeAndDateVo.setModifyId(userId);

        return codeAndDateVo;
    }
}
