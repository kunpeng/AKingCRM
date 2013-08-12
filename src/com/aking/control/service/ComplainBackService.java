package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.ComplainBackDao;
import com.aking.model.subject.ComplainBack;
import com.aking.model.subject.Customer;
import com.aking.model.subject.User;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.ComplainBackVO;

@Component
public class ComplainBackService extends BaseService<ComplainBack, String> {

	@Autowired
	private ComplainBackDao complainBackDao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private UserService userService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return complainBackDao;
	}

	public List<ComplainBackVO> loadByCustomer(String customerId) throws Exception {
		String hql = "From ComplainBack c where c.customer = '" + customerId + "'";
		List<ComplainBack> complainBacks = complainBackDao.find(hql);
		// List<ComplainBackVO> complainBackVOs = new ArrayList<ComplainBackVO>();
		// for (ComplainBack complainBack : complainBacks) {
		// ComplainBackVO complainBackVO = new ComplainBackVO();
		// BeanUtil.copyProperties(complainBack, complainBackVO);
		// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		// complainBackVO.setComplainDate(sdf.format(complainBack.getComplainDate()));
		// complainBackVO.setDisposeDate(sdf.format(complainBack.getDisposeDate()));
		// complainBackVOs.add(complainBackVO);
		// }
		List<ComplainBackVO> complainBackVOs = this.copy2VO(complainBacks);
		return complainBackVOs;
	}

	public Boolean saveComplain(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		ComplainBack complainBack = null;
		String id = (String) paramsMap.get("id");
		String customerId = (String) paramsMap.get("customerId");
		String receiveUserId = (String) paramsMap.get("receiveUserId");
		String disposeUserId = (String) paramsMap.get("disposeUserId");
		if (id == null || id.equals("")) {
			complainBack = new ComplainBack();
		} else {
			complainBack = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(complainBack, paramsMap);
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			complainBack.setCustomer(customer);
		}
		if (receiveUserId != null) {
			User user = userService.getById(receiveUserId);
			complainBack.setReceiveUser(user);
		}
		if (disposeUserId != null) {
			User user = userService.getById(disposeUserId);
			complainBack.setDisposeUser(user);
		}
		complainBackDao.save(complainBack);
		return true;
	}

	public Boolean delComplainByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<ComplainBack> complainBacks = new ArrayList<ComplainBack>();
		for (String id : idsArray) {
			ComplainBack complainBack = complainBackDao.get(id);
			complainBacks.add(complainBack);
		}
		complainBackDao.deleteAll(complainBacks);
		return true;
	}

	public ComplainBackVO getComplainByID(String id) throws Exception {
		ComplainBack complainBack = complainBackDao.get(id);
		List<ComplainBack> complainBacks = new ArrayList<ComplainBack>();
		complainBacks.add(complainBack);
		List<ComplainBackVO> complainBackVOs = this.copy2VO(complainBacks);
		if (complainBackVOs.size() > 0) {
			return complainBackVOs.get(0);
		}
		return null;
	}

	private List<ComplainBackVO> copy2VO(List<ComplainBack> complainBacks) throws Exception {
		List<ComplainBackVO> complainBackVOs = new ArrayList<ComplainBackVO>();
		for (ComplainBack complainBack : complainBacks) {
			ComplainBackVO complainBackVO = new ComplainBackVO();
			BeanUtil.copyProperties(complainBack, complainBackVO);
//			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//			complainBackVO.setComplainDate(sdf.format(complainBack.getComplainDate()));
//			complainBackVO.setDisposeDate(sdf.format(complainBack.getDisposeDate()));
			complainBackVO.setReceiveUserId(complainBack.getReceiveUser().getId());
			complainBackVO.setReceiveUser(complainBack.getReceiveUser().getName());
			complainBackVO.setDisposeUserId(complainBack.getDisposeUser().getId());
			complainBackVO.setDisposeUser(complainBack.getDisposeUser().getName());
			complainBackVOs.add(complainBackVO);
		}
		return complainBackVOs;
	}

}
