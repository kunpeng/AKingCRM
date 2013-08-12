package com.aking.control.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.control.service.CategoryService;
import com.aking.model.subject.Category;
import com.aking.util.WriteResult;
import com.aking.view.vo.CategoryVO;

@Component
public class CategoryAction extends BaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger(this.getClass().getName());
	/**
	 * @preserve
	 */
	@Autowired
	private CategoryService categoryService;

	public void loadAll() throws Exception {
		List<CategoryVO> categoryVOs = categoryService.getRoot();

		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSON json = JSONSerializer.toJSON(categoryVOs, config);

		WriteResult.outputResult(json.toString(), response);
	}

	public void loadAll4Tree() {
		try {
			JSONArray jsTree = new JSONArray();
			List<CategoryVO> categoryVOs = categoryService.getRoot();
			if (categoryVOs.size() > 0) {
				CategoryVO categoryVO = categoryVOs.get(0);
				JSONObject jsonObj = new JSONObject();
				jsonObj.element("id", categoryVO.getId());
				jsonObj.element("name", categoryVO.getName());
				jsonObj.element("text", categoryVO.getText());
				jsonObj.element("demo", categoryVO.getMemo());
				jsonObj.element("parentId", categoryVO.getParentId());
				jsonObj.element("parentName", categoryVO.getParentName());
				jsonObj.element("parentText", categoryVO.getParentText());
				// jsTree.element("attr", attrObj);
				if (categoryVO.getChildren() != null) {
					String childJson = this.getChildTree(categoryVO.getChildren());
					jsonObj.element("children", childJson);
					jsonObj.element("type", "parent");
				} else {
					jsonObj.element("type", "child");
				}
				jsTree.add(jsonObj);
			} else {
				JSONObject jsonObj = new JSONObject();
				jsonObj.element("id", "root");
				jsonObj.element("name", "name");
				jsonObj.element("type", "child");
				jsTree.add(jsonObj);
			}
			// String testStr = "{identifier:'id',label:'name',items:[{id:'01',name:'中国'}]}";
			WriteResult.outputResult(jsTree.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// public void loadAll4Tree() {
	// try {
	// JSONArray jsTree = new JSONArray();
	// // JSONObject jsTree = new JSONObject();
	// List<CategoryVO> categoryVOs = categoryService.getAll();
	// // if (categoryVOs.size() > 0) {
	// // for (CategoryVO categoryVO : categoryVOs) {
	// // JSONObject cateJson = new JSONObject();
	// // // JSONObject jsTree = new JSONObject();
	// // cateJson.element("id", categoryVO.getId());
	// // cateJson.element("name", categoryVO.getName());
	// // cateJson.element("parent", categoryVO.getParentId());
	// // jsTree.add(cateJson);
	// // }
	// // } else {
	// JSONObject cateJson = new JSONObject();
	// cateJson.element("id", "root");
	// cateJson.element("name", "行业分类");
	// jsTree.add(cateJson);
	// // }
	// // String testStr = "{identifier:'id',label:'name',items:[{id:'01',name:'中国'}]}";
	// WriteResult.outputResult(jsTree.toString(), response);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public void loadCateByPid() {
		try {
			String parentId = request.getParameter("parentid");
			List<Category> categories = new ArrayList<Category>();
			if (parentId.equalsIgnoreCase("root")) {
				Category topCategory = categoryService.getTop();
				if (topCategory != null)
					categories = categoryService.getByParent(categoryService.getTop().getId());
			} else {
				categories = categoryService.getByParent(parentId);
			}
			JSONArray jsTree = new JSONArray();
			// JsTree[] jsTrees = new JsTree[categories.size()];
			for (int i = 0; i < categories.size(); i++) {
				Category category = categories.get(i);
				JSONObject jsonObj = new JSONObject();
				// JsTree jsTree = new JsTree();
				// jsTree.setData(category.getName());
				// jsTree.setState("open");
				// jsTree.setAttr("{ id:" + category.getId() + "}");
				// jsTrees[i] = jsTree;
				jsonObj.element("id", category.getId());
				jsonObj.element("name", category.getName());
				if (category.getChildren().size() <= 0) {
					jsonObj.element("children", "true");
				}
				jsTree.add(jsonObj);
			}
			// WriteResult.outputResult(JsonUtil.obj2json(jsTrees), response);
			WriteResult.outputResult(jsTree.toString(), response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getChildTree(List<CategoryVO> categoryVOs) throws Exception {
		Collections.sort(categoryVOs);
		// Arrays.sort(categoryVOs.toArray());
		JSONArray childTree = new JSONArray();
		for (CategoryVO categoryVO : categoryVOs) {
			JSONObject childJson = new JSONObject();
			childJson.element("name", categoryVO.getName());
			// JSONObject attrObj = new JSONObject();
			childJson.element("id", categoryVO.getId());
			childJson.element("text", categoryVO.getText());
			childJson.element("memo", categoryVO.getMemo());
			childJson.element("parentId", categoryVO.getParentId());
			childJson.element("parentName", categoryVO.getParentName());
			childJson.element("parentText", categoryVO.getParentText());
			// childJson.element("attr", attrObj);
			if (categoryVO.getChildren() != null) {
				// childJson.element("state", "open");
				String childStr = this.getChildTree(categoryVO.getChildren());
				childJson.element("children", childStr);
				childJson.element("type", "parent");
			} else {
				childJson.element("type", "child");
			}
			childTree.add(childJson);
		}
		return childTree.toString();
	}

	public void loadList() {
		try {
			JSONObject resultObj = new JSONObject();
			List<CategoryVO> categoryVOs = categoryService.loadForCombo();
			JSONArray rows = new JSONArray();
			for (int i = 0; i < categoryVOs.size(); i++) {
				CategoryVO categoryVO = categoryVOs.get(i);
				String[] data = new String[2];
				data[0] = categoryVO.getName();
				data[1] = categoryVO.getMemo();

				JSONObject cateObj = new JSONObject();
				cateObj.element("id", categoryVO.getId());
				cateObj.element("cell", data);

				rows.add(cateObj);
			}

			resultObj.element("page", 1);
			resultObj.element("total", categoryVOs.size());
			resultObj.element("rows", rows);
			WriteResult.outputResult(resultObj.toString(), response);

			// WriteResult.outPutListResult(categoryVOs, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadForCombo() throws Exception {
		List<CategoryVO> categoryVOs = categoryService.loadForCombo();

		JSONArray cates = new JSONArray();
		for (CategoryVO categoryVO : categoryVOs) {
			JSONObject obj = new JSONObject();
			obj.element("id", categoryVO.getId());
			obj.element("name", categoryVO.getName());
			obj.element("label", categoryVO.getName());
			cates.add(obj);
		}
		WriteResult.outputResult(cates.toString(), response);
		// WriteResult.outPutListResult(categoryVOs, response);
	}

	public void saveCategory() {
		try {
			logger.log(Level.INFO, "正在保存行业类别...");
			// HttpServletRequest request = request;
			Category category;
			// String data = request.getParameter("data");
			String parentId = request.getParameter("parentid");
			String id = request.getParameter("categoryid");
			String name = request.getParameter("name");
			String text = request.getParameter("text");
			String memo = request.getParameter("memo");

			if (id == null || id.length() <= 0 || id.contains("root")) {
				category = new Category();
			} else {
				category = categoryService.getById(id);
			}

			category.setName(name);
			category.setText(text);
			category.setMemo(memo);
			category.setLeaf(true);
			// 判断是否有根节点，如没有，则添加根节点
			Category topCate = categoryService.getTop();
			if (topCate == null) {
				topCate = new Category();
				topCate.setLeaf(false);
				topCate.setName("行业分类");
				topCate.setMemo("行业分类");
				topCate.setText("行业分类");
				categoryService.save(topCate);
			}

			// 设置父节点
			if (id.contains("root") || parentId.contains("root")) {
				category.setParent(topCate);
			} else {
				Category parentCategory = categoryService.getById(parentId);
				category.setParent(parentCategory);
			}

			categoryService.save(category);

			// Boolean saveResult = categoryService.saveCategory(data);
			// String result = "";
			// if (saveResult) {
			// result = "{success:true,info:'保存成功!'}";
			// } else {
			// result = "{success:false,info:'保存失败!'}";
			// }
			String result = "{\"success\":\"true\",\"info\":\"保存成功!\",\"id\":\"" + category.getId() + "\"}";
			WriteResult.outputResult(result, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateCategory() throws Exception {
		logger.log(Level.INFO, "正在修改行业类别...");
		// HttpServletRequest request = request;
		String data = request.getParameter("data");
		Boolean saveResult = categoryService.updateCategory(data);
		String result = "";
		if (saveResult) {
			result = "{success:true,info:'保存成功!'}";
		} else {
			result = "{success:false,info:'保存失败!'}";
		}
		WriteResult.outputResult(result, response);
	}

	public void delCategory() {
		try {
			logger.log(Level.INFO, "正在删除行业类别...");
			// HttpServletRequest request = request;
			String data = request.getParameter("ids");
			Boolean saveResult = categoryService.delCategory(data);
			String result = "";
			if (saveResult) {
				result = "{\"success\":\"true\",\"info\":\"删除成功!\"}";
			} else {
				result = "{\"success\":\"false\",\"info\":\"删除失败!\"}";
			}
			WriteResult.outputResult(result, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
