package com.aking.model.constant;

public enum RoleType {
	ADMIN(0), // 管理员
	NORMAL(1);// 普通用户
	int id;

	RoleType(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
