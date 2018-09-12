package com.nice.good.service;
import com.nice.good.model.RfidLabel;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/05/17.
 */
public interface RfidLabelService extends Service<RfidLabel> {
      void rfidLabelAdd(RfidLabel rfidLabel,String userId);
      void rfidLabelUpdate(RfidLabel rfidLabel,String userId);
}
