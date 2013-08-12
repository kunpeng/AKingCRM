package com.aking.model.subject;

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

import com.aking.model.constant.RoleType;
import com.aking.model.system.MenuUser;
import com.aking.model.system.SystemFunctionUser;

/**
 * 用户
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_USER")
public class User {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	// 代码
	private String code;
	// 用户名称
	private String name;
	// 密码
	private String password;
	// 所属机构
	@ManyToOne
	@JoinColumn(name = "ORG_ID")
	private Org org;
	// 角色类型
	@Enumerated
	private RoleType roleType;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "operateUser")
	private Set<Customer> customers = new HashSet<Customer>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "chargeUser")
	private Set<BusinessContact> chargeBusinessContacts = new HashSet<BusinessContact>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recordUser")
	private Set<BusinessContact> recordBusinessContacts = new HashSet<BusinessContact>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "visitUser")
	private Set<AfterVisit> afterVisits = new HashSet<AfterVisit>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receiveUser")
	private Set<ComplainBack> receiveComplainBacks = new HashSet<ComplainBack>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "disposeUser")
	private Set<ComplainBack> disposeComplainBacks = new HashSet<ComplainBack>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "recordUser")
	private Set<AffairRemind> affairReminds = new HashSet<AffairRemind>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<SystemFunctionUser> systemFunctionUsers = new HashSet<SystemFunctionUser>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<MenuUser> menuUsers = new HashSet<MenuUser>();

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Set<BusinessContact> getChargeBusinessContacts() {
		return chargeBusinessContacts;
	}

	public void setChargeBusinessContacts(Set<BusinessContact> chargeBusinessContacts) {
		this.chargeBusinessContacts = chargeBusinessContacts;
	}

	public Set<BusinessContact> getRecordBusinessContacts() {
		return recordBusinessContacts;
	}

	public void setRecordBusinessContacts(Set<BusinessContact> recordBusinessContacts) {
		this.recordBusinessContacts = recordBusinessContacts;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<AfterVisit> getAfterVisits() {
		return afterVisits;
	}

	public void setAfterVisits(Set<AfterVisit> afterVisits) {
		this.afterVisits = afterVisits;
	}

	public Set<ComplainBack> getReceiveComplainBacks() {
		return receiveComplainBacks;
	}

	public void setReceiveComplainBacks(Set<ComplainBack> receiveComplainBacks) {
		this.receiveComplainBacks = receiveComplainBacks;
	}

	public Set<ComplainBack> getDisposeComplainBacks() {
		return disposeComplainBacks;
	}

	public void setDisposeComplainBacks(Set<ComplainBack> disposeComplainBacks) {
		this.disposeComplainBacks = disposeComplainBacks;
	}

	public Set<AffairRemind> getAffairReminds() {
		return affairReminds;
	}

	public void setAffairReminds(Set<AffairRemind> affairReminds) {
		this.affairReminds = affairReminds;
	}

	public Set<SystemFunctionUser> getSystemFunctionUsers() {
		return systemFunctionUsers;
	}

	public void setSystemFunctionUsers(Set<SystemFunctionUser> systemFunctionUsers) {
		this.systemFunctionUsers = systemFunctionUsers;
	}

	public Set<MenuUser> getMenuUsers() {
		return menuUsers;
	}

	public void setMenuUsers(Set<MenuUser> menuUsers) {
		this.menuUsers = menuUsers;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public Set<Customer> getCustomers() {
		return customers;
	}

	public void setCustomers(Set<Customer> customers) {
		this.customers = customers;
	}

}
