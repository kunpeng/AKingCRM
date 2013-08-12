package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.AffairRemindDao;
import com.aking.model.subject.AffairRemind;
import com.aking.model.subject.Customer;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.AffairRemindVO;

@Component
public class AffairRemindService extends BaseService<AffairRemind, String> {

	@Autowired
	private AffairRemindDao affairRemindDao;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return affairRemindDao;
	}

	public List<AffairRemindVO> loadByCustomer(String customerId) throws Exception {
		String hql = "From AffairRemind a where a.customer = '" + customerId + "'";
		List<AffairRemind> affairReminds = affairRemindDao.find(hql);
		List<AffairRemindVO> affairRemindVOs = new ArrayList<AffairRemindVO>();
		for (AffairRemind affairRemind : affairReminds) {
			AffairRemindVO affairRemindVO = new AffairRemindVO();
			BeanUtil.copyProperties(affairRemind, affairRemindVO);
			affairRemindVOs.add(affairRemindVO);
		}
		return affairRemindVOs;
	}

	public Boolean saveRemind(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		AffairRemind affairRemind = null;
		String id = (String) paramsMap.get("id");
		String customerId = (String) paramsMap.get("customerId");
		if (id == null || id.equals("")) {
			affairRemind = new AffairRemind();
		} else {
			affairRemind = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(affairRemind, paramsMap);
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			affairRemind.setCustomer(customer);
		}
		affairRemindDao.save(affairRemind);
		return true;
	}

	public Boolean delRemindByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<AffairRemind> affairReminds = new ArrayList<AffairRemind>();
		for (String id : idsArray) {
			AffairRemind affairRemind = affairRemindDao.get(id);
			affairReminds.add(affairRemind);
		}
		affairRemindDao.deleteAll(affairReminds);
		return true;
	}

	public AffairRemindVO getRemindByID(String id) throws Exception {
		AffairRemind affairRemind = affairRemindDao.get(id);
		List<AffairRemind> affairReminds = new ArrayList<AffairRemind>();
		affairReminds.add(affairRemind);
		List<AffairRemindVO> affairRemindVOs = this.copy2VO(affairReminds);
		if (affairRemindVOs.size() > 0) {
			return affairRemindVOs.get(0);
		}
		return null;
	}

	private List<AffairRemindVO> copy2VO(List<AffairRemind> affairReminds) throws Exception {
		List<AffairRemindVO> affairRemindVOs = new ArrayList<AffairRemindVO>();
		for (AffairRemind affairRemind : affairReminds) {
			AffairRemindVO affairRemindVO = new AffairRemindVO();
			BeanUtil.copyProperties(affairRemind, affairRemindVO);
			affairRemindVOs.add(affairRemindVO);
		}
		return affairRemindVOs;
	}

}
