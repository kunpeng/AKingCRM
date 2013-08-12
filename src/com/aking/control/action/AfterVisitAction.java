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

import com.aking.control.service.AfterVisitService;
import com.aking.control.service.CustomerService;
import com.aking.control.service.UserService;
import com.aking.model.subject.AfterVisit;
import com.aking.model.subject.Customer;
import com.aking.model.subject.User;
import com.aking.util.WriteResult;
import com.aking.view.vo.AfterVisitVO;
import com.opensymphony.xwork2.ActionContext;

@Component
public class AfterVisitAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private AfterVisitService afterVisitService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	/**
	 * 获取用户的售后回访记录
	 * 
	 * @throws Exception
	 */
	public void loadByCustomer() {
		try {
			logger.log(Level.INFO, "正在加载客户售后回访信息...");
			String customerid = request.getParameter("customerid");
			List<AfterVisitVO> afterVisitVOs = afterVisitService.loadByCustomer(customerid);
			// JSONObject resultObj = new JSONObject();
			JSONArray rows = new JSONArray();
			for (AfterVisitVO afterVisitVO : afterVisitVOs) {
				// String[] data = new String[4];
				// data[0] = afterVisitVO.getVisitUser();
				// data[1] = afterVisitVO.getVisitDate();
				// data[2] = afterVisitVO.getContent();
				// data[3] = afterVisitVO.getResult();
				//
				// JSONObject obj = new JSONObject();
				// obj.element("id", afterVisitVO.getId());
				// obj.element("cell", data);

				rows.add(afterVisitVO);
			}

			// resultObj.element("page", 1);
			// resultObj.element("total", afterVisitVOs.size());
			// resultObj.element("rows", rows);
			// String contacts = JsonUtil.obj2Json("aaData", dataResult);

			WriteResult.outputResult(rows.toString(), response);
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] customerIds = (String[]) paramMap.get("customerid");
			// String customerId = "";
			// if (customerIds.length > 0) {
			// customerId = customerIds[0];
			// }
			// List<AfterVisitVO> afterVisitVOs = afterVisitService.loadByCustomer(customerId);
			// WriteResult.outPutListResult(afterVisitVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveVisit() {
		try {
			logger.log(Level.INFO, "正在保存客户售后回访信息...");
			String id = request.getParameter("visitid");
			String customerid = request.getParameter("customerid");
			String visitUserId = request.getParameter("visitUser");
			String visitDate = request.getParameter("visitDate");
			String content = request.getParameter("content");
			String result = request.getParameter("result");

			AfterVisit afterVisit;
			if (id == null || id.length() <= 0) {
				afterVisit = new AfterVisit();
			} else {
				afterVisit = afterVisitService.getById(id);
			}

			User user = userService.getById(visitUserId);
			Customer customer = customerService.getById(customerid);
			afterVisit.setVisitUser(user);
			afterVisit.setCustomer(customer);
			afterVisit.setVisitDate(visitDate);
			afterVisit.setContent(content);
			afterVisit.setResult(result);

			afterVisitService.save(afterVisit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// String result = "";
		// try {

		// ActionContext context = ActionContext.getContext();
		// Map<String, Object> paramMap = context.getParameters();
		// Boolean saveResult = afterVisitService.saveVisit(paramMap);
		// if (saveResult) {
		// result = "{success:true,info:'保存成功!'}";
		// } else {
		// result = "{success:false,info:'保存失败!'}";
		// }
		// } catch (Exception e) {
		// e.printStackTrace();
		// result = "{success:false,info:'保存失败!'}";
		// }
		// WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void delVisitByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = afterVisitService.delVisitByID(ids);
		String result = "";
		if (delResult) {
			result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadVisitByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] visitids = (String[]) paramMap.get("visitid");
		String visitid = "";
		if (visitids.length > 0) {
			visitid = visitids[0];
		}
		AfterVisitVO afterVisitVO = afterVisitService.getVisitByID(visitid);
		ExtStore extStore = new ExtStore(1, afterVisitVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

}
