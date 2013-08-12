package com.aking.control.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDao;
import com.aking.dao.impl.ProductDao;
import com.aking.model.subject.Customer;
import com.aking.model.subject.Product;
import com.aking.util.BeanUtil;
import com.aking.util.PropertyUtil;
import com.aking.util.ReflectUtil;
import com.aking.view.vo.ProductVO;

@Component
public class ProductService extends BaseService<Product, String> {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CustomerService customerService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected GenericDao getGenricDao() {
		return productDao;
	}

	public List<ProductVO> loadByCustomer(String customerId) throws Exception {
		String hql = "From Product p where p.customer = '" + customerId + "'";
		List<Product> products = productDao.find(hql);
		List<ProductVO> productVOs = new ArrayList<ProductVO>();
		for (Product product : products) {
			ProductVO productVO = new ProductVO();
			BeanUtil.copyProperties(product, productVO);
			productVO.setProductId(product.getId());
			productVOs.add(productVO);
		}
		return productVOs;
	}

	public Boolean saveProduct(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		Product product = null;
		String id = (String) paramsMap.get("id");
		String customerId = (String) paramsMap.get("customerId");
		if (id == null || id.equals("")) {
			product = new Product();
		} else {
			product = this.getById(id);
		}
		ReflectUtil.convertMapToJavaBean(product, paramsMap);
		if (customerId != null) {
			Customer customer = customerService.getById(customerId);
			product.setCustomer(customer);
		}
		productDao.save(product);
		return true;
	}

	public Boolean delProductByID(String ids) throws Exception {
		String[] idsArray = ids.split(",");
		List<Product> products = new ArrayList<Product>();
		for (String id : idsArray) {
			Product product = productDao.get(id);
			products.add(product);
		}
		productDao.deleteAll(products);
		return true;
	}

	public ProductVO getProductByID(String id) throws Exception {
		Product product = productDao.get(id);
		List<Product> products = new ArrayList<Product>();
		products.add(product);
		List<ProductVO> productVOs = this.copy2VO(products);
		if (productVOs.size() > 0) {
			return productVOs.get(0);
		}
		return null;
	}

	private List<ProductVO> copy2VO(List<Product> products) throws Exception {
		List<ProductVO> productVOs = new ArrayList<ProductVO>();
		for (Product product : products) {
			ProductVO productVO = new ProductVO();
			BeanUtil.copyProperties(product, productVO);
			productVO.setProductId(product.getId());
			productVOs.add(productVO);
		}
		return productVOs;
	}

}
