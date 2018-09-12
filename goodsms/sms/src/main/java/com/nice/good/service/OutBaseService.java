package com.nice.good.service;

import com.nice.good.core.Result;
import com.nice.good.dto.OutBaseDto;
import com.nice.good.dto.OutDetailDto;
import com.nice.good.dto.GatherDto;
import com.nice.good.model.OutBase;
import com.nice.good.core.Service;
import com.nice.good.model.SeatStock;
import com.nice.good.model.Stock;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
public interface OutBaseService extends Service<OutBase> {
	void outBaseAdd(OutBase outBase,String placeId, String userId) throws Exception;

	String hangUp(List<String> baseIds, Integer hangUp, String userId);

	String deleteByBaseId(List<String> baseIds);

	String generateMainOutPick(List<OutBase> outBases,String placeId, String userId) throws Exception;

	String outMainSend(List<OutBase> outBases, String placeId,String userId);

	String cancelMainOutPick(List<OutBase> outBases,String placeId, String userId) throws Exception;

	/**
	 * 通过最大 id 值查询 OutBase.java中的 SendCode(发货编号)  --  rk  2018/05/02
	 *
	 * @return
	 */
	OutBase findSendCodeByMaxId();

	Result rfidGather(OutBase outBase, String userId);

	String rfidGatherSave(GatherDto gatherDto,String placeId, String userId);


	String cancelOutBase(List<OutBase> outBases, String userId);

	String addOutDetail(List<SeatStock> stocks, String baseId, String userId) throws Exception;

	String delOutDetail(List<String> detailIds,String userId);

	Result listOutDetail(String baseId);

	void uploadExcelForAddStoreSeat(List<OutBaseDto> success, List<OutDetailDto> success1, String userId) throws Exception;

	List<OutBase> findByOutBase(OutBase outBase,String placeId, List<String> gooderCodes);
}
