package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.SeatStockMapper;
import com.nice.good.model.SeatStock;
import com.nice.good.service.SeatStockService;
import com.nice.good.core.AbstractService;
import com.nice.good.vo.SeatStockVo;
import com.nice.good.vo.StockNumVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * Created by CodeGenerator on 2018/04/09.
 */
@Service
@Transactional
public class SeatStockServiceImpl extends AbstractService<SeatStock> implements SeatStockService {
	@Resource
	private SeatStockMapper seatStockMapper;

	@Override
	public void seatStockAdd(SeatStock seatStock, String userId) {


		seatStock.setCreateId(userId);
		seatStock.setModifyId(userId);
		seatStock.setCreateDate(TimeStampUtils.getTimeStamp());
		seatStock.setModifyDate(TimeStampUtils.getTimeStamp());

		seatStockMapper.insert(seatStock);

	}


	@Override
	public void seatStockUpdate(SeatStock seatStock, String userId) {

		seatStock.setModifyId(userId);
		seatStock.setModifyDate(TimeStampUtils.getTimeStamp());
		seatStockMapper.updateByPrimaryKey(seatStock);
	}


	@Override
	public StockNumVo countSeatNum(Map<String, Object> conditionMap) {
		return seatStockMapper.countSeatNum(conditionMap);
	}

	@Override
	public List<SeatStock> getByParams(Map<String, Object> params) {
		return seatStockMapper.getByParams(params);
	}

	@Override
	public List<SeatStockVo> findByBar(SeatStockVo seatStockvo) {
		return seatStockMapper.findByBar(seatStockvo);
	}


}
