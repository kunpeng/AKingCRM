package com.aking.control.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.CategoryService;
import com.aking.control.service.CityService;
import com.aking.control.service.CustomerService;
import com.aking.control.service.CustomerSourceService;
import com.aking.control.service.CustomerTypeService;
import com.aking.control.service.UserService;
import com.aking.model.subject.Category;
import com.aking.model.subject.City;
import com.aking.model.subject.Customer;
import com.aking.model.subject.CustomerSource;
import com.aking.model.subject.CustomerType;
import com.aking.model.subject.User;
import com.aking.util.WriteResult;
import com.aking.view.vo.CustomerVO;
import com.opensymphony.xwork2.ActionContext;

/**
 * @comment:
 * @author:Tangkp
 * @date:2011-3-8
 * @version:1.0
 */
@Component
public class CustomerAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private CustomerService customerService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CustomerTypeService customerTypeService;

	@Autowired
	private CustomerSourceService customerSourceService;

	@Autowired
	private CityService cityService;

	@Autowired
	private UserService userService;

	/**
	 * @throws Exception
	 */
	public void loadAll() throws Exception {
		try {
			logger.log(Level.INFO, "加载所有客户信息...");
			List<CustomerVO> customerVOs = customerService.loadAllCustomer();
			JSONArray rows = new JSONArray();
			// String[][] dataResult = new String[customerVOs.size()][];
			for (int i = 0; i < customerVOs.size(); i++) {
				CustomerVO customerVO = customerVOs.get(i);
				rows.add(customerVO);
			}
			WriteResult.outputResult(rows.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param categoryID
	 * @throws Exception
	 */
	public void loadByCategory() {
		try {
			logger.log(Level.INFO, "加载相关客户信息...");
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] categoryIDs = (String[]) paramMap.get("cateid");
			// String categoryID = "";
			// if (categoryIDs.length > 0) {
			// categoryID = categoryIDs[0];
			// }
			String categoryID = request.getParameter("cateid");
			Category category = new Category();

			if (categoryID.equalsIgnoreCase("root")) {
				category = categoryService.getTop();
			} else {
				category = categoryService.getById(categoryID);
			}

			String categories = "";
			// Category category = categoryService.getById(categoryID);
			if (!category.getLeaf()) {
				List<Category> childCategories = new ArrayList<Category>(category.getChildren());
				for (Category childCategory : childCategories) {
					if (categories.length() <= 0) {
						categories = "'" + childCategory.getId() + "'";
					} else {
						categories += ",'" + childCategory.getId() + "'";
					}
				}
			} else {
				categories = "'" + categoryID + "'";
			}
			List<CustomerVO> customerVOs = customerService.getByCategory(categories);

			// JSONObject resultObj = new JSONObject();
			JSONArray rows = new JSONArray();
			// String[][] dataResult = new String[customerVOs.size()][];
			for (int i = 0; i < customerVOs.size(); i++) {
				CustomerVO customerVO = customerVOs.get(i);
				// String[] data = new String[10];
				// data[0] = customerVO.getCategory();
				// data[1] = customerVO.getCode();
				// data[2] = customerVO.getName();
				// data[3] = customerVO.getCustomerType();
				// data[4] = customerVO.getCountry();
				// data[5] = customerVO.getProvince();
				// data[6] = customerVO.getCity();
				// data[7] = customerVO.getAddress();
				// data[8] = customerVO.getOfficetel();
				// // data[9] = customerVO.getTax();
				// data[9] = customerVO.getEmail();
				// // dataResult[i] = data;
				//
				// JSONObject customerObj = new JSONObject();
				// customerObj.element("id", customerVO.getId());
				// customerObj.element("cell", data);

				// rows.add(customerObj);

				rows.add(customerVO);
			}

			// resultObj.element("page", 1);
			// resultObj.element("total", customerVOs.size());
			// resultObj.element("rows", rows);

			WriteResult.outputResult(rows.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveCustomer() {
		try {
			logger.log(Level.INFO, "正在保存客户信息...");
			Customer customer;

			String id = request.getParameter("customerid");
			String name = request.getParameter("customername");
			String code = request.getParameter("customercode");
			String customertypeid = request.getParameter("customertype");
			String categoryid = request.getParameter("category");
			String customersourceid = request.getParameter("customersource");
			// String countryid = request.getParameter("country");
			// String provinceid = request.getParameter("province");
			String cityid = request.getParameter("city");
			String officetel = request.getParameter("officetel");
			String postcode = request.getParameter("postcode");
			String address = request.getParameter("address");
			String fax = request.getParameter("fax");
			String email = request.getParameter("email");
			String homepage = request.getParameter("homepage");
			String represent = request.getParameter("represent");
			String tax = request.getParameter("tax");
			String bank = request.getParameter("bank");
			String bankAccount = request.getParameter("bankAccount");
			String mainProduct = request.getParameter("mainProduct");
			String demand = request.getParameter("demand");
			String memo = request.getParameter("memo");
			String isvalid = request.getParameter("isvalid");
			String operateDate = request.getParameter("operateDate");
			String operateUserId = request.getParameter("operateUser");

			CustomerType customerType = customerTypeService.getById(customertypeid);
			CustomerSource customerSource = customerSourceService.getById(customersourceid);
			Category category = categoryService.getById(categoryid);
			City city = cityService.getById(cityid);

			if (id == null || id.length() <= 0) {
				customer = new Customer();
			} else {
				customer = customerService.getById(id);
			}

			customer.setName(name);
			customer.setCode(code);
			customer.setCustomerType(customerType);
			customer.setCustomerSource(customerSource);
			customer.setCategory(category);
			customer.setCity(city);
			customer.setOfficetel(officetel);
			customer.setPostcode(postcode);
			customer.setAddress(address);
			customer.setFax(fax);
			customer.setEmail(email);
			customer.setHomepage(homepage);
			customer.setRepresent(represent);
			customer.setTax(tax);
			customer.setBank(bank);
			customer.setBankAccount(bankAccount);
			customer.setMainProduct(mainProduct);
			customer.setDemand(demand);
			customer.setMemo(memo);
			customer.setIsvalid(Boolean.valueOf(isvalid.equalsIgnoreCase("on") ? "true" : "false"));
			customer.setOperateDate(operateDate);

			if (operateUserId != null) {
				User operateUser = userService.getById(operateUserId);
				customer.setOperateUser(operateUser);
			}

			customerService.save(customer);

			String result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
			WriteResult.outputResult(result, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ActionContext context = ActionContext.getContext();
		// Map<String, Object> paramMap = context.getParameters();
		// Boolean saveResult = customerService.saveCustomer(paramMap);
		// String result = "";
		// if (saveResult) {
		// result = "{success:true,info:'保存成功!'}";
		// } else {
		// result = "{success:false,info:'保存失败!'}";
		// }
		// WriteResult.outputResult(result, response);
	}

	public void loadCustomerByID() throws Exception {
		logger.log(Level.INFO, "加载相关客户信息...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] customerids = (String[]) paramMap.get("id");
		String customerid = "";
		if (customerids.length > 0) {
			customerid = customerids[0];
			// customerid = "297e29b038ffc9c00138ffcc84f10002";
		}
		CustomerVO customerVO = customerService.getCustomerById(customerid);
		// Customer customer = customerService.getById(customerid);
		// System.out.println("正在载入..." + customerid);
		// ExtStore extStore = new ExtStore(1, customerVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(customerVO, config);
		WriteResult.outputResult(json.toString(), response);
	}

	public void delCustomerByID() {
		logger.log(Level.INFO, "正在删除数据...");
		String result = "";
		try {
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] idsarray = (String[]) paramMap.get("");
			// String ids = "";
			// if (idsarray.length > 0) {
			// ids = idsarray[0];
			// }
			String customerid = request.getParameter("customerid");
			Boolean delResult = customerService.delCustomerByID(customerid);

			if (delResult) {
				result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
			} else {
				result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, response);
	}

	public static void main(String[] args) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.element("name", "123");
		jsonObject.element("address", "test");
		System.out.println(jsonObject.toString());
	}

}
