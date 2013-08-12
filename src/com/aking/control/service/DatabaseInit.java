package com.aking.control.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.aking.model.constant.Constants;
import com.aking.util.PropertyFileUtil;

public class DatabaseInit extends HibernateDaoSupport {

	/**
	 * 初始化数据库
	 * 
	 * @throws Exception
	 */
	public void initDatabase() {
		try {
			// 判断系统是否需要初始化数据库
			String startserver_initdata = PropertyFileUtil.loadPropByKey(Constants.propertiesPath, "startserver_initdata");
			if (!startserver_initdata.equalsIgnoreCase("true")) {
				return;
			}

			// 调用Hibernate的数据库执行功能
			Object obj = this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
				public Object doInHibernate(Session arg0) throws HibernateException, SQLException {
					Connection conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
					return conn.createStatement();
				}
			});
			Statement st = (Statement) obj;
			// 执行初始化操作
			List<String> sqlList = this.readInitTxt();
			for (String sql : sqlList) {
				st.execute(sql);
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 标记系统已初始化
		PropertyFileUtil.setProperty(Constants.propertiesPath, "startserver_initdata", "false");
	}

	/**
	 * 读取初始化文件
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<String> readInitTxt() throws Exception {
		List<String> sqlList = new ArrayList<String>();
		String sqlpath = Constants.INITSQLPATH;
		File sqlFile = new File(sqlpath);
		BufferedReader br = new BufferedReader(new FileReader(sqlFile));
		String sqlstr = null;
		sqlstr = br.readLine();
		while (sqlstr != null) {
			if (sqlstr.length() > 0 && !sqlstr.startsWith("#")) {
				sqlList.add(sqlstr);
			}
			sqlstr = br.readLine();
		}
		br.close();
		return sqlList;
	}
}
