package com.borneo.framework.base.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.borneo.framework.base.entity.Trole;
import com.borneo.framework.base.entity.TroleModule;

@Repository(TroleModuleDAO.BEAN_NAME)
public class TroleModuleDAOImpl extends BaseDAOImpl implements TroleModuleDAO {

    @Override
    @SuppressWarnings("unchecked")
    public List<TroleModule> getTroleModules(Long roleId) {
        DetachedCriteria dc = DetachedCriteria.forClass(TroleModule.class);
        dc.add(Restrictions.eq(TroleModule.ALIAS_TROLE + "." + Trole.ALIAS_TID, roleId));
        return findByCriteria(dc);
    }
}
