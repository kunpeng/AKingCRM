package com.aking.control.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.DealRecordDao;
import com.aking.model.subject.Customer;
import com.aking.model.subject.DealRecord;
import com.aking.model.subject.Product;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.DealRecordVO;

@Component
public class DealRecordService extends BaseService<DealRecord, String> {

	@Autowired
	private DealRecordDao dealRecordDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return dealRecordDao;
	}

	public List<DealRecordVO> loadByCustomer(String customerId) throws Exception {
		String hql = "From DealRecord d where d.customer = '" + customerId + "'";
		List<DealRecord> dealRecords = dealRecordDao.find(hql);
		List<DealRecordVO> dealRecordVOs = new ArrayList<DealRecordVO>();
		for (DealRecord dealRecord : dealRecords) {
			DealRecordVO dealRecordVO = new DealRecordVO();
			BeanUtil.copyProperties(dealRecord, dealRecordVO);
			// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			// dealRecordVO.setDealDate(sdf.format(dealRecord.getDealDate()));
			// dealRecordVO.setDeliverDate(sdf.format(dealRecord.getDeliverDate()));
			Product product = dealRecord.getProduct();
			dealRecordVO.setProductId(product.getId());
			dealRecordVO.setProductName(product.getName());
			dealRecordVO.setStandard(product.getStandard());
			double price = dealRecord.getPrice();
			double quantity = dealRecord.getQuantity();
			Double sum = price * quantity;
			dealRecordVO.setSum(sum);
			dealRecordVOs.add(dealRecordVO);
		}
		return dealRecordVOs;
	}

	public Boolean savaRecord(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		DealRecord dealRecord = null;
		String id = (String) paramsMap.get("id");
		String productId = (String) paramsMap.get("productId");
		String customerId = (String) paramsMap.get("customerId");
		if (id == null || id.equals("")) {
			dealRecord = new DealRecord();
		} else {
			dealRecord = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(dealRecord, paramsMap);
		if (productId != null) {
			Product product = productService.getById(productId);
			dealRecord.setProduct(product);
		}
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			dealRecord.setCustomer(customer);
		}
		dealRecordDao.save(dealRecord);
		return true;
	}

	public Boolean delRecordByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<DealRecord> dealRecords = new ArrayList<DealRecord>();
		for (String id : idsArray) {
			DealRecord dealRecord = dealRecordDao.get(id);
			dealRecords.add(dealRecord);
		}
		dealRecordDao.deleteAll(dealRecords);
		return true;
	}

	public DealRecordVO getRecordByID(String id) throws Exception {
		DealRecord dealRecord = dealRecordDao.get(id);
		List<DealRecord> dealRecords = new ArrayList<DealRecord>();
		dealRecords.add(dealRecord);
		List<DealRecordVO> dealRecordVOs = this.copy2VO(dealRecords);
		if (dealRecordVOs.size() > 0) {
			return dealRecordVOs.get(0);
		}
		return null;
	}

	private List<DealRecordVO> copy2VO(List<DealRecord> dealRecords) throws Exception {
		List<DealRecordVO> dealRecordVOs = new ArrayList<DealRecordVO>();
		for (DealRecord dealRecord : dealRecords) {
			DealRecordVO dealRecordVO = new DealRecordVO();
			BeanUtil.copyProperties(dealRecord, dealRecordVO);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			dealRecordVO.setDealDate(sdf.format(dealRecord.getDealDate()));
			dealRecordVO.setDeliverDate(sdf.format(dealRecord.getDeliverDate()));
			dealRecordVOs.add(dealRecordVO);
		}
		return dealRecordVOs;
	}

}
