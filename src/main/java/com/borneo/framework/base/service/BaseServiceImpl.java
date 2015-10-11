package com.borneo.framework.base.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.BaseDAO;
import com.borneo.framework.base.vo.TableModel;
import com.borneo.framework.common.utils.PageConstant;
import com.borneo.framework.common.utils.PaginationSupport;
import com.borneo.framework.common.utils.StringUtils;
//#TOFIX_IN_TECHSTORE//import com.borneo.rapidkl.service.SystemLogService;

/**
 * @author peter.yuan
 */
@Service("baseService")
public class BaseServiceImpl implements BaseService {

    private final Logger log = LoggerFactory.getLogger(getClass());

  //#TOFIX_IN_TECHSTORE
    //@Resource
   // protected SystemLogService systemLogService;

    @Resource
    private BaseDAO baseDAO;

    @Resource
    private MessageSource messageSource;

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#delete(java.lang.Object)
     */
    @Override
    public void delete(Object entity) {
        baseDAO.delete(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#deleteAll(java.util.Collection)
     */
    @Override
    public void deleteAll(Collection collections) {
        baseDAO.deleteAll(collections);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#execute(org.springframework.orm.hibernate3.HibernateCallback)
     */
    @Override
    public Object execute(HibernateCallback action) throws DataAccessException {
        return baseDAO.execute(action);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#find(java.lang.String)
     */
    @Override
    public List find(String query) {
        return baseDAO.find(query);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#find(java.lang.String, java.lang.Object)
     */
    @Override
    public List find(String query, Object parameter) {
        return baseDAO.find(query, parameter);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#find(java.lang.String, java.lang.Object[])
     */
    @Override
    public List find(String query, Object[] parameters) {
        return baseDAO.find(query, parameters);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findAll(java.lang.Class)
     */
    @Override
    public List findAll(Class entity) {
        return baseDAO.findAll(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findByCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public List findByCriteria(DetachedCriteria detachedCriteria) {
        return baseDAO.findByCriteria(detachedCriteria);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
     */
    @Override
    public List findByCriteria(DetachedCriteria detachedCriteria, int pageSize, int startIndex) {
        return baseDAO.findByCriteria(detachedCriteria, pageSize, startIndex);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findPageByCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria) {
        return baseDAO.findPageByCriteria(detachedCriteria);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findPageByCriteria(org.hibernate.criterion.DetachedCriteria, int)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int startIndex) {
        return baseDAO.findPageByCriteria(detachedCriteria, startIndex);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findPageByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int pageSize, int startIndex) {
        return baseDAO.findPageByCriteria(detachedCriteria, pageSize, startIndex);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#flush()
     */
    @Override
    public void flush() {
        baseDAO.flush();
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#get(java.lang.Class, java.io.Serializable)
     */
    @Override
    public <T> T get(Class<T> entity, Serializable id) {
        return baseDAO.get(entity, id);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#getCountByCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public int getCountByCriteria(DetachedCriteria detachedCriteria) {
        return baseDAO.getCountByCriteria(detachedCriteria);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#load(java.lang.Class, java.io.Serializable)
     */
    @Override
    public Object load(Class entity, Serializable id) {
        return baseDAO.load(entity, id);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#save(java.lang.Object)
     */
    @Override
    public void save(Object entity) {
        baseDAO.save(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(Object entity) {
        baseDAO.saveOrUpdate(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#update(java.lang.Object)
     */
    @Override
    public void update(Object entity) {
        baseDAO.update(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection collection) {
        baseDAO.saveOrUpdateAll(collection);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#update(java.lang.Object, org.hibernate.LockMode)
     */
    @Override
    public void update(Object entity, LockMode lockMode) {
        baseDAO.update(entity, lockMode);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findPageByCriteria(org.hibernate.criterion.DetachedCriteria, int, int, int)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int total, int pageSize, int startIndex) {
        return baseDAO.findPageByCriteria(detachedCriteria, total, pageSize, startIndex);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#setCacheable(java.lang.Boolean)
     */
    @Override
    public void setCacheable(Boolean result) {
        baseDAO.setCacheable(result);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findByProperty(java.lang.Class, java.lang.String[], java.lang.String[])
     */
    @Override
    public List findByProperty(Class entity, String[] properties, String[] params) {
        return baseDAO.findByProperty(entity, properties, params);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.service.ICommonService#findObjByProperty(java.lang.Class, java.lang.String[], java.lang.String[])
     */
    @Override
    public Object findObjByProperty(Class entity, String[] properties, String[] params) {
        return baseDAO.findObjByProperty(entity, properties, params);
    }

    @Override
    public boolean checkDuplicateField(Class<?> clazz, String idName, Object idValue, String fieldName, Object fieldValue) {
        return baseDAO.checkDuplicateField(clazz, idName, idValue, fieldName, fieldValue);
    }

    /*
     * (non-Javadoc)
     * @see com.borneo.framework.base.service.BaseService#clear()
     */
    @Override
    public void clear() {
        baseDAO.clear();
    }

    /*
     * (non-Javadoc)
     * @see com.borneo.framework.base.service.BaseService#executeUpdate(java.lang.String, java.lang.Object[])
     */
    @Override
    public int executeUpdate(String hql, Object... params) {
        return baseDAO.executeUpdate(hql, params);
    }

    /*
     * (non-Javadoc)
     * @see com.borneo.framework.base.service.BaseService#executeUpdate(java.lang.String, java.lang.Object[], org.hibernate.type.Type[])
     */

    @Override
    public int executeUpdate(String hql, Object[] params, Type[] types) {
        return baseDAO.executeUpdate(hql, params, types);
    }

    /*
     * (non-Javadoc)
     * @see com.borneo.framework.base.service.BaseService#findByProperty(java.lang.Class, java.lang.String[], java.lang.String[], java.lang.String[], java.lang.String[])
     */
    @Override
    public List findByProperty(Class entity, String[] properties, String[] params, String[] orderProperties, String[] orderValue) {
        return baseDAO.findByProperty(entity, properties, params, orderProperties, orderValue);
    }

    @Override
    public void merge(Object object) {
        baseDAO.merge(object);
    }

    @Override
    public PaginationSupport findPageByCriteria(HttpServletRequest request, DetachedCriteria detachedCriteria, int total, int pageSize, int startIndex) {
        String sortField = StringUtils.getParameter(request, PageConstant.PAGE_FORM_SORT_FIELD_NAME, "");
        String sortType = StringUtils.getParameter(request, PageConstant.PAGE_FORM_SORT_TYPE_NAME, "");
        TableModel tableModel = new TableModel();
        tableModel.setSortField(sortField);
        tableModel.setSortType(sortType);
        request.setAttribute(PageConstant.PAGE_FORM_SORT_FIELD_NAME, sortField);//need store in jsp page
        request.setAttribute(PageConstant.PAGE_FORM_SORT_TYPE_NAME, sortType);
        return baseDAO.findPageByCriteria(tableModel, detachedCriteria, total, pageSize, startIndex);
    }

    @Override
    public PaginationSupport findPageByCriteria(TableModel tableModel, DetachedCriteria detachedCriteria, int total, int pageSize, int startIndex) {
        return baseDAO.findPageByCriteria(tableModel, detachedCriteria, total, pageSize, startIndex);
    }

    @Override
    public String getMessage(String key, Object... params) {
        return messageSource.getMessage(key, params, Locale.ENGLISH);
    }

    @Override
    public String getMessage(String key, Object[] params, Locale locale) {
        return messageSource.getMessage(key, params, locale);
    }

    @Override
    public void evictHibernateCache() throws Exception {
        baseDAO.evictHibernateCache();
    }

    @Override
    public PaginationSupport findPageByCriteria(TableModel tableModel, DetachedCriteria detachedCriteria, int pageSize, int startIndex) {
        return baseDAO.findPageByCriteria(tableModel, detachedCriteria, pageSize, startIndex);
    }

    @Override
    public int getCountByCriteria(TableModel tableModel, DetachedCriteria detachedCriteria) {
        return baseDAO.getCountByCriteria(tableModel, detachedCriteria);
    }

}
