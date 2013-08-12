package com.aking.model.subject;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 省份
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_PROVINCE")
public class Province {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	// 代码
	private String code;
	// 名称
	private String name;
	// 城市
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "province")
	private Set<City> citys = new HashSet<City>();
	// 国家
	@ManyToOne
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;

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

	public Set<City> getCitys() {
		return citys;
	}

	public void setCitys(Set<City> citys) {
		this.citys = citys;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
