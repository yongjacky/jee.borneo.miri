package com.borneo.framework.base.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.borneo.framework.base.entity.TipayLog;

@Repository(TipayLogDAO.BEAN_NAME)
public class TipayLogDAOImpl extends BaseDAOImpl implements TipayLogDAO {

    @Override
    public TipayLog getIpayLogByOrderId(String orderId) {
        DetachedCriteria dCriteria = DetachedCriteria.forClass(TipayLog.class);
        dCriteria.add(Restrictions.eq(TipayLog.ALIAS_ORDER_ID, orderId));
        
        TipayLog tipayLog = null;
        List<TipayLog> list = findByCriteria(dCriteria);
        if(!list.isEmpty()) {
            tipayLog = list.get(0);
        }
        
        return tipayLog;
    }

    @Override
    public TipayLog getIpayLogBy(String orderId, Integer paymentId, String merchantCode, String currency) {
        DetachedCriteria dCriteria = DetachedCriteria.forClass(TipayLog.class);
        dCriteria.add(Restrictions.eq(TipayLog.ALIAS_ORDER_ID, orderId));
        dCriteria.add(Restrictions.eq(TipayLog.ALIAS_PAYMENT_ID, paymentId));
        dCriteria.add(Restrictions.eq(TipayLog.ALIAS_MERCHANT_CODE, merchantCode));
        dCriteria.add(Restrictions.eq(TipayLog.ALIAS_CURRENCY, currency));
        
        TipayLog tipayLog = null;
        List<TipayLog> list = findByCriteria(dCriteria);
        if(!list.isEmpty()) {
            tipayLog = list.get(0);
        }
        
        return tipayLog;
    }

}
