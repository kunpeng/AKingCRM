package com.aking.control.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.MenuDao;
import com.aking.model.system.Menu;

/**
 * @author AKing
 * @version 1.0
 */
@Component
public class MenuService extends BaseService<Menu, java.lang.String> {

	@Autowired
	private MenuDao menuDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return menuDao;
	}

	public void loadMenuFromMap() {

	}

	public void getFunctionFromMap() {

	}

}
