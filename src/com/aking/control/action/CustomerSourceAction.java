package com.aking.control.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.CustomerSourceService;
import com.aking.model.subject.CustomerSource;
import com.aking.util.WriteResult;
import com.aking.view.vo.CustomerSourceVO;

@Component
public class CustomerSourceAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * @preserve
	 */
	@Autowired
	private CustomerSourceService customerSourceService;

	public void loadForCombo() throws Exception {
		List<CustomerSourceVO> customerSourceVOs = customerSourceService.loadForCombo();

		JSONArray sources = new JSONArray();
		for (CustomerSourceVO customerSourceVO : customerSourceVOs) {
			JSONObject obj = new JSONObject();
			obj.element("id", customerSourceVO.getId());
			obj.element("name", customerSourceVO.getName());
			obj.element("label", customerSourceVO.getName());
			sources.add(obj);
		}

		WriteResult.outputResult(sources.toString(), response);

		// WriteResult.outPutListResult(customerSourceVOs, ServletActionContext.getResponse());
	}

	public void loadForList() {
		try {
			// JSONObject resultObj = new JSONObject();
			List<CustomerSourceVO> customerSourceVOs = customerSourceService.loadForList();
			JSONArray rows = new JSONArray();

			for (int i = 0; i < customerSourceVOs.size(); i++) {
				CustomerSourceVO customerSourceVO = customerSourceVOs.get(i);
				// String[] data = new String[3];
				// data[0] = customerSourceVO.getName();
				// data[1] = customerSourceVO.getCode();
				// data[2] = customerSourceVO.getMemo();
				//
				// JSONObject customerSourceObj = new JSONObject();
				// customerSourceObj.element("id", customerSourceVO.getId());
				// customerSourceObj.element("cell", data);

				rows.add(customerSourceVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", customerSourceVOs.size());
			// resultObj.element("rows", rows);
			WriteResult.outputResult(rows.toString(), response);

			// WriteResult.outPutListResult(customerSourceVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveCustomerSource() {
		try {
			logger.log(Level.INFO, "正在保存客户来源...");

			String id = request.getParameter("customersourceid");
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			String memo = request.getParameter("memo");

			CustomerSource customerSource;
			if (id == null || id.length() <= 0) {
				customerSource = new CustomerSource();
			} else {
				customerSource = customerSourceService.getById(id);
			}

			customerSource.setName(name);
			customerSource.setCode(code);
			customerSource.setMemo(memo);

			customerSourceService.save(customerSource);

			String result = "{\"success\":\"true\",\"info\":\"保存成功!\",\"id\":\"" + customerSource.getId() + "\"}";

			WriteResult.outputResult(result, response);

			// HttpServletRequest request = ServletActionContext.getRequest();
			// String data = request.getParameter("data");
			// Boolean saveResult = customerSourceService.saveCustomerSource(data);
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

	public void updateCustomerSource() throws Exception {
		logger.log(Level.INFO, "正在保存客户来源...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("data");
		Boolean saveResult = customerSourceService.updateCustomerSource(data);
		String result = "";
		if (saveResult) {
			result = "{success:true,info:'保存成功!'}";
		} else {
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void delCustomerSource() throws Exception {
		logger.log(Level.INFO, "正在删除行业来源...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("ids");
		Boolean saveResult = customerSourceService.delCustomerSource(data);
		String result = "";
		if (saveResult) {
			result = "{\"success\":\"true\",\"info\":\"删除成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"删除失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

}
