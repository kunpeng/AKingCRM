package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.BusinessContactDao;
import com.aking.model.subject.ActivityTheme;
import com.aking.model.subject.BusinessContact;
import com.aking.model.subject.ContactWay;
import com.aking.model.subject.Customer;
import com.aking.model.subject.User;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.BusinessContactVO;

@Component
public class BusinessContactService extends BaseService<BusinessContact, java.lang.String> {

	@Autowired
	private BusinessContactDao businessContactDao;

	@Autowired
	private ContactWayService contactWayService;

	@Autowired
	private ActivityThemeService activityThemeService;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return businessContactDao;
	}

	public List<BusinessContactVO> loadByCustomer(String customerID) throws Exception {
		String hql = "From BusinessContact b where b.customer = '" + customerID + "'";
		List<BusinessContact> businessContacts = businessContactDao.find(hql);
		List<BusinessContactVO> businessContactVOs = new ArrayList<BusinessContactVO>();
		for (BusinessContact businessContact : businessContacts) {
			BusinessContactVO businessContactVO = new BusinessContactVO();
			BeanUtil.copyProperties(businessContact, businessContactVO);
			businessContactVO.setActivityTheme(businessContact.getActivityTheme().getName());
			businessContactVO.setContactWay(businessContact.getContactWay().getName());
			businessContactVO.setCustomer(businessContact.getCustomer().getName());
			businessContactVO.setChargeUser(businessContact.getChargeUser().getName());
			businessContactVO.setRecordUser(businessContact.getRecordUser().getName());

			businessContactVO.setActivityThemeId(businessContact.getActivityTheme().getId());
			businessContactVO.setContactWayId(businessContact.getContactWay().getId());
			// businessContactVO.setCustomer(businessContact.getCustomer().getName());
			businessContactVO.setChargeUserId(businessContact.getChargeUser().getId());
			businessContactVO.setRecordUserId(businessContact.getRecordUser().getId());
			// businessContactVO.setId(businessContact.getId());
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// businessContactVO.setContactDate(sdf.format(businessContact.getContactDate()));
			businessContactVOs.add(businessContactVO);
		}
		return businessContactVOs;
	}

	public List<BusinessContactVO> loadAllContact() throws Exception {
		List<BusinessContact> businessContacts = businessContactDao.loadAll();
		List<BusinessContactVO> businessContactVOs = new ArrayList<BusinessContactVO>();
		for (BusinessContact businessContact : businessContacts) {
			BusinessContactVO businessContactVO = new BusinessContactVO();
			businessContactVO.setCustomer(businessContact.getCustomer().getName());
			businessContactVO.setId(businessContact.getId());
			businessContactVO.setContactDate(businessContact.getContactDate());
			businessContactVO.setActivityTheme(businessContact.getActivityTheme().getName());
			businessContactVO.setContactWay(businessContact.getContactWay().getName());
			businessContactVO.setChargeUser(businessContact.getChargeUser().getName());
			// businessContactVO.setRecordUser(businessContact.getRecordUser().getName());
			businessContactVOs.add(businessContactVO);
		}
		return businessContactVOs;
	}

	public Boolean saveContact(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		BusinessContact businessContact = null;
		String id = (String) paramsMap.get("contactid");
		String contactWayId = (String) paramsMap.get("contactWay");
		String activityThemeId = (String) paramsMap.get("activityTheme");
		String chargeUserId = (String) paramsMap.get("chargeUser");
		String recordUserId = (String) paramsMap.get("recordUser");
		String customerId = (String) paramsMap.get("customerid");
		if (id == null || id.equals("")) {
			businessContact = new BusinessContact();
		} else {
			businessContact = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(businessContact, paramsMap);
		if (contactWayId != null) {
			ContactWay contactWay = contactWayService.getById(contactWayId);
			businessContact.setContactWay(contactWay);
		}
		if (activityThemeId != null) {
			ActivityTheme activityTheme = activityThemeService.getById(activityThemeId);
			businessContact.setActivityTheme(activityTheme);
		}
		if (chargeUserId != null) {
			User user = userService.getById(chargeUserId);
			businessContact.setChargeUser(user);
		}
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			businessContact.setCustomer(customer);
		}
		if (recordUserId != null) {
			User recordUser = userService.getById(recordUserId);
			businessContact.setRecordUser(recordUser);
		}
		businessContactDao.save(businessContact);
		return true;
	}

	public Boolean delContactByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<BusinessContact> businessContacts = new ArrayList<BusinessContact>();
		for (String id : idsArray) {
			BusinessContact businessContact = businessContactDao.get(id);
			businessContacts.add(businessContact);
		}
		businessContactDao.deleteAll(businessContacts);
		return true;
	}

	public BusinessContactVO getContactByID(String id) throws Exception {
		BusinessContact businessContact = businessContactDao.get(id);
		List<BusinessContact> businessContacts = new ArrayList<BusinessContact>();
		businessContacts.add(businessContact);
		List<BusinessContactVO> businessContactVOs = this.copy2VO(businessContacts);
		if (businessContactVOs.size() > 0) {
			return businessContactVOs.get(0);
		}
		return null;
	}

	private List<BusinessContactVO> copy2VO(List<BusinessContact> businessContacts) throws Exception {
		List<BusinessContactVO> businessContactVOs = new ArrayList<BusinessContactVO>();
		for (BusinessContact businessContact : businessContacts) {
			BusinessContactVO businessContactVO = new BusinessContactVO();
			BeanUtil.copyProperties(businessContact, businessContactVO);
			ActivityTheme activityTheme = businessContact.getActivityTheme();
			if (activityTheme != null) {
				businessContactVO.setActivityThemeId(activityTheme.getId());
				businessContactVO.setActivityTheme(activityTheme.getName());
			}
			ContactWay contactWay = businessContact.getContactWay();
			if (contactWay != null) {
				businessContactVO.setContactWayId(contactWay.getId());
				businessContactVO.setContactWay(contactWay.getName());
			}
			User chargeUser = businessContact.getChargeUser();
			if (chargeUser != null) {
				businessContactVO.setChargeUserId(chargeUser.getId());
				businessContactVO.setChargeUser(chargeUser.getName());
			}
			businessContactVOs.add(businessContactVO);
		}
		return businessContactVOs;
	}
}
