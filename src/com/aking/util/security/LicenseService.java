package com.aking.util.security;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Component;

import com.aking.model.constant.Constants;
import com.aking.model.constant.License;
import com.aking.util.PropertyFileUtil;
import com.aking.util.PropertyUtil;

@Component
public class LicenseService {
	// private static String certificatePath;
	// private static String propertiesPath;
	//
	// static {
	// try {
	// certificatePath = LicenseService.class.getResource("/").toURI().getPath() + "aking.cer";
	// propertiesPath = LicenseService.class.getResource("/").toURI().getPath() + "server.properties";
	// } catch (URISyntaxException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }

	public static boolean registerServer(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = PropertyUtil.getRequestMap(paramMap);
		// String machineCode = (String) paramsMap.get("machineCode");
		String licenseCode = (String) paramsMap.get("licenseCode");
		PropertyFileUtil.setProperty(Constants.propertiesPath, "licenseCode", licenseCode);
		return LicenseService.verifyRegisterCode();
	}

	public static boolean verifyRegisterCode() throws Exception {
		// String filePath = License.class.getResource("/").toURI().getPath() + "server.properties";
		try {
			if (Constants.propertiesPath.startsWith("/")) {
				Constants.propertiesPath = Constants.propertiesPath.substring(1);
			}
			Properties props = PropertyFileUtil.loadAllProps(Constants.propertiesPath);
			License.licenseCode = props.getProperty("licenseCode");
			// String certificatePath = LicenseService.class.getResource("/").toURI().getPath() + "ExcelReport.cer";
			String currentMachineCode = MachineCode.getMachineCode();
			License.machineCode = currentMachineCode;
			String registerCode = License.licenseCode;
			if (registerCode == null || registerCode.length() <= 0) {
				License.isValid = false;
				License.verifyResult = "未检测到注册码!";
				// ServerProperties.setMachineCode(currentMachineCode);
				return false;
			}
			byte[] encryptCode = Coder.decryptBASE64(registerCode);
			byte[] decryptCode = RSACoder.decryptByPublicKey(encryptCode, Constants.certificatePath);
			String registerInfo = new String(decryptCode);
			Map<String, String> propertyMap = new HashMap<String, String>();
			String[] infos = registerInfo.split("\r\n");
			for (String info : infos) {
				String[] property = info.split("=");
				propertyMap.put(property[0], property[1]);
				// System.out.println(info);
			}
			String machineCode = "";
			machineCode = propertyMap.get("machineCode");
			if (!machineCode.equals(currentMachineCode)) {
				License.isValid = false;
				License.verifyResult = "特征码不匹配!";
				// ServerProperties.setMachineCode(currentMachineCode);
				return false;
			}
			// 验证是否过期
			String versionType = propertyMap.get("versionType");
			if (versionType.equals("permanent")) {
				License.isValid = true;
				License.verifyResult = "永久注册码!";
				return true;
			}
			// 试用版本检测是否到期
			String registerDate = propertyMap.get("registerDate");
			int timeLimit = Integer.valueOf(propertyMap.get("timeLimit"));

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date regDate = sdf.parse(registerDate);
			Calendar expireCalendar = Calendar.getInstance();
			expireCalendar.setTime(regDate);
			expireCalendar.add(Calendar.DAY_OF_MONTH, timeLimit);

			Calendar calendar = Calendar.getInstance();
			long dateGap = expireCalendar.getTimeInMillis() - calendar.getTimeInMillis();
			if (dateGap <= 0) {
				License.isValid = false;
				License.verifyResult = "注册码已过期!";
				// ServerProperties.setMachineCode(currentMachineCode);
				return false;
			}
			License.isValid = true;
			License.verifyResult = "注册码验证通过!";
			return true;
		} catch (Exception e) {
			License.isValid = false;
			License.verifyResult = "注册码验证失败!";
			return false;
		}
	}
}
