package com.borneo.framework.base.dao;

import java.util.List;

import com.borneo.framework.base.entity.Tmodule;

public interface TmoduleDAO extends BaseDAO {
    String BEAN_NAME = "tmoduleDAO";
    public List<Tmodule> getTmodules()throws Exception;
}
