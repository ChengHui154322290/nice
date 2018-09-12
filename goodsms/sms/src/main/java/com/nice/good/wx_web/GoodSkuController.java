package com.nice.good.wx_web;

import com.nice.good.service.GoodSkuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
* Created by CodeGenerator on 2018/08/06.
*/
@RestController
@RequestMapping("/good")
public class GoodSkuController {

	private static Logger log = LoggerFactory.getLogger(GoodSkuController.class);


    @Resource
    private GoodSkuService goodSkuService;

}
