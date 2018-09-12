package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.dao.GoodMapper;
import com.nice.good.model.Provider;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.PackManageMapper;
import com.nice.good.model.PackManage;
import com.nice.good.service.PackManageService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.util.List;


@Service
@Transactional
public class PackManageServiceImpl extends AbstractService<PackManage> implements PackManageService {

    @Resource
    private PackManageMapper packManageMapper;


    @Resource
    private GoodMapper goodMapper;

    @Override
    public String packManageAdd(PackManage packManage,String placeId, String userId) throws Exception {
        String packId = packManage.getPackId();
        String errorMsg = "";
        if (packId == null) {
            //新增操作
            packId = IdsUtils.getOrderId();
            packManage.setPackId(packId);

            errorMsg = CheckUniqueFiled(packManage, packId,placeId, errorMsg);
            if (StringUtils.isNotBlank(errorMsg)) {
                return errorMsg;
            }
            packManage.setCreateId(userId);
            packManage.setModifyId(userId);
            packManage.setCreateDate(TimeStampUtils.getTimeStamp());
            packManage.setModifyDate(TimeStampUtils.getTimeStamp());

            packManage.setPlaceId(placeId);

            packManageMapper.insert(packManage);
        } else {
            //修改操作

            errorMsg = CheckUniqueFiled(packManage, packId,placeId, errorMsg);
            if (StringUtils.isNotBlank(errorMsg)) {
                return errorMsg;
            }

            packManage.setModifyId(userId);
            packManage.setModifyDate(TimeStampUtils.getTimeStamp());
            packManageMapper.updateByPrimaryKey(packManage);
        }
        return errorMsg;
    }

    /**
     * 包装管理删除操作
     *
     * @param packIds
     * @return
     */
    @Override
    public String deleteByPackId(List<String> packIds) {
        String errorMsg = "";

        for (String packId : packIds) {

            if (packId == null) {
                continue;

            }

            //如果在货品档案中有关联,则不能删除
            PackManage packManage = packManageMapper.selectByPrimaryKey(packId);
            if (packManage == null) {
                continue;
            }

            List<String> goodIds = goodMapper.selectGoodIdByPackCode(packManage.getPackCode());
            if (goodIds != null && goodIds.size() > 0) {
                errorMsg += "包装" + packManage.getPackCode() + "在货品档案中有关联,删除失败!\n";
                continue;
            }

            //最后执行删除操作
            packManageMapper.deleteByPrimaryKey(packId);
        }

        return errorMsg;
    }

    /**
     * 包装编码不可重复
     *
     * @param packManage
     * @param id
     * @param errorMsg
     * @return
     */

    private String CheckUniqueFiled(PackManage packManage, String id,String placeId, String errorMsg) {
        String packCode = packManage.getPackCode();
        if (packCode != null) {
            String packId = packManageMapper.findIdByPackCode(packCode,placeId);
            if (packId != null && !id.equals(packId)) {
                errorMsg = "包装编码不能重复!";
                return errorMsg;
            }
        }
        return errorMsg;
    }


}
