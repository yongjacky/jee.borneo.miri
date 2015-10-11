package com.borneo.framework.base.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.SystemConfigDAO;
import com.borneo.framework.base.entity.SystemConfig;

@Service(SystemConfigService.BEAN_NAME)
public class SystemConfigServiceImpl extends BaseServiceImpl implements SystemConfigService {

    @Resource
    private SystemConfigDAO systemConfigDAO;

    @Override
    public SystemConfig getSystemConfig() {
        return systemConfigDAO.getSystemConfig();
    }
}
