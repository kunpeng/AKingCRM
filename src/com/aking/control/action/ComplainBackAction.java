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

import com.aking.control.service.ComplainBackService;
import com.aking.control.service.CustomerService;
import com.aking.control.service.UserService;
import com.aking.model.subject.ComplainBack;
import com.aking.model.subject.Customer;
import com.aking.model.subject.User;
import com.aking.util.WriteResult;
import com.aking.view.vo.ComplainBackVO;
import com.opensymphony.xwork2.ActionContext;

@Component
public class ComplainBackAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private ComplainBackService complainBackService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private UserService userService;

	/**
	 * 获取用户的投诉反馈记录
	 * 
	 * @throws Exception
	 */
	public void loadByCustomer() {
		try {
			logger.log(Level.INFO, "正在加载客户投诉反馈信息...");
			String customerId = request.getParameter("customerid");
			List<ComplainBackVO> complainBackVOs = complainBackService.loadByCustomer(customerId);
			// JSONObject resultObj = new JSONObject();
			JSONArray rows = new JSONArray();
			for (ComplainBackVO complainBackVO : complainBackVOs) {
				// String[] data = new String[6];
				// data[0] = complainBackVO.getReceiveUser();
				// data[1] = complainBackVO.getComplainDate();
				// data[2] = complainBackVO.getComplainContent();
				// data[3] = complainBackVO.getDisposeResult();
				// data[4] = complainBackVO.getDisposeDate();
				// data[5] = complainBackVO.getDisposeUser();
				//
				// JSONObject obj = new JSONObject();
				// obj.element("id", complainBackVO.getId());
				// obj.element("cell", data);

				rows.add(complainBackVO);
			}

			// resultObj.element("page", 1);
			// resultObj.element("total", complainBackVOs.size());
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
			// List<ComplainBackVO> complainBackVOs = complainBackService.loadByCustomer(customerId);
			// WriteResult.outPutListResult(complainBackVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveComplain() {
		try {
			String id = request.getParameter("complainbackid");
			String customerid = request.getParameter("customerid");
			String receiveUserid = request.getParameter("receiveUser");
			String complainDate = request.getParameter("complainDate");
			String complainContent = request.getParameter("complainContent");
			String disposeResult = request.getParameter("disposeResult");
			String disposeDate = request.getParameter("disposeDate");
			String disposeUserid = request.getParameter("disposeUser");

			ComplainBack complainBack;
			if (id == null || id.length() <= 0) {
				complainBack = new ComplainBack();
			} else {
				complainBack = complainBackService.getById(id);
			}

			Customer customer;
			User receiveUser;
			User disposeUser;
			customer = customerService.getById(customerid);
			receiveUser = userService.getById(receiveUserid);
			disposeUser = userService.getById(disposeUserid);

			complainBack.setReceiveUser(receiveUser);
			complainBack.setComplainDate(complainDate);
			complainBack.setComplainContent(complainContent);
			complainBack.setDisposeResult(disposeResult);
			complainBack.setDisposeDate(disposeDate);
			complainBack.setDisposeUser(disposeUser);
			complainBack.setCustomer(customer);

			complainBackService.save(complainBack);
			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// Boolean saveResult = complainBackService.saveComplain(paramMap);
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

	public void delComplainByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = complainBackService.delComplainByID(ids);
		String result = "";
		if (delResult) {
			result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadComplainByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] complainids = (String[]) paramMap.get("complainid");
		String complainid = "";
		if (complainids.length > 0) {
			complainid = complainids[0];
		}
		ComplainBackVO complainBackVO = complainBackService.getComplainByID(complainid);
		ExtStore extStore = new ExtStore(1, complainBackVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

}
