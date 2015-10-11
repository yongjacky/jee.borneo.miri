package com.borneo.framework.base.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.borneo.framework.base.entity.Tmodule;

@Repository(TmoduleDAO.BEAN_NAME)
public class TmoduleDAOImpl extends BaseDAOImpl implements TmoduleDAO {

	public List<Tmodule> getTmodules()throws Exception{
		DetachedCriteria dc = DetachedCriteria.forClass(Tmodule.class);
		dc.add(Restrictions.eq(Tmodule.ALIAS_HIDDEN_FLAG, new Boolean(false)));
		
		@SuppressWarnings("unchecked")
		List<Tmodule> list = findByCriteria(dc);
		
		return list;
	}
}
