package com.nice.good.service;

import com.nice.good.model.Gooder;
import com.nice.good.core.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/25.
 */
public interface GooderService extends Service<Gooder> {
    String gooderAdd(Gooder gooder,String placeId, String userId,HttpServletRequest request) throws Exception;

    String deleteByGooderId(List<String> gooderIds);

    /**
     * 在 n_gooder 表中， 通过 gooder_name 查询 gooder_code   2018/05/21  16:47
     *
     * @param gooder_name
     * @return
     */
    String findGooderCodeByGooderName(String gooder_name);


    /**
     * 查询 g_good表 中的 gooder_code -- 除去重复的编码
     *
     * @return
     */
    List<String> findGooderCodes();

    /**
     * 查询 n_gooder表 中的所有 gooder_name    -- 2018/05/21  11:31 rk
     *
     * @return
     */
    List<String> findAllGooderNames();

    /**
     * 查询 n_gooder表 中的所有 gooder_code    -- 2018/05/21  11:31 rk
     *
     * @return
     */
    List<String> selectGooderCodes(String placeId);


}
