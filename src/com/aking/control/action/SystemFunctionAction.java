package com.aking.control.action;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.aking.control.service.SystemFunctionService;
import com.aking.util.WriteResult;
import com.aking.view.vo.SystemFunctionVO;

public class SystemFunctionAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private SystemFunctionService functionService;

	public void load4Tree() {
		try {
			JSONObject jsTree = new JSONObject();
			jsTree.element("data", "功能权限");
			JSONObject attrObj = new JSONObject();
			attrObj.element("id", "");
			jsTree.element("attr", attrObj);
			List<SystemFunctionVO> systemFunctionVOs = functionService.getRoot();
			jsTree.element("children", this.getChildTree(systemFunctionVOs));
			WriteResult.outputResult(jsTree.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getChildTree(List<SystemFunctionVO> systemFunctionVOs) throws Exception {
		// Collections.sort(categoryVOs);
		// Arrays.sort(categoryVOs.toArray());
		JSONArray childTree = new JSONArray();
		for (SystemFunctionVO systemFunctionVO : systemFunctionVOs) {
			JSONObject childJson = new JSONObject();
			childJson.element("data", systemFunctionVO.getName());
			JSONObject attrObj = new JSONObject();
			attrObj.element("id", systemFunctionVO.getId());
			childJson.element("attr", attrObj);
			if (systemFunctionVO.getChildren() != null) {
				childJson.element("state", "open");
				String childStr = this.getChildTree(systemFunctionVO.getChildren());
				childJson.element("children", childStr);
			}
			childTree.add(childJson);
		}
		return childTree.toString();
	}

}
