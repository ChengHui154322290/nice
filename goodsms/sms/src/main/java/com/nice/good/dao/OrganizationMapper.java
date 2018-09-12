package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.Organization;

import java.util.List;

public interface OrganizationMapper extends Mapper<Organization> {
    String findIdByOrgCode(String orgCode);
    List<String> selectByUpCode(String orgCode);
    List<Organization> finaAllSonByUpCode(String orgCode);
    List<String> selectAllOrgName();
    /**
     * 查询 b_organization 表 中的 org_code
     * @return
     */
    List<String> findOrgCodes();

    String newFindIdByOrgCode(String orgId);

    List<String> selectAllOrgCode();
}