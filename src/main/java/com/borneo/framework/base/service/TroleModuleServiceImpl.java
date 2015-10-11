package com.borneo.framework.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.TroleModuleDAO;
import com.borneo.framework.base.entity.TroleModule;

@Service(TroleModuleService.BEAN_NAME)
public class TroleModuleServiceImpl extends BaseServiceImpl implements TroleModuleService {

    @Resource
    private TroleModuleDAO troleModuleDAO;

    @Override
    public List<TroleModule> getTroleModules(Long roleId) {
        return troleModuleDAO.getTroleModules(roleId);
    }
}
