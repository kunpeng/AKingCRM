/**
 * 
 */
package com.aking.model.constant;

/**
 * @author Administrator
 * 
 */
public enum MenuGroup {
	SYSTEM(0), // 功能菜单
	CUSTOMER(1);// 客户菜单

	int id;

	private MenuGroup(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}
}
