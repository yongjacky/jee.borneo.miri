package com.borneo.framework.web.service;

import javax.jws.WebService;

import com.borneo.framework.web.service.mo.BorneoWSMo;

/**
 * User: seven_shi@qq.com Date: 13-8-23 Time: 上午11:07
 */

@WebService
public interface BorneoWebService {

    BorneoWSMo findBorneo();
}
