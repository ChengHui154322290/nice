package com.nice.good.service.impl;


import com.nice.good.constant.FILE_PATH;
import com.nice.good.model.GoodPicture;
import com.nice.good.utils.PictureIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.StoreSeatPictureMapper;
import com.nice.good.model.StoreSeatPicture;
import com.nice.good.service.StoreSeatPictureService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/08/13.
 */
@Service
@Transactional
public class StoreSeatPictureServiceImpl extends AbstractService<StoreSeatPicture> implements StoreSeatPictureService {
    @Resource
    private StoreSeatPictureMapper storeSeatPictureMapper;

    @Override
    public List<String> goodPictureAdd(MultipartFile[] files, String userId) {

        List<String> list = new ArrayList<>();
        //商品图片上传
        String imgName = null;
        if (files != null && files.length > 0) {
            for (MultipartFile f : files) {
                try {
                    StoreSeatPicture seatPicture = new StoreSeatPicture();
                    // 获取文件名
                    String fileName = f.getOriginalFilename();
                    // 获取文件的后缀名
                    String suffixName = fileName.substring(fileName.lastIndexOf("."));
                    Long imgId = PictureIdUtils.getImgId();
                    imgName = imgId + suffixName;
                    //设置图片信息
                    seatPicture.setImgId(imgId);
                    list.add(imgId+suffixName);
                    seatPicture.setCreateId(userId);
                    seatPicture.setImgName(imgName);
                    seatPicture.setCreateDate(TimeStampUtils.getTimeStamp());
                    //上传文件
                    uploadFileUtil(f.getBytes(), FILE_PATH.IMG_PATH, imgName);
                    //向数据库中保存文件
                    seatPicture.setImgUrl(imgName);
                    storeSeatPictureMapper.insert(seatPicture);

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
    public void deletePicture(List<String> imgIds) {
        for (String imgUrl : imgIds) {
            storeSeatPictureMapper.deletePicture(imgUrl);
        }
    }


}
