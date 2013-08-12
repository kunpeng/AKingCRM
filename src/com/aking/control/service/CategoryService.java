package com.aking.control.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.CategoryDao;
import com.aking.model.subject.Category;
import com.aking.util.BeanUtil;
import com.aking.view.vo.CategoryVO;

@Component
public class CategoryService extends BaseService<Category, java.lang.String> {

	@Autowired
	private CategoryDao categoryDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		// TODO Auto-generated method stub
		return categoryDao;
	}

	public List<CategoryVO> getRoot() throws Exception {
		String hql = "From Category c where c.parent = null";
		List<Category> categories = categoryDao.find(hql);
		List<CategoryVO> categoryVOs = this.copy2VO(categories);
		return categoryVOs;
	}

	public List<CategoryVO> getAll() throws Exception {
		List<Category> categories = categoryDao.loadAll();
		List<CategoryVO> categoryVOs = this.copy2VO(categories);
		return categoryVOs;
	}

	public List<CategoryVO> loadForCombo() throws Exception {
		String hql = "From Category c where c.leaf = 1";
		List<Category> categories = categoryDao.find(hql);
		List<CategoryVO> categoryVOs = this.copy2VO(categories);
		return categoryVOs;
	}

	public Category getTop() throws Exception {
		String hql = "From Category c where c.parent = null";
		List<Category> categories = categoryDao.find(hql);
		if (categories.size() > 0) {
			return categories.get(0);
		}
		return null;
	}

	public List<Category> getByParent(String parentId) throws Exception {
		String hql = "From Category c where c.parent.id = '" + (parentId == null ? "null" : parentId) + "'";
		List<Category> categories = categoryDao.find(hql);
		return categories;
	}

	@SuppressWarnings("unchecked")
	public Boolean saveCategory(String data) throws Exception {
		Category topCategory = this.getTop();
		if (topCategory == null) {
			topCategory = this.addTopCategory();
		}
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				Category category = new Category();
				category.setName(jsonObj.getString("name"));
				category.setMemo(jsonObj.getString("memo"));
				category.setText(jsonObj.getString("text"));
				// if (topCategory != null) {
				category.setParent(topCategory);
				category.setLeaf(true);
				// } else {
				// category.setParent(null);
				// category.setLeaf(false);
				// }
				this.save(category);
			}
		} else {
			JSONObject jsondata = JSONObject.fromObject(data);
			Category category = new Category();
			category.setName(jsondata.getString("name"));
			category.setMemo(jsondata.getString("memo"));
			category.setText(jsondata.getString("text"));
			// if (topCategory != null) {
			category.setParent(topCategory);
			category.setLeaf(true);
			// } else {
			// category.setParent(null);
			// category.setLeaf(false);
			// }
			this.save(category);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Boolean updateCategory(String data) throws Exception {
		Category topCategory = this.getTop();
		if (topCategory == null) {
			topCategory = this.addTopCategory();
		}
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				String id = jsonObj.getString("id");
				Category category = this.getById(id);
				category.setName(jsonObj.getString("name"));
				category.setMemo(jsonObj.getString("memo"));
				category.setText(jsonObj.getString("text"));
				// if (topCategory != null) {
				category.setParent(topCategory);
				category.setLeaf(true);
				// } else {
				// category.setParent(null);
				// category.setLeaf(false);
				// }
				this.save(category);
			}
		} else {
			JSONObject jsondata = JSONObject.fromObject(data);
			String id = jsondata.getString("id");
			Category category = this.getById(id);
			category.setName(jsondata.getString("name"));
			category.setMemo(jsondata.getString("memo"));
			category.setText(jsondata.getString("text"));
			// if (topCategory != null) {
			category.setParent(topCategory);
			category.setLeaf(true);
			// } else {
			// category.setParent(null);
			// category.setLeaf(false);
			// }
			this.save(category);
		}
		return true;
	}

	public Boolean delCategory(String data) throws Exception {
		String[] ids = data.split(",");
		for (String id : ids) {
			Category category = this.getById(id);
			this.delete(category);
		}
		return true;
	}

	public Category addTopCategory() throws Exception {
		Category category = new Category();
		category.setName("行业类别");
		category.setText("行业类别");
		category.setMemo("行业类别");
		category.setLeaf(false);
		this.save(category);
		return category;
	}

	private List<CategoryVO> copy2VO(List<Category> categories) throws Exception {
		List<CategoryVO> categoryVOs = new ArrayList<CategoryVO>();
		for (Category category : categories) {
			CategoryVO categoryVO = new CategoryVO();
			BeanUtil.copyProperties(category, categoryVO);
			categoryVO.setCategoryId(category.getId());
			categoryVO.setParentId(category.getParent() == null ? null : category.getParent().getId());
			categoryVO.setParentName(category.getParent() == null ? null : category.getParent().getName());
			categoryVO.setParentText(category.getParent() == null ? null : category.getParent().getText());
			List<Category> childCategories = new ArrayList<Category>(category.getChildren());
			if (childCategories.size() > 0) {
				List<CategoryVO> childCategoryVOs = this.copy2VO(childCategories);
				categoryVO.setChildren(childCategoryVOs);
			}
			categoryVOs.add(categoryVO);
		}
		return categoryVOs;
	}
}
