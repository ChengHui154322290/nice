package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.Good;
import com.nice.good.model.GoodPicture;
import com.nice.good.vo.GoodDetailVo;
import com.nice.good.vo.GoodVo;
import com.nice.good.wx_model.GoodSkuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface GoodMapper extends Mapper<Good> {

	String findIdByGoodCode(@Param(value = "gooderCode") String gooderCode,
							@Param(value = "goodCode") String goodCode);

	String findIdByCommodityCode(@Param(value = "gooderCode") String gooderCode,
								 @Param(value = "commodityCode") String commodityCode);

	List<Good> selectByConditionMap(Map conditionMap);

	List<String> selectGoodByGooderCode(String gooderCode);

	List<GoodVo> findByGoodCondition(Map goodMap);

	String selectCommodityCodeByGoodCode(String goodCode);

	List<String> selectGoodIdByPackCode(String packCode);

	Good selectByGooderCodeAndGoodCode(@Param(value = "gooderCode") String gooderCode, @Param(value = "goodCode") String goodCode);


	List<String> selectgoodCode();


	List<String> selectGooderCode();

	List<String> selectBygoodCode(String gooderCode);

	Good selectgoodNameModel(String goodCode);


	String selectCommodityCode(@Param(value = "gooderCode") String gooderCode, @Param(value = "goodCode") String goodCode);


	GoodDetailVo selectGoodDetail(@Param(value = "gooderCode") String gooderCode, @Param(value = "goodCode") String goodCode);

	Good getOneByGoodCode(String goodCode);


    void deleteBySkuCode(String skuCode);

    String selectBySpuCode(String spuCode);

    void deleteBySpuCode(String spuCode);
}