package com.nice.good.service;
import com.nice.good.wx_model.ShowCount;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
public interface ShowCountService extends Service<ShowCount> {
      void showCountAdd(ShowCount showCount,String userId);
      void showCountUpdate(ShowCount showCount,String userId);

	ShowCount getByShowRoomId(Integer showRoomId);

	void newCountRecord(ShowCount showCount);

	void addBespeakNum(Integer id);

}
