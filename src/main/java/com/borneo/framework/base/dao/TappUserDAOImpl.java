package com.borneo.framework.base.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.common.utils.EConstant.UserType;

@Repository(TappUserDAO.BEAN_NAME)
public class TappUserDAOImpl extends BaseDAOImpl implements TappUserDAO {

    @Override
    public TappUser loadAdminByUsername(String username) {
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.createAlias("troles", "_troles");
        dc.add(Restrictions.eq(TappUser.ALIAS_USERNAME, username));
        dc.add(Restrictions.or(Restrictions.eq("_troles.troleCode", "admin"), Restrictions.eq("_troles.troleCode", "vcircle")));
        List<TappUser> list = findByCriteria(dc);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public TappUser loadMerchantByUsername(String username) {
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.createAlias("troles", "_troles");
        dc.add(Restrictions.eq(TappUser.ALIAS_USERNAME, username));
        dc.add(Restrictions.eq("_troles.troleCode", "merchant"));
        List<TappUser> list = findByCriteria(dc);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public TappUser loadUserByUsername(String username) {
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.add(Restrictions.eq(TappUser.ALIAS_USERNAME, username));
        List<TappUser> list = findByCriteria(dc);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public TappUser loadMemberByUsername(String username) {
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.add(Restrictions.eq(TappUser.ALIAS_USERNAME, username));
        List<TappUser> list = findByCriteria(dc);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public TappUser loadUserByUsernameByActive(String username) {
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.add(Restrictions.eq(TappUser.ALIAS_USERNAME, username));
        dc.add(Restrictions.eq(TappUser.ALIAS_STATUS, Boolean.TRUE));
        List<TappUser> list = findByCriteria(dc);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public TappUser findUserByPass(Long id, String pass) {
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.add(Restrictions.eq(TappUser.ALIAS_ID, id));
        dc.add(Restrictions.eq(TappUser.ALIAS_PASSWORD, pass));
        List<TappUser> list = findByCriteria(dc);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public List<TappUser>  findUserByType(UserType userType) {
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.add(Restrictions.eq(TappUser.ALIAS_USER_TYPE, userType.ordinal()));
        dc.add(Restrictions.eq(TappUser.ALIAS_STATUS, Boolean.TRUE));
        List<TappUser> list = findByCriteria(dc);
        return findByCriteria(dc);
    }
}
