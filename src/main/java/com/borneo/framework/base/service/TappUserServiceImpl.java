package com.borneo.framework.base.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.TappUserDAO;
import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.common.utils.EConstant.UserType;

@Service(TappUserService.BEAN_NAME)
public class TappUserServiceImpl extends BaseServiceImpl implements TappUserService {

    @Resource
    private TappUserDAO tappUserDAO;

    @Override
    public TappUser loadUserByUsername(String username) {
        return tappUserDAO.loadUserByUsername(username);
    }

    @Override
    public TappUser loadUserByUsernameByActive(String username) {
        return tappUserDAO.loadUserByUsernameByActive(username);
    }

    @Override
    public TappUser findUserByPass(Long id, String pass) {
        return tappUserDAO.findUserByPass(id, pass);
    }
    
    @Override
    public List<TappUser>  findUserByType(UserType userType){
        return tappUserDAO.findUserByType(userType);
    }
}
