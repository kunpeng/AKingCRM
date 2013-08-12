package com.aking.view.vo;

import java.util.List;

public class SystemFunctionVO {
	private String id;
	private String code;
	private String name;
	private List<SystemFunctionVO> children;

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

	public List<SystemFunctionVO> getChildren() {
		return children;
	}

	public void setChildren(List<SystemFunctionVO> children) {
		this.children = children;
	}

}
