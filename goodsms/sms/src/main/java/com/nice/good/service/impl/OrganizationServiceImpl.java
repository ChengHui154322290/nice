package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.core.Result;
import com.nice.good.model.GoodArea;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.OrganizationMapper;
import com.nice.good.model.Organization;
import com.nice.good.service.OrganizationService;
import com.nice.good.core.AbstractService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;


/**
 * @Description: 基础档案-组织机构
 * @Author: fqs
 * @Date: 2018/3/23 10:30
 * @Version: 1.0
 */
@Service
@Transactional
public class OrganizationServiceImpl extends AbstractService<Organization> implements OrganizationService {
    @Resource
    private OrganizationMapper organizationMapper;

    @Override
    public String organizationAdd(Organization organization, String userId) throws Exception {


        String orgId = organization.getOrgId();
        String errorMsg = "";
        if (orgId == null) {
            //新增操作
            orgId = IdsUtils.getOrderId();
            //判断字段是否重复
            errorMsg = CheckUniqueFiled(organization, orgId, errorMsg);
            if (StringUtils.isNotBlank(errorMsg)) {
                return errorMsg;
            }
            organization.setOrgId(orgId);
            organization.setCreater(userId);
            organization.setModifier(userId);
            organization.setCreatetime(TimeStampUtils.getTimeStamp());
            organization.setModifytime(TimeStampUtils.getTimeStamp());

            organizationMapper.insert(organization);

        } else {
            //修改操作
            organization.setModifier(userId);
            organization.setModifytime(TimeStampUtils.getTimeStamp());
            organizationMapper.updateByPrimaryKey(organization);
            List<Organization> organizations = organization.getOrganizations();

            if (organizations!=null && organizations.size()!=0){
                for (Organization organization1:organizations){
                    organization1.setModifier(userId);
                    organization1.setModifytime(TimeStampUtils.getTimeStamp());
                    organizationMapper.updateByPrimaryKey(organization1);
                }
            }
        }

        return errorMsg;
    }


    /**
     * 删除操作
     *
     * @param orgIds
     * @return
     */
    @Override
    public String deleteByOrgId(List<String> orgIds) {

        String errorMsg = "";

        for (String orgId : orgIds) {
            if (orgId == null) {
                continue;
            }

            Organization organization = organizationMapper.selectByPrimaryKey(orgId);
            if (organization == null) {
                continue;
            }

            String orgCode = organization.getOrgCode();


            List<String> sonCodes = organizationMapper.selectByUpCode(orgCode);
            if (sonCodes != null && sonCodes.size() > 0) {
                errorMsg += "组织" + orgCode + "关联有子组织,删除失败!\n";
                continue;
            }


            List<String> list = organizationMapper.selectAllOrgCode();
            if (list.contains(orgCode)) {
                errorMsg += "组织" + orgCode + "和收货单或者采购单存在关联,删除失败!\n";
                continue;
            }

            organizationMapper.deleteByPrimaryKey(orgId);
        }

        return errorMsg;


    }


    private String CheckUniqueFiled(Organization organization, String orgId, String errorMsg) {

        String orgCode = organization.getOrgCode();

        if (orgCode != null) {
            String id = organizationMapper.findIdByOrgCode(orgCode);
            if (id != null && !orgId.equals(id)) {
                errorMsg = "组织编码" + orgCode + "重复,新增失败!";
                return errorMsg;
            }
        }

        return errorMsg;
    }

    /**
     * 查询 b_organization 表 中的 org_code
     *
     * @return
     */
    @Override
    public List<String> findOrgCodes() {
        return organizationMapper.findOrgCodes();
    }


    /**
     * 修改新增子组织
     *
     * @param organization
     * @param orgCode
     * @param userId
     * @return
     */
    @Override
    public String addSonOrg(Organization organization, String orgCode, String userId) throws Exception {

        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        String errorMsg = "";


        String orgId = IdsUtils.getOrderId();
        //判断字段是否重复
        errorMsg = CheckUniqueFiled(organization, orgId, errorMsg);
        if (StringUtils.isNotBlank(errorMsg)) {
            return errorMsg;
        }

        organization.setOrgId(orgId);
        organization.setCreater(userId);
        organization.setCreatetime(timeStamp);
        organization.setModifier(userId);
        organization.setModifytime(timeStamp);

        //关联父级组织编码
        organization.setUpCode(orgCode);

        organizationMapper.insert(organization);

        return errorMsg;
}

    /**
     * 修改子组织刷新操作
     *
     * @param orgCode
     * @return
     */
    @Override
    public Result listSonOrg(String orgCode) {

        Result result = new Result();
        List<Organization> organizations = organizationMapper.finaAllSonByUpCode(orgCode);
        result.setData(organizations);
        return result;
    }
}
