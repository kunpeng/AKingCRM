package com.aking.view.vo;

import java.util.List;

public class OrgVO {
	// 标识
	private String id;
	// 代码
	private String code;
	// 机构名称
	private String name;

	private String parentId;
	private String parentName;
	private String parentCode;
	// 父节点
	private OrgVO parent;
	// 子节点
	private List<OrgVO> children;

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

	public OrgVO getParent() {
		return parent;
	}

	public void setParent(OrgVO parent) {
		this.parent = parent;
	}

	public List<OrgVO> getChildren() {
		return children;
	}

	public void setChildren(List<OrgVO> children) {
		this.children = children;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

}
