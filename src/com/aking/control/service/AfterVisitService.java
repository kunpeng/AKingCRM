package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.AfterVisitDao;
import com.aking.model.subject.AfterVisit;
import com.aking.model.subject.Customer;
import com.aking.model.subject.User;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.AfterVisitVO;

@Component
public class AfterVisitService extends BaseService<AfterVisit, String> {

	@Autowired
	private AfterVisitDao afterVisitDao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return afterVisitDao;
	}

	public List<AfterVisitVO> loadByCustomer(String customerId) throws Exception {
		String hql = "From AfterVisit a where a.customer = '" + customerId + "'";
		List<AfterVisit> afterVisits = afterVisitDao.find(hql);
		List<AfterVisitVO> afterVisitVOs = this.copy2VO(afterVisits);
		// List<AfterVisitVO> afterVisitVOs = new ArrayList<AfterVisitVO>();
		// List<aft>
		// for (AfterVisit afterVisit : afterVisits) {
		// AfterVisitVO afterVisitVO = new AfterVisitVO();
		// BeanUtil.copyProperties(afterVisit, afterVisitVO);
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		// afterVisitVO.setVisitDate(sdf.format(afterVisit.getVisitDate()));
		// afterVisitVOs.add(afterVisitVO);
		// }
		return afterVisitVOs;
	}

	public Boolean saveVisit(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		AfterVisit afterVisit = null;
		String id = (String) paramsMap.get("id");
		String customerId = (String) paramsMap.get("customerId");
		String visitUserId = (String) paramsMap.get("visitUserId");
		if (id == null || id.equals("")) {
			afterVisit = new AfterVisit();
		} else {
			afterVisit = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(afterVisit, paramsMap);
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			afterVisit.setCustomer(customer);
		}
		if (visitUserId != null) {
			User user = userService.getById(visitUserId);
			afterVisit.setVisitUser(user);
		}
		afterVisitDao.save(afterVisit);
		return true;
	}

	public Boolean delVisitByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<AfterVisit> afterVisits = new ArrayList<AfterVisit>();
		for (String id : idsArray) {
			AfterVisit afterVisit = afterVisitDao.get(id);
			afterVisits.add(afterVisit);
		}
		afterVisitDao.deleteAll(afterVisits);
		return true;
	}

	public AfterVisitVO getVisitByID(String id) throws Exception {
		AfterVisit afterVisit = afterVisitDao.get(id);
		List<AfterVisit> afterVisits = new ArrayList<AfterVisit>();
		afterVisits.add(afterVisit);
		List<AfterVisitVO> afterVisitVOs = this.copy2VO(afterVisits);
		if (afterVisitVOs.size() > 0) {
			return afterVisitVOs.get(0);
		}
		return null;
	}

	private List<AfterVisitVO> copy2VO(List<AfterVisit> afterVisits) throws Exception {
		List<AfterVisitVO> afterVisitVOs = new ArrayList<AfterVisitVO>();
		for (AfterVisit afterVisit : afterVisits) {
			AfterVisitVO afterVisitVO = new AfterVisitVO();
			BeanUtil.copyProperties(afterVisit, afterVisitVO);
//			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//			afterVisitVO.setVisitDate(sdf.format(afterVisit.getVisitDate()));
			afterVisitVO.setVisitUserId(afterVisit.getVisitUser().getId());
			afterVisitVO.setVisitUser(afterVisit.getVisitUser().getName());
			afterVisitVOs.add(afterVisitVO);
		}
		return afterVisitVOs;
	}

}
