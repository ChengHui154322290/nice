package com.nice.good.constant;

public interface REDIS_KEY {

    //RFID-采集-选中的物品
    String ELECT_GOODS_KEY = "goods:elect";
    //RFID-检测-标签数据缓存池
    String RFID_MONITOR_KEY = "rfid:monitor";

    //从mysql数据库中查询所有标签编码
    String MYSQL_LABEL_CODE="mysql_labels";

    //redis中存放所有标签编码
    String REDIS_LABEL_CODE="redis_labels";


    //采集时上一个货品编码
    String LAST_GOOD_CODE="last_good";

    //采集时的所有货品
    String All_GATHER_GOOD="gather_goods";


    //上传成功图片的货品编码集合

    String IMG_UPLOAD_SUCCESS="successList";

    //上传失败图片的货品编码集合

    String IMG_UPLOAD_Fail="failList";

}
