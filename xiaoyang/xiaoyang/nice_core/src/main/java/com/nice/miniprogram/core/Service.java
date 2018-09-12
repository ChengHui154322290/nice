package com.nice.miniprogram.core;


import java.util.List;

/**
 * Service 层 基础接口，其他Service 接口 请继承该接口
 */
public interface Service<T> {
    int save(T model);//持久化

    void delete(T model);//刪除

    void update(T model);//更新

    T findById(String id);//通过ID查找

    T getById(int id);//通过ID查找

    List<T> selectList(T model);//根据对象属性查询对象列表

    int selectCount(T t);//根据对象中的条件计数

}
