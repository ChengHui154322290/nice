package com.nice.miniprogram.service;
import com.nice.miniprogram.model.Exhibition;
import com.nice.miniprogram.core.Service;
import java.util.List;
import java.util.Map;

/**
 * Created by CodeGenerator on 2018/08/14.
 */
public interface ExhibitionService extends Service<Exhibition> {

    List<Exhibition> getListByParams(Map<String,Object> params);


}
