package com.aking.control.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.ProductQuoteDao;
import com.aking.model.subject.Customer;
import com.aking.model.subject.Product;
import com.aking.model.subject.ProductQuote;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.ProductQuoteVO;

@Component
public class ProductQuoteService extends BaseService<ProductQuote, String> {

	@Autowired
	private ProductQuoteDao productQuoteDao;

	@Autowired
	private ProductService productService;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return productQuoteDao;
	}

	public List<ProductQuoteVO> loadByCustomer(String customerId) throws Exception {
		String hql = "From ProductQuote p where p.customer = '" + customerId + "'";
		List<ProductQuote> productQuotes = productQuoteDao.find(hql);
		List<ProductQuoteVO> productQuoteVOs = new ArrayList<ProductQuoteVO>();
		for (ProductQuote productQuote : productQuotes) {
			ProductQuoteVO productQuoteVO = new ProductQuoteVO();
			BeanUtil.copyProperties(productQuote, productQuoteVO);
			productQuoteVO.setProductname(productQuote.getProduct().getName());
			productQuoteVO.setStandard(productQuote.getProduct().getStandard());
			productQuoteVO.setType(productQuote.getProduct().getType());
			// SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			// productQuoteVO.setQuoteDate(sdf.format(productQuote.getQuoteDate()));
			productQuoteVO.setQuoteDate(productQuote.getQuoteDate());
			productQuoteVOs.add(productQuoteVO);
		}
		return productQuoteVOs;
	}

	public Boolean saveQuote(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		ProductQuote productQuote = null;
		String id = (String) paramsMap.get("id");
		String productId = (String) paramsMap.get("productId");
		String customerId = (String) paramsMap.get("customerId");
		if (id == null || id.equals("")) {
			productQuote = new ProductQuote();
		} else {
			productQuote = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(productQuote, paramsMap);
		if (productId != null) {
			Product product = productService.getById(productId);
			productQuote.setProduct(product);
		}
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			productQuote.setCustomer(customer);
		}
		productQuoteDao.save(productQuote);
		return true;
	}

	public Boolean delQuoteByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<ProductQuote> productQuotes = new ArrayList<ProductQuote>();
		for (String id : idsArray) {
			ProductQuote productQuote = productQuoteDao.get(id);
			productQuotes.add(productQuote);
		}
		productQuoteDao.deleteAll(productQuotes);
		return true;
	}

	public ProductQuoteVO getQuoteByID(String id) throws Exception {
		ProductQuote productQuote = productQuoteDao.get(id);
		List<ProductQuote> productQuotes = new ArrayList<ProductQuote>();
		productQuotes.add(productQuote);
		List<ProductQuoteVO> productQuoteVOs = this.copy2VO(productQuotes);
		if (productQuoteVOs.size() > 0) {
			return productQuoteVOs.get(0);
		}
		return null;
	}

	private List<ProductQuoteVO> copy2VO(List<ProductQuote> productQuotes) throws Exception {
		List<ProductQuoteVO> productQuoteVOs = new ArrayList<ProductQuoteVO>();
		for (ProductQuote productQuote : productQuotes) {
			ProductQuoteVO productQuoteVO = new ProductQuoteVO();
			BeanUtil.copyProperties(productQuote, productQuoteVO);
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			productQuoteVO.setQuoteDate(sdf.format(productQuote.getQuoteDate()));
			productQuoteVO.setProductname(productQuote.getProduct().getName());
			productQuoteVO.setStandard(productQuote.getProduct().getStandard());
			productQuoteVO.setType(productQuote.getProduct().getType());
			productQuoteVO.setProductId(productQuote.getProduct().getId());
			productQuoteVOs.add(productQuoteVO);
		}
		return productQuoteVOs;
	}

}
