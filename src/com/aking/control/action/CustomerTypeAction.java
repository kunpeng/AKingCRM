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

import com.aking.control.service.CustomerTypeService;
import com.aking.model.subject.CustomerType;
import com.aking.util.WriteResult;
import com.aking.view.vo.CustomerTypeVO;

@Component
public class CustomerTypeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * @preserve
	 */
	@Autowired
	private CustomerTypeService customerTypeService;

	public void loadForCombo() throws Exception {
		List<CustomerTypeVO> customerTypeVOs = customerTypeService.loadForCombo();

		JSONArray types = new JSONArray();
		for (CustomerTypeVO customerTypeVO : customerTypeVOs) {
			JSONObject typeObj = new JSONObject();
			typeObj.element("id", customerTypeVO.getId());
			typeObj.element("label", customerTypeVO.getName());
			types.add(typeObj);
		}

		WriteResult.outputResult(types.toString(), response);

		// WriteResult.outPutListResult(customerTypeVOs, ServletActionContext.getResponse());

		// ExtStore extStore = new ExtStore(customerTypes.size(), customerTypes);
		//
		// JsonConfig config = new JsonConfig();
		// config.setIgnoreDefaultExcludes(false);
		// config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		//
		// JSONObject json = JSONObject.fromObject(extStore, config);
		//
		// WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

	public void loadForList() {
		try {
			// JSONObject resultObj = new JSONObject();
			List<CustomerTypeVO> customerTypeVOs = customerTypeService.loadForList();
			JSONArray rows = new JSONArray();
			for (int i = 0; i < customerTypeVOs.size(); i++) {
				CustomerTypeVO customerTypeVO = customerTypeVOs.get(i);
				// String[] data = new String[3];
				// data[0] = customerTypeVO.getName();
				// data[1] = customerTypeVO.getCode();
				// data[2] = customerTypeVO.getMemo();
				//
				// JSONObject customerTypeObj = new JSONObject();
				// customerTypeObj.element("id", customerTypeVO.getId());
				// customerTypeObj.element("cell", data);

				rows.add(customerTypeVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", customerTypeVOs.size());
			// resultObj.element("rows", rows);
			WriteResult.outputResult(rows.toString(), response);

			// WriteResult.outPutListResult(customerTypeVOs, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveCustomerType() {
		try {
			logger.log(Level.INFO, "正在保存客户类别...");
			CustomerType customerType;
			// String data = request.getParameter("data");
			String id = request.getParameter("customertypeid");
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			String memo = request.getParameter("memo");

			if (id == null || id.length() <= 0) {
				customerType = new CustomerType();
			} else {
				customerType = customerTypeService.getById(id);
			}

			customerType.setName(name);
			customerType.setCode(code);
			customerType.setMemo(memo);

			customerTypeService.save(customerType);

			// Boolean saveResult = categoryService.saveCategory(data);
			// String result = "";
			// if (saveResult) {
			// result = "{success:true,info:'保存成功!'}";
			// } else {
			// result = "{success:false,info:'保存失败!'}";
			// }
			String result = "{\"success\":\"true\",\"info\":\"保存成功!\",\"id\":\"" + customerType.getId() + "\"}";
			WriteResult.outputResult(result, response);
			// HttpServletRequest request = ServletActionContext.getRequest();
			// String data = request.getParameter("data");
			// Boolean saveResult = customerTypeService.saveCustomerType(data);
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

	public void updateCustomerType() throws Exception {
		logger.log(Level.INFO, "正在更新客户类别...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("data");
		Boolean saveResult = customerTypeService.updateCustomerType(data);
		String result = "";
		if (saveResult) {
			result = "{success:true,info:'保存成功!'}";
		} else {
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void delCustomerType() throws Exception {
		logger.log(Level.INFO, "正在删除行业类别...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("ids");
		Boolean saveResult = customerTypeService.delCustomerType(data);
		String result = "";
		if (saveResult) {
			result = "{\"success\":\"true\",\"info\":\"删除成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"删除失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}
}
