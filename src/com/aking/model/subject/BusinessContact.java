package com.aking.model.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "CRM_BUSINESSCONTACT")
public class BusinessContact {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;

	// 联系日期
	private String contactDate;

	// 主题
	@ManyToOne
	@JoinColumn(name = "ACTIVITYTHEME_ID")
	private ActivityTheme activityTheme;

	// 洽谈方式
	@ManyToOne
	@JoinColumn(name = "CONTACTWAY_ID")
	private ContactWay contactWay;

	// 负责人
	@ManyToOne
	@JoinColumn(name = "CHARGEUSER_ID")
	private User chargeUser;

	// 录入员
	@ManyToOne
	@JoinColumn(name = "RECORDUSER_ID")
	private User recordUser;

	// 客户
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public String getContactDate() {
		return contactDate;
	}

	public void setContactDate(String contactDate) {
		this.contactDate = contactDate;
	}

	public ActivityTheme getActivityTheme() {
		return activityTheme;
	}

	public void setActivityTheme(ActivityTheme activityTheme) {
		this.activityTheme = activityTheme;
	}

	public ContactWay getContactWay() {
		return contactWay;
	}

	public void setContactWay(ContactWay contactWay) {
		this.contactWay = contactWay;
	}

	public User getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(User chargeUser) {
		this.chargeUser = chargeUser;
	}

	public User getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(User recordUser) {
		this.recordUser = recordUser;
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
}
