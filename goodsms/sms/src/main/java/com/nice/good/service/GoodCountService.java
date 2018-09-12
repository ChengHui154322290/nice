package com.nice.good.service;
import com.nice.good.core.Service;
import com.nice.good.wx_model.GoodCount;


/**
 * Created by CodeGenerator on 2018/08/06.
 */
public interface GoodCountService extends Service<GoodCount> {
      void goodCountAdd(GoodCount goodCount,String userId);
      void goodCountUpdate(GoodCount goodCount,String userId);

	GoodCount getGoodCount(String goodId);

	void newCountRecord(GoodCount goodCount);

	void addCollarNum(String goodId);
}
