package com.aking.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDaoImpl;
import com.aking.model.subject.Customer;

/**
 * @comment:
 * @author:Tangkp
 * @date:2011-3-7
 * @version:1.0
 */
@Component
public class CustomerDao extends GenericDaoImpl<Customer, Serializable> {

	// @Override
	// protected Class<Customer> getEntityClass() {
	// // TODO Auto-generated method stub
	// return Customer.class;
	// }

}
