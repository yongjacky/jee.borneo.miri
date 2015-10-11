/**
 *
 */
package com.borneo.framework.base.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borneo.framework.common.utils.UserInfoUtil;

/**
 * @author peter.yuan
 */
@Service(InitSystemService.BEAN_NAME)
public class InitSystemServiceImpl extends BaseServiceImpl implements InitSystemService {

    @Resource
    private TappUserService tappUserService;

    @PostConstruct
    public void init() {
        UserInfoUtil.tappUserService = tappUserService;

    }
}
