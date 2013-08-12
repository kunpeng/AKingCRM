package com.aking.view.vo;

import java.util.List;

public class CategoryVO implements Comparable<CategoryVO> {
	private String id;
	private String categoryId;
	// 类别名称
	private String name;
	private String text;
	// 备注
	private String memo;
	// 是否为叶子节点:0,有下级;1,无下级
	private Boolean leaf;

	private String parentId;

	private String parentName;

	private String parentText;

	// 下级节点
	private List<CategoryVO> children;

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<CategoryVO> getChildren() {
		return children;
	}

	public void setChildren(List<CategoryVO> children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public int compareTo(CategoryVO o) {
		return this.getName().compareTo(o.getName());
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

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

}
