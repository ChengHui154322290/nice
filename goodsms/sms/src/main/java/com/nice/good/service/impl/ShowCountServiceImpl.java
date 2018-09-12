package com.nice.good.service.impl;


import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.ShowCountMapper;
import com.nice.good.wx_model.ShowCount;
import com.nice.good.service.ShowCountService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
@Service
@Transactional
public class ShowCountServiceImpl extends AbstractService<ShowCount> implements ShowCountService {
	@Resource
	private ShowCountMapper showCountMapper;

	@Override
	public void showCountAdd(ShowCount showCount, String userId) {


		showCountMapper.insert(showCount);

	}


	@Override
	public void showCountUpdate(ShowCount showCount, String userId) {

		showCountMapper.updateByPrimaryKey(showCount);
	}

	@Override
	public ShowCount getByShowRoomId(Integer showRoomId) {
		return showCountMapper.getByShowRoomId(showRoomId);
	}

	@Override
	public void newCountRecord(ShowCount showCount) {
		showCountMapper.insert(showCount);
	}

	@Override
	public void addBespeakNum(Integer id) {
		showCountMapper.addBespeakNum(id);
	}

}
