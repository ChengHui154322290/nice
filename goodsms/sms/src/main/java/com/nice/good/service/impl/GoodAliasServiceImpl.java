package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GoodAliasMapper;
import com.nice.good.model.GoodAlias;
import com.nice.good.service.GoodAliasService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description: 货品档案-货品别名
 * @Author: fqs
 * @Date: 2018/3/23 10:40
 * @Version: 1.0
 */
@Service
@Transactional
public class GoodAliasServiceImpl extends AbstractService<GoodAlias> implements GoodAliasService {
    @Resource
    private GoodAliasMapper goodAliasMapper;

    @Override
    public String goodAliasAdd(GoodAlias goodAlias, String userId) throws Exception {

        String errorMsg = "";
        String aliasId = goodAlias.getAliasId();

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        if (aliasId == null) {
            //新增操作
             aliasId = IdsUtils.getOrderId();

            errorMsg = CheckUniqueFiled(goodAlias, aliasId, errorMsg);
            if (StringUtils.isBlank(errorMsg)) {

                //货品别名编码,货品别名类型不可重复
                goodAlias.setAliasId(aliasId);
                goodAlias.setCreater(userId);
                goodAlias.setModifier(userId);
                goodAlias.setCreatetime(timeStamp);
                goodAlias.setModifytime(timeStamp);

                goodAliasMapper.insert(goodAlias);
            } else {
                return errorMsg;
            }

        }else {
            //修改操作
            errorMsg = CheckUniqueFiled(goodAlias, aliasId, errorMsg);
            if (StringUtils.isBlank(errorMsg)) {
                goodAlias.setModifier(userId);
                goodAlias.setModifytime(timeStamp);
                goodAliasMapper.updateByPrimaryKey(goodAlias);
            }else {
                return errorMsg;
            }
        }
        return errorMsg;
    }


    private String CheckUniqueFiled(GoodAlias goodAlias, String id, String errorMsg) {
        String aliasCode = goodAlias.getAliasCode();
        String aliasType = goodAlias.getAliasType();
        if (aliasCode == null) {
            String aliasId = goodAliasMapper.findIdByAliasCode(aliasCode);
            if (aliasId != null && !id.equals(aliasId)) {
                errorMsg = "货品别名编码不能重复!";
                return errorMsg;
            }
        }
        if (aliasType != null) {
            String aliasId = goodAliasMapper.findIdByAliasType(aliasType);
            if (aliasId != null && !id.equals(aliasId)) {
                errorMsg = "货品别名类型不能重复!";
                return errorMsg;
            }

        }
        return errorMsg;
    }

}
