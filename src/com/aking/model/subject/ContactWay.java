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
 * 洽谈方式
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_CONTACTWAY")
public class ContactWay {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	private String code;
	private String name;
	private String memo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "contactWay")
	private Set<BusinessContact> businessContacts = new HashSet<BusinessContact>();

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Set<BusinessContact> getBusinessContacts() {
		return businessContacts;
	}

	public void setBusinessContacts(Set<BusinessContact> businessContacts) {
		this.businessContacts = businessContacts;
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
}
