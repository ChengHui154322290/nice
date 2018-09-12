package com.nice.good.service.impl;


import com.nice.good.constant.ID_PREFIX;
import com.nice.good.dao.StoreAreaMapper;
import com.nice.good.dao.StoreSeatMapper;
import com.nice.good.model.StoreArea;
import com.nice.good.model.StoreSeat;
import com.nice.good.utils.IdsUtils;
import com.nice.good.utils.RandomIdUtils;
import com.nice.good.utils.TimeStampUtils;
import com.nice.good.dao.SysPlaceMapper;
import com.nice.good.model.SysPlace;
import com.nice.good.service.SysPlaceService;
import com.nice.good.core.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
@Service
@Transactional
public class SysPlaceServiceImpl extends AbstractService<SysPlace> implements SysPlaceService {
    @Resource
    private SysPlaceMapper sysPlaceMapper;

    @Resource
    private StoreAreaMapper storeAreaMapper;

    @Resource
    private StoreSeatMapper storeSeatMapper;

    @Override
    public void sysPlaceAdd(SysPlace sysPlace, String userId) throws Exception {


        String placeId = IdsUtils.getOrderId();
        Timestamp timeStamp = TimeStampUtils.getTimeStamp();
        sysPlace.setPlaceId(placeId);
        sysPlace.setCreateId(userId);
        sysPlace.setModifyId(userId);
        sysPlace.setCreateDate(timeStamp);
        sysPlace.setModifyDate(timeStamp);

        sysPlaceMapper.insert(sysPlace);

    }




    @Override
    public void sysPlaceUpdate(SysPlace sysPlace, String userId) {

        sysPlace.setModifyId(userId);
        sysPlace.setModifyDate(TimeStampUtils.getTimeStamp());
        sysPlaceMapper.updateByPrimaryKey(sysPlace);
    }

    /**
     * 通过 exhibition(场地名称) 查询 place_number(场地编号)， 在 a_sys_place 表中   -- 2018/05/21  17:18
     *
     * @param exhibition
     * @return
     */
    @Override
    public String findPlaceNumberByExhibition(String exhibition) {
        return sysPlaceMapper.findPlaceNumberByExhibition(exhibition);
    }

    /**
     * 通过 placeNumber 删除 a_sys_place 中的场地信息
     * 2018/05/12 11:11 rk
     * @param placeNumber
     */
    @Override
    public void deleteByPlaceNumber(String placeNumber) {
        sysPlaceMapper.deleteByPlaceNumber(placeNumber);
    }

    /**
     * 通过 placeNumber(场地编码) 查询 i_store_area 表中 placeNumber(场地编码) 下是否有 area_code(库区编号)
     * 2018/05/12  10:15  rk
     *
     * @param placeNumber
     * @return
     */
    @Override
    public List<String> findAreaCodeByPlaceNumber(String placeNumber) {
        return sysPlaceMapper.findAreaCodeByPlaceNumber(placeNumber);
    }

    // 检查place_number是否重复
    @Override
    public String checkSysPlaceNumber(String placeNumber) {

        return sysPlaceMapper.checkSysPlaceNumber(placeNumber);
    }

    /**
     * 模糊匹配，查询 a_sys_place
     *
     * @param placeNumber
     * @param exhibition
     * @param type
     * @param country
     * @param province
     * @param city
     * @param district
     * @return
     */
    @Override
    public List<SysPlace> findBySevenParameters(String placeNumber, String exhibition, String type, String country, String province, String city, String district) {
        return sysPlaceMapper.findBySevenParameters(placeNumber, exhibition, type, country, province, city, district);
    }

    /**
     * 查询 a_sys_place 表 中的 exhibition(场地名称)
     *
     * @return
     */
    @Override
    public List<String> findExhibitions() {
        return sysPlaceMapper.findExhibitions();
    }


    /**
     * 查询 a_sys_place 表 中的 place_number
     *
     * @return
     */
    @Override
    public List<String> findPlaceNumbers() {
        return sysPlaceMapper.findPlaceNumbers();
    }


}
