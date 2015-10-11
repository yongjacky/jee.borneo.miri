package com.borneo.framework.base.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.TipayLogDAO;
import com.borneo.framework.base.entity.TipayLog;
import com.borneo.framework.common.utils.EConstant;
import com.borneo.framework.common.utils.HttpRequest;
import com.borneo.framework.common.utils.NumberFormatUtils;
import com.borneo.framework.common.utils.StringUtils;
import com.borneo.framework.base.vo.RequeryRequest;

@Service(TipayLogService.BEAN_NAME)
public class TipayLogServiceImpl extends BaseServiceImpl implements TipayLogService {

    private Logger log = Logger.getLogger(TipayLogServiceImpl.class);

    @Resource
    private TipayLogDAO tipayLogDAO;

    @Override
    public TipayLog getIpayLogBy(String orderId, Integer paymentId, String merchantCode, String currency) {
        TipayLog ipayLog = null;
        try {
            ipayLog = tipayLogDAO.getIpayLogBy(orderId, paymentId, merchantCode, currency);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ipayLog;
    }

    @Override
    public TipayLog getIpayLogByOrderId(String orderId) {
        TipayLog ipayLog = null;
        try {
            ipayLog = tipayLogDAO.getIpayLogByOrderId(orderId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ipayLog;
    }

    @Override
    public void createTipayLog(String merchantCode, String merchantKey, String orderId, Double amount, String currency, String signature) {
        try {
            TipayLog ipayLog = tipayLogDAO.getIpayLogByOrderId(orderId);
            if (ipayLog == null) {
                ipayLog = new TipayLog();
                ipayLog.setMerchantCode(merchantCode);
                ipayLog.setMerchantKey(merchantKey);
                ipayLog.setOrderId(orderId);
                ipayLog.setCurrency(currency);
                ipayLog.setAmount(amount);
                ipayLog.setSignature(signature);
                ipayLog.setCreatedDate(new Date());

                save(ipayLog);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void updateTipayLog(String merchantCode, String merchantKey, String orderId, String authCode, Integer paymentId, String status) {
        try {
            TipayLog ipayLog = tipayLogDAO.getIpayLogByOrderId(orderId); 
            if (ipayLog != null) {
                if(ipayLog.getMerchantCode().equalsIgnoreCase(merchantCode) && ipayLog.getMerchantKey().equalsIgnoreCase(merchantKey)) {
                   
                    ipayLog.setAuthCode(authCode);
                    ipayLog.setPaymentId(paymentId);
                    ipayLog.setStatus(status);
                    ipayLog.setModifiedDate(new Date());
                    
                    update(ipayLog);
                }else{
                    
                }
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Whether successful or fail payment from mobile end, this function will perform requery to ipay.
    @Override
    public String ipayRequeryTrans(String orderId) {
        String status = "fail";
        try {
            TipayLog ipayLog = tipayLogDAO.getIpayLogByOrderId(orderId);
            if (ipayLog == null) {
                return status;
            } else {
                log.info("OrderId:" + ipayLog.getOrderId());
                String url = SpringUtil.getMessage("ipay.requery.url"); //SpringUtil.getMessage("ipay.requery.url", new Object[]{});

                if (!StringUtils.isEmpty(url)) {
                    RequeryRequest requeryRequest = new RequeryRequest(ipayLog.getMerchantCode(), ipayLog.getOrderId(), NumberFormatUtils.convertPointToDecimal(ipayLog.getAmount()));
                    log.info("Request URL:" + url);
                    log.info("Request Params:" + requeryRequest.toString());
                    HttpRequest httpRequest = HttpRequest.post(url).send(requeryRequest.toString());
                    log.info("HTTP Code:" + httpRequest.code());
                    if (httpRequest.ok()) { // Connection response code == 200

                        // Get the response body. Possible return value are : 00, Invalid parameters, Record not found, Incorrect amount, Payment fail, M88Admin
                        String result = httpRequest.body();
                        log.info("Response Body:" + result);
                        ipayLog.setStatus(result);

                        if (EConstant.IpayRequeryResponseCode.Successful.toString().equalsIgnoreCase(result)) {
                            ipayLog.setIpayStatus(EConstant.IpayStatusCode.Successful.toString());
                            status = "successful";
                        } else {
                            ipayLog.setIpayStatus(EConstant.IpayStatusCode.Fail.toString());
                        }

                        ipayLog.setModifiedDate(new Date());
                        update(ipayLog);
                    } else {
                        // connection fail. no action. status=fail. At this time, if mobile client confirm payment is sucessful, Admin can perform 'click to check' button to requery ipay manually.
                    }
                } else {
                    log.info("Request URL: null or empty");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("ipayRequeryTrans().status:"+status);
        return status;
    }
}
