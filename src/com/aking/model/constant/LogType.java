package com.aking.model.constant;

public enum LogType {
	INFO(0), ERROR(1), DEGUG(2);
	int id;

	public int getId() {
		return id;
	}

	private LogType(int id) {
		this.id = id;
	}
}
