/**
 * 
 */
package com.aking.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Administrator
 * 
 */
public class PropertyFileUtil {
	/**
	 * 读取属性文件中的所有内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static Properties loadAllProps(String filePath) {
		Properties props = new Properties();
		try {
			FileInputStream fs = new FileInputStream(filePath);
			// System.out.println(filePath);
			InputStream is = new BufferedInputStream(fs);
			props.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	/**
	 * 获取指定的属性值
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public static String loadPropByKey(String filePath,
			String key) {
		Properties props = loadAllProps(filePath);
		String propValue = props.getProperty(key);
		return propValue;
	}

	/**
	 * 设置指定的属性值
	 * 
	 * @param filePath
	 * @param key
	 * @param value
	 */
	public static void setProperty(String filePath,
			String key,
			String value) {
		Properties props = loadAllProps(filePath);
		props.setProperty(key, value);
		try {
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			props.store(fos, "CRM系统属性配置");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
