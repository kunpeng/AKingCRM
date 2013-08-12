package com.aking.control.service;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aking.model.constant.Constants;
import com.aking.model.constant.License;
import com.aking.util.security.LicenseService;

@Component
public class SystemInit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Logger logger = Logger.getLogger("系统初始化");

	@Autowired
	private SystemFunctionService systemFunctionService;

	@Override
	public void init() {

		// 当前路径
		Constants.PROJECT_PATH = this.getClass().getResource("/").getPath();

		// 验证注册码
		try {
			this.verifyReg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 初始化系统
		// TODO 暂时人工干预
		// 第一次安装系统时，初始化数据
		// logger.log(Level.INFO, "初始化数据库");
		// DatabaseInit databaseInit = new DatabaseInit();
		// databaseInit.initDatabase();

		// 读取功能配置文件,初始化功能配置
		try {
			// systemFunctionService.loadFunctionFromMap();
		} catch (Exception e) {
			logger.log(Level.INFO, "加载功能配置出错！");
			logger.log(Level.DEBUG, e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 验证注册码
	 * 
	 * @throws Exception
	 */
	protected void verifyReg() throws Exception {
		try {
			Boolean verifyResult = LicenseService.verifyRegisterCode();
			if (verifyResult) {
				logger.log(Level.INFO, "注册码验证成功!");
			} else {
				logger.log(Level.INFO, License.verifyResult);
				logger.log(Level.INFO, "当前特征码:" + License.machineCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
