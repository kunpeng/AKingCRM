package com.aking.view.vo;

public class BusinessContactVO {
	// 标识
	private String id;

	private String activityThemeId;

	// 主题
	private String activityTheme;

	private String contactWayId;

	// 洽谈方式
	private String contactWay;

	private String chargeUserId;

	// 负责人
	private String chargeUser;

	// 录入员
	private String recordUser;

	private String recordUserId;

	// 客户
	private String customer;

	// 联系日期
	private String contactDate;

	public String getContactWayId() {
		return contactWayId;
	}

	public void setContactWayId(String contactWayId) {
		this.contactWayId = contactWayId;
	}

	public String getChargeUserId() {
		return chargeUserId;
	}

	public void setChargeUserId(String chargeUserId) {
		this.chargeUserId = chargeUserId;
	}

	public String getActivityThemeId() {
		return activityThemeId;
	}

	public void setActivityThemeId(String activityThemeId) {
		this.activityThemeId = activityThemeId;
	}

	public String getContactDate() {
		return contactDate;
	}

	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivityTheme() {
		return activityTheme;
	}

	public void setActivityTheme(String activityTheme) {
		this.activityTheme = activityTheme;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getRecordUserId() {
		return recordUserId;
	}

	public void setRecordUserId(String recordUserId) {
		this.recordUserId = recordUserId;
	}

}
