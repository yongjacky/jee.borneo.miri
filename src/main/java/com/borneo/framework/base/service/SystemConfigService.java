package com.borneo.framework.base.service;

import com.borneo.framework.base.entity.SystemConfig;

public interface SystemConfigService extends BaseService {

    String BEAN_NAME = "systemConfigService";

    SystemConfig getSystemConfig();

}
