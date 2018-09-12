package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.PackManage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PackManageMapper extends Mapper<PackManage> {
    String findIdByPackCode(@Param(value = "packCode") String packCode,
                            @Param(value = "placeId") String placeId);

    List<String> selectPackCodes(@Param(value = "placeId") String placeId);

    PackManage selectByPackCode(@Param(value = "packCode") String packCode,
                                @Param(value = "placeId") String placeId);
}