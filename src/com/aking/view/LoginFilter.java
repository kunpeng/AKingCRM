package com.aking.view;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.aking.control.service.UserService;
import com.aking.model.constant.License;

public class LoginFilter implements Filter {
	// private String indexpath = "/";

	private String ignoreUrls;
	private String registerjsp;
	private String loginjsp;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request,
			ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String request_path = req.getRequestURI();
		if (!this.match(request_path, ignoreUrls)) {
			if (!License.isValid) {
				res.sendRedirect(registerjsp);
				return;
			}
			// 验证是否登录
			Boolean islogin = UserService.checkLogin(req.getSession());
			if (!islogin) {
				res.sendRedirect(loginjsp);
				// req.getRequestDispatcher(loginjsp).forward(req, res);
				return;
			}
		}

		// if (!this.match(request_path, ignoreUrls) && islogin) {
		// // res.sendRedirect(loginjsp);
		// req.getRequestDispatcher(loginjsp).forward(req, res);
		// return;
		// }
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		String path = filterConfig.getInitParameter("register");
		if (path != null && path.length() > 0) {
			registerjsp = path;
		}

		String urls = filterConfig.getInitParameter("ignoreUrls");
		if (urls != null && urls.length() > 0) {
			ignoreUrls = urls;
		}

		String login = filterConfig.getInitParameter("login");
		if (login != null && login.length() > 0) {
			loginjsp = login;
		}
	}

	private boolean match(String request_path,
			String urls) {
		// if (request_path.equalsIgnoreCase("/") || request_path.equalsIgnoreCase(indexpath)) {
		// return true;
		// }
		String[] tmp = urls.split(",");
		for (int i = 0; i < tmp.length; i++) {
			if (request_path.startsWith(tmp[i]))
				return true;
		}
		return false;
	}
}
