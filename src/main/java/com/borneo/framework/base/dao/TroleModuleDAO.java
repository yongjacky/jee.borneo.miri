package com.borneo.framework.base.dao;

import java.util.List;

import com.borneo.framework.base.entity.TroleModule;

public interface TroleModuleDAO extends BaseDAO {

    String BEAN_NAME = "troleModuleDAO";

    List<TroleModule> getTroleModules(Long roleId);
}
