package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.dao.OrderMapper;
import com.nice.good.dao.ReceiveOrderMapper;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.ProviderMapper;
import com.nice.good.model.Provider;
import com.nice.good.service.ProviderService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description: 供应商档案
 * @Author: fqs
 * @Date: 2018/3/23 10:31
 * @Version: 1.0
 */
@Service
@Transactional
public class ProviderServiceImpl extends AbstractService<Provider> implements ProviderService {
    @Resource
    private ProviderMapper providerMapper;


    @Resource
    private OrderMapper orderMapper;


    @Resource
    private ReceiveOrderMapper receiveOrderMapper;


    @Override
    public String providerAdd(Provider provider, String userId) throws Exception {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        String errorMsg = null;
        String providerId = provider.getProviderId();
        if (providerId == null) {
            //新增操作
            providerId = IdsUtils.getOrderId();
            //供应商编码不可重复
            errorMsg = CheckUniqueFiled(provider, providerId, errorMsg);
            if (errorMsg != null) {
                return errorMsg;
            }
            provider.setProviderId(providerId);
            provider.setCreater(userId);
            provider.setModifier(userId);
            provider.setCreatetime(timeStamp);
            provider.setModifytime(timeStamp);

            providerMapper.insert(provider);

        } else {
            //修改操作
            errorMsg = CheckUniqueFiled(provider, providerId, errorMsg);
            if (errorMsg != null) {
                return errorMsg;
            }
            provider.setModifier(userId);
            provider.setModifytime(timeStamp);
            providerMapper.updateByPrimaryKey(provider);

        }

        return errorMsg;

    }

    @Override
    public String deleteByProviderId(List<String> providerIds) {

        String errorMsg = "";

        for (String providerId: providerIds) {

            if(providerId==null){
                continue;
            }
            Provider provider = providerMapper.selectByPrimaryKey(providerId);
            if (provider == null) {
                continue;
            }

            String providerCode = provider.getProviderCode();

            //如果采购单中有供应商信息,则不可以删除
            List<String> orderIds = orderMapper.selectOrderByProviderCode(providerCode);
            if (orderIds != null && orderIds.size() > 0) {
                errorMsg += "供应商"+providerCode+"和采购单有关联,删除失败!\n";
                continue;
            }

            //如果收货单中有供应商信息,则不可以删除
            List<String> receiveIds = receiveOrderMapper.selectReceiveByProviderCode(providerCode);
            if (receiveIds != null && receiveIds.size() > 0) {
                errorMsg += "供应商"+providerCode+"和收货单有关联,删除失败!\n";
                continue;
            }


            //最后执行删除操作
            providerMapper.deleteByPrimaryKey(providerId);
        }


        return errorMsg;
    }

    private String CheckUniqueFiled(Provider provider, String id, String errorMsg) {
        String providerCode = provider.getProviderCode();
        if (providerCode != null) {
            String providerId = providerMapper.findIdByProviderCode(providerCode);
            if (providerId != null && !id.equals(providerId)) {
                errorMsg = "供应商编码不能重复!";
                return errorMsg;
            }
        }
        return errorMsg;
    }

    /**
     * 查询 e_provider表 中的 provider_code
     * @return
     */
    @Override
    public List<String> findProviderCodes() {
        return providerMapper.findProviderCodes();
    }
}
