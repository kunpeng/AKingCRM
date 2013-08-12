package com.aking.control.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.CustomerSourceDao;
import com.aking.model.subject.CustomerSource;
import com.aking.util.BeanUtil;
import com.aking.view.vo.CustomerSourceVO;

@Component
public class CustomerSourceService extends BaseService<CustomerSource, String> {

	@Autowired
	private CustomerSourceDao customerSourceDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		// TODO Auto-generated method stub
		return customerSourceDao;
	}

	public List<CustomerSourceVO> loadForCombo() throws Exception {
		List<CustomerSourceVO> customerSourceVOs = new ArrayList<CustomerSourceVO>();
		List<CustomerSource> customerSources = customerSourceDao.loadAll();
		for (CustomerSource customerSource : customerSources) {
			CustomerSourceVO customerSourceVO = new CustomerSourceVO();
			BeanUtil.copyProperties(customerSource, customerSourceVO);
			customerSourceVO.setCustomerSourceId(customerSource.getId());
			customerSourceVOs.add(customerSourceVO);
		}
		return customerSourceVOs;
	}

	public List<CustomerSourceVO> loadForList() throws Exception {
		return this.loadForCombo();
	}

	@SuppressWarnings("unchecked")
	public Boolean saveCustomerSource(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				CustomerSource customerSource = new CustomerSource();
				customerSource.setCode(jsonObj.getString("code"));
				customerSource.setName(jsonObj.getString("name"));
				customerSource.setMemo(jsonObj.getString("memo"));
				this.save(customerSource);
			}
		} else {
			JSONObject jsonObj = JSONObject.fromObject(data);
			CustomerSource customerSource = new CustomerSource();
			customerSource.setCode(jsonObj.getString("code"));
			customerSource.setName(jsonObj.getString("name"));
			customerSource.setMemo(jsonObj.getString("memo"));
			this.save(customerSource);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Boolean updateCustomerSource(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				String id = jsonObj.getString("id");
				CustomerSource customerSource = this.getById(id);
				customerSource.setCode(jsonObj.getString("code"));
				customerSource.setName(jsonObj.getString("name"));
				customerSource.setMemo(jsonObj.getString("memo"));
				this.save(customerSource);
			}
		} else {
			JSONObject jsonObj = JSONObject.fromObject(data);
			String id = jsonObj.getString("id");
			CustomerSource customerSource = this.getById(id);
			customerSource.setCode(jsonObj.getString("code"));
			customerSource.setName(jsonObj.getString("name"));
			customerSource.setMemo(jsonObj.getString("memo"));
			this.save(customerSource);
		}
		return true;
	}

	public Boolean delCustomerSource(String data) throws Exception {
		String[] ids = data.split(",");
		for (String id : ids) {
			CustomerSource customerSource = this.getById(id);
			this.delete(customerSource);
		}
		return true;
	}
}
