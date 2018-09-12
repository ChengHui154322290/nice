package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.Category;
import com.nice.miniprogram.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
* Created by CodeGenerator on 2018/07/07.
*/
@Api(value = "分类接口",description = "类目相关操作")
@RestController
@RequestMapping("/category")
public class CategoryController {

	private static Logger log = LoggerFactory.getLogger(CategoryController.class);


    @Resource
    private CategoryService categoryService;

    @ApiOperation(value = "获取二级类目列表", httpMethod = "POST")
    @PostMapping("/list")
    public Result getList(HttpServletRequest request) {
        List<Category> categories = categoryService.getSecondLevelCategories();
        List<Map<String,Object>> retList = new ArrayList<>();
        for(Category category:categories){
            Map<String,Object> retMap = new HashMap<>();
            retMap.put("categoryId",category.getId());
            retMap.put("categoryName",category.getName());
            retList.add(retMap);
        }
		return ResultGenerator.genSuccessResult(retList);
 	}
}
