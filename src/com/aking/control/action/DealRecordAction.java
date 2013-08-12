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
import com.aking.control.service.DealRecordService;
import com.aking.control.service.ProductService;
import com.aking.model.subject.Customer;
import com.aking.model.subject.DealRecord;
import com.aking.model.subject.Product;
import com.aking.util.WriteResult;
import com.aking.view.vo.DealRecordVO;
import com.opensymphony.xwork2.ActionContext;

@Component
public class DealRecordAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private DealRecordService dealRecordService;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	/**
	 * 获取用户的成交记录
	 * 
	 * @throws Exception
	 */
	public void loadByCustomer() {
		try {
			logger.log(Level.INFO, "正在加载客户成交记录信息...");
			String customerid = request.getParameter("customerid");
			List<DealRecordVO> dealRecordVOs = dealRecordService.loadByCustomer(customerid);
			// JSONObject resultObj = new JSONObject();
			JSONArray rows = new JSONArray();
			for (DealRecordVO dealRecordVO : dealRecordVOs) {
				// String[] data = new String[11];
				// data[0] = dealRecordVO.getDealDate();
				// data[1] = dealRecordVO.getProductName();
				// data[2] = dealRecordVO.getStandard();
				// data[3] = String.valueOf(dealRecordVO.getQuantity());
				// data[4] = String.valueOf(dealRecordVO.getPrice());
				// double sum = dealRecordVO.getQuantity() * dealRecordVO.getPrice();
				// data[5] = String.valueOf(sum);
				// data[6] = String.valueOf(dealRecordVO.getBusinessCost());
				// data[7] = dealRecordVO.getInvoice();
				// data[8] = dealRecordVO.getPayCondition();
				// data[9] = dealRecordVO.getDeliverDate();
				// data[10] = dealRecordVO.getMemo();
				//
				// JSONObject obj = new JSONObject();
				// obj.element("id", dealRecordVO.getId());
				// obj.element("cell", data);

				rows.add(dealRecordVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", dealRecordVOs.size());
			// resultObj.element("rows", rows);

			WriteResult.outputResult(rows.toString(), response);
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] customerIds = (String[]) paramMap.get("customerid");
			// String customerId = "";
			// if (customerIds.length > 0) {
			// customerId = customerIds[0];
			// }
			// List<DealRecordVO> dealRecordVOs = dealRecordService.loadByCustomer(customerId);
			// WriteResult.outPutListResult(dealRecordVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveRecord() {
		try {
			String id = request.getParameter("recordid");
			String customerid = request.getParameter("customerid");
			String productid = request.getParameter("dealrecord_product");
			String quantity = request.getParameter("quantity");
			String price = request.getParameter("price");
			String businessCost = request.getParameter("businessCost");
			String dealDate = request.getParameter("dealDate");
			String invoice = request.getParameter("invoice");
			String payCondition = request.getParameter("payCondition");
			String deliverDate = request.getParameter("deliverDate");
			String memo = request.getParameter("memo");

			DealRecord dealRecord;
			if (id == null || id.length() <= 0) {
				dealRecord = new DealRecord();
			} else {
				dealRecord = dealRecordService.getById(id);
			}

			Product product = productService.getById(productid);
			Customer customer = customerService.getById(customerid);

			dealRecord.setProduct(product);
			dealRecord.setCustomer(customer);
			dealRecord.setQuantity(Integer.valueOf(quantity));
			dealRecord.setPrice(Double.valueOf(price));
			dealRecord.setBusinessCost(Double.valueOf(businessCost));
			dealRecord.setDealDate(dealDate);
			dealRecord.setInvoice(invoice);
			dealRecord.setPayCondition(payCondition);
			dealRecord.setDeliverDate(deliverDate);
			dealRecord.setMemo(memo);

			dealRecordService.save(dealRecord);

			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// Boolean saveResult = dealRecordService.savaRecord(paramMap);
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

	public void delRecordByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = dealRecordService.delRecordByID(ids);
		String result = "";
		if (delResult) {
			result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadRecordByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] recordids = (String[]) paramMap.get("recordid");
		String recordid = "";
		if (recordids.length > 0) {
			recordid = recordids[0];
		}
		DealRecordVO dealRecordVO = dealRecordService.getRecordByID(recordid);
		ExtStore extStore = new ExtStore(1, dealRecordVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

}
