package com.borneo.framework.base.dao;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.borneo.framework.base.vo.TableModel;
import com.borneo.framework.common.utils.BeanUtils;
import com.borneo.framework.common.utils.DateUtils;
import com.borneo.framework.common.utils.PaginationSupport;
import com.borneo.framework.spring.mvc.tag.SearchValueBindControllerTag;

/**
 * @author peter.yuan
 */
@Repository("baseDAO")
public class BaseDAOImpl implements BaseDAO {

    private static final boolean CACHEQUERIES_DEFAULT = true;
    protected final Logger log = LoggerFactory.getLogger(getClass());
    protected SessionFactory sessionFactory;
    private JdbcTemplate jdbcTemplate;
    private HibernateTemplate hibernateTemplate;

    private static void bindParameters(Query query, Object[] params, Type[] types) {
        if (params != null) {
            if (types == null) {
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i]);
                }
            } else {
                for (int i = 0; i < params.length; i++) {
                    query.setParameter(i, params[i], types[i]);
                }
            }
        }
    }

    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * @return the hibernateTemplate
     */
    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    /**
     * @param sessionFactory the hibernateTemplate to set
     */
    @Resource
    public void setHibernateTemplate(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.hibernateTemplate = new HibernateTemplate(this.sessionFactory);
        this.hibernateTemplate.setCacheQueries(CACHEQUERIES_DEFAULT);
    }

    @Override
    public Session getSession() {
        return getSession(hibernateTemplate.isAllowCreate());
    }

    @Override
    public Session getSession(boolean allowCreate) {
        return allowCreate ? SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), hibernateTemplate.getEntityInterceptor(), hibernateTemplate.getJdbcExceptionTranslator()) : SessionFactoryUtils.getSession(hibernateTemplate.getSessionFactory(), false);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#delete(java.lang.Object)
     */
    @Override
    public void delete(Object entity) {
        getHibernateTemplate().delete(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#deleteAll(java.util.Collection)
     */
    @Override
    public void deleteAll(Collection collections) {
        getHibernateTemplate().deleteAll(collections);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#execute(org.springframework.orm.hibernate3.HibernateCallback)
     */
    @Override
    public Object execute(HibernateCallback action) throws DataAccessException {
        return getHibernateTemplate().execute(action);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#find(java.lang.String)
     */
    @Override
    public List find(String query) {
        return getHibernateTemplate().find(query);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#find(java.lang.String, java.lang.Object)
     */
    @Override
    public List find(String query, Object parameter) {
        return getHibernateTemplate().find(query, parameter);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#find(java.lang.String, java.lang.Object[])
     */
    @Override
    public List find(String query, Object[] parameters) {
        return getHibernateTemplate().find(query, parameters);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findAll(java.lang.Class)
     */
    @Override
    public List findAll(Class entity) {
        return getHibernateTemplate().find("from " + entity.getName());
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findByCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public List findByCriteria(final DetachedCriteria detachedCriteria) {
        return findByCriteria(detachedCriteria, -1, -1);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
     */
    @Override
    public List findByCriteria(final DetachedCriteria detachedCriteria, final int maxResults, final int firstResult) {
        Assert.notNull(detachedCriteria, "DetachedCriteria must not be null");
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        return findByCriteria(criteria, maxResults, firstResult);
    }

    private List findByCriteria(final Criteria criteria, final int maxResults, final int firstResult) {
        Assert.notNull(criteria, "Criteria must not be null");
        if (getHibernateTemplate().isCacheQueries()) {
            criteria.setCacheable(true);
            if (getHibernateTemplate().getQueryCacheRegion() != null) {
                criteria.setCacheRegion(getHibernateTemplate().getQueryCacheRegion());
            }
        }
        if (getHibernateTemplate().getFetchSize() > 0) {
            criteria.setFetchSize(getHibernateTemplate().getFetchSize());
        }

        SessionFactoryUtils.applyTransactionTimeout(criteria, getSessionFactory());
        CriteriaImpl impl = (CriteriaImpl) criteria;
        ResultTransformer transformer = impl.getResultTransformer();
        if (transformer == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (firstResult >= 0) {
            criteria.setFirstResult(firstResult);
        }
        if (maxResults > 0) {
            criteria.setMaxResults(maxResults);
        }
        if (getHibernateTemplate().getMaxResults() > 0) {
            criteria.setMaxResults(getHibernateTemplate().getMaxResults());
        }
        this.hibernateTemplate.setCacheQueries(CACHEQUERIES_DEFAULT);
        return criteria.list();
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findPageByCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria) {
        return findPageByCriteria(detachedCriteria, PaginationSupport.PAGESIZE, 0);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findPageByCriteria(org.hibernate.criterion.DetachedCriteria, int)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int startIndex) {
        return findPageByCriteria(detachedCriteria, PaginationSupport.PAGESIZE, startIndex);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findPageByCriteria(org.hibernate.criterion.DetachedCriteria, int, int)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int pageSize, int startIndex) {
        Assert.notNull(detachedCriteria, "DetachedCriteria must not be null");
        List items = findByCriteria(copy(detachedCriteria), pageSize, startIndex);
        int totalCount = getCountByCriteria(copy(detachedCriteria));
        PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
        return ps;
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findPageByCriteria(org.hibernate.criterion.DetachedCriteria, int, int, int)
     */
    @Override
    public PaginationSupport findPageByCriteria(DetachedCriteria detachedCriteria, int total, int pageSize, int startIndex) {
        Assert.notNull(detachedCriteria, "DetachedCriteria must not be null");
        List items = findByCriteria(detachedCriteria, pageSize, startIndex);
        PaginationSupport ps = new PaginationSupport(items, total, pageSize, startIndex);
        return ps;
    }

    private PaginationSupport findPageByCriteria(Criteria criteria, int total, int pageSize, int startIndex) {
        Assert.notNull(criteria, "Criteria must not be null");
        List items = findByCriteria(criteria, pageSize, startIndex);
        PaginationSupport ps = new PaginationSupport(items, total, pageSize, startIndex);
        return ps;
    }

    /**
     * @param detachedCriteria
     * @return
     */
    public int getCountByCriteria4Cartoon(final DetachedCriteria detachedCriteria) {
        Integer count = (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = detachedCriteria.getExecutableCriteria(session);
                return criteria.setProjection(Projections.rowCount()).uniqueResult();
            }
        });
        return count.intValue();
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#flush()
     */
    @Override
    public void flush() {
        getHibernateTemplate().flush();
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#get(java.lang.Class, java.io.Serializable)
     */
    @Override
    public <T> T get(Class<T> entity, Serializable id) {
        return getHibernateTemplate().get(entity, id);
    }

    @Override
    public int getCountByCriteria(TableModel tableModel, DetachedCriteria detachedCriteria) {
        Criteria countCriteria = copy(detachedCriteria).getExecutableCriteria(getSession());
        countCriteria.setProjection(null);
        removeOrders(countCriteria);
        appendSearchField(tableModel, countCriteria);
        int total = (Integer) countCriteria.setProjection(Projections.rowCount()).uniqueResult();
        return total;
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#getCountByCriteria(org.hibernate.criterion.DetachedCriteria)
     */
    @Override
    public int getCountByCriteria(final DetachedCriteria detachedCriteria) {
        Integer count = (Integer) getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Criteria c = copy(detachedCriteria).getExecutableCriteria(session);
                CriteriaImpl impl = (CriteriaImpl) c;
                try {
                    BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

                int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();
                if (totalCount < 1) {
                    return -1;
                }

                return Integer.valueOf(totalCount);
            }
        });
        return count.intValue();
    }

    private int getCountByCriteria(final Criteria c) {
        CriteriaImpl impl = (CriteriaImpl) c;
        try {
            BeanUtils.setFieldValue(impl, "orderEntries", new ArrayList());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();
        return totalCount;

    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#load(java.lang.Class, java.io.Serializable)
     */
    @Override
    public Object load(Class entity, Serializable id) {
        return getHibernateTemplate().load(entity, id);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#save(java.lang.Object)
     */
    @Override
    public void save(Object entity) {
        getHibernateTemplate().save(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#saveOrUpdate(java.lang.Object)
     */
    @Override
    public void saveOrUpdate(Object entity) {
        getHibernateTemplate().saveOrUpdate(entity);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#update(java.lang.Object)
     */
    @Override
    public void update(Object entity) {
        getHibernateTemplate().update(entity);
    }

    @Override
    public void saveOrUpdateAll(Collection collection) {
        getHibernateTemplate().saveOrUpdateAll(collection);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#update(java.lang.Object, org.hibernate.LockMode)
     */
    @Override
    public void update(Object entity, LockMode lockMode) {
        getHibernateTemplate().update(entity, lockMode);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#getJdbcTemplateInstance()
     */
    public JdbcTemplate getJdbcTemplateInstance() {
        return jdbcTemplate;
    }

    @Resource
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public DetachedCriteria copy(DetachedCriteria criteria) {
        try {
            ByteArrayOutputStream baostream = new ByteArrayOutputStream();
            ObjectOutputStream oostream = new ObjectOutputStream(baostream);
            oostream.writeObject(criteria);
            oostream.flush();
            oostream.close();

            ByteArrayInputStream baistream = new ByteArrayInputStream(baostream.toByteArray());
            ObjectInputStream oistream = new ObjectInputStream(baistream);
            DetachedCriteria copy = (DetachedCriteria) oistream.readObject();
            oistream.close();

            return copy;
        } catch (Exception t) {
            throw new HibernateException(t);
        }
    }

    private Criteria copy(Criteria criteria) {
        try {
            ByteArrayOutputStream baostream = new ByteArrayOutputStream();
            ObjectOutputStream oostream = new ObjectOutputStream(baostream);
            oostream.writeObject(criteria);
            oostream.flush();
            oostream.close();

            ByteArrayInputStream baistream = new ByteArrayInputStream(baostream.toByteArray());
            ObjectInputStream oistream = new ObjectInputStream(baistream);
            Criteria copy = (Criteria) oistream.readObject();
            oistream.close();

            return copy;
        } catch (Exception t) {
            throw new HibernateException(t);
        }
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#setCacheable(java.lang.Boolean)
     */
    @Override
    public void setCacheable(Boolean result) {
        this.hibernateTemplate.setCacheQueries(result);
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findByProperty(java.lang.Class, java.lang.String[], java.lang.String[])
     */
    @Override
    public List findByProperty(Class entity, String[] properties, String[] params) {
        StringBuffer hql = new StringBuffer();
        hql.append("from ").append(entity.getName()).append(" where 1=1 ");
        if ((properties != null) && (properties.length > 0)) {
            for (int i = 0; i < properties.length; i++) {
                if ((params != null) && !StringUtils.isEmpty(params[i])) {
                    String property = properties[i];
                    hql.append(" and ").append(property).append(" = '").append(params[i]).append("'");
                }
            }
        }
        return find(hql.toString());
    }

    /*
     * (non-Javadoc)
     * @see com.digitalchina.dcis.core.dao.ICommonDao#findObjByProperty(java.lang.Class, java.lang.String[], java.lang.String[])
     */
    @Override
    public Object findObjByProperty(Class entity, String[] properties, String[] params) {
        List list = findByProperty(entity, properties, params);
        if ((list != null) && !(list.isEmpty())) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List findByProperty(Class entity, String[] properties, String[] params, String[] orderProperties, String[] orderValue) {
        StringBuffer hql = new StringBuffer();
        hql.append("from ").append(entity.getName()).append(" where 1=1 ");
        if ((properties != null) && (properties.length > 0)) {
            for (int i = 0; i < properties.length; i++) {
                if ((params != null) && !StringUtils.isEmpty(params[i])) {
                    String property = properties[i];
                    hql.append(" and ").append(property).append(" = '").append(params[i]).append("'");
                }
            }
        }
        if ((orderProperties != null) && (orderProperties.length > 0)) {
            hql.append(" order by ");
            for (int i = 0; i < orderProperties.length; i++) {
                if ((orderValue != null) && !StringUtils.isEmpty(orderValue[i])) {
                    String orderPropertie = orderProperties[i];
                    hql.append(orderPropertie).append(" ").append(orderValue[i]);
                }
                if (i < (orderProperties.length - 1)) {
                    hql.append(",");
                }
            }
        }
        return find(hql.toString());
    }

    @Override
    public boolean checkDuplicateField(Class<?> clazz, String idName, Object idValue, String fieldName, Object fieldValue) {
        DetachedCriteria dc = DetachedCriteria.forClass(clazz);
        if (fieldValue instanceof String) {
            dc.add(Restrictions.eq(fieldName, fieldValue).ignoreCase());
        } else {
            dc.add(Restrictions.eq(fieldName, fieldValue));
        }
        if (idValue != null) {
            dc.add(Restrictions.ne(idName, idValue));
        }
        List<?> list = findByCriteria(dc);
        return ((list != null) && !list.isEmpty());
    }

    @Override
    public void clear() {
        getHibernateTemplate().clear();
    }

    @Override
    public void merge(Object object) {
        getHibernateTemplate().merge(object);
    }

    @Override
    public int executeUpdate(String hql, Object... params) {
        return executeUpdate(hql, params, null);
    }

    @Override
    public int executeUpdate(String hql, Object[] params, Type[] types) {
        Query query = getSession().createQuery(hql);
        bindParameters(query, params, types);
        return query.executeUpdate();
    }

    @Override
    public PaginationSupport findPageByCriteria(TableModel tableModel, DetachedCriteria detachedCriteria, int total, int pageSize, int startIndex) {
        return findPageByCriteria(tableModel, detachedCriteria, pageSize, startIndex);
    }

    @Override
    public PaginationSupport findPageByCriteria(TableModel tableModel, DetachedCriteria detachedCriteria, int pageSize, int startIndex) {
        int total = getCountByCriteria(tableModel, detachedCriteria);
        Criteria dataListCriteria = detachedCriteria.getExecutableCriteria(getSession());
        appendSearchField(tableModel, dataListCriteria);
        if ((tableModel != null) && tableModel.isSortActive()) {
            if ("asc".equalsIgnoreCase(tableModel.getSortType())) {
                dataListCriteria.addOrder(Order.asc(tableModel.getSortField()));
            } else {
                dataListCriteria.addOrder(Order.desc(tableModel.getSortField()));
            }
        }
        return findPageByCriteria(dataListCriteria, total, pageSize, startIndex);
    }

    private boolean needParse(String dataType) {
        if (SearchValueBindControllerTag.DATA_TYPE_DATE.equalsIgnoreCase(dataType) || SearchValueBindControllerTag.DATA_TYPE_TIMESTAMP.equalsIgnoreCase(dataType) || SearchValueBindControllerTag.DATA_TYPE_BIGDECIMAL.equalsIgnoreCase(dataType) || SearchValueBindControllerTag.DATA_TYPE_DOUBLE.equalsIgnoreCase(dataType) || SearchValueBindControllerTag.DATA_TYPE_FLOAT.equalsIgnoreCase(dataType)
                || SearchValueBindControllerTag.DATA_TYPE_INTEGER.equalsIgnoreCase(dataType) || SearchValueBindControllerTag.DATA_TYPE_LONG.equalsIgnoreCase(dataType) || SearchValueBindControllerTag.DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
            return true;
        }
        return false;

    }

    private void appendSearchField(TableModel tableModel, Criteria criteria) {
        if ((tableModel != null) && (tableModel.getSearchKeyMap() != null) && !tableModel.getSearchKeyMap().isEmpty()) {
            //Class targetClass = criteria.getClass();
            Iterator iter = tableModel.getSearchKeyMap().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                Object key = entry.getKey();
                Object val = entry.getValue();
                String propertyName = key.toString();
                String matchType = tableModel.getMatchTypeMap().get(key);

                String dataType = tableModel.getDataTypeMap().get(key);
                if (StringUtils.isBlank(dataType)) {
                    dataType = SearchValueBindControllerTag.DATA_TYPE_STRING;
                }
                try {
                    //Field field = targetClass.getDeclaredField(key.toString());
                    String keyString = key.toString();
                    if (keyString.indexOf(SearchValueBindControllerTag.ALAIS_SPLIT_KEY) != 0) {
                        String[] strs = key.toString().split(SearchValueBindControllerTag.ALAIS_SPLIT_KEY);
                        keyString = strs[0];
                        propertyName = keyString;
                    }
                    if (keyString.indexOf('.') != 0) {
                        String[] strs = keyString.split("\\.");
                        for (int i = 0; i < strs.length; i++) {
                            if (i < (strs.length - 1)) {
                                criteria.createAlias(strs[i], strs[i]);
                            }
                        }
                    }

                    if (needParse(dataType) && (SearchValueBindControllerTag.MATCH_TYPE_EQ.equalsIgnoreCase(matchType) || StringUtils.isBlank(matchType))) {
                        if (SearchValueBindControllerTag.DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.between(propertyName, DateUtils.getBeginDateByStr(val.toString(), null), DateUtils.getEndDateByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_TIMESTAMP.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.between(propertyName, DateUtils.getBeginTimeByStr(val.toString(), null), DateUtils.getEndTimeByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_BIGDECIMAL.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.eq(propertyName, new BigDecimal(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_DOUBLE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.eq(propertyName, Double.parseDouble(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_FLOAT.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.eq(propertyName, Float.parseFloat(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_INTEGER.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.eq(propertyName, Integer.parseInt(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_LONG.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.eq(propertyName, Long.parseLong(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.eq(propertyName, val.toString()).ignoreCase());
                        }

                    } else if (SearchValueBindControllerTag.MATCH_TYPE_LIKE.equalsIgnoreCase(matchType)) {
                        criteria.add(Restrictions.like(propertyName, val.toString(), MatchMode.ANYWHERE).ignoreCase());
                    } else if (SearchValueBindControllerTag.MATCH_TYPE_LEFTLIKE.equalsIgnoreCase(matchType)) {
                        criteria.add(Restrictions.like(propertyName, "%" + val));
                    } else if (SearchValueBindControllerTag.MATCH_TYPE_RIGHTLIKE.equalsIgnoreCase(matchType)) {
                        criteria.add(Restrictions.like(propertyName, val + "%"));
                    } else if (needParse(dataType) && SearchValueBindControllerTag.MATCH_TYPE_GE.equalsIgnoreCase(matchType)) {
                        if (SearchValueBindControllerTag.DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, DateUtils.getBeginDateByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_TIMESTAMP.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, DateUtils.getBeginTimeByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_BIGDECIMAL.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, new BigDecimal(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_DOUBLE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, Double.parseDouble(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_FLOAT.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, Float.parseFloat(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_INTEGER.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, Integer.parseInt(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_LONG.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, Long.parseLong(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.ge(propertyName, val.toString()).ignoreCase());
                        }
                    } else if (needParse(dataType) && SearchValueBindControllerTag.MATCH_TYPE_GT.equalsIgnoreCase(matchType)) {
                        if (SearchValueBindControllerTag.DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, DateUtils.getBeginDateByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_TIMESTAMP.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, DateUtils.getBeginTimeByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_BIGDECIMAL.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, new BigDecimal(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_DOUBLE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, Double.parseDouble(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_FLOAT.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, Float.parseFloat(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_INTEGER.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, Integer.parseInt(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_LONG.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, Long.parseLong(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.gt(propertyName, val.toString()).ignoreCase());
                        }
                    } else if (needParse(dataType) && SearchValueBindControllerTag.MATCH_TYPE_LE.equalsIgnoreCase(matchType)) {
                        if (SearchValueBindControllerTag.DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, DateUtils.getEndDateByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_TIMESTAMP.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, DateUtils.getEndTimeByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_BIGDECIMAL.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, new BigDecimal(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_DOUBLE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, Double.parseDouble(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_FLOAT.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, Float.parseFloat(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_INTEGER.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, Integer.parseInt(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_LONG.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, Long.parseLong(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.le(propertyName, val.toString()).ignoreCase());
                        }
                    } else if (needParse(dataType) && SearchValueBindControllerTag.MATCH_TYPE_LT.equalsIgnoreCase(matchType)) {
                        if (SearchValueBindControllerTag.DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, DateUtils.getEndDateByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_TIMESTAMP.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, DateUtils.getEndTimeByStr(val.toString(), null)));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_BIGDECIMAL.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, new BigDecimal(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_DOUBLE.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, Double.parseDouble(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_FLOAT.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, Float.parseFloat(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_INTEGER.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, Integer.parseInt(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_LONG.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, Long.parseLong(val.toString())));
                        } else if (SearchValueBindControllerTag.DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                            criteria.add(Restrictions.lt(propertyName, val.toString()).ignoreCase());
                        }
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            }
        }
    }

    private void removeOrders(Criteria criteria) {
        try {
            Field field = CriteriaImpl.class.getDeclaredField("orderEntries");
            field.setAccessible(true);
            field.set(criteria, new ArrayList());
        } catch (Exception ex) {
            log.debug(ex.getMessage());
        }
    }

    @Override
    public void evictHibernateCache() throws Exception {
        SessionFactory sf = getSessionFactory();
        Map<String, CollectionMetadata> roleMap = sf.getAllCollectionMetadata();
        for (String roleName : roleMap.keySet()) {
            sf.evictCollection(roleName);
        }
        Map<String, ClassMetadata> entityMap = sf.getAllClassMetadata();
        for (String entityName : entityMap.keySet()) {
            sf.evictEntity(entityName);
        }
        sf.evictQueries();
    }

}
