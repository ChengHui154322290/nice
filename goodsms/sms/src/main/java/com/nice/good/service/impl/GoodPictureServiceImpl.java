package com.nice.good.service.impl;


import com.nice.good.constant.FILE_PATH;
import com.nice.good.constant.ID_PREFIX;
import com.nice.good.utils.PictureIdUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.GoodPictureMapper;
import com.nice.good.model.GoodPicture;
import com.nice.good.service.GoodPictureService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * @Description: 货品档案-图片上传
 * @Author: fqs
 * @Date: 2018/3/23 10:28
 * @Version: 1.0
 */
@Service
@Transactional
public class GoodPictureServiceImpl extends AbstractService<GoodPicture> implements GoodPictureService {
    @Resource
    private GoodPictureMapper goodPictureMapper;


    @Override
    public void deletePicture(List<String> imgIds) {
        for (String imgUrl : imgIds) {
            goodPictureMapper.deletePicture(imgUrl);
        }
    }

    @Override
    public List<String> goodPictureAdd(MultipartFile[] files, String userId) {

        List<String> list = new ArrayList<>();
        //商品图片上传
        String imgName = null;
        if (files != null && files.length > 0) {
            for (MultipartFile f : files) {

                //用来限制用户上传文件大小的  4M
                int maxPostSize = 4 * 1024 * 1024;

                long size = f.getSize();

                if (size > maxPostSize) {
                    continue;
                }

                try {
                    GoodPicture goodPicture = new GoodPicture();
                    // 获取文件名
                    String fileName = f.getOriginalFilename();
                    // 获取文件的后缀名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    Long imgId = PictureIdUtils.getImgId();
                    imgName = imgId + suffixName;
                    //设置图片信息
                    goodPicture.setImgId(imgId);
                    list.add(imgId + suffixName);
                    goodPicture.setCreater(userId);
                    goodPicture.setImgName(imgName);
                    goodPicture.setModifier(userId);
                    goodPicture.setCreatetime(TimeStampUtils.getTimeStamp());
                    goodPicture.setModifytime(TimeStampUtils.getTimeStamp());
                    //上传文件
                    uploadFileUtil(f.getBytes(), FILE_PATH.IMG_PATH, imgName);
                    //向数据库中保存文件
                    goodPicture.setImgUrl(imgName);
                    goodPictureMapper.insert(goodPicture);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;

    }

    /**
     * 上传文件的方法
     *
     * @param file：文件的字节
     * @param imgPath：文件的路径
     * @param imgName：文件的名字
     * @throws Exception
     */
    public void uploadFileUtil(byte[] file, String imgPath, String imgName) throws Exception {
        File targetFile = new File(imgPath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(imgPath + imgName);
        out.write(file);
        out.flush();
        out.close();
    }


    @Override
    public void goodPictureUpdate(GoodPicture goodPicture, String userId) {

        goodPicture.setModifier(userId);
        goodPicture.setModifytime(TimeStampUtils.getTimeStamp());
        goodPictureMapper.updateByPrimaryKey(goodPicture);
    }

    @Override
    public List<GoodPicture> getByGoodId(String goodId) {
        return goodPictureMapper.getByGoodId(goodId);
    }
}
