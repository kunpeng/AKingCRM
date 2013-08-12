package com.aking.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

import com.aking.dao.base.PaginationSupport;

public interface GenericDao<T, ID extends Serializable> {

	public abstract void saveOrUpdate(T t) throws DataAccessException;

	public abstract T load(ID id) throws DataAccessException;

	public abstract T get(ID id) throws DataAccessException;

	public abstract boolean contains(T t) throws DataAccessException;

	public abstract void delete(T t, LockMode lockMode) throws DataAccessException;

	public abstract void delete(T t) throws DataAccessException;

	public abstract void deleteAll(Collection<T> entities) throws DataAccessException;

	public abstract List<T> find(String queryString, Object value) throws DataAccessException;

	public abstract List<T> find(String queryString, Object[] values) throws DataAccessException;

	public abstract List<T> find(String queryString) throws DataAccessException;

	public abstract void refresh(T t, LockMode lockMode) throws DataAccessException;

	public abstract void refresh(T t) throws DataAccessException;

	public abstract Serializable save(T t) throws DataAccessException;

	public abstract void saveOrUpdateAll(Collection<T> entities) throws DataAccessException;

	public abstract void update(T t, LockMode lockMode) throws DataAccessException;

	public abstract void update(T t) throws DataAccessException;

	public abstract List<T> list() throws DataAccessException;

	public abstract List<T> loadAll() throws DataAccessException;

	public abstract List<T> findByNamedQuery(String queryName) throws DataAccessException;

	public abstract List<T> findByNamedQuery(String queryName, Object value) throws DataAccessException;

	public abstract List<T> findByNamedQuery(String queryName, Object[] values) throws DataAccessException;

	public abstract PaginationSupport findPageByCriteria(final DetachedCriteria detachedCriteria, final int pageSize, final int startIndex);

	public abstract PaginationSupport findPageByQuery(final String hql, final String countHql, final int pageSize, final int startIndex);

	public abstract void merge(T t) throws DataAccessException;
}