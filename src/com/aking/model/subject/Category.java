package com.aking.model.subject;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 类别目录
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_CATEGORY")
public class Category {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	// 类别名称
	private String name;
	private String text;

	// 备注
	private String memo;

	// 上级
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Category parent;

	// 下级
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private Set<Category> children = new HashSet<Category>();

	// 是否为叶子节点:0,有下级;1,无下级
	private Boolean leaf;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "category")
	private Set<Customer> customers = new HashSet<Customer>();

	public Boolean getLeaf() {
		return leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
