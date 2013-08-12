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

import com.aking.control.service.ActivityThemeService;
import com.aking.model.subject.ActivityTheme;
import com.aking.util.WriteResult;
import com.aking.view.vo.ActivityThemeVO;

@Component
public class ActivityThemeAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * @preserve
	 */
	@Autowired
	private ActivityThemeService activityThemeService;

	public void loadForCombo() throws Exception {
		List<ActivityThemeVO> activityThemeVOs = activityThemeService.loadForCombo();
		JSONArray themes = new JSONArray();
		for (ActivityThemeVO activityThemeVO : activityThemeVOs) {
			JSONObject themeObj = new JSONObject();
			themeObj.element("id", activityThemeVO.getId());
			themeObj.element("name", activityThemeVO.getName());
			themeObj.element("label", activityThemeVO.getName());
			themes.add(themeObj);
		}

		WriteResult.outputResult(themes.toString(), response);
		// WriteResult.outPutListResult(activityThemeVOs, ServletActionContext.getResponse());
	}

	public void loadForList() {
		// List<ActivityThemeVO> activityThemeVOs = activityThemeService.loadForList();
		// WriteResult.outPutListResult(activityThemeVOs, ServletActionContext.getResponse());
		try {
			// JSONObject resultObj = new JSONObject();
			List<ActivityThemeVO> activityThemeVOs = activityThemeService.loadForList();
			JSONArray rows = new JSONArray();

			for (ActivityThemeVO activityThemeVO : activityThemeVOs) {
				// String[] data = new String[3];
				// data[0] = activityThemeVO.getName();
				// data[1] = activityThemeVO.getCode();
				// data[2] = activityThemeVO.getMemo();
				//
				// JSONObject activityThemeObj = new JSONObject();
				// activityThemeObj.element("id", activityThemeVO.getId());
				// activityThemeObj.element("cell", data);

				rows.add(activityThemeVO);
			}
			// resultObj.element("page", 1);
			// resultObj.element("total", activityThemeVOs.size());
			// resultObj.element("rows", rows);
			WriteResult.outputResult(rows.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveActivityTheme() {
		try {
			logger.log(Level.INFO, "正在保存活动主题...");
			String id = request.getParameter("activitythemeid");
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			String memo = request.getParameter("memo");

			ActivityTheme activityTheme;
			if (id == null || id.length() <= 0) {
				activityTheme = new ActivityTheme();
			} else {
				activityTheme = activityThemeService.getById(id);
			}

			activityTheme.setName(name);
			activityTheme.setCode(code);
			activityTheme.setMemo(memo);

			activityThemeService.save(activityTheme);
			String result = "{\"success\":\"true\",\"info\":\"保存成功!\",\"id\":\"" + activityTheme.getId() + "\"}";

			WriteResult.outputResult(result, response);

			// HttpServletRequest request = ServletActionContext.getRequest();
			// String data = request.getParameter("data");
			// Boolean saveResult = activityThemeService.saveActivityTheme(data);
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

	public void updateActivityTheme() throws Exception {
		logger.log(Level.INFO, "正在更新活动主题...");
		HttpServletRequest request = ServletActionContext.getRequest();
		String data = request.getParameter("data");
		Boolean saveResult = activityThemeService.updateActivityTheme(data);
		String result = "";
		if (saveResult) {
			result = "{success:true,info:'保存成功!'}";
		} else {
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

	public void delActivityTheme() {
		try {
			logger.log(Level.INFO, "正在删除活动主题...");
			HttpServletRequest request = ServletActionContext.getRequest();
			String data = request.getParameter("ids");
			Boolean saveResult = activityThemeService.delActivityTheme(data);
			String result = "";
			if (saveResult) {
				result = "{\"success\":\"true\",\"info\":\"保存成功!\"}";
			} else {
				result = "{\"success\":\"false\",\"info\":\"保存失败!\"}";
			}
			WriteResult.outputResult(result, ServletActionContext.getResponse());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
