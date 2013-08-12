package com.aking.control.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.ContactWayDao;
import com.aking.model.subject.ContactWay;
import com.aking.util.BeanUtil;
import com.aking.view.vo.ContactWayVO;

@Component
public class ContactWayService extends BaseService<ContactWay, String> {

	@Autowired
	private ContactWayDao contactWayDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return contactWayDao;
	}

	public List<ContactWayVO> loadForCombo() throws Exception {
		List<ContactWay> contactWays = contactWayDao.loadAll();
		List<ContactWayVO> contactWayVOs = new ArrayList<ContactWayVO>();
		for (ContactWay contactWay : contactWays) {
			ContactWayVO contactWayVO = new ContactWayVO();
			BeanUtil.copyProperties(contactWay, contactWayVO);
			contactWayVO.setContactWayId(contactWay.getId());
			contactWayVOs.add(contactWayVO);
		}
		return contactWayVOs;
	}

	public List<ContactWayVO> loadForList() throws Exception {
		return this.loadForCombo();
	}

	@SuppressWarnings("unchecked")
	public Boolean saveContactWay(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				ContactWay contactWay = new ContactWay();
				contactWay.setCode(jsonObj.getString("code"));
				contactWay.setName(jsonObj.getString("name"));
				contactWay.setMemo(jsonObj.getString("memo"));
				this.save(contactWay);
			}
		} else {
			JSONObject jsonObj = JSONObject.fromObject(data);
			ContactWay contactWay = new ContactWay();
			contactWay.setCode(jsonObj.getString("code"));
			contactWay.setName(jsonObj.getString("name"));
			contactWay.setMemo(jsonObj.getString("memo"));
			this.save(contactWay);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Boolean updateContactWay(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				String id = jsonObj.getString("id");
				ContactWay contactWay = this.getById(id);
				contactWay.setCode(jsonObj.getString("code"));
				contactWay.setName(jsonObj.getString("name"));
				contactWay.setMemo(jsonObj.getString("memo"));
				this.save(contactWay);
			}
		} else {
			JSONObject jsonObj = JSONObject.fromObject(data);
			String id = jsonObj.getString("id");
			ContactWay contactWay = this.getById(id);
			contactWay.setCode(jsonObj.getString("code"));
			contactWay.setName(jsonObj.getString("name"));
			contactWay.setMemo(jsonObj.getString("memo"));
			this.save(contactWay);
		}
		return true;
	}

	public Boolean delContactWay(String data) throws Exception {
		String[] ids = data.split(",");
		for (String id : ids) {
			ContactWay contactWay = this.getById(id);
			this.delete(contactWay);
		}
		return true;
	}

}
