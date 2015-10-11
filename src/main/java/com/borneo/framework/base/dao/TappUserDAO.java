package com.borneo.framework.base.dao;

import java.util.List;

import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.common.utils.EConstant.UserType;

public interface TappUserDAO extends BaseDAO {

    String BEAN_NAME = "tappUserDAO";

    TappUser loadUserByUsername(String username);

    TappUser loadAdminByUsername(String username);

    TappUser loadMerchantByUsername(String username);

    TappUser loadMemberByUsername(String username);

    TappUser loadUserByUsernameByActive(String username);

    TappUser findUserByPass(Long id, String pass);
    
    List<TappUser> findUserByType(UserType userType);
}
