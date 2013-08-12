package com.aking.control.action;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.AffairRemindService;
import com.aking.util.WriteResult;
import com.aking.view.vo.AffairRemindVO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component
public class AffairRemindAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger(this.getClass().getName());

	/**
	 * @preserve
	 */
	@Autowired
	private AffairRemindService affairRemindService;

	/**
	 * 获取用户的事务提醒信息
	 * 
	 * @throws Exception
	 */
	public void loadByCustomer() throws Exception {
		logger.log(Level.INFO, "正在加载客户事务提醒信息...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] customerIds = (String[]) paramMap.get("customerid");
		String customerId = "";
		if (customerIds.length > 0) {
			customerId = customerIds[0];
		}
		List<AffairRemindVO> affairRemindVOs = affairRemindService.loadByCustomer(customerId);
		WriteResult.outPutListResult(affairRemindVOs, ServletActionContext.getResponse());
	}

	public void saveRemind() throws Exception {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		Boolean saveResult = affairRemindService.saveRemind(paramMap);
		String result = "";
		if (saveResult) {
			result = "{success:true,info:'保存成功!'}";
		} else {
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void delRemindByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] idsarray = (String[]) paramMap.get("ids");
		String ids = "";
		if (idsarray.length > 0) {
			ids = idsarray[0];
		}
		Boolean delResult = affairRemindService.delRemindByID(ids);
		String result = "";
		if (delResult) {
			result = "{success:true,info:'保存成功!'}";
		} else {
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void loadRemindByID() throws Exception {
		logger.log(Level.INFO, "正在删除联系人记录...");
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		String[] remindids = (String[]) paramMap.get("remindid");
		String remindid = "";
		if (remindids.length > 0) {
			remindid = remindids[0];
		}
		AffairRemindVO affairRemindVO = affairRemindService.getRemindByID(remindid);
		ExtStore extStore = new ExtStore(1, affairRemindVO);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		WriteResult.outputResult(json.toString(), ServletActionContext.getResponse());
	}

}
