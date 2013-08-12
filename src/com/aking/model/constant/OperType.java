package com.aking.model.constant;

public enum OperType {
	LOGIN(0), NEWDATA(1);
	int id;

	public int getId() {
		return id;
	}

	private OperType(int id) {
		this.id = id;
	}
}
