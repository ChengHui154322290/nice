package com.nice.good.service;
import com.nice.good.core.Result;
import com.nice.good.model.Organization;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/03/23.
 */
public interface OrganizationService extends Service<Organization> {
      String organizationAdd(Organization organization,String userId) throws Exception;
      String deleteByOrgId(List<String> orgIds);
      /**
       * 查询 b_organization 表 中的 org_code
       * @return
       */
      List<String> findOrgCodes();

      String addSonOrg(Organization organization,String orgCode,String userId) throws Exception;

      Result listSonOrg(String orgCode);

}
