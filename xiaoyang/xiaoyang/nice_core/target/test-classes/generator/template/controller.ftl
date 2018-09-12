<#--package ${basePackage}.web;-->
package ${basePackage}.api;
import ${basePackage}.core.Result;
import ${basePackage}.core.ResultGenerator;
import ${basePackage}.model.${modelNameUpperCamel};
import ${basePackage}.service.${modelNameUpperCamel}Service;
<#--import ${basePackage}.enums.ResultCode;-->
<#--import com.nice.good.base.BaseController;-->

<#--import com.github.pagehelper.PageHelper;-->
<#--import com.github.pagehelper.PageInfo;-->
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
<#--import org.springframework.web.bind.annotation.RequestParam;-->
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;

<#--import tk.mybatis.mapper.entity.Condition;-->
<#--import tk.mybatis.mapper.entity.Example.Criteria;-->




<#--import java.util.List;-->
<#--import org.apache.commons.lang3.StringUtils;-->

/**
* Created by ${author} on ${date}.
*/
@RestController
@RequestMapping("${baseRequestMapping}")
public class ${modelNameUpperCamel}Controller {

	private static Logger log = LoggerFactory.getLogger(${modelNameUpperCamel}Controller.class);


    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    @PostMapping("/add")
    public Result add(@RequestBody ${modelNameUpperCamel} ${modelNameLowerCamel},HttpServletRequest request) {


		return ResultGenerator.genSuccessResult();
 	}
}
