package com.nice.good.service;

import com.nice.good.dto.TaskDto;

public interface PdaOutPickService {

    String submitSave(TaskDto taskDto,String placeId,String userId);
}
