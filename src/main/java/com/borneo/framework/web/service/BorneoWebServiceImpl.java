package com.borneo.framework.web.service;

import java.util.Date;

import javax.jws.WebService;

import com.borneo.framework.web.service.mo.BorneoWSMo;

/**
 * User: seven_shi@qq.com Date: 13-8-23 Time: 上午11:07
 */

@WebService(endpointInterface = "com.borneo.framework.web.service.BorneoWebService")
public class BorneoWebServiceImpl implements BorneoWebService {

    @Override
    public BorneoWSMo findBorneo() {
        return new BorneoWSMo(true, 9999, new Date(), "seven");
    }
}
