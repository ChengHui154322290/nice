package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.Suggestion;
import com.nice.miniprogram.model.User;
import com.nice.miniprogram.service.SuggestionService;
import com.nice.miniprogram.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
* Created by CodeGenerator on 2018/08/17.
*/
@Api(value = "投诉与建议接口",description = "投诉与建议相关操作")
@RestController
@RequestMapping("/suggestion")
public class SuggestionController {

	private static Logger log = LoggerFactory.getLogger(SuggestionController.class);


    @Resource
    private SuggestionService suggestionService;
    @Resource
    private UserService userService;

    @ApiOperation(value = "新增投诉与建议", httpMethod = "POST")
    @PostMapping("/add")
    public Result add(@RequestBody Suggestion suggestion,HttpServletRequest request) {
        Integer userId = suggestion.getOperator();
        User user = userService.getUser(userId);
        suggestion.setCreater(user.getAccount());
        suggestion.setCreatetime(new Date());
        suggestion.setModifier(user.getAccount());
        suggestion.setModifytime(new Date());
        suggestionService.save(suggestion);
		return ResultGenerator.genSuccessResult();
 	}
}
