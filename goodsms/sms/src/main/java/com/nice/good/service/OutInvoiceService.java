package com.nice.good.service;
import com.nice.good.model.OutInvoice;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/04/11.
 */
public interface OutInvoiceService extends Service<OutInvoice> {
      void outInvoiceAdd(OutInvoice outInvoice,String userId);
      void outInvoiceUpdate(OutInvoice outInvoice,String userId);
}
