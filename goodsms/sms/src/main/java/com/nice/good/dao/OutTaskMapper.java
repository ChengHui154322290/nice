package com.nice.good.dao;

import com.nice.good.core.Mapper;
import com.nice.good.model.OutTask;

import java.util.List;

public interface OutTaskMapper extends Mapper<OutTask> {

    Integer selectCountByDetailId(String detailId);

    List<OutTask> selectAllByDetailId(String detailId);
}