package com.borneo.framework.base.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.borneo.framework.base.vo.TableModel;
import com.borneo.framework.common.utils.PaginationSupport;

/**
 * @author peter.yuan
 */
public interface BaseDAO {

    void setCacheable(final Boolean result);

    void save(final Object entity);

    Object execute(HibernateCallback action) throws DataAccessException;

    void saveOrUpdate(final Object entity);

    void update(final Object entity);

    void saveOrUpdateAll(final Collection collection);

    void update(final Object entity, final LockMode lockMode);

    void flush();

    void delete(final Object entity);

    Object load(final Class entity, final Serializable id);

    <T> T get(final Class<T> entity, final Serializable id);

    List findAll(final Class entity);

    List find(final String query);

    List find(final String query, final Object parameter);

    List find(final String query, final Object[] parameters);

    List findByCriteria(final DetachedCriteria detachedCriteria);

    List findByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex);

    PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria);

    PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria, final int startIndex);

    PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex);

    PaginationSupport findPageByCriteria(final TableModel tableModel, final DetachedCriteria detachedCriteria, final int total, final int pageSize, final int startIndex);

    PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria, final int total, final int pageSize, final int startIndex);

    PaginationSupport findPageByCriteria(final TableModel tableModel, final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex);

    int getCountByCriteria(final TableModel tableModel, final DetachedCriteria detachedCriteria);

    int getCountByCriteria(final DetachedCriteria detachedCriteria);

    void deleteAll(Collection collections);

    List findByProperty(Class entity, String[] properties, String[] params);

    List findByProperty(Class entity, String[] properties, String[] params, String[] orderProperties, String[] orderValue);

    Object findObjByProperty(Class entity, String[] properties, String[] params);

    boolean checkDuplicateField(Class<?> clazz, String idName, Object idValue, String fieldName, Object fieldValue);

    void clear();

    void merge(Object object);

    int executeUpdate(String hql, Object... params);

    int executeUpdate(String hql, Object[] params, Type[] types);

    Session getSession(boolean allowCreate);

    Session getSession();

    void evictHibernateCache() throws Exception;

}
