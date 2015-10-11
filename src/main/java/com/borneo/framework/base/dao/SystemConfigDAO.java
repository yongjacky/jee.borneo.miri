package com.borneo.framework.base.dao;

import com.borneo.framework.base.entity.SystemConfig;

public interface SystemConfigDAO extends BaseDAO {

    String BEAN_NAME = "systemConfigDAO";

    SystemConfig getSystemConfig();

}
