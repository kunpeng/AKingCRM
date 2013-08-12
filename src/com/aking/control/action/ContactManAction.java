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

import com.aking.control.service.ContactManService;
import com.aking.control.service.CustomerService;
import com.aking.model.subject.ContactMan;
import com.aking.model.subject.Customer;
import com.aking.util.WriteResult;
import com.aking.view.vo.ContactManVO;
import com.opensymphony.xwork2.ActionContext;

@Component
public class ContactManAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private ContactManService contactManService;

	@Autowired
	private CustomerService customerService;

	public void loadByCustomer() {
		try {
			logger.log(Level.INFO, "正在加载客户联系人信息...");
			String customerid = request.getParameter("customerid");
			List<ContactManVO> contactManVOs = contactManService.loadByCustomer(customerid);
			// JSONObject resultObj = new JSONObject();
			JSONArray rows = new JSONArray();
			for (int i = 0; i < contactManVOs.size(); i++) {
				ContactManVO contactManVO = contactManVOs.get(i);
				// String[] data = new String[13];
				// data[0] = contactManVO.getName();
				// // data[1] = contactManVO.getIsCharge().toString();
				// data[1] = "1";
				// data[2] = contactManVO.getDepartment();
				// data[3] = contactManVO.getPosition();
				// data[4] = contactManVO.getOfficetel();
				// data[5] = contactManVO.getPhone();
				// data[6] = contactManVO.getHomePhone();
				// data[7] = contactManVO.getEmail();
				// data[8] = contactManVO.getFax();
				// data[9] = contactManVO.getQq();
				// data[10] = contactManVO.getAddress();
				// data[11] = contactManVO.getBirthday();
				// data[12] = contactManVO.getHobby();
				// // data[0] = businessContactVO.getContactDate();
				// // data[1] = businessContactVO.getActivityTheme();
				// // data[2] = businessContactVO.getContactWay();
				// // data[3] = businessContactVO.getChargeUser();
				// // data[4] = businessContactVO.getRecordUser();
				// // dataResult[i] = data;
				//
				// JSONObject contactObj = new JSONObject();
				// contactObj.element("id", contactManVO.getId());
				// contactObj.element("cell", data);

				rows.add(contactManVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", contactManVOs.size());
			// resultObj.element("rows", rows);

			WriteResult.outputResult(rows.toString(), response);

			// ActionContext context = ActionContext.getContext();
			// Map<String, Object> paramMap = context.getParameters();
			// String[] customerIds = (String[]) paramMap.get("customerId");
			// String customerId = "";
			// if (customerIds.length > 0) {
			// customerId = customerIds[0];
			// }
			// List<ContactManVO> contactManVOs = contactManService.loadByCustomer(customerId);
			//
			// WriteResult.outPutListResult(contactManVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveMan() {
		try {
			String id = request.getParameter("contactmanid");
			String customerid = request.getParameter("customerid");
			String name = request.getParameter("manname");
			// String isCharge = request.getParameter("isCharge");
			String department = request.getParameter("department");
			String position = request.getParameter("position");
			String officetel = request.getParameter("officetel");
			String phone = request.getParameter("phone");
			String homePhone = request.getParameter("homePhone");
			String email = request.getParameter("email");
			String fax = request.getParameter("fax");
			String qq = request.getParameter("qq");
			String address = request.getParameter("address");
			String birthday = request.getParameter("birthday");
			String hobby = request.getParameter("hobby");

			ContactMan contactMan;

			if (id == null || id.length() <= 0) {
				contactMan = new ContactMan();
			} else {
				contactMan = contactManService.getById(id);
			}

			contactMan.setName(name);
			contactMan.setDepartment(department);
			contactMan.setPosition(position);
			contactMan.setOfficetel(officetel);
			contactMan.setPhone(phone);
			contactMan.setHomePhone(homePhone);
			contactMan.setEmail(email);
			contactMan.setFax(fax);
			contactMan.setQq(qq);
			contactMan.setAddress(address);
			contactMan.setBirthday(birthday);
			contactMan.setHobby(hobby);
			Customer customer = customerService.getById(customerid);
			contactMan.setCustomer(customer);

			contactManService.save(contactMan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// ActionContext context = ActionContext.getContext();
		// Map<String, Object> paramMap = context.getParameters();
		// Boolean saveResult = contactManService.saveMan(paramMap);
		// String result = "";
		// if (saveResult) {
		// result = "{success:true,info:'保存成功!'}";
		// } else {
		// result = "{success:false,info:'保存失败!'}";
		// }
		// WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void delManByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = contactManService.delManByID(ids);
		String result = "";
		if (delResult) {
			result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadManByID() throws Exception {
		logger.log(Level.INFO, "加载相关客户信息...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] manids = (String[]) paramMap.get("manid");
		String manid = "";
		if (manids.length > 0) {
			manid = manids[0];
		}
		ContactManVO contactManVO = contactManService.getManByID(manid);
		ExtStore extStore = new ExtStore(1, contactManVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}
}
