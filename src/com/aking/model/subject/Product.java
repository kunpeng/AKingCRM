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
 * 客户产品
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_PRODUCT")
public class Product {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;
	private String name;
	private String standard;
	private String type;
	private Integer productAbility;
	private Integer stockQuantity;
	private String memo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	private Set<ProductQuote> productQuotedPrices = new HashSet<ProductQuote>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
	private Set<DealRecord> dealRecords = new HashSet<DealRecord>();

	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public Set<DealRecord> getDealRecords() {
		return dealRecords;
	}

	public void setDealRecords(Set<DealRecord> dealRecords) {
		this.dealRecords = dealRecords;
	}

	public Set<ProductQuote> getProductQuotedPrices() {
		return productQuotedPrices;
	}

	public void setProductQuotedPrices(Set<ProductQuote> productQuotedPrices) {
		this.productQuotedPrices = productQuotedPrices;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getProductAbility() {
		return productAbility;
	}

	public void setProductAbility(Integer productAbility) {
		this.productAbility = productAbility;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

}
