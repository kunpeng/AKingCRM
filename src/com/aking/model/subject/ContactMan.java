package com.aking.model.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 联系人
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_CONTACTMAN")
public class ContactMan {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	// 名称
	private String name;
	// 部门
	private String department;
	// 职位
	private String position;
	// 办公电话
	private String officetel;
	// 手机
	private String phone;
	// 家庭电话
	private String homePhone;
	// 电子邮件
	private String email;
	// 传真
	private String fax;
	// QQ
	private String qq;
	// 地址
	private String address;
	// 生日
	private String birthday;
	// 爱好
	private String hobby;
	// 是否为主联系人
	private Boolean isCharge;

	// 客户
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public Boolean getIsCharge() {
		return isCharge;
	}

	public void setIsCharge(Boolean isCharge) {
		this.isCharge = isCharge;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
