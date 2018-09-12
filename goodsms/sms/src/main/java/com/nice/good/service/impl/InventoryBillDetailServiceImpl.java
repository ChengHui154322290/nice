package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.InventoryBillDetailMapper;
import com.nice.good.model.InventoryBillDetail;
import com.nice.good.service.InventoryBillDetailService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/06/07.
 */
@Service
public class InventoryBillDetailServiceImpl extends AbstractService<InventoryBillDetail> implements InventoryBillDetailService {
    @Resource
    private InventoryBillDetailMapper inventoryBillDetailMapper;


    @Override
    public void inventoryBillDetailUpdate(InventoryBillDetail inventoryBillDetail, String userId) {

        inventoryBillDetail.setModifyId(userId);
        inventoryBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());
        inventoryBillDetailMapper.updateByPrimaryKey(inventoryBillDetail);
    }



    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 对应的 status 值，返回 HashSet<T> 集合   -- 2018/06/13  16:04  rk
     * @param inventory_bill_id
     * @return
     */
    @Override
    public List<Integer> selectStatusByInventoryBillId(String inventory_bill_id) {
        return inventoryBillDetailMapper.selectStatusByInventoryBillId(inventory_bill_id);
    }


    /**
     * 通过 inventory_bill_id(盘点单编号) 查询 k_inventory_bill_detail 表中相应的 List<T> InventoryBillDetail   --  2018/06/12  15:30  rk
     * @param inventoryBillId
     * @return
     */
    @Override
    public List<InventoryBillDetail> selectInventoryBillDetailByInventoryBillId(String inventoryBillId) {
        return inventoryBillDetailMapper.selectInventoryBillDetailByInventoryBillId(inventoryBillId);
    }




}
