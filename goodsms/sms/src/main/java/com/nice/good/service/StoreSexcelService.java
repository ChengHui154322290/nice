package com.nice.good.service;
import com.nice.good.model.StoreSexcel;
import com.nice.good.core.Service;
import org.springframework.web.multipart.MultipartFile;


/**
 * Created by CodeGenerator on 2018/03/25.
 */
public interface StoreSexcelService extends Service<StoreSexcel> {
      String storeSexcelAdd(String fileName,MultipartFile file);

}
