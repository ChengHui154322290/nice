package com.nice.good.service;
import com.nice.good.model.TypeDetail;
import com.nice.good.core.Service;


/**
 * Created by CodeGenerator on 2018/07/23.
 */
public interface TypeDetailService extends Service<TypeDetail> {
      void typeDetailAdd(TypeDetail typeDetail,String userId);
      void typeDetailUpdate(TypeDetail typeDetail,String userId);

	TypeDetail findByTypeCode(String typeCode);

	String deleteByDetail(TypeDetail typeDetail);
}
