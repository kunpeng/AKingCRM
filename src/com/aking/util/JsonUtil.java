package com.aking.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class JsonUtil {

	public static String obj2Json(String jsonName,
			Object obj) throws Exception {
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		// JSONObject jsonArray = JSONObject.fromObject(obj, config);
		JSONObject jsonResult = new JSONObject();

		jsonResult = JSONObject.fromObject(jsonResult.element(jsonName, obj), config);

		return jsonResult.toString();
	}

	public static String obj2json(Object[] obj) throws Exception {
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		JSONArray jsonArray = new JSONArray();
		jsonArray = JSONArray.fromObject(obj, config);
		return jsonArray.toString();
	}
}
