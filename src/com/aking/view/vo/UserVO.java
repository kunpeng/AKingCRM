package com.aking.view.vo;

import java.util.List;

import com.aking.model.constant.RoleType;

public class UserVO {
	private String id;
	private String chargeUserId;
	// 代码
	private String code;
	// 用户名称
	private String name;
	// 密码
	private String password;

	// 机构代码
	private String orgCode;
	// 机构ID
	private String orgId;

	// 角色类型
	private RoleType roleType;

	private List<MenuVO> menuVOs;

	private List<SystemFunctionVO> systemFunctionVOs;

	public List<SystemFunctionVO> getSystemFunctionVOs() {
		return systemFunctionVOs;
	}

	public void setSystemFunctionVOs(List<SystemFunctionVO> systemFunctionVOs) {
		this.systemFunctionVOs = systemFunctionVOs;
	}

	public List<MenuVO> getMenuVOs() {
		return menuVOs;
	}

	public void setMenuVOs(List<MenuVO> menuVOs) {
		this.menuVOs = menuVOs;
	}

	public String getChargeUserId() {
		return chargeUserId;
	}

	public void setChargeUserId(String chargeUserId) {
		this.chargeUserId = chargeUserId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

}
