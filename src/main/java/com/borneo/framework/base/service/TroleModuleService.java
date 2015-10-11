package com.borneo.framework.base.service;

import java.util.List;

import com.borneo.framework.base.entity.TroleModule;

public interface TroleModuleService extends BaseService {

    String BEAN_NAME = "troleModuleService";

    List<TroleModule> getTroleModules(Long roleId);
}
