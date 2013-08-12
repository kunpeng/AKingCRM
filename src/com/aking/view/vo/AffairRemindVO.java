package com.aking.view.vo;

public class AffairRemindVO {
	private String id;
	private String affairDate;
	private String affairTime;
	private String theme;
	private String memo;
	private String recordDateTime;
	private String recordUser;

	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAffairDate() {
		return affairDate;
	}

	public void setAffairDate(String affairDate) {
		this.affairDate = affairDate;
	}

	public String getAffairTime() {
		return affairTime;
	}

	public void setAffairTime(String affairTime) {
		this.affairTime = affairTime;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRecordDateTime() {
		return recordDateTime;
	}

	public void setRecordDateTime(String recordDateTime) {
		this.recordDateTime = recordDateTime;
	}

}
