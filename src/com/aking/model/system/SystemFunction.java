package com.aking.model.system;

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

@Entity
@Table(name = "CRM_SYSTEMFUNCTION")
public class SystemFunction {
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;

	private String code;
	private String name;

	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private SystemFunction parent;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private Set<SystemFunction> children = new HashSet<SystemFunction>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "systemFunction")
	private Set<Menu> menus = new HashSet<Menu>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "systemFunction")
	private Set<SystemFunctionUser> systemFunctionUsers = new HashSet<SystemFunctionUser>();

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

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}

	public Set<SystemFunctionUser> getSystemFunctionUsers() {
		return systemFunctionUsers;
	}

	public void setSystemFunctionUsers(Set<SystemFunctionUser> systemFunctionUsers) {
		this.systemFunctionUsers = systemFunctionUsers;
	}

	public SystemFunction getParent() {
		return parent;
	}

	public void setParent(SystemFunction parent) {
		this.parent = parent;
	}

	public Set<SystemFunction> getChildren() {
		return children;
	}

	public void setChildren(Set<SystemFunction> children) {
		this.children = children;
	}

}
