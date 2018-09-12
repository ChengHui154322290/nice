package com.nice.miniprogram.api;
import com.nice.miniprogram.model.ModuleRegion;
import com.nice.miniprogram.service.ModuleRegionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;


/**
* Created by CodeGenerator on 2018/06/19.
*/
@RestController
@RequestMapping("/module/region")
public class ModuleRegionController {

	private static Logger log = LoggerFactory.getLogger(ModuleRegionController.class);


    @Resource
    private ModuleRegionService moduleRegionService;

	/**
	 * 省市区查询(三级联动)
	 * @param
	 * @return
	 */
	@GetMapping("/list")
	public List<ModuleRegion> listAll(@RequestParam(defaultValue = "100000") String id) {
		List<ModuleRegion> list = moduleRegionService.findRegion(id);
		return list;
	}
}
