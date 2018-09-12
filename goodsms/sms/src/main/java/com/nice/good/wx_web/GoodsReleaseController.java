package com.nice.good.wx_web;

import com.nice.good.base.BaseController;
import com.nice.good.constant.FILE_PATH;
import com.nice.good.core.Result;
import com.nice.good.core.ResultGenerator;
import com.nice.good.dao.StoreAreaMapper;
import com.nice.good.dao.StoreSeatMapper;
import com.nice.good.enums.ResultCode;
import com.nice.good.model.*;
import com.nice.good.service.*;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.PictureIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.wx_model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/goodsRelease")
public class GoodsReleaseController extends BaseController {

    private static Logger log = LoggerFactory.getLogger(GoodsReleaseController.class);

    @Resource
    private GoodSpuService goodSpuService;
    @Resource
    private GoodSkuService goodSkuService;
    @Resource
    private StoreAreaMapper storeAreaMapper;
    @Resource
    private StoreSeatMapper storeSeatMapper;
    @Resource
    private SysPlaceService sysPlaceService;
    @Resource
    private SeatStockService seatStockService;
    @Resource
    private StockService stockService;
    @Resource
    private GoodImgService goodImgService;


    private final String IMG_PATH="/nice/miniprogram/img/";

    /**
     * 商品发布
     * @param goodSpuVo
     * @param request
     * @return
     */
    @Transactional
    @PostMapping("/spuRelease")
    public Result spuRelease(@RequestBody GoodSpuVo goodSpuVo, HttpServletRequest request ){

        String placeId = getPlaceId(request);
        String userId = getUserName(request);
        String areaCode = "SJXNKQ"+userId; //商家虚拟库区
        String seatCode = "SJXNKW"+userId; //商家虚拟库位
        /* ========================================= 新增库区库位 =================================== */
        addAreaAndSeat(placeId,areaCode,seatCode,userId);
        /* ========================================= 新增库区库位end =================================== */
        /* ========================================= 新增商品信息（Spu） =================================== */
        GoodSpu goodSpu = new GoodSpu();
        BeanUtils.copyProperties(goodSpuVo,goodSpu);    //spu: 商品信息
        goodSpu.setStatus(1);
        goodSpu.setCreater(userId);
        goodSpu.setModifier(userId);
        goodSpu.setCreatetime(TimeStampUtils.getTimeStamp());
        goodSpu.setModifytime(TimeStampUtils.getTimeStamp());
        goodSpuService.save(goodSpu);//新增商品信息（Spu）
        /* ========================================= 新增商品信息（Spu） end  =================================== */
        List<GoodSkuVoNew> goosSkus = goodSpuVo.getSkuList();
        /* ========================================= 新增货品信息（Sku） =================================== */
        for(GoodSkuVoNew skuVo :goosSkus){
            GoodSku goodSku = new GoodSku();
            BeanUtils.copyProperties(skuVo,goodSku);     //sku: 货品信息
            if(null==goodSku.getBrokerageRatio()||"".equals(goodSku.getBrokerageRatio())){
                goodSku.setBrokerageRatio(goodSpu.getBrokerageRatio());
            }
            goodSku.setSpuCode(goodSpu.getSpuCode());
            if(StringUtils.isBlank(goodSku.getSkuName())){
                goodSku.setSkuName(goodSpu.getSpuName());
            }
            GoodSku sku = goodSkuService.getBySku(goodSku.getSkuCode());
            if(null!=sku){
                return ResultGenerator.genFailResult(ResultCode.OPERATION_ILLEGAL).setMessage("货品编码已存在，请重新输入！");
            }
            goodSku.setStatus(1);
            goodSku.setCreater(userId);
            goodSku.setModifier(userId);
            goodSku.setCreatetime(TimeStampUtils.getTimeStamp());
            goodSku.setModifytime(TimeStampUtils.getTimeStamp());
            goodSkuService.save(goodSku);  //新增货品信息（Sku）
            Integer inventory = skuVo.getInventory();   //库存量
            //新增库位库存和库存
            addSeatStockAndStock(userId,placeId,seatCode,inventory,goodSku);
        }
        /* ========================================= 新增货品信息（Sku）end =================================== */
        /* ========================================== 新增图片 =========================================== */
        List<GoodImg> imgList = new ArrayList<>();
        List<GoodImg> pictureList = goodSpuVo.getPictureList();
        for(int i=0;i<pictureList.size();i++){
            GoodImg goodImg = pictureList.get(i);
            if(i==0){
                goodImg.setIsMain(1);
            }
            goodImg.setImgUrl(goodImg.getImgName());
            goodImg.setCreater(userId);
            goodImg.setCreatetime(TimeStampUtils.getTimeStamp());
            goodImg.setModifier(userId);
            goodImg.setModifytime(TimeStampUtils.getTimeStamp());
            goodImg.setSpuCode(goodSpuVo.getSpuCode());
            imgList.add(goodImg);
        }
        List<GoodImg> detailPictureList = goodSpuVo.getDetailPictureList();
        for(GoodImg goodImg:detailPictureList){
            goodImg.setImgUrl(goodImg.getImgName());
            goodImg.setIsMain(2);
            goodImg.setCreater(userId);
            goodImg.setCreatetime(TimeStampUtils.getTimeStamp());
            goodImg.setModifier(userId);
            goodImg.setModifytime(TimeStampUtils.getTimeStamp());
            goodImg.setSpuCode(goodSpuVo.getSpuCode());
            imgList.add(goodImg);
        }
        goodImgService.save(imgList);
        /* ========================================== 新增图片 end =========================================== */

        return ResultGenerator.genSuccessResult();
    }

    /**
     * 新增库区库位
     */
    private void addAreaAndSeat(String placeId,String areaCode ,String seatCode,String userId){
        //==================================== 新增库区 =====================================
        String id = storeAreaMapper.findIdByAreaCode(areaCode,placeId);
        SysPlace sysPlace = sysPlaceService.findById(placeId);
        String placeNumber = sysPlace.getPlaceNumber();
        if (id == null) {
            String areaId = IdsUtils.getOrderId();
            StoreArea storeArea = new StoreArea();
            storeArea.setAreaId(areaId);
            storeArea.setPlaceNumber(placeNumber);
            storeArea.setAreaCode(areaCode);
            storeArea.setAreaName("商家虚拟库区"+userId);
            storeArea.setAreaType("成品");
            storeArea.setStatement("系统预置");
            storeArea.setCreater("system");
            storeArea.setModifier("system");
            storeArea.setCreatetime(TimeStampUtils.getTimeStamp());
            storeArea.setModifytime(TimeStampUtils.getTimeStamp());
            //关联场地
            storeArea.setPlaceId(placeId);
            storeAreaMapper.insert(storeArea);
        }
        //==================================== 新增库位 =====================================

        String id1 = storeSeatMapper.findIdBySeatCode(seatCode,placeId);
        if (id1 == null ) {
            String seatId = IdsUtils.getOrderId();
            StoreSeat storeSeat = new StoreSeat();
            storeSeat.setSeatId(seatId);
            storeSeat.setPlaceNumber(placeNumber);
            storeSeat.setAreaCode("SJXNKQ"+userId);
            storeSeat.setSeatName("商家虚拟库位");
            storeSeat.setSeatCode("SJXNKW"+userId);
            storeSeat.setMixGood(1);
            storeSeat.setMixBatch(1);
            storeSeat.setSeatType("暂存");
            storeSeat.setLevel("1");
            storeSeat.setSeatTag("无");
            storeSeat.setSeatStatus(1);
            storeSeat.setStatement("系统预置");
            storeSeat.setCreater("system");
            storeSeat.setModifier("system");
            storeSeat.setCreatetime(TimeStampUtils.getTimeStamp());
            storeSeat.setModifytime(TimeStampUtils.getTimeStamp());
            storeSeat.setPlaceId(placeId);
            storeSeatMapper.insert(storeSeat);
        }
    }

    /**
     * 新增库位库存和库存
     */
    private void addSeatStockAndStock(String userId,String placeId,String seatCode,Integer inventory,GoodSku goodSku){
        //============================== 新增库位库存 ==================================
        GoodSpu goodSpu = goodSpuService.getBySpu(goodSku.getSpuCode());
        SeatStock seatStock = new SeatStock();
        seatStock.setStockId(IdsUtils.getOrderId());
        seatStock.setGoodCode(goodSku.getSkuCode());//商品编码
        seatStock.setSeatCode(seatCode);//库位编码
        seatStock.setCommodityCode(goodSpu.getSpuCode());
        seatStock.setGoodName(goodSpu.getSpuName());
        seatStock.setNowNum(inventory);
        seatStock.setUseNum(inventory);
        seatStock.setAllotNum(0);
        seatStock.setPickNum(0);
        seatStock.setFreezeNum(0);
        seatStock.setCreateId(userId);
        seatStock.setModifyId(userId);
        seatStock.setCreateDate(TimeStampUtils.getTimeStamp());
        seatStock.setModifyDate(TimeStampUtils.getTimeStamp());
        seatStock.setPlaceId(placeId);
        seatStockService.save(seatStock);

        //================================ 新增库存 =================================
        //新增库存
        Stock stock = new Stock();
        stock.setStockId(IdsUtils.getOrderId());
        stock.setGoodCode(goodSku.getSkuCode());//商品编码
        stock.setCommodityCode(goodSpu.getSpuCode());
        stock.setGoodName(goodSpu.getSpuName());
        stock.setNowNum(inventory);
        stock.setUseNum(inventory);
        stock.setAllotNum(0);
        stock.setPickNum(0);
        stock.setFreezeNum(0);
        stock.setCreateId(userId);
        stock.setModifyId(userId);
        stock.setCreateDate(TimeStampUtils.getTimeStamp());
        stock.setModifyDate(TimeStampUtils.getTimeStamp());
        stock.setPlaceId(placeId);
        stockService.save(stock);

    }

    /**
     * 上传图片（列表图、轮播图、详情图）
     * @param multipartFile
     * @param request
     * @return
     */
    @PostMapping("/uploadGoodImg")
    public Result uploadGoodImg( @RequestParam("file") MultipartFile multipartFile,HttpServletRequest request) {
        Map<String,Object> retMap = new HashMap<>();
        MultipartHttpServletRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartHttpServletRequest.class);
        List<MultipartFile> files = multipartRequest.getFiles("file");
        if(files == null ||files.size()==0){
            return ResultGenerator.genFailResult(ResultCode.PICTURE_IS_NULL);
        }
        MultipartFile file = files.get(0);
        //用来限制用户上传文件大小的  4M
        int maxPostSize = 3 * 1024 * 1024;
        long size = file.getSize();
        if(size>maxPostSize){
            //图片大于3兆
            return ResultGenerator.genFailResult(ResultCode.PIC_OVER_MAXSIZE);
        }
        String fileName = file.getOriginalFilename();
        // 获取文件的后缀名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        Long imgId = PictureIdUtils.getImgId();
        String imgName = imgId + suffixName;
        try {
            uploadFileUtil(file.getBytes(), FILE_PATH.IMG_PATH, imgName);
        } catch (Exception e) {
            log.error("新增对象操作异常e:{}",e);
            return ResultGenerator.genFailResult(ResultCode.UPLOAD_IS_FAIL);
        }
        return ResultGenerator.genSuccessResult().setMessage("图片上传成功").setData(imgName);
    }

    /**
     * 上传图片
     */
    private void uploadFileUtil(byte[] file, String imgPath, String imgName) throws Exception {
        File targetFile = new File(imgPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(imgPath + imgName);
        out.write(file);
        out.flush();
        out.close();
    }
}
