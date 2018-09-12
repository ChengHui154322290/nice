package com.nice.miniprogram.service;
import com.nice.miniprogram.model.ShowCount;
import com.nice.miniprogram.core.Service;
import java.util.List;

/**
 * Created by CodeGenerator on 2018/07/21.
 */
public interface ShowCountService extends Service<ShowCount> {

    ShowCount getByShowRoomId(Integer showRoomId);
    ShowCount getByExhibitionRoomId(Integer exhibitionRoomId);

    void newCountRecord(ShowCount showCount);

    void addBespeakNum(Integer id);
    void addClickNum(Integer id);
}
