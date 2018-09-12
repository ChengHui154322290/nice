package com.nice.miniprogram.service;
import com.nice.miniprogram.model.SysPlace;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/07/18.
 */
public interface SysPlaceService extends Service<SysPlace> {

    List<SysPlace> getList();

    SysPlace getByPlaceNumber(String placeNumber);
}
