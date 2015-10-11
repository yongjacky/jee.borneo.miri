package com.borneo.framework.base.service;

import com.borneo.framework.base.entity.TipayLog;

public interface TipayLogService {
    String BEAN_NAME = "tipayLogService";
    
    TipayLog getIpayLogBy(String orderId, Integer paymentId, String merchantCode, String currency);
    TipayLog getIpayLogByOrderId(String orderId);
    
    void createTipayLog(String merchantCode, String merchantKey, String orderId, Double amount, String currency, String signature);
    void updateTipayLog(String merchantCode, String merchantKey, String orderId, String authCode, Integer paymentId, String status);     // status is from mobile end
    String ipayRequeryTrans(String orderId);   // return 'sucess' or 'fail'
    
}
