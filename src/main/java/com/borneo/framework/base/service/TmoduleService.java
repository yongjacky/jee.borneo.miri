package com.borneo.framework.base.service;

import java.util.List;

import com.borneo.framework.base.entity.Tmodule;

public interface TmoduleService extends BaseService {

    String BEAN_NAME = "tmoduleService";

    public List<Tmodule> getTmodules();
}
