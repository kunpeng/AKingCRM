package com.aking.control.action;

import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.OrgServcie;
import com.aking.control.service.UserService;
import com.aking.model.constant.Constants;
import com.aking.model.constant.RoleType;
import com.aking.model.subject.Org;
import com.aking.model.subject.User;
import com.aking.util.CrmUtil;
import com.aking.util.PropertyFileUtil;
import com.aking.util.WriteResult;
import com.aking.view.vo.UserVO;

@Component
public class UserAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private UserService userService;

	@Autowired
	private OrgServcie orgServcie;

	public void loadForCombo() throws Exception {
		JSONArray users = new JSONArray();
		List<UserVO> userVOs = userService.loadForCombo();
		for (UserVO userVO : userVOs) {
			JSONObject userObj = new JSONObject();
			userObj.element("id", userVO.getId());
			userObj.element("name", userVO.getName());
			userObj.element("label", userVO.getName());
			users.add(userObj);
		}

		WriteResult.outputResult(users.toString(), response);

		// WriteResult.outPutListResult(userVOs, ServletActionContext.getResponse());
	}

	public void loadListByOrg() {
		try {
			// HttpServletRequest request = ServletActionContext.getRequest();
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			String orgId = request.getParameter("orgid");
			List<UserVO> userVOs = userService.getByOrg(orgId);
			JSONArray rows = new JSONArray();
			for (UserVO user : userVOs) {
				user.setPassword(CrmUtil.getInstance().decrypt(user.getPassword()));
				rows.add(user);
			}
			WriteResult.outputResult(rows.toString(), response);
			// JSONObject resultObj = new JSONObject();
			// List<UserVO> userVOs = userService.loadListByOrg();
			// JSONArray rows = new JSONArray();
			// for (int i = 0; i < userVOs.size(); i++) {
			// UserVO userVO = userVOs.get(i);
			// String[] data = new String[3];
			// data[0] = userVO.getName();
			// data[1] = userVO.getCode();
			// data[2] = userVO.getPassword();
			//
			// JSONObject userObj = new JSONObject();
			// userObj.element("id", userVO.getId());
			// userObj.element("cell", data);
			//
			// rows.add(userObj);
			// }
			// resultObj.element("page", 1);
			// resultObj.element("total", userVOs.size());
			// resultObj.element("rows", rows);
			// WriteResult.outputResult(resultObj.toString(), response);
			// WriteResult.outPutListResult(userVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @SuppressWarnings("unchecked")
	public void saveUser() throws Exception {
		// ActionContext context = ActionContext.getContext();
		// Map<String, Object> paramMap = context.getParameters();
		// Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		logger.log(Level.INFO, "正在保存用户...");

		String id = request.getParameter("userid");
		String orgId = request.getParameter("orgid");
		String userName = request.getParameter("username");
		String userCode = request.getParameter("usercode");
		String userPass = request.getParameter("userpass");
		String roleType = request.getParameter("roletype");

		Org org = orgServcie.getById(orgId);

		User user;
		if (id == null || id.length() <= 0) {
			user = new User();
			user.setOrg(org);
		} else {
			user = userService.getById(id);
		}
		user.setName(userName);
		user.setCode(userCode);
		user.setPassword(CrmUtil.getInstance().encrypt(userPass));

		// 定义用户角色类型
		if (roleType.equalsIgnoreCase("admin")) {
			user.setRoleType(RoleType.ADMIN);
		} else {
			user.setRoleType(RoleType.NORMAL);
		}

		userService.save(user);

		// String data = request.getParameter("data");
		// if (data.startsWith("[")) {
		// JSONArray jsondata = JSONArray.fromObject(data);
		// for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
		// JSONObject jsonObj = ite.next();
		// User user = new User();
		// user.setName(jsonObj.getString("name"));
		// user.setCode(jsonObj.getString("code"));
		// userService.save(user);
		// }
		// } else {
		// JSONObject jsondata = JSONObject.fromObject(data);
		// User user = new User();
		// user.setName(jsondata.getString("name"));
		// user.setCode(jsondata.getString("code"));
		// userService.save(user);
		// }
		String result = "{\"success\":\"true\",\"info\":\"保存成功!\",\"id\":\"" + user.getId() + "\"}";
		WriteResult.outputResult(result, response);
	}

	public void delUser() throws Exception {
		logger.log(Level.INFO, "正在删除用户...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("ids");
		String[] ids = data.split(",");
		for (String id : ids) {
			User user = userService.getById(id);
			userService.delete(user);
		}
	}

	public void updateUser() throws Exception {
		logger.log(Level.INFO, "正在修改用户...");
		String result = "";
		// HttpServletRequest request = ServletActionContext.getRequest();
		String userId = request.getParameter("userid");
		// String userCode = request.getParameter("usercode");
		// String userName = request.getParameter("username");
		String olduserpass = request.getParameter("olduserpass");
		String userpass = request.getParameter("userpass");
		User user = userService.getById(userId);
		if (!user.getPassword().equals(CrmUtil.getInstance().encrypt(olduserpass))) {
			result = "{\"success\":\"failure\",\"info\":\"您输入的密码与原密码不一致!\"}";
		} else {
			user.setPassword(CrmUtil.getInstance().encrypt(userpass));
			userService.save(user);
			result = "{\"success\":\"true\",\"info\":\"密码修改成功\"}";
			this.clearSession();
		}
		// String data = request.getParameter("data");
		// if (data.startsWith("[")) {
		// JSONArray jsondata = JSONArray.fromObject(data);
		// for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
		// JSONObject jsonObj = ite.next();
		// String id = jsonObj.getString("id");
		// User user = userService.getById(id);
		// user.setName(jsonObj.getString("name"));
		// user.setCode(jsonObj.getString("code"));
		// userService.save(user);
		// }
		// } else {
		// JSONObject jsondata = JSONObject.fromObject(data);
		// String id = jsondata.getString("id");
		// User user = userService.getById(id);
		// // User user = new User();
		// user.setName(jsondata.getString("name"));
		// user.setCode(jsondata.getString("code"));
		// userService.save(user);
		// }
		// result = "{success:true,info:'保存成功!'}";
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	// public void getUserByID() throws Exception {
	// logger.log(Level.INFO, "用户登录...");
	// String userID = request.getParameter("userID");
	// UserVO userVO = userService.getUserByID(userID);
	// }

	@SuppressWarnings("unchecked")
	public String login() throws Exception {
		logger.log(Level.INFO, "用户登录...");
		Boolean checkResult = false;
		boolean isDefaultAdmin = false;
		User serverUser = null;
		// String result = "";
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 如果用户为admin，则视为管理员登录
		if (username.equalsIgnoreCase("admin")) {
			isDefaultAdmin = true;
		}
		if (isDefaultAdmin) {
			checkResult = this.adminLogin(password);
		} else {
			serverUser = userService.checkUser(username, CrmUtil.getInstance().encrypt(password));
			if (serverUser != null) {
				checkResult = true;
			}
		}
		if (checkResult) {
			// 保存登录用户信息至session
			if (isDefaultAdmin) {
				this.updateSession(isDefaultAdmin, null);
			} else {
				this.updateSession(isDefaultAdmin, serverUser);
			}
			session.remove("errinfo");
			// request.setAttribute("crmuser", username);
			return "success";
			// result = "{success:true,info:'登录成功!'}";
		} else {
			session.put("errinfo", "用户名或密码错误");
			return "failure";
			// result = "{success:false,info:'登录失败!'}";
		}
		// WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void logout() throws Exception {
		logger.log(Level.INFO, "用户注销... ...");
		this.clearSession();
		// session.remove("crmuser");
		// return "success";
		String result = "{\"success\":\"true\",\"info\":\"用户注销成功!\"}";
		WriteResult.outputResult(result, response);
	}

	/**
	 * 管理员身份验证
	 * 
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private Boolean adminLogin(String password) throws Exception {
		String passstr = CrmUtil.getInstance().encrypt(password);
		Properties props = PropertyFileUtil.loadAllProps(Constants.propertiesPath);
		String passprop = props.getProperty("admin");
		if (passstr.equals(passprop)) {
			return true;
		}
		return false;
	}

	public void changeInfo() throws Exception {
		logger.log(Level.INFO, "修改密码... ...");
		String username = (String) session.get("crmuser");
		String userpass = request.getParameter("userpass");
		String newuserpass = request.getParameter("newuserpass");
		String newuserpassconfirm = request.getParameter("newuserpassconfirm");
		// 验证两次输入的新密码是否一致
		if (!newuserpass.equals(newuserpassconfirm)) {
			String result = "{\"success\":\"false\",\"info\":\"新密码不一致!\"}";
			WriteResult.outputResult(result, response);
			return;
		}
		// 验证用户名和密码是否正确
		Boolean checkResult = false;
		// String result = "";
		// 如果用户为admin，则视为管理员登录
		if (username.equalsIgnoreCase("admin")) {
			checkResult = this.adminLogin(userpass);
		} else {
			// checkResult = userService.checkUser(username, CrmUtil.getInstance().encrypt(userpass));
		}
		if (!checkResult) {
			String result = "{\"success\":\"false\",\"info\":\"用户名或旧密码错误!\"}";
			WriteResult.outputResult(result, response);
			return;
		}
		// 修改用户密码
		// 修改管理员的密码
		if (username.equalsIgnoreCase("admin")) {
			PropertyFileUtil.setProperty(Constants.propertiesPath, "admin", CrmUtil.getInstance().encrypt(newuserpass));
		} else {
			// 修改一般用户的密码
			User user = userService.getByCode(username);
			user.setPassword(CrmUtil.getInstance().encrypt(newuserpass));
			userService.save(user);
		}
		String result = "{\"success\":\"true\",\"info\":\"修改成功!\"}";
		WriteResult.outputResult(result, response);
	}

	@SuppressWarnings("unchecked")
	private void updateSession(boolean isDefaultAdmin,
			User user) throws Exception {
		if (isDefaultAdmin) {
			session.put("isDefaultAdmin", "true");
			session.put("usercode", Constants.defaultAdminCode);
			session.put("username", "系统管理员");
			session.put("roletype", RoleType.ADMIN.toString());
		} else {
			session.put("isdefaultadmin", "false");
			session.put("userid", user.getId());
			session.put("usercode", user.getCode());
			session.put("username", user.getName());
			session.put("roletype", user.getRoleType().toString());
		}
	}

	private void clearSession() throws Exception {
		session.remove("isdefaultadmin");
		session.remove("userid");
		session.remove("usercode");
		session.remove("username");
		session.remove("roletype");
	}

	public static void main(String[] args) throws Exception {
		System.out.println(CrmUtil.getInstance().encrypt("1"));
		// System.out.println(RoleType.ADMIN.toString());
	}
}
