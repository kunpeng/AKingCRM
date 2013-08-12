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
 * 业务提醒
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_BUSINESSREMIND")
public class BusinessRemind {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private Integer id;
	private String code;
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	private String remindDate;
	private String remindTime;
	@ManyToOne
	@JoinColumn(name = "ACTIVITYTHEME_ID")
	private ActivityTheme activityTheme;
	private String demo;
	private Boolean remindWindow;
	private Integer days;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getRemindDate() {
		return remindDate;
	}

	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
	}

	public String getRemindTime() {
		return remindTime;
	}

	public void setRemindTime(String remindTime) {
		this.remindTime = remindTime;
	}

	public ActivityTheme getActivityTheme() {
		return activityTheme;
	}

	public void setActivityTheme(ActivityTheme activityTheme) {
		this.activityTheme = activityTheme;
	}

	public String getDemo() {
		return demo;
	}

	public void setDemo(String demo) {
		this.demo = demo;
	}

	public Boolean getRemindWindow() {
		return remindWindow;
	}

	public void setRemindWindow(Boolean remindWindow) {
		this.remindWindow = remindWindow;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

}
