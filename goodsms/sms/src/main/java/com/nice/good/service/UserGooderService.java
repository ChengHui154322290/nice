package com.nice.good.service;
import com.nice.good.model.UserGooder;
import com.nice.good.core.Service;

import java.util.List;


/**
 * Created by CodeGenerator on 2018/04/21.
 */
public interface UserGooderService extends Service<UserGooder> {
      void userGooderAdd(UserGooder userGooder,String userId) throws Exception ;
      void userGooderUpdate(UserGooder userGooder,String userId);


      /**
       * 通过 username 查询 gooderName -- List<String>   -- 2018/05/21  14:12
       * @param username
       * @return
       */
      List<String> findGooderNameByUsername(String username);


      /**
       * 通过 username 查询 gooderCode -- List<String>
       * @param username
       * @return
       */
      List<String> findGooderCodeByUsername(String username);

      /**
       * 通过 username 删除 a_user_gooder 表中对应的数据
       * @param username
       */
      void deleteUserGooderByUsername(String username);

      /**
       * 传递 username、 gooderCode、 userId 数据，对 a_user_gooder 表进行数据插入 。
       * @param username
       * @param gooderCode
       * @param userId
       * @throws Exception
       */
      void insertUserGooderByUsernameGooderCode(String username, String gooderCode, String userId) throws Exception;

}
