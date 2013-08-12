package com.aking.control.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.CustomerService;
import com.aking.control.service.ProductService;
import com.aking.model.subject.Customer;
import com.aking.model.subject.Product;
import com.aking.util.WriteResult;
import com.aking.view.vo.ProductVO;
import com.opensymphony.xwork2.ActionContext;

@Component
public class ProductAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	/**
	 * 获取用户的产品记录
	 * 
	 * @throws Exception
	 */
	public void loadByCustomer() {
		try {
			logger.log(Level.INFO, "正在加载客户产品记录信息...");
			String customerid = request.getParameter("customerid");
			List<ProductVO> productVOs = productService.loadByCustomer(customerid);
			// JSONObject resultObj = new JSONObject();
			JSONArray rows = new JSONArray();
			for (int i = 0; i < productVOs.size(); i++) {
				ProductVO productVO = productVOs.get(i);
				// String[] data = new String[6];
				// data[0] = productVO.getName();
				// data[1] = productVO.getStandard();
				// data[2] = productVO.getType();
				// data[3] = String.valueOf(productVO.getProductAbility());
				// data[4] = String.valueOf(productVO.getStockQuantity());
				// data[5] = productVO.getMemo();
				//
				// JSONObject productObj = new JSONObject();
				// productObj.element("id", productVO.getId());
				// productObj.element("cell", data);

				rows.add(productVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", productVOs.size());
			// resultObj.element("rows", rows);

			WriteResult.outputResult(rows.toString(), response);
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] customerIds = (String[]) paramMap.get("customerid");
			// String customerId = "";
			// if (customerIds.length > 0) {
			// customerId = customerIds[0];
			// }
			// List<ProductVO> productVOs = productService.loadByCustomer(customerId);
			// WriteResult.outPutListResult(productVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveProduct() {
		try {
			String id = request.getParameter("productid");
			String customerid = request.getParameter("customerid");
			String name = request.getParameter("productname");
			String standard = request.getParameter("standard");
			String type = request.getParameter("type");
			String productAbility = request.getParameter("productAbility");
			String stockQuantity = request.getParameter("stockQuantity");
			String memo = request.getParameter("memo");

			Product product;
			if (id == null || id.length() <= 0) {
				product = new Product();
			} else {
				product = productService.getById(id);
			}

			product.setName(name);
			product.setStandard(standard);
			product.setType(type);
			product.setProductAbility(Integer.valueOf(productAbility));
			product.setStockQuantity(Integer.valueOf(stockQuantity));
			product.setMemo(memo);

			Customer customer = customerService.getById(customerid);
			product.setCustomer(customer);

			productService.save(product);

			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// Boolean saveResult = productService.saveProduct(paramMap);
			// String result = "";
			// if (saveResult) {
			// result = "{success:true,info:'保存成功!'}";
			// } else {
			// result = "{success:false,info:'保存失败!'}";
			// }
			// WriteResult.outputResult(result, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void delProductByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = productService.delProductByID(ids);
		String result = "";
		if (delResult) {
			result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadProductByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] productids = (String[]) paramMap.get("productid");
		String productid = "";
		if (productids.length > 0) {
			productid = productids[0];
		}
		ProductVO productVO = productService.getProductByID(productid);
		ExtStore extStore = new ExtStore(1, productVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

	public void load4Combo() {
		try {
			JSONArray pros = new JSONArray();
			List<Product> products = productService.loadAll();
			for (Product product : products) {
				JSONObject obj = new JSONObject();
				obj.element("id", product.getId());
				obj.element("name", product.getName());
				obj.element("label", product.getName());
				pros.add(obj);
			}
			WriteResult.outputResult(pros.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
