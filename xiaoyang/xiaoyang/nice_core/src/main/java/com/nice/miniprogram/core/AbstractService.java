package com.nice.miniprogram.core;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Condition;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * 基于通用MyBatis Mapper插件的Service接口的实现
 */
public abstract class AbstractService<T> implements Service<T> {

    @Autowired
    protected CoreMapper<T> mapper;

    private Class<T>  modelClass; // 当前泛型真实类型的Class

    public AbstractService() {
        ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
        modelClass = (Class<T>) pt.getActualTypeArguments()[0];
    }

    @Override
    public int save(T model) {
        return mapper.insert(model);
    }

    @Override
    public void delete(T model) {
//        mapper.delete(model);
        mapper.deleteByPrimaryKey(model);
    }

    @Override
    public void update(T model) {
        mapper.updateByPrimaryKey(model);
    }

    @Override
    public T findById(String id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public T getById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectList(T model) {
        return mapper.select(model);
    }

    @Override
    public int selectCount(T t){ return mapper.selectCount(t); }
}
