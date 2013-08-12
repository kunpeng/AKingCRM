package com.aking.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PropertyUtil {
	/**
	 * 从Action中获取参数
	 * 
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> getRequestMap(Map<String, Object> paramMap) throws Exception {
		Map<String, Object> paramsMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : paramMap.entrySet()) {
			Object[] params = (Object[]) entry.getValue();
			if (params.length > 0) {
				paramsMap.put(entry.getKey(), params[0]);
			}
		}
		return paramsMap;
	}
}
