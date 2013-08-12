package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.CustomerDao;
import com.aking.model.subject.Category;
import com.aking.model.subject.City;
import com.aking.model.subject.Country;
import com.aking.model.subject.Customer;
import com.aking.model.subject.CustomerSource;
import com.aking.model.subject.CustomerType;
import com.aking.model.subject.Province;
import com.aking.model.subject.User;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.CustomerVO;

/**
 * @comment:
 * @author:Tangkp
 * @date:2011-3-7
 * @version:1.0
 */
@Component
public class CustomerService extends BaseService<Customer, java.lang.String> {

	// private Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private CustomerDao customerDao;

	@Autowired
	private CustomerTypeService customerTypeService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CustomerSourceService customerSourceService;

	@Autowired
	private CityService cityService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return customerDao;
	}

	public List<CustomerVO> loadAllCustomer() throws Exception {
		List<Customer> customers = customerDao.loadAll();
		List<CustomerVO> customerVOs = this.copy2VO(customers);
		return customerVOs;
	}

	public List<CustomerVO> getByCategory(String categoryID) throws Exception {
		String hql = "From Customer c where c.category in (" + categoryID + ")";
		List<Customer> customers = customerDao.find(hql);
		List<CustomerVO> customerVOs = this.copy2VO(customers);
		return customerVOs;
	}

	private List<CustomerVO> copy2VO(List<Customer> customers) throws Exception {
		List<CustomerVO> customerVOs = new ArrayList<CustomerVO>();
		for (Customer customer : customers) {
			CustomerVO customerVO = new CustomerVO();
			// customerVO.setId(customer.getId());
			// customerVO.setName(customer.getName());
			// customerVO.setCode(customer.getCode());
			// customerVO.setCustomerType(customer.getCustomerType().getName());
			BeanUtil.copyProperties(customer, customerVO);
			City city = customer.getCity();
			if (city != null) {
				customerVO.setCityId(customer.getCity().getId());
				customerVO.setCity(customer.getCity().getName());
				Province province = city.getProvince();
				if (province != null) {
					customerVO.setProvinceId(province.getId());
					customerVO.setProvince(province.getName());
					Country country = province.getCountry();
					if (country != null) {
						customerVO.setCountryId(country.getId());
						customerVO.setCountry(country.getName());
					}
				}
			}
			Category category = customer.getCategory();
			if (category != null) {
				customerVO.setCategoryId(category.getId());
				customerVO.setCategory(category.getName());
			}
			CustomerType customerType = customer.getCustomerType();
			if (customerType != null) {
				customerVO.setCustomerTypeId(customerType.getId());
				customerVO.setCustomerType(customerType.getName());
			}
			CustomerSource customerSource = customer.getCustomerSource();
			if (customerSource != null) {
				customerVO.setCustomerSourceId(customerSource.getId());
				customerVO.setCustomerSource(customerSource.getName());
			}
			User operateUser = customer.getOperateUser();
			if (operateUser != null) {
				customerVO.setOperateUserId(operateUser.getId());
				customerVO.setOperateUserName(operateUser.getName());
			}
			customerVOs.add(customerVO);
		}
		return customerVOs;
	}

	public Boolean saveCustomer(Map<String, Object> paramMap) throws Exception {
		try {
			Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
			Customer customer = new Customer();
			ReflectUtil.convertMapToJavaBean(customer, paramsMap);
			// String[] names = (String[]) paramMap.get("name");
			// String[] codes = (String[]) paramMap.get("code");
			String[] customerTypes = (String[]) paramMap.get("customerTypeId");
			String customerTypeId = "";
			String[] categorys = (String[]) paramMap.get("categoryId");
			String categoryId = "";
			String[] customerSources = (String[]) paramMap.get("customerSourceId");
			String customerSourceId = "";
			String[] cities = (String[]) paramMap.get("cityId");
			String cityId = "";
			// String[] postcodes = (String[]) paramMap.get("postcode");
			// String[] addresses = (String[]) paramMap.get("address");
			// String[] officetels = (String[]) paramMap.get("officetel");
			// String[] faxs = (String[]) paramMap.get("fax");
			// String[] homepages = (String[]) paramMap.get("homepage");
			// String[] emails = (String[]) paramMap.get("email");
			// String[] represents = (String[]) paramMap.get("represent");
			// String[] taxs = (String[]) paramMap.get("tax");
			// String[] banks = (String[]) paramMap.get("bank");
			// String[] bankAccounts = (String[]) paramMap.get("bankAccount");
			// String[] mainProducts = (String[]) paramMap.get("mainProduct");
			// String[] users = (String[]) paramMap.get("user");
			// String[] demands = (String[]) paramMap.get("demand");
			// String[] memos = (String[]) paramMap.get("memo");
			// String[] operateDates = (String[]) paramMap.get("operateDate");
			// String[] QQs = (String[]) paramMap.get("QQ");
			// String[] isvalids = (String[]) paramMap.get("isvalid");
			// if (names.length > 0) {
			// customer.setName(names[0]);
			// }
			// if (codes.length > 0) {
			// customer.setCode(codes[0]);
			// }
			if (customerTypes.length > 0) {
				customerTypeId = customerTypes[0];
				CustomerType customerType = customerTypeService.getById(customerTypeId);
				customer.setCustomerType(customerType);
			}
			if (categorys.length > 0) {
				categoryId = categorys[0];
				Category category = categoryService.getById(categoryId);
				customer.setCategory(category);
			}
			if (customerSources.length > 0) {
				customerSourceId = customerSources[0];
				CustomerSource customerSource = customerSourceService.getById(customerSourceId);
				customer.setCustomerSource(customerSource);
			}
			if (cities.length > 0) {
				cityId = cities[0];
				City city = cityService.getById(cityId);
				customer.setCity(city);
			}
			// if (postcodes.length > 0) {
			// customer.setPostcode(postcodes[0]);
			// }
			// if (addresses.length > 0) {
			// customer.setAddress(addresses[0]);
			// }
			// if (officetels.length > 0) {
			// customer.setOfficetel(officetels[0]);
			// }
			// if (faxs.length > 0) {
			// customer.setFax(faxs[0]);
			// }
			// if (homepages.length > 0) {
			// customer.setHomepage(homepages[0]);
			// }
			// if (emails.length > 0) {
			// customer.setEmail(emails[0]);
			// }
			// if (represents.length > 0) {
			// customer.setRepresent(represents[0]);
			// }
			// if (taxs.length > 0) {
			// customer.setTax(taxs[0]);
			// }
			// if (banks.length > 0) {
			// customer.setBank(banks[0]);
			// }
			// if (bankAccounts.length > 0) {
			// customer.setBankAccount(bankAccounts[0]);
			// }
			// if (mainProducts.length > 0) {
			// customer.setMainProduct(mainProducts[0]);
			// }
			// if (demands.length > 0) {
			// customer.setDemand(demands[0]);
			// }
			// if (memos.length > 0) {
			// customer.setMemo(memos[0]);
			// }
			// if (QQs.length > 0) {
			// customer.setQq(QQs[0]);
			// }
			customerDao.save(customer);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public CustomerVO getCustomerById(String id) throws Exception {
		Customer customer = this.getById(id);
		if (customer == null) {
			return null;
		}
		List<Customer> customers = new ArrayList<Customer>();
		customers.add(customer);
		List<CustomerVO> customerVOs = this.copy2VO(customers);
		if (customerVOs.size() > 0) {
			return customerVOs.get(0);
		}
		return null;
	}

	public Boolean delCustomerByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		for (String id : idsArray) {
			Customer customer = customerDao.get(id);
			customerDao.delete(customer);
		}
		return true;
	}
}
