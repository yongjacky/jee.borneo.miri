package com.borneo.framework.base.service;

import java.util.List;

import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.common.utils.EConstant.UserType;

public interface TappUserService extends BaseService {

    String BEAN_NAME = "tappUserService";

    TappUser loadUserByUsername(String username);

    TappUser loadUserByUsernameByActive(String username);

    TappUser findUserByPass(Long id, String pass);

    List<TappUser> findUserByType(UserType userType);
}
