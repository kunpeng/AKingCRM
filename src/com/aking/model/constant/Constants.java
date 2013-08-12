package com.aking.model.constant;

import java.net.URISyntaxException;

public class Constants {
	public static String PROJECT_PATH = null;
	// 初始化脚本路径
	public static String INITSQLPATH = null;
	// 数字签名证书路径
	public static String certificatePath = null;
	// 系统配置文件路径
	public static String propertiesPath = null;
	// 菜单配置文件路径
	public static String FUNCTIONMAPPATH = null;

	static {
		try {
			INITSQLPATH = Constants.class.getResource("/").toURI().getPath() + "initsql.txt";
			certificatePath = Constants.class.getResource("/").toURI().getPath() + "aking.cer";
			propertiesPath = Constants.class.getResource("/").toURI().getPath() + "server.properties";
			FUNCTIONMAPPATH = Constants.class.getResource("/").toURI().getPath() + "FunctionMap.xml";
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	public static String defaultAdminCode = "admin";
}
