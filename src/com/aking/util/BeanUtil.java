package com.aking.util;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtil {

	/**
	 * 拷贝obj1的属性至obj2中
	 * 
	 * @param sourceObj
	 * @param targetObj
	 * @return
	 * @throws Exception
	 */
	public static Object copyProperties(Object sourceObj,
			Object targetObj) throws Exception {

		Method[] origDescriptors = sourceObj.getClass().getMethods();
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if (name != null && name.startsWith("set")) {
				name = name.substring("set".length());
				String getProperty = name;
				String strStart = name.substring(0, 1);
				String strEnd = name.substring(1);
				name = strStart.toLowerCase().concat(strEnd);
				String property = "get".concat(getProperty);
				Method method = null;
				try {
					method = sourceObj.getClass().getMethod(property, null);
				} catch (Exception e) {
					property = "is".concat(getProperty);
					method = sourceObj.getClass().getMethod(property, null);
				}
				Class<?> clazz = method.getReturnType();

				if (method != null) {
					Object obj = method.invoke(sourceObj, null);
					if (clazz.equals(Float.class) || clazz.equals(float.class)) {
						if (obj != null) {
							String val = String.valueOf(obj);
							if (!val.equalsIgnoreCase("")) {
								float dval = Float.valueOf(val).floatValue();
								if (dval != 0 && sourceObj != null) {
									BeanUtils.copyProperty(targetObj, name, obj);
								}
							}
						}
						// if (obj1 != null)
						// BeanUtils.copyProperty(obj2, name, obj);
					} else if (clazz.equals(String.class)) {
						if (obj != null) {
							String val = String.valueOf(obj);
							if (!val.equalsIgnoreCase("") && sourceObj != null) {
								BeanUtils.copyProperty(targetObj, name, obj);
							}
						}
					} else if (clazz.equals(int.class) || clazz.equals(Integer.class)) {
						if (obj != null) {
							String val = String.valueOf(obj);
							if (!val.equalsIgnoreCase("")) {
								int dval = Double.valueOf(val).intValue();
								if (dval != 0 && sourceObj != null) {
									BeanUtils.copyProperty(targetObj, name, obj);
								}
							}
						}
					} else if (clazz.equals(long.class) || clazz.equals(Long.class)) {
						if (obj != null) {
							String val = String.valueOf(obj);
							if (!val.equalsIgnoreCase("")) {
								long dval = Double.valueOf(val).longValue();
								if (dval != 0 && sourceObj != null) {
									BeanUtils.copyProperty(targetObj, name, obj);
								}
							}
						}
					} else if (clazz.equals(double.class) || clazz.equals(Double.class)) {
						if (obj != null) {
							String val = String.valueOf(obj);
							if (!val.equalsIgnoreCase("")) {
								double dval = Double.valueOf(val).doubleValue();
								if (dval != 0 && sourceObj != null) {
									BeanUtils.copyProperty(targetObj, name, obj);
								}
							}
						}
					} else if (clazz.equals(Boolean.class) || clazz.equals(boolean.class)) {
						if (obj != null) {
							String val = String.valueOf(obj);
							boolean dval = Boolean.valueOf(val);
							if (dval && sourceObj != null) {
								BeanUtils.copyProperty(targetObj, name, obj);
							}
						}
					} else if (clazz.isEnum()) {
						Method methods = null;
						try {
							methods = targetObj.getClass().getMethod(origDescriptors[i].getName(), obj.getClass());
						} catch (Exception e) {
						}
						if (methods != null && obj != null)
							methods.invoke(targetObj, obj);
					}
				}
			}
		}
		return targetObj;
	}

	// public static List<?> copyProperties(List<Object> obj1,
	// Class<?> clazz) throws Exception {
	// clazz.getClass();
	// List<clazz.getClass()> copyList = new ArrayList<clazz>();
	// for (Object o : obj1) {
	//
	// }
	// }
}
