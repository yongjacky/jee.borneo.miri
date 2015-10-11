package com.borneo.framework.base.service;

import java.util.List;

import com.borneo.framework.base.entity.Trole;

public interface TroleService extends BaseService {

    String BEAN_NAME = "troleService";

    Trole getRoleByCode(String code);
    List<Trole> getTroles();
}
