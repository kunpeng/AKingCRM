package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.ContactManDao;
import com.aking.model.subject.ContactMan;
import com.aking.model.subject.Customer;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.ContactManVO;

@Component
public class ContactManService extends BaseService<ContactMan, String> {

	@Autowired
	private ContactManDao contactManDao;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return contactManDao;
	}

	public List<ContactManVO> loadByCustomer(String customerId) throws Exception {
		String hql = "From ContactMan c where c.customer = '" + customerId + "'";
		List<ContactMan> contactMans = contactManDao.find(hql);
		List<ContactManVO> contactManVOs = new ArrayList<ContactManVO>();
		for (ContactMan contactMan : contactMans) {
			ContactManVO contactManVO = new ContactManVO();
			BeanUtil.copyProperties(contactMan, contactManVO);
//			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//			contactManVO.setBirthday(sdf.format(contactMan.getBirthday()));
			contactManVOs.add(contactManVO);
		}
		return contactManVOs;
	}

	public Boolean saveMan(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		ContactMan contactMan = null;
		String id = (String) paramsMap.get("id");
		String customerId = (String) paramsMap.get("customerId");
		if (id == null || id.equals("")) {
			contactMan = new ContactMan();
		} else {
			contactMan = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(contactMan, paramsMap);
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			contactMan.setCustomer(customer);
		}
		contactManDao.save(contactMan);
		return true;
	}

	public Boolean delManByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<ContactMan> contactMans = new ArrayList<ContactMan>();
		for (String id : idsArray) {
			ContactMan contactMan = contactManDao.get(id);
			contactMans.add(contactMan);
		}
		contactManDao.deleteAll(contactMans);
		return true;
	}

	public ContactManVO getManByID(String id) throws Exception {
		ContactMan contactMan = contactManDao.get(id);
		List<ContactMan> contactMans = new ArrayList<ContactMan>();
		contactMans.add(contactMan);
		List<ContactManVO> contactManVOs = this.copy2VO(contactMans);
		if (contactManVOs.size() > 0) {
			return contactManVOs.get(0);
		}
		return null;
	}

	private List<ContactManVO> copy2VO(List<ContactMan> contactMans) throws Exception {
		List<ContactManVO> contactManVOs = new ArrayList<ContactManVO>();
		for (ContactMan contactMan : contactMans) {
			ContactManVO contactManVO = new ContactManVO();
			BeanUtil.copyProperties(contactMan, contactManVO);
//			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//			contactManVO.setBirthday(sdf.format(contactMan.getBirthday()));
			contactManVOs.add(contactManVO);
		}
		return contactManVOs;
	}

}
