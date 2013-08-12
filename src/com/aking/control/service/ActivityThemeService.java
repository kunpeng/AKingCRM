package com.aking.control.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.ActivityThemeDao;
import com.aking.model.subject.ActivityTheme;
import com.aking.util.BeanUtil;
import com.aking.view.vo.ActivityThemeVO;

@Component
public class ActivityThemeService extends BaseService<ActivityTheme, String> {

	@Autowired
	private ActivityThemeDao activityThemeDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return activityThemeDao;
	}

	public List<ActivityThemeVO> loadForCombo() throws Exception {
		List<ActivityTheme> activityThemes = activityThemeDao.loadAll();
		List<ActivityThemeVO> activityThemeVOs = new ArrayList<ActivityThemeVO>();
		for (ActivityTheme activityTheme : activityThemes) {
			ActivityThemeVO activityThemeVO = new ActivityThemeVO();
			BeanUtil.copyProperties(activityTheme, activityThemeVO);
			activityThemeVO.setActivityThemeId(activityTheme.getId());
			activityThemeVOs.add(activityThemeVO);
		}
		return activityThemeVOs;
	}

	public List<ActivityThemeVO> loadForList() throws Exception {
		return this.loadForCombo();
	}

	@SuppressWarnings("unchecked")
	public Boolean saveActivityTheme(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				ActivityTheme activityTheme = new ActivityTheme();
				activityTheme.setCode(jsonObj.getString("code"));
				activityTheme.setName(jsonObj.getString("name"));
				activityTheme.setMemo(jsonObj.getString("memo"));
				this.save(activityTheme);
			}
		} else {
			JSONObject jsonObj = JSONObject.fromObject(data);
			ActivityTheme activityTheme = new ActivityTheme();
			activityTheme.setCode(jsonObj.getString("code"));
			activityTheme.setName(jsonObj.getString("name"));
			activityTheme.setMemo(jsonObj.getString("memo"));
			this.save(activityTheme);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Boolean updateActivityTheme(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				String id = jsonObj.getString("id");
				ActivityTheme activityTheme = this.getById(id);
				activityTheme.setCode(jsonObj.getString("code"));
				activityTheme.setName(jsonObj.getString("name"));
				activityTheme.setMemo(jsonObj.getString("memo"));
				this.save(activityTheme);
			}
		} else {
			JSONObject jsonObj = JSONObject.fromObject(data);
			String id = jsonObj.getString("id");
			ActivityTheme activityTheme = this.getById(id);
			activityTheme.setCode(jsonObj.getString("code"));
			activityTheme.setName(jsonObj.getString("name"));
			activityTheme.setMemo(jsonObj.getString("memo"));
			this.save(activityTheme);
		}
		return true;
	}

	public Boolean delActivityTheme(String data) throws Exception {
		String[] ids = data.split(",");
		for (String id : ids) {
			ActivityTheme activityTheme = this.getById(id);
			this.delete(activityTheme);
		}
		return true;
	}

}
