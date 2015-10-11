package com.borneo.framework.base.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.borneo.framework.base.entity.SystemConfig;

@Repository(SystemConfigDAO.BEAN_NAME)
public class SystemConfigDAOImpl extends BaseDAOImpl implements SystemConfigDAO {

    @Override
    public SystemConfig getSystemConfig() {
        SystemConfig systemConfig;
        List<SystemConfig> systemConfigs = findAll(SystemConfig.class);
        if (systemConfigs.isEmpty()) {
            systemConfig = new SystemConfig();
            systemConfig.setConversionRate(Double.valueOf(100));
            save(systemConfig);
        } else {
            systemConfig = systemConfigs.get(0);
        }
        return systemConfig;
    }
}
