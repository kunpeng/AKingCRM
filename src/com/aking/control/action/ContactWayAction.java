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

import com.aking.control.service.ContactWayService;
import com.aking.model.subject.ContactWay;
import com.aking.util.WriteResult;
import com.aking.view.vo.ContactWayVO;

@Component
public class ContactWayAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * @preserve
	 */
	@Autowired
	private ContactWayService contactWayService;

	public void loadForCombo() throws Exception {
		JSONArray ways = new JSONArray();
		List<ContactWayVO> contactWayVOs = contactWayService.loadForCombo();
		for (ContactWayVO contactWayVO : contactWayVOs) {
			JSONObject wayObj = new JSONObject();
			wayObj.element("id", contactWayVO.getId());
			wayObj.element("name", contactWayVO.getName());
			wayObj.element("label", contactWayVO.getName());
			ways.add(wayObj);
		}
		WriteResult.outputResult(ways.toString(), response);
		// WriteResult.outPutListResult(contactWayVOs, ServletActionContext.getResponse());
	}

	public void loadForList() {
		// List<ContactWayVO> contactWayVOs = contactWayService.loadForList();
		// WriteResult.outPutListResult(contactWayVOs, ServletActionContext.getResponse());
		try {
			// JSONObject resultObj = new JSONObject();
			List<ContactWayVO> contactWayVOs = contactWayService.loadForList();
			JSONArray rows = new JSONArray();

			for (ContactWayVO contactWayVO : contactWayVOs) {
				// String[] data = new String[3];
				// data[0] = contactWayVO.getName();
				// data[1] = contactWayVO.getCode();
				// data[2] = contactWayVO.getMemo();
				//
				// JSONObject contactWayObj = new JSONObject();
				// contactWayObj.element("id", contactWayVO.getId());
				// contactWayObj.element("cell", data);

				rows.add(contactWayVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", contactWayVOs.size());
			// resultObj.element("rows", rows);
			WriteResult.outputResult(rows.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveContactWay() {
		try {
			logger.log(Level.INFO, "正在保存联系方式...");
			String id = request.getParameter("contactwayid");
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			String memo = request.getParameter("memo");

			ContactWay contactWay;
			if (id == null || id.length() <= 0) {
				contactWay = new ContactWay();
			} else {
				contactWay = contactWayService.getById(id);
			}

			contactWay.setName(name);
			contactWay.setCode(code);
			contactWay.setMemo(memo);

			contactWayService.save(contactWay);

			String result = "{\"success\":\"true\",\"info\":\"保存成功!\",\"id\":\"" + contactWay.getId() + "\"}";
			WriteResult.outputResult(result, response);
			// HttpServletRequest request = ServletActionContext.getRequest();
			// String data = request.getParameter("data");
			// Boolean saveResult = contactWayService.saveContactWay(data);
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

	public void updateContactWay() throws Exception {
		logger.log(Level.INFO, "正在更新联系方式...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("data");
		Boolean saveResult = contactWayService.updateContactWay(data);
		String result = "";
		if (saveResult) {
			result = "{success:true,info:'保存成功!'}";
		} else {
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void delContactWay() throws Exception {
		logger.log(Level.INFO, "正在删除联系方式...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("ids");
		Boolean saveResult = contactWayService.delContactWay(data);
		String result = "";
		if (saveResult) {
			result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
		} else {
			result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

}
