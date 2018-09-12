package com.nice.miniprogram.api;
import com.nice.miniprogram.core.Result;
import com.nice.miniprogram.core.ResultGenerator;
import com.nice.miniprogram.model.*;
import com.nice.miniprogram.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
* Created by CodeGenerator on 2018/08/21.
*/
@RestController
@RequestMapping("/good")
public class GoodController {

	private static Logger log = LoggerFactory.getLogger(GoodController.class);


    @Resource
    private GoodService goodService;
    @Resource
    private GoodSkuService goodSkuService;
    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodPictureService goodPictureService;
    @Resource
    private GoodImgService goodImgService;


//    @PostMapping("/add")
    public Result add(@RequestBody Good good,HttpServletRequest request) {


		return ResultGenerator.genSuccessResult();
 	}


 	@Transactional
    @ApiOperation(value = "数据转移 ", httpMethod = "GET")
    @GetMapping("/parseData")
    public Result parseData(HttpServletRequest request){
        List<Good> goodList = goodService.selectList(null);
//        List<GoodSpu> spuList = new ArrayList<>();
//        List<GoodSku> skuList = new ArrayList<>();
        for(Good good:goodList){
            GoodSpu spu = goodSpuService.getBySpu(good.getCommodityCode());
            if(spu == null) {
                spu = new GoodSpu();
                spu.setBrandId(0);
                spu.setBrokerageLink(good.getBrokerageLink());
                spu.setBrokerageRatio(good.getBrokerageRatio());
                spu.setBulk(good.getBulk());
                spu.setCompanyId(good.getCompanyId());
                spu.setCategoryId(good.getCategoryId());
                spu.setGoodLink(good.getGoodLink());
                spu.setGooderCode(good.getGooderCode());
                spu.setNetWeight(good.getNetWeight());
                spu.setPackCode(good.getPackCode());
                spu.setPeriod(good.getPeriod());
                spu.setPeriodUnite(good.getPeriodUnite());
                spu.setPriceSection("");
                spu.setProperty(good.getProperty());
                spu.setRemark(good.getRemark());
                spu.setRoughWeight(good.getRoughWeight());
                spu.setSpuCode(good.getCommodityCode());
                spu.setSpuName(good.getGoodName());
                spu.setStatus(good.getStatus());
                spu.setStyleId(good.getStyleId());
                spu.setTareWeight(good.getTareWeight());
                goodSpuService.save(spu);
            }
            GoodSku sku = new GoodSku();
            sku.setBrokerageLink(good.getBrokerageLink());
            sku.setBrokerageRatio(good.getBrokerageRatio());
            sku.setCompanyId(good.getCompanyId());
            sku.setDiscountContent(good.getDiscountContent());
            sku.setDiscountLink(good.getDiscountLink());
            sku.setDiscountMethod(good.getDiscountMethod());
            sku.setGoodColor(good.getGoodColor());
            sku.setGoodSize(good.getGoodSize());
            sku.setNormalPrice(good.getNormalPrice());
            sku.setRemark(good.getRemark());
            sku.setSeckillPrice(good.getSeckillPrice());
            sku.setSkuCode(good.getGoodCode());
            sku.setSkuName(good.getGoodName());
            sku.setSpuCode(good.getCommodityCode());
            sku.setStatus(good.getStatus());
            goodSkuService.save(sku);
        }

        return ResultGenerator.genSuccessResult();
    }

    @Transactional
    @ApiOperation(value = "数据转移 ", httpMethod = "GET")
    @GetMapping("/parseImg")
    public Result parseImg(HttpServletRequest request) {
        List<GoodSku> skuList = goodSkuService.selectList(null);
        for(GoodSku goodSku:skuList){
            Good good = goodService.getOneByGoodCode(goodSku.getSkuCode());
            String goodId = good.getGoodId();
            List<GoodPicture> goodPictures = goodPictureService.getByGoodId(goodId);
            for(int i=0;i<goodPictures.size();i++){
                GoodImg img = goodImgService.getBySpu(goodSku.getSpuCode());
                if(i==0 && img==null){
                    img = new GoodImg();
                    img.setImgName(goodPictures.get(i).getImgName());
                    img.setImgUrl(goodPictures.get(i).getImgUrl());
                    img.setIsMain(1);
                    img.setSkuCode(goodSku.getSkuCode());
                    img.setSpuCode(goodSku.getSpuCode());
                }else{
                    img = new GoodImg();
                    img.setImgName(goodPictures.get(i).getImgName());
                    img.setImgUrl(goodPictures.get(i).getImgUrl());
                    img.setSkuCode(goodSku.getSkuCode());
                    img.setSpuCode(goodSku.getSpuCode());
                }
                goodImgService.save(img);
            }
        }
        return ResultGenerator.genSuccessResult();
    }

//    @Transactional
//    @ApiOperation(value = "详情图片 ", httpMethod = "GET")
//    @GetMapping("/parseDetailImg")
    public Result parseDetailImg(HttpServletRequest request) {
        List<GoodSpu> spuList = goodSpuService.selectList(null);
        for(GoodSpu goodSpu:spuList){
            String goodLink = goodSpu.getGoodLink();
            if(StringUtils.isNotBlank(goodLink)){
                String[] goodLinks = goodLink.split("id=");
                if(goodLinks.length>1){
                    String partLink = goodLinks[1];
                    String[] parts = partLink.split("&");
                    String itemId = parts[0];
                    String url = "http://192.168.254.200:5001/getdetailpic/"+ itemId;

                }
            }
        }
        return ResultGenerator.genSuccessResult();
    }

}
