package com.aking.dao.base;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.aking.dao.base.PaginationSupport;

@SuppressWarnings("unchecked")
public abstract class GenericDaoImpl<T, ID extends Serializable> extends HibernateDaoSupport implements
		GenericDao<T, ID> {
	private Log logger = LogFactory.getLog(getClass());

	protected Class<T> entityClass;

	public GenericDaoImpl() {
	}

	@SuppressWarnings("rawtypes")
	protected Class getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
					.getActualTypeArguments()[0];
			logger.debug("T class = " + entityClass.getName());
		}
		return entityClass;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#saveOrUpdate(T)
	 */
	public void saveOrUpdate(T t) throws DataAccessException {
		this.getHibernateTemplate().saveOrUpdate(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#load(ID)
	 */
	public T load(ID id) throws DataAccessException {
		T load = (T) getHibernateTemplate().load(getEntityClass(), id);
		return load;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#get(ID)
	 */
	public T get(ID id) throws DataAccessException {
		T load = (T) getHibernateTemplate().get(getEntityClass(), id);
		return load;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#contains(T)
	 */
	public boolean contains(T t) throws DataAccessException {
		return getHibernateTemplate().contains(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#delete(T, org.hibernate.LockMode)
	 */
	public void delete(T t,
			LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().delete(t, lockMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#delete(T)
	 */
	public void delete(T t) throws DataAccessException {
		getHibernateTemplate().delete(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#deleteAll(java.util.Collection)
	 */
	public void deleteAll(Collection<T> entities) throws DataAccessException {
		getHibernateTemplate().deleteAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#find(java.lang.String, java.lang.Object)
	 */
	public List<T> find(String queryString,
			Object value) throws DataAccessException {
		List<T> find = (List<T>) getHibernateTemplate().find(queryString, value);
		return find;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#find(java.lang.String, java.lang.Object[])
	 */
	public List<T> find(String queryString,
			Object[] values) throws DataAccessException {
		List<T> find = (List<T>) getHibernateTemplate().find(queryString, values);
		return find;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#find(java.lang.String)
	 */
	public List<T> find(String queryString) throws DataAccessException {
		// System.out.println(queryString);
		return (List<T>) getHibernateTemplate().find(queryString);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#refresh(T, org.hibernate.LockMode)
	 */
	public void refresh(T t,
			LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().refresh(t, lockMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#refresh(T)
	 */
	public void refresh(T t) throws DataAccessException {
		getHibernateTemplate().refresh(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#save(T)
	 */
	public Serializable save(T t) throws DataAccessException {
		return getHibernateTemplate().save(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#saveOrUpdateAll(java.util.Collection)
	 */
	public void saveOrUpdateAll(Collection<T> entities) throws DataAccessException {
		getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#update(T, org.hibernate.LockMode)
	 */
	public void update(T t,
			LockMode lockMode) throws DataAccessException {
		getHibernateTemplate().update(t, lockMode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#update(T)
	 */
	public void update(T t) throws DataAccessException {
		getHibernateTemplate().update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#list()
	 */
	public List<T> list() throws DataAccessException {
		return getHibernateTemplate().loadAll(getEntityClass());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#findByNamedQuery(java.lang.String)
	 */
	public List<T> findByNamedQuery(String queryName) throws DataAccessException {
		return getHibernateTemplate().findByNamedQuery(queryName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#findByNamedQuery(java.lang.String, java.lang.Object)
	 */
	public List<T> findByNamedQuery(String queryName,
			Object value) throws DataAccessException {
		return getHibernateTemplate().findByNamedQuery(queryName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#findByNamedQuery(java.lang.String, java.lang.Object[])
	 */
	public List<T> findByNamedQuery(String queryName,
			Object[] values) throws DataAccessException {
		return getHibernateTemplate().findByNamedQuery(queryName, values);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#findPageByCriteria(org.hibernate.criterion .DetachedCriteria, int, int)
	 */
	@SuppressWarnings("rawtypes")
	public PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria,
			final int pageSize,
			final int startIndex) {
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = detachedCriteria.getExecutableCriteria(session);
				int totalCount = ((Integer) criteria.setProjection(Projections.rowCount()).uniqueResult())
						.intValue();
				criteria.setProjection(null);
				List items = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.dtsz.model.base.GenericDao#findPageByQuery(java.lang.String, java.lang.String, int, int)
	 */
	@SuppressWarnings("rawtypes")
	public PaginationSupport findPageByQuery(final String hql,
			final String countHql,
			final int pageSize,
			final int startIndex) {
		return (PaginationSupport) getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				int totalCount = ((Integer) session.createQuery(countHql).iterate().next()).intValue();
				Query query = session.createQuery(hql);
				query.setFirstResult(startIndex);
				query.setMaxResults(pageSize);
				List items = query.list();
				PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
				return ps;

			}
		});
	}

	/**
	 * 根据hql加载分页，默认页大小，从第0条开始
	 */
	public PaginationSupport findPageByQuery(final String hql,
			Object... values) {
		return findPageByQuery(hql, PaginationSupport.PAGESIZE, 0, values);
	}

	/**
	 * 根据hql加载分页，默认页大小，从第startIndex条开始
	 */
	public PaginationSupport findPageByQuery(final String hql,
			final int startIndex,
			Object... values) {
		return findPageByQuery(hql, PaginationSupport.PAGESIZE, startIndex, values);
	}

	/**
	 * 根据hql加载分页，指定页大小和起始位置
	 */
	@SuppressWarnings("rawtypes")
	public PaginationSupport findPageByQuery(final String hql,
			final int pageSize,
			final int startIndex,
			Object... values) {
		int totalCount = getCountByQuery(hql, values);

		if (totalCount < 1)
			return new PaginationSupport(new ArrayList(0), 0);

		Query query = createQuery(hql, values);
		List items = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
		PaginationSupport ps = new PaginationSupport(items, totalCount, pageSize, startIndex);
		return ps;
	}

	/**
	 * 根据hql统计总数
	 */
	@SuppressWarnings("rawtypes")
	public int getCountByQuery(final String hql,
			Object... values) {
		String countQueryString = " select count (*) " + removeSelect(removeOrders(hql));
		List countlist = getHibernateTemplate().find(countQueryString, values);
		return Integer.parseInt(countlist.get(0).toString());
	}

	/**
	 * 方法取自SpringSide. 创建Query对象. 对于需要first,max,fetchsize,cache,cacheRegion等诸多设置的函数,可以在返回Query后自行设置. 留意可以连续设置,如下：
	 * 
	 * <pre>
	 * dao.getQuery(hql).setMaxResult(100).setCacheable(true).list();
	 * </pre>
	 * 
	 * 调用方式如下：
	 * 
	 * <pre>
	 *        dao.createQuery(hql)  
	 *        dao.createQuery(hql,arg0);  
	 *        dao.createQuery(hql,arg0,arg1);  
	 *        dao.createQuery(hql,new Object[arg0,arg1,arg2])
	 * </pre>
	 * 
	 * @param values
	 *            可变参数.
	 */
	public Query createQuery(String hql,
			Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 方法取自SpringSide. 去除hql的orderby子句
	 */
	private static String removeOrders(String hql) {
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}

	/**
	 * 方法取自SpringSide. 去除hql的select子句，未考虑union的情况
	 */
	private static String removeSelect(String hql) {
		int beginPos = hql.toLowerCase().indexOf("from");
		return hql.substring(beginPos);
	}

	// 加载对象

	/** */
	/**
	 * 加载所有的对象
	 */
	public List<T> loadAll() {
		return (List<T>) getHibernateTemplate().loadAll(getEntityClass());
	}

	public void merge(T t) throws DataAccessException {
		getHibernateTemplate().merge(t);
	}

	public T objectMerge(T t) throws DataAccessException {
		return (T) getHibernateTemplate().merge(t);
		// getHibernateTemplate().flush();
		// return (T) result;
	}

}