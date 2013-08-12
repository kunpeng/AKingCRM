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
 * 客户
 * 
 * @author TangKP
 * 
 */
@Entity
@Table(name = "CRM_CUSTOMER")
public class Customer {
	// 标识
	@Id
	@Column(name = "SID", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	private String id;

	// 客户地址
	private String address;

	// 客户主页
	private String homepage;

	// 客户来源
	@ManyToOne
	@JoinColumn(name = "CUSTOMERSOURCE_ID")
	private CustomerSource customerSource;

	// 城市
	@ManyToOne
	@JoinColumn(name = "CITY_ID")
	private City city;

	// 传真
	private String fax;

	// 行业类别
	@ManyToOne
	@JoinColumn(name = "CATEGORY_ID")
	private Category category;

	// 代码
	private String code;

	// 客户类别
	@ManyToOne
	@JoinColumn(name = "CUSTOMERTYPE_ID")
	private CustomerType customerType;

	// 邮编
	private String postcode;

	// 客户名称
	private String name;

	// 备注
	private String memo;

	// 客户需求
	private String demand;

	// 客户银行账号
	private String bankAccount;

	// 录入日期
	private String operateDate;

	// 客户开户银行
	private String bank;

	// 公司电话
	private String officetel;

	// 客户主要产品
	private String mainProduct;

	// 电子邮件
	private String email;

	// 客户法人代表
	private String represent;

	// 客户QQ
	private String qq;

	// 税号
	private String tax;

	// 是否可用
	private Boolean isvalid;

	// 业务员
	@ManyToOne
	@JoinColumn(name = "OPERATEUSER_ID")
	private User operateUser;

	// 业务联系
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<BusinessContact> businessContacts = new HashSet<BusinessContact>();

	// 联系人
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<ContactMan> contactMans = new HashSet<ContactMan>();

	// 商品报价
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<ProductQuote> productQuotedPrices = new HashSet<ProductQuote>();

	// 成交记录
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<DealRecord> dealRecords = new HashSet<DealRecord>();

	// 售后回访
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<AfterVisit> afterVisits = new HashSet<AfterVisit>();

	// 投诉反馈
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<ComplainBack> complainBacks = new HashSet<ComplainBack>();

	// 客户产品
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<Product> products = new HashSet<Product>();

	// 事务提醒
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<AffairRemind> affairReminds = new HashSet<AffairRemind>();

	// 业务提醒
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
	private Set<BusinessRemind> businessReminds = new HashSet<BusinessRemind>();

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public Boolean getIsvalid() {
		return isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public String getOfficetel() {
		return officetel;
	}

	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRepresent() {
		return represent;
	}

	public void setRepresent(String represent) {
		this.represent = represent;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getMainProduct() {
		return mainProduct;
	}

	public void setMainProduct(String mainProduct) {
		this.mainProduct = mainProduct;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ProductQuote> getProductQuotedPrices() {
		return productQuotedPrices;
	}

	public void setProductQuotedPrices(Set<ProductQuote> productQuotedPrices) {
		this.productQuotedPrices = productQuotedPrices;
	}

	public CustomerSource getCustomerSource() {
		return customerSource;
	}

	public void setCustomerSource(CustomerSource customerSource) {
		this.customerSource = customerSource;
	}

	public CustomerType getCustomerType() {
		return customerType;
	}

	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	public Set<ContactMan> getContactMans() {
		return contactMans;
	}

	public void setContactMans(Set<ContactMan> contactMans) {
		this.contactMans = contactMans;
	}

	public Set<DealRecord> getDealRecords() {
		return dealRecords;
	}

	public void setDealRecords(Set<DealRecord> dealRecords) {
		this.dealRecords = dealRecords;
	}

	public Set<AfterVisit> getAfterVisits() {
		return afterVisits;
	}

	public void setAfterVisits(Set<AfterVisit> afterVisits) {
		this.afterVisits = afterVisits;
	}

	public Set<ComplainBack> getComplainBacks() {
		return complainBacks;
	}

	public void setComplainBacks(Set<ComplainBack> complainBacks) {
		this.complainBacks = complainBacks;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Set<AffairRemind> getAffairReminds() {
		return affairReminds;
	}

	public void setAffairReminds(Set<AffairRemind> affairReminds) {
		this.affairReminds = affairReminds;
	}

	public Set<BusinessRemind> getBusinessReminds() {
		return businessReminds;
	}

	public void setBusinessReminds(Set<BusinessRemind> businessReminds) {
		this.businessReminds = businessReminds;
	}

	public User getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(User operateUser) {
		this.operateUser = operateUser;
	}

}
