package com.aking.control.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.aking.dao.base.GenericDao;
import com.aking.util.BeanUtil;

/**
 */
@Component
@Transactional
public abstract class BaseService<E, PK extends Serializable> {

	protected Log log = LogFactory.getLog(getClass());

	protected abstract GenericDao<E, PK> getGenricDao();

	@Transactional(readOnly = true)
	public E getById(PK id) {
		return (E) getGenricDao().get(id);
	}

	public void saveOrUpdate(E entity) {
		getGenricDao().saveOrUpdate(entity);
	}

	@SuppressWarnings("unchecked")
	public E save(E entity) {
		return (E) getGenricDao().save(entity);
	}

	public void update(E entity) {
		getGenricDao().update(entity);
	}

	public void delete(E entity) {
		getGenricDao().delete(entity);
	}

	public List<E> loadAll() {
		return getGenricDao().loadAll();
	}

	public List<E> findRoot() {
		return null;
	}

	public List<?> copy2VO(List<E> oList,
			List<?> oListVO) throws Exception {
		List copyList = new ArrayList();
		for (Object o : oList) {
			for (Object ovo : oListVO) {
				BeanUtil.copyProperties(o, ovo);
				copyList.add(ovo);
			}
		}
		return copyList;
	}
}
