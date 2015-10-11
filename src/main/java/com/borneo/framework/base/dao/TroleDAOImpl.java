package com.borneo.framework.base.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.borneo.framework.base.entity.Trole;

@Repository(TroleDAO.BEAN_NAME)
public class TroleDAOImpl extends BaseDAOImpl implements TroleDAO {

    @Override
    public Trole getRoleByCode(String code) {
        DetachedCriteria dc = DetachedCriteria.forClass(Trole.class);
        dc.add(Restrictions.eq(Trole.ALIAS_TROLE_CODE, code).ignoreCase());
        @SuppressWarnings("unchecked")
		List<Trole> list = findByCriteria(dc);
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    public List<Trole> getTroles()throws Exception{
    	DetachedCriteria dc = DetachedCriteria.forClass(Trole.class);
    	dc.add(Restrictions.eq(Trole.ALIAS_HIDDEN_FLAG, new Boolean(false)));
    	@SuppressWarnings("unchecked")
		List<Trole> list = findByCriteria(dc);
    	return list;
    }
}
