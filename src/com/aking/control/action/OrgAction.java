package com.aking.control.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.OrgServcie;
import com.aking.model.subject.Org;
import com.aking.util.WriteResult;
import com.aking.view.vo.OrgVO;

@Component
public class OrgAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Autowired
	private OrgServcie orgServcie;

	// public void loadAll4Tree() {
	// try {
	// JSONObject jsTree = new JSONObject();
	// List<OrgVO> orgVOs = orgServcie.getRoot();
	// if (orgVOs.size() > 0) {
	// OrgVO orgVO = orgVOs.get(0);
	// jsTree.element("data", orgVO.getName());
	// JSONObject attrObj = new JSONObject();
	// attrObj.element("id", orgVO.getId());
	// jsTree.element("attr", attrObj);
	// if (orgVO.getChildren() != null) {
	// String childStr = this.getChildTree(orgVO.getChildren());
	// jsTree.element("children", childStr);
	// jsTree.element("state", "open");
	// }
	// } else {
	// jsTree.element("data", "组织机构");
	// }
	// WriteResult.outputResult(jsTree.toString(), response);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public String getChildTree(List<OrgVO> orgVOs) throws Exception {
	// JSONArray childTree = new JSONArray();
	// for (OrgVO orgVO : orgVOs) {
	// JSONObject childJson = new JSONObject();
	// childJson.element("data", orgVO.getName());
	// JSONObject attrObj = new JSONObject();
	// attrObj.element("id", orgVO.getId());
	// childJson.element("attr", attrObj);
	// if (orgVO.getChildren() != null) {
	// childJson.element("state", "open");
	// String childStr = this.getChildTree(orgVO.getChildren());
	// childJson.element("children", childStr);
	// }
	// childTree.add(childJson);
	// }
	// return childTree.toString();
	// }

	public void loadAll4Tree() {
		try {
			JSONArray jsTree = new JSONArray();
			List<OrgVO> orgVOs = orgServcie.getRoot();
			if (orgVOs.size() > 0) {
				// for (OrgVO orgVO : orgVOs.get(0).getChildren()) {
				OrgVO orgVO = orgVOs.get(0);
				JSONObject jsonObj = new JSONObject();
				jsonObj.element("id", orgVO.getId());
				jsonObj.element("name", orgVO.getName());
				jsonObj.element("code", orgVO.getCode());
				jsonObj.element("parentid", orgVO.getParentId());
				jsonObj.element("parentname", orgVO.getParentName());
				jsonObj.element("parentcode", orgVO.getParentCode());
				// jsonObj.element("checked", "true");
				if (orgVO.getChildren() != null) {
					String childStr = this.getChildTree(orgVO.getChildren());
					jsonObj.element("children", childStr);
					jsonObj.element("type", "parent");
				} else {
					jsonObj.element("type", "child");
				}
				jsTree.add(jsonObj);
				// }
			} else {
				JSONObject jsonObj = new JSONObject();
				jsonObj.element("id", "root");
				jsonObj.element("name", "name");
				jsonObj.element("type", "child");
				jsTree.add(jsonObj);
			}
			WriteResult.outputResult(jsTree.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getChildTree(List<OrgVO> orgVOs) throws Exception {
		JSONArray childTree = new JSONArray();
		for (OrgVO orgVO : orgVOs) {
			JSONObject childJson = new JSONObject();
			childJson.element("id", orgVO.getId());
			childJson.element("name", orgVO.getName());
			childJson.element("code", orgVO.getCode());
			childJson.element("parentid", orgVO.getParentId());
			childJson.element("parentname", orgVO.getParentName());
			childJson.element("parentcode", orgVO.getParentCode());
			// childJson.element("checked", "true");
			// JSONObject attrObj = new JSONObject();
			// childJson.element("attr", attrObj);
			if (orgVO.getChildren() != null) {
				String childStr = this.getChildTree(orgVO.getChildren());
				childJson.element("children", childStr);
				childJson.element("type", "parent");
			} else {
				childJson.element("type", "child");
			}
			childTree.add(childJson);
		}
		return childTree.toString();
	}

	public void loadByPId() {
		try {
			String parentId = request.getParameter("parentid");
			List<Org> orgs = new ArrayList<Org>();
			if (parentId.equalsIgnoreCase("root")) {
				orgs = orgServcie.getByParent(orgServcie.getTop().getId());
			} else {
				orgs = orgServcie.getByParent(parentId);
			}

			JSONArray jsTree = new JSONArray();
			for (Org org : orgs) {
				JSONObject jsonObj = new JSONObject();
				jsonObj.element("id", org.getId());
				jsonObj.element("name", org.getName());
				if (org.getChildren().size() <= 0) {
					jsonObj.element("children", "true");
				}
				jsTree.add(jsonObj);
			}
			WriteResult.outputResult(jsTree.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveOrg() {
		try {
			logger.log(Level.INFO, "正在保存机构...");

			String orgid = request.getParameter("orgid");
			String parentid = request.getParameter("parentid");
			String name = request.getParameter("name");
			String code = request.getParameter("code");

			Org org;
			if (orgid == null || orgid.length() <= 0 || orgid.contains("root")) {
				org = new Org();
			} else {
				org = orgServcie.getById(orgid);
			}

			org.setName(name);
			org.setCode(code);

			// 如果数据库中没有根节点，添加一个根节点
			Org root = orgServcie.getTop();
			if (root == null) {
				root = new Org();
				root.setName("组织机构");
				root.setCode("组织机构");
				orgServcie.save(root);
			}

			if (orgid.contains("root") || parentid.contains("root")) {
				org.setParent(root);
			} else {
				org.setParent(orgServcie.getById(parentid));
			}

			orgServcie.save(org);

			String result = "{\"success\":\"true\",\"info\":\"保存成功!\",\"id\":\"" + org.getId() + "\"}";
			WriteResult.outputResult(result, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void delOrg() {
		logger.log(Level.INFO, "正在删除机构...");
		// HttpServletRequest request = request;
		String data = request.getParameter("ids");
		// Boolean saveResult = orgServcie..delCategory(data);
		for (String id : data.split(",")) {
			Org org = orgServcie.getById(id);
			orgServcie.delete(org);
		}
	}

	public void updateOrg() {

	}

}
