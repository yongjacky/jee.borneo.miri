package com.borneo.framework.base.dao;

import com.borneo.framework.base.entity.TipayLog;

public interface TipayLogDAO extends BaseDAO{

    String BEAN_NAME = "tipayLogDAO";
    
    TipayLog getIpayLogByOrderId(String orderId);
    TipayLog getIpayLogBy(String orderId, Integer paymentId, String merchantCode, String currency);
}
