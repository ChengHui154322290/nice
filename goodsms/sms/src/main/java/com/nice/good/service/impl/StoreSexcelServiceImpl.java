package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.StoreSexcelMapper;
import com.nice.good.model.StoreSexcel;
import com.nice.good.service.StoreSexcelService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;

/**
 * @Description:   展厅档案-库位导入
 * @Author:   fqs
 * @Date:  2018/3/23 10:33
 * @Version:   1.0
 */
@Service
@Transactional
public class StoreSexcelServiceImpl extends AbstractService<StoreSexcel> implements StoreSexcelService {
    @Resource
    private StoreSexcelMapper storeSexcelMapper;

    @Override
    public  String storeSexcelAdd(String fileName,MultipartFile file){

        return null;

    }


}
