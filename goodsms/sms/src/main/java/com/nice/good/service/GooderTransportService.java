package com.nice.good.service;
import com.nice.good.model.GooderTransport;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/16.
 */
public interface GooderTransportService extends Service<GooderTransport> {
      void gooderTransportAdd(GooderTransport gooderTransport,String userId);
      void gooderTransportUpdate(GooderTransport gooderTransport,String userId);
}
