package com.aking.view.vo;

import java.util.List;

public class JsTree {
	private String data;
	private String state;
	private String attr;
	private List<JsTree> children;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAttr() {
		return attr;
	}

	public void setAttr(String attr) {
		this.attr = attr;
	}

	public List<JsTree> getChildren() {
		return children;
	}

	public void setChildren(List<JsTree> children) {
		this.children = children;
	}

}
