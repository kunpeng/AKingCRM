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
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.BusinessContactService;
import com.aking.util.WriteResult;
import com.aking.view.vo.BusinessContactVO;
import com.opensymphony.xwork2.ActionContext;

@Component
public class BusinessContactAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private BusinessContactService businessContactService;

	public void loadAll() throws Exception {
		try {
			JSONObject resultObj = new JSONObject();
			List<BusinessContactVO> businessContactVOs = businessContactService.loadAllContact();
			JSONArray rows = new JSONArray();
			// String[][] dataResult = new String[businessContactVOs.size()][];
			for (int i = 0; i < businessContactVOs.size(); i++) {
				BusinessContactVO businessContactVO = businessContactVOs.get(i);
				String[] data = new String[5];
				data[0] = businessContactVO.getContactDate();
				data[1] = businessContactVO.getActivityTheme();
				data[2] = businessContactVO.getContactWay();
				data[3] = businessContactVO.getChargeUser();
				data[4] = businessContactVO.getRecordUser();
				// dataResult[i] = data;

				JSONObject contactObj = new JSONObject();
				contactObj.element("id", businessContactVO.getId());
				contactObj.element("cell", data);

				rows.add(contactObj);
			}

			resultObj.element("page", 1);
			resultObj.element("total", businessContactVOs.size());
			resultObj.element("rows", rows);
			// String contacts = JsonUtil.obj2Json("aaData", dataResult);

			WriteResult.outputResult(resultObj.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadByCustomer() {
		try {
			logger.log(Level.INFO, "正在加载客户业务联系信息...");
			// JSONObject resultObj = new JSONObject();
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] customerIDs = (String[]) paramMap.get("customerid");
			String customerId = request.getParameter("customerid");
			// String customerID = "";
			// if (customerIDs.length > 0) {
			// customerID = customerIDs[0];
			// }
			List<BusinessContactVO> businessContactVOs = new ArrayList<BusinessContactVO>();
			// try {
			businessContactVOs = businessContactService.loadByCustomer(customerId);
			JSONArray rows = new JSONArray();
			for (int i = 0; i < businessContactVOs.size(); i++) {
				// BusinessContactVO businessContactVO = businessContactVOs.get(i);
				// String[] data = new String[5];
				// data[0] = businessContactVO.getContactDate();
				// data[1] = businessContactVO.getActivityTheme();
				// data[2] = businessContactVO.getContactWay();
				// data[3] = businessContactVO.getChargeUser();
				// data[4] = businessContactVO.getRecordUser();
				// // dataResult[i] = data;
				//
				// JSONObject contactObj = new JSONObject();
				// contactObj.element("id", businessContactVO.getId());
				// contactObj.element("cell", data);

				rows.add(businessContactVOs.get(i));
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", businessContactVOs.size());
			// resultObj.element("rows", rows);
			// } catch (Exception e) {
			// e.printStackTrace();
			// }

			WriteResult.outputResult(rows.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// WriteResult.outPutListResult(businessContactVOs, ServletActionContext.getResponse());

		// ExtStore extStore = new ExtStore(businessContactVOs.size(), businessContactVOs);
		//
		// JsonConfig config = new JsonConfig();
		// config.setIgnoreDefaultExcludes(false);
		// config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//
		// JSONObject json = JSONObject.fromObject(extStore, config);
		//
		// WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

	public void loadFilter() throws Exception {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] customerIDs = (String[]) paramMap.get("customerid");
		String customerID = "";
		if (customerIDs.length > 0) {
			customerID = customerIDs[0];
		}
		List<BusinessContactVO> businessContactVOs = new ArrayList<BusinessContactVO>();
		try {
			businessContactVOs = businessContactService.loadByCustomer(customerID);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ExtStore extStore = new ExtStore(businessContactVOs.size(), businessContactVOs);

		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);

		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

	public void saveContact() {
		String result = "";
		try {
			ActionContext context = ActionContext.getContext();
			Map<String, Object> paramMap = context.getParameters();
			// Boolean result = businessContactService.saveContact(paramMap);
			Boolean saveResult = businessContactService.saveContact(paramMap);
			if (saveResult) {
				result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
			} else {
				result = "{\"success\":\"true\",\"info\":\"保存失败!\"}";
			}
			WriteResult.outputResult(result, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:false,info:'保存失败!'}";
		}
	}

	/**
	 * 根据业务联系ID删除记录
	 * 
	 * @throws Exception
	 */
	public void delContactByID() throws Exception {
		logger.log(Level.INFO, "正在删除业务联系记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = businessContactService.delContactByID(ids);
		String result = "";
		if (delResult) {
			result = "{\"success\":\"true\",\"info\":\"删除成功!\"}";
		} else {
			result = "{\"success\":\"true\",\"info\":\"删除失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadContactByID() throws Exception {
		logger.log(Level.INFO, "加载相关客户信息...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] contactids = (String[]) paramMap.get("contactid");
		String contactid = "";
		if (contactids.length > 0) {
			contactid = contactids[0];
		}
		BusinessContactVO businessContactVO = businessContactService.getContactByID(contactid);
		ExtStore extStore = new ExtStore(1, businessContactVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}
}
