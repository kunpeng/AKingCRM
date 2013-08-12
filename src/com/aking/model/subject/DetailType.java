package com.aking.model.subject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "CRM_DETAILTYPE")
public class DetailType {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	private String typename;
	private String strorder;
	private String typetable;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTypetable() {
		return typetable;
	}
	public void setTypetable(String typetable) {
		this.typetable = typetable;
	}

	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public String getStrorder() {
		return strorder;
	}
	public void setStrorder(String strorder) {
		this.strorder = strorder;
	}
}
