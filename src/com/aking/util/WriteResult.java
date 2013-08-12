package com.aking.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.aking.control.action.ExtStore;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

public class WriteResult {
	public static void outputResult(String result,
			HttpServletResponse response) {
		// JsonConfig config = new JsonConfig();
		// config.setIgnoreDefaultExcludes(false);
		// config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		// JSONObject json = JSONObject.fromObject(o, config);
		try {
			System.out.println(result);
			response.setHeader("ContentType", "text/json");
			response.setCharacterEncoding("utf-8");

			// 情况1：正常，浏览器按utf-8方式查看
			// response.setContentType("text/html; charset=gbk");
			// response.setCharacterEncoding("utf-8");
			// 情况2：正常，浏览器按简体中文方式查看
			// response.setContentType("text/html; charset=utf-8");
			// response.setCharacterEncoding("gbk");

			PrintWriter pw = response.getWriter();
			pw.write(result);
			pw.flush();
			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public static void outPutListResult(List<?> o,
			HttpServletResponse response) {
		ExtStore extStore = new ExtStore(o.size(), o);

		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false);
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);

		JSONObject json = JSONObject.fromObject(extStore, config);
		try {
			System.out.println(json.toString());
			response.setHeader("ContentType", "text/json");
			response.setCharacterEncoding("utf-8");

			// 情况1：正常，浏览器按utf-8方式查看
			// response.setContentType("text/html; charset=gbk");
			// response.setCharacterEncoding("utf-8");
			// 情况2：正常，浏览器按简体中文方式查看
			// response.setContentType("text/html; charset=utf-8");
			// response.setCharacterEncoding("gbk");

			PrintWriter pw = response.getWriter();
			pw.write(json.toString());
			pw.flush();
			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
