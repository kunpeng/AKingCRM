package com.aking.model.subject;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 联系活动主题
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_ACTIVITYTHEME")
public class ActivityTheme {
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	private String code;
	private String name;
	private String memo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "activityTheme")
	private Set<BusinessContact> businessContacts = new HashSet<BusinessContact>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "activityTheme")
	private Set<BusinessRemind> businessReminds = new HashSet<BusinessRemind>();

	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Set<BusinessContact> getBusinessContacts() {
		return businessContacts;
	}
	public void setBusinessContacts(Set<BusinessContact> businessContacts) {
		this.businessContacts = businessContacts;
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
	public Set<BusinessRemind> getBusinessReminds() {
		return businessReminds;
	}
	public void setBusinessReminds(Set<BusinessRemind> businessReminds) {
		this.businessReminds = businessReminds;
	}

}
