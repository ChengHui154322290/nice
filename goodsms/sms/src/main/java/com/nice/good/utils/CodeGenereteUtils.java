package com.nice.good.utils;


import com.nice.good.dao.OrderMapper;
import com.nice.good.dao.OutBaseMapper;
import com.nice.good.dao.OutPickMapper;
import com.nice.good.dao.ReceiveOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CodeGenereteUtils {

    private static Logger log = LoggerFactory.getLogger(CodeGenereteUtils.class);



//    调整单号的规则：自动生成  （RD+8位日期+6位流水）示例：RD20150202124455
//
//    移动单号的规则：自动生成  （YD+8位日期+6位流水）示例：YD20150202124455
//
//    盘点单号的规则：自动生成  （CC+8位日期+4位流水）示例：CC201804110001
//
//    交易编号规则：自动生成（LS+6位流水）示例：LS000001


    @Resource
    private OrderMapper orderMapper;

    @Resource
    private ReceiveOrderMapper receiveOrderMapper;

    @Resource
    private OutBaseMapper outBaseMapper;


    @Resource
    private OutPickMapper outPickMapper;

    /**
     * 采购单号的规则：自动生成（PO+6位日期+5位流水）示例：PO180327000001
     * @return
     */

    public  String getPurchase(){
        Integer id = orderMapper.selectMaxId();
        if (id==null){
            id=1;
        }else {
            id++;
        }

        String purchase_code= "PO" + getBody(id);

        return purchase_code;
    }

    /**
     *  收货单号的规则：自动生成（ASN+6位日期+5位流水）示例：ASN180909000001
     * @return
     */

    public  String getReceive(){
        Integer id = receiveOrderMapper.selectMaxId();
        if (id==null){
            id=1;
        }else {
            id++;
        }

        String receive_code= "ASN" + getBody(id);


        return receive_code;
    }

    /**
     * 出库单号的规则：自动生成（FH+6位日期+5位流水）示例：SO180909000001
     * @return
     */

    public  String getOutCode(){
        Integer id = outBaseMapper.selectMaxId();
        if (id==null){
            id=1;
        }else {
            id++;
        }

        String send_code= "FH" + getBody(id);


        return send_code;
    }

    /**
     *  拣货明细编号规则：自动生成（JH+6位日期+5位流水）示例：JH180909000001
     * @return
     */

    public  String getPickCode(){
        Integer id = outPickMapper.selectMaxId();
        if (id==null){
            id=1;
        }else {
            id++;
        }

        String detail_code= "JH" + getBody(id);

        return detail_code;
    }


    private String getBody(Integer id) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()).substring(2,8) + String.format("%06d", id);
    }
}
