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
import com.aking.control.service.ProductQuoteService;
import com.aking.control.service.ProductService;
import com.aking.model.subject.Customer;
import com.aking.model.subject.Product;
import com.aking.model.subject.ProductQuote;
import com.aking.util.WriteResult;
import com.aking.view.vo.ProductQuoteVO;
import com.opensymphony.xwork2.ActionContext;

@Component
public class ProductQuoteAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private ProductQuoteService productQuoteService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	/**
	 * 获取用户的产品报价
	 * 
	 * @throws Exception
	 */
	public void loadByCustomer() {
		try {
			logger.log(Level.INFO, "正在加载客户产品报价信息...");
			String customerId = request.getParameter("customerid");
			List<ProductQuoteVO> productQuoteVOs = productQuoteService.loadByCustomer(customerId);
			// JSONObject resultObj = new JSONObject();
			JSONArray rows = new JSONArray();
			for (ProductQuoteVO productQuoteVO : productQuoteVOs) {
				// String[] data = new String[6];
				// data[0] = productQuoteVO.getQuoteDate();
				// data[1] = productQuoteVO.getProductname();
				// data[2] = productQuoteVO.getStandard();
				// data[3] = productQuoteVO.getType();
				// data[4] = String.valueOf(productQuoteVO.getPrice());
				// data[5] = productQuoteVO.getQuotationNo();
				//
				// JSONObject obj = new JSONObject();
				// obj.element("id", productQuoteVO.getId());
				// obj.element("cell", data);

				rows.add(productQuoteVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", productQuoteVOs.size());
			// resultObj.element("rows", rows);

			WriteResult.outputResult(rows.toString(), response);
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] customerIds = (String[]) paramMap.get("customerid");
			// String customerId = "";
			// if (customerIds.length > 0) {
			// customerId = customerIds[0];
			// }
			// List<ProductQuoteVO> productQuoteVOs = productQuoteService.loadByCustomer(customerId);
			// WriteResult.outPutListResult(productQuoteVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveQuote() {
		try {
			String id = request.getParameter("productquoteid");
			String customerid = request.getParameter("customerid");
			String quoteDate = request.getParameter("quoteDate");
			String productid = request.getParameter("productquote_product");
			String price = request.getParameter("price");
			String quotationNo = request.getParameter("quotationNo");

			ProductQuote productQuote;
			if (id == null || id.length() <= 0) {
				productQuote = new ProductQuote();
			} else {
				productQuote = productQuoteService.getById(id);
			}

			productQuote.setQuoteDate(quoteDate);
			productQuote.setPrice(Float.valueOf(price));
			productQuote.setQuotationNo(quotationNo);
			Product product = productService.getById(productid);
			productQuote.setProduct(product);
			Customer customer = customerService.getById(customerid);
			productQuote.setCustomer(customer);

			productQuoteService.save(productQuote);
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// Boolean saveResult = productQuoteService.saveQuote(paramMap);
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

	public void delQuoteByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = productQuoteService.delQuoteByID(ids);
		String result = "";
		if (delResult) {
			result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadQuoteByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] quoteids = (String[]) paramMap.get("quoteid");
		String quoteid = "";
		if (quoteids.length > 0) {
			quoteid = quoteids[0];
		}
		ProductQuoteVO productQuoteVO = productQuoteService.getQuoteByID(quoteid);
		ExtStore extStore = new ExtStore(1, productQuoteVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

}
