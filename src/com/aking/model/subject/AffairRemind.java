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
 * 事务提醒
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_AFFAIRREMIND")
public class AffairRemind {
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	private String affairDate;
	private String affairTime;
	private String theme;
	private String memo;
	private String recordDateTime;

	@ManyToOne
	@JoinColumn(name = "RECORDUSER_ID")
	private User recordUser;

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public String getAffairDate() {
		return affairDate;
	}

	public void setAffairDate(String affairDate) {
		this.affairDate = affairDate;
	}

	public String getAffairTime() {
		return affairTime;
	}

	public void setAffairTime(String affairTime) {
		this.affairTime = affairTime;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRecordDateTime() {
		return recordDateTime;
	}

	public void setRecordDateTime(String recordDateTime) {
		this.recordDateTime = recordDateTime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

}
