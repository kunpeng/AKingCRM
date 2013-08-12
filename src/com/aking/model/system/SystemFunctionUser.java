package com.aking.model.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.aking.model.subject.User;

@Entity
@Table(name = "CRM_SYSTEMFUNCTIONUSER")
public class SystemFunctionUser {
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;

	@ManyToOne
	@JoinColumn(name = "SYSTEMFUNCITON_ID")
	private SystemFunction systemFunction;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SystemFunction getSystemFunction() {
		return systemFunction;
	}

	public void setSystemFunction(SystemFunction systemFunction) {
		this.systemFunction = systemFunction;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
