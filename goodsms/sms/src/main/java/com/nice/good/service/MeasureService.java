package com.nice.good.service;
import com.nice.good.model.Measure;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/01.
 */
public interface MeasureService extends Service<Measure> {
      void measureAdd(Measure measure,String userId) throws Exception;
      void measureUpdate(Measure measure,String userId);
}
