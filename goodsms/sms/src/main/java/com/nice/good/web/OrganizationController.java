package com.nice.good.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nice.good.aop.LogAnnotation;
import com.nice.good.base.BaseController;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.OrganizationMapper;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.Organization;
import com.nice.good.service.OrganizationService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

;

/**
 * @Description: 基础档案-组织机构
 * @Author: fqs
 * @Date: 2018/3/23 10:30
 * @Version: 1.0
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(OrganizationController.class);


    @Resource
    private OrganizationService organizationService;

    @Resource
    private OrganizationMapper organizationMapper;


//    @LogAnnotation(logType = "其他日志",content = "组织档案新增")
    @PostMapping("/add")
    public Result add(@RequestBody Organization organization, HttpServletRequest request) {
        if (organization == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }


        String userId = getUserName(request);

        try {

            String errorMsg = organizationService.organizationAdd(organization, userId);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("新增对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }

        return ResultGenerator.genSuccessResult();
    }


    /**
     * 组织删除
     *
     * @param
     * @return
     */
    @LogAnnotation(logType = "其他日志",content = "组织档案删除")
    @PostMapping("/delete")
    public Result delete(@RequestParam List<String> orgIds) {

        if (orgIds == null || orgIds.size()==0) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String errorMsg = organizationService.deleteByOrgId(orgIds);
            if (StringUtils.isNotBlank(errorMsg)) {
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }

        } catch (Exception e) {
            log.error("删除对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();
    }



    @PostMapping("/list")
    public Result list(@RequestBody Organization organization, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "20") Integer size) {

        PageHelper.startPage(page, size);

        Condition condition = new Condition(organization.getClass());

        /**
         * 根据id倒序输出结果
         */
        condition.orderBy("id").desc();

        Criteria criteria = condition.createCriteria();

        if (StringUtils.isNotEmpty(organization.getOrgCode())) {
            criteria.andLike("orgCode", "%" + organization.getOrgCode() + "%");

        }
        if (StringUtils.isNotEmpty(organization.getOrgName())) {
            criteria.andLike("orgName", "%" + organization.getOrgName() + "%");

        }


        PageInfo pageInfo = null;
        try {
            List<Organization> list = organizationService.findByCondition(condition);
            pageInfo = new PageInfo(list);
        } catch (Exception e) {
            log.error("查询对象操作异常e:{}", e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 罗列当前组织的所有子组织
     */
    @PostMapping("/listSon")
    public Result listSon(@RequestBody Organization organization) {
        if (organization == null) {
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        List<Organization> list = organizationMapper.finaAllSonByUpCode(organization.getOrgCode());
        organization.setOrganizations(list);

        return ResultGenerator.genSuccessResult(organization);

    }

    /**
     * 下拉列表展示所有组织编码
     */

    @PostMapping("/pullOrgList")
    public List<String> getAllOrg(){

        List<String> list = organizationMapper.findOrgCodes();

        return list;
    }

    /**
     * 新增子组织
     */
    @LogAnnotation(logType = "其他日志",content = "组织档案新增子组织")
    @PostMapping("/addSonOrg")
    public Result addSonOrg(@RequestBody Organization organization,@RequestParam String orgCode,HttpServletRequest request){

        if(organization == null){
            return ResultGenerator.genFailResult(ResultCode.OBJECT_IS_NULL);
        }

        try {
            String userId = getUserName(request);

            String errorMsg = organizationService.addSonOrg(organization,orgCode,userId);

            if (StringUtils.isNotBlank(errorMsg)){
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage(errorMsg);
            }
        } catch (Exception e) {
            log.error("新增子组织失败{}",e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult();

    }

    /**
     * 修改刷新子组织组织
     */
    @PostMapping("/listSonOrg")
    public Result listSonOrg(@RequestParam String orgCode){

        Object data;
        try {

            Result result = organizationService.listSonOrg(orgCode);

            data = result.getData();

        } catch (Exception e) {
            log.error("查询货品库区操作异常e:{}",e);
            return ResultGenerator.genFailResult(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return ResultGenerator.genSuccessResult().setData(data);

    }

}
