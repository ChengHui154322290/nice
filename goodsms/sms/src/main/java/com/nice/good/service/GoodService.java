package com.nice.good.service;

import com.nice.good.core.Result;
import com.nice.good.dto.GoodImportDto;
import com.nice.good.model.Good;
import com.nice.good.core.Service;
import com.nice.good.model.GoodAlias;
import com.nice.good.model.GoodArea;
import com.nice.good.model.GoodConfig;
import com.nice.good.vo.GoodVo;
import com.nice.good.wx_model.GoodSkuVo;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface GoodService extends Service<Good> {

    String goodAdd(Good good,String placeId, String userId) throws Exception;

    String deleteGoodByGoodId(List<String> goodId,String placeId);

    List<Good> findByConditions(Good good);

    String delGoodAlias(List<String> aliasIds);

    String addGoodAlias(List<GoodAlias> goodAliasList, String goodId, String userId) throws Exception;

    Result listGoodAlias(String goodId);

    String uploadExcelForAddStoreSeat(List<GoodImportDto> success,String placeId, String userId) throws Exception;

    void parseImg(String userId);

}
