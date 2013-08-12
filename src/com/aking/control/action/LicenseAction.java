package com.aking.control.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Component;

import com.aking.model.constant.License;
import com.aking.util.security.LicenseService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Component
public class LicenseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void getMachineCode() throws Exception {
		HttpServletResponse response = ServletActionContext.getResponse();
		String result = "{\"data\":{\"machineCode\":\"" + License.machineCode + "\"},\"success\":\"true\",\"totalCount\":1}";
		try {
			System.out.println(result);
			response.setHeader("ContentType", "text/json");
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();
			pw.write(result);
			pw.flush();
			pw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public String registerServer() throws Exception {
		ActionContext context = ActionContext.getContext();
		Map<String, Object> paramMap = context.getParameters();
		Boolean regResult = LicenseService.registerServer(paramMap);
		// String result = "";
		if (regResult) {
			return "success";
			// result = "{success:true,info:'注册成功!'}";
		} else {
			return "failure";
			// result = "{success:false,info:'" + License.verifyResult + "'}";
		}
		// WriteResult.outputResult(result, ServletActionContext.getResponse());
	}

}
