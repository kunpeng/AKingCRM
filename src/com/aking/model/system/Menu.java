package com.aking.model.system;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.aking.model.constant.MenuGroup;

@Entity
@Table(name = "CRM_MENU")
public class Menu {
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	private String name;
	private String code;
	private String text;
	private String isleaf;
	private String url;
	private String memo;
	@Enumerated
	private MenuGroup menuGroup;

	// 上级菜单
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Menu parent;

	// 子菜单
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "parent")
	private Set<Menu> children = new HashSet<Menu>();

	@ManyToOne
	@JoinColumn(name = "SYSTEMFUNCTION_ID")
	private SystemFunction systemFunction;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "menu")
	private Set<MenuUser> menuUsers = new HashSet<MenuUser>();

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIsleaf() {
		return isleaf;
	}

	public void setIsleaf(String isleaf) {
		this.isleaf = isleaf;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public MenuGroup getMenuGroup() {
		return menuGroup;
	}

	public void setMenuGroup(MenuGroup menuGroup) {
		this.menuGroup = menuGroup;
	}

	public SystemFunction getSystemFunction() {
		return systemFunction;
	}

	public void setSystemFunction(SystemFunction systemFunction) {
		this.systemFunction = systemFunction;
	}

	public Set<MenuUser> getMenuUsers() {
		return menuUsers;
	}

	public void setMenuUsers(Set<MenuUser> menuUsers) {
		this.menuUsers = menuUsers;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Set<Menu> getChildren() {
		return children;
	}

	public void setChildren(Set<Menu> children) {
		this.children = children;
	}

}
