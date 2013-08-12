package com.aking.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.aking.dao.base.GenericDaoImpl;
import com.aking.model.subject.User;

@Component
public class UserDao extends GenericDaoImpl<User, Serializable> {

}
