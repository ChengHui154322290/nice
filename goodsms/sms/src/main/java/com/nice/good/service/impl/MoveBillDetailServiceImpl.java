package com.nice.good.service.impl;


import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.MoveBillDetailMapper;
import com.nice.good.model.MoveBillDetail;
import com.nice.good.service.MoveBillDetailService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


/**
 * Created by CodeGenerator on 2018/06/05.
 */
@Service
@Transactional
public class MoveBillDetailServiceImpl extends AbstractService<MoveBillDetail> implements MoveBillDetailService {
    @Resource
    private MoveBillDetailMapper moveBillDetailMapper;


    /**
     * 通过 moveBillId(移动单编号) 查询 k_move_bill_detail 表中的 status 值   -- 2018/06/14  10:04  rk
     * @param moveBillId
     * @return
     */
    @Override
    public List<Integer> selectStatusByMoveBillId(String moveBillId) {
        return moveBillDetailMapper.selectStatusByMoveBillId(moveBillId);
    }

    /**
     * 更新 k_move_bill_detail 表中数据  -- 2018/06/09  11:23  rk
     *
     * @param moveBillDetail
     */
    @Override
    public void moveBillDetailUpdate(MoveBillDetail moveBillDetail) {
        moveBillDetailMapper.updateByPrimaryKey(moveBillDetail);
    }

    /**
     * 通过 detail_id(主键id) 查询 k_move_bill_detail(移动明细表) 中对应的 status 值  -- 2018/06/09  09:43  rk
     *
     * @param detailId
     * @return
     */
    @Override
    public Integer selectStatusByDetailId(String detailId) {
        return moveBillDetailMapper.selectStatusByDetailId(detailId);
    }

    /**
     * 通过 move_bill_id(移动单编号) 查询 MoveBillDetail.java 信息   -- 2018/06/08  16:08  rk
     *
     * @param move_bill_id
     * @return
     */
    @Override
    public List<MoveBillDetail> selectMoveBillDetailByMoveBillId(String move_bill_id) {
        return moveBillDetailMapper.selectMoveBillDetailByMoveBillId(move_bill_id);
    }

    /**
     * 新增移动明细单  -- 2018/06/05  14:58 rk
     *
     * @param moveBillDetail
     */
    @Override
    public void moveBillDetailAdd(MoveBillDetail moveBillDetail) throws Exception {

        // 设置 k_move_bill_detail 表的主键 detail_id
        moveBillDetail.setDetailId(IdsUtils.getOrderId());

        moveBillDetailMapper.insert(moveBillDetail);
    }

    @Override
    public void moveBillDetailAdd(MoveBillDetail moveBillDetail, String userId) throws Exception {

        // 设置 主键id
        moveBillDetail.setDetailId(IdsUtils.getOrderId());
        // 设置 创建人
        moveBillDetail.setCreateId(userId);
        // 设置 修改人
        moveBillDetail.setModifyId(userId);
        // 设置 创建时间
        moveBillDetail.setCreateDate(TimeStampUtils.getTimeStamp());
        // 设置 修改时间
        moveBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());

        moveBillDetailMapper.insert(moveBillDetail);

    }


    @Override
    public void moveBillDetailUpdate(MoveBillDetail moveBillDetail, String userId) {

        moveBillDetail.setModifyId(userId);
        moveBillDetail.setModifyDate(TimeStampUtils.getTimeStamp());
        moveBillDetailMapper.updateByPrimaryKey(moveBillDetail);
    }

}
