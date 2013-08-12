package com.aking.control.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.CustomerTypeDao;
import com.aking.model.subject.CustomerType;
import com.aking.util.BeanUtil;
import com.aking.view.vo.CustomerTypeVO;

@Component
public class CustomerTypeService extends BaseService<CustomerType, java.lang.String> {

	@Autowired
	private CustomerTypeDao customerTypeDao;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		// TODO Auto-generated method stub
		return customerTypeDao;
	}

	public List<CustomerTypeVO> loadForCombo() throws Exception {
		List<CustomerType> customerTypes = customerTypeDao.loadAll();
		List<CustomerTypeVO> customerTypeVOs = new ArrayList<CustomerTypeVO>();
		for (CustomerType customerType : customerTypes) {
			CustomerTypeVO customerTypeVO = new CustomerTypeVO();
			BeanUtil.copyProperties(customerType, customerTypeVO);
			customerTypeVO.setCustomerTypeId(customerType.getId());
			customerTypeVOs.add(customerTypeVO);
		}
		return customerTypeVOs;
	}

	public List<CustomerTypeVO> loadForList() throws Exception {
		return this.loadForCombo();
	}

	@SuppressWarnings("unchecked")
	public Boolean saveCustomerType(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				CustomerType customerType = new CustomerType();
				customerType.setCode(jsonObj.getString("code"));
				customerType.setName(jsonObj.getString("name"));
				customerType.setMemo(jsonObj.getString("memo"));
				this.save(customerType);
			}
		} else {
			JSONObject jsondata = JSONObject.fromObject(data);
			CustomerType customerType = new CustomerType();
			customerType.setCode(jsondata.getString("code"));
			customerType.setName(jsondata.getString("name"));
			customerType.setMemo(jsondata.getString("memo"));
			this.save(customerType);
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public Boolean updateCustomerType(String data) throws Exception {
		if (data.startsWith("[")) {
			JSONArray jsondata = JSONArray.fromObject(data);
			for (Iterator<JSONObject> ite = jsondata.iterator(); ite.hasNext();) {
				JSONObject jsonObj = ite.next();
				String id = jsonObj.getString("id");
				CustomerType customerType = this.getById(id);
				customerType.setCode(jsonObj.getString("code"));
				customerType.setName(jsonObj.getString("name"));
				customerType.setMemo(jsonObj.getString("memo"));
				this.save(customerType);
			}
		} else {
			JSONObject jsondata = JSONObject.fromObject(data);
			String id = jsondata.getString("id");
			CustomerType customerType = this.getById(id);
			customerType.setCode(jsondata.getString("code"));
			customerType.setName(jsondata.getString("name"));
			customerType.setMemo(jsondata.getString("memo"));
			this.save(customerType);
		}
		return true;
	}

	public Boolean delCustomerType(String data) throws Exception {
		String[] ids = data.split(",");
		for (String id : ids) {
			CustomerType customerType = this.getById(id);
			this.delete(customerType);
		}
		return true;
	}

}
