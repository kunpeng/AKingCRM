package com.aking.control.action;

public class ExtStore {
	String success;
	int totalCount;
	Object aaData;

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Object getAaData() {
		return aaData;
	}

	public void setAaData(Object aaData) {
		this.aaData = aaData;
	}

	public ExtStore(int totalCount, Object data) {
		super();
		this.totalCount = totalCount;
		this.aaData = data;
		this.success = "true";
	}
}
