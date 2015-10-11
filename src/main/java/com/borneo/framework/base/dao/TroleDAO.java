package com.borneo.framework.base.dao;

import java.util.List;

import com.borneo.framework.base.entity.Trole;

public interface TroleDAO extends BaseDAO {

    String BEAN_NAME = "troleDAO";

    Trole getRoleByCode(String code);
    
    List<Trole> getTroles()throws Exception;
}
