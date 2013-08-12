package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.UserDao;
import com.aking.exception.UserException;
import com.aking.model.subject.User;
import com.aking.model.system.MenuUser;
import com.aking.model.system.SystemFunctionUser;
import com.aking.util.BeanUtil;
import com.aking.view.vo.MenuVO;
import com.aking.view.vo.SystemFunctionVO;
import com.aking.view.vo.UserVO;

@Component
public class UserService extends BaseService<User, String> {

	@Autowired
	private UserDao userDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return userDao;
	}

	public List<UserVO> loadForCombo() throws Exception {
		List<User> users = userDao.loadAll();
		List<UserVO> userVOs = new ArrayList<UserVO>();
		for (User user : users) {
			UserVO userVO = new UserVO();
			BeanUtil.copyProperties(user, userVO);
			userVO.setChargeUserId(user.getId());
			userVOs.add(userVO);
		}
		return userVOs;
	}

	public List<UserVO> loadListByOrg() throws Exception {
		return this.loadForCombo();
	}

	public List<UserVO> getByOrg(String orgId) throws Exception {
		try {
			String hql = "From User u where u.org ='" + orgId + "'";
			List<User> users = userDao.find(hql);
			List<UserVO> userVOs = new ArrayList<UserVO>();
			for (User user : users) {
				UserVO userVO = new UserVO();
				BeanUtil.copyProperties(user, userVO);
				userVO.setChargeUserId(user.getId());
				userVO.setOrgCode(user.getOrg().getCode());
				userVO.setOrgId(user.getOrg().getId());
				userVOs.add(userVO);
			}
			return userVOs;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public UserVO getUserByID(String userid) throws Exception {
		UserVO userVO = new UserVO();
		User user = userDao.get(userid);
		if (user == null) {
			throw new UserException("not found such user!");
		}
		BeanUtil.copyProperties(user, userVO);
		List<MenuVO> menuVOs = new ArrayList<MenuVO>();
		for (MenuUser menuUser : user.getMenuUsers()) {
			MenuVO menuVO = new MenuVO();
			BeanUtil.copyProperties(menuUser.getMenu(), menuVO);
			menuVOs.add(menuVO);
		}
		userVO.setMenuVOs(menuVOs);
		List<SystemFunctionVO> systemFunctionVOs = new ArrayList<SystemFunctionVO>();
		for (SystemFunctionUser systemFunctionUser : user.getSystemFunctionUsers()) {
			SystemFunctionVO systemFunctionVO = new SystemFunctionVO();
			BeanUtil.copyProperties(systemFunctionUser.getSystemFunction(), systemFunctionVO);
			systemFunctionVOs.add(systemFunctionVO);
		}
		userVO.setSystemFunctionVOs(systemFunctionVOs);
		return userVO;
	}

	/**
	 * 验证用户身份
	 * 
	 * @param userid
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public User checkUser(String userid,
			String password) throws Exception {
		String hql = "From User u where u.code = '" + userid + "' and u.password = '" + password + "'";
		List<User> users = userDao.find(hql);
		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 验证用户是否登录
	 * 
	 * @param session
	 * @return
	 */
	public static Boolean checkLogin(HttpSession session) {
		String crmuser = (String) session.getAttribute("usercode");
		if (crmuser != null && crmuser.length() > 0) {
			return true;
		}
		return false;
	}

	public User getByCode(String userCode) throws Exception {
		String hql = "From User u where u.code = '" + userCode + "'";
		List<User> users = userDao.find(hql);
		if (users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

}
