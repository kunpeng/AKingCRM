<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String loginerrinfo = (String) session.getAttribute("errinfo");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户登录</title>
<link rel="stylesheet" href="css/login.css" />
</head>
<script type="text/javascript">
	window.onload = function() {
		document.getElementById("username").focus();
	};

	function passKeyDown(event) {
		var evt = event ? event : (window.event ? window.event : null);
		if (evt.keyCode == 13) {
			document.getElementById("loginform").submit();
		}
	}

	function nameKeyDown(event) {
		var evt = event ? event : (window.event ? window.event : null);
		if (evt.keyCode == 13) {
			document.getElementById("password").focus();
		}
	}

	function login() {
		document.getElementById("loginform").submit();
	}
</script>
<body>
	<div id="logincontainer">
		<div id="login-top" style="visibility: hidden;">客户管理系统</div>
		<div id="login-left"></div>
		<div id="login-right">
			<form id="loginform" name="loginform" action="user/userlogin.do">
				<%
					if (loginerrinfo != null && loginerrinfo.length() != 0) {
				%>
				<div style="width: 100%; text-align: center;">
					<label><font color="red" size="1"><%=loginerrinfo%></font></label>
				</div>
				<%
					}
				%>
				<div class="field" style="margin-bottom: 5px">
					<label> 账 号: </label> <input type="text" name="username"
						id="username" title="请输入用户名称" value="" maxlength="15" tabindex="1"
						onKeyDown="nameKeyDown(event);" />
				</div>
				<div class="field" style="margin-bottom: 5px">
					<label> 密 码: </label> <input type="password" name="password"
						id="password" maxlength="15" tabindex="2"
						onKeyDown="passKeyDown(event);" />
				</div>
				<div class="submit" style="text-align: center;">
					<img id="img_login" src="images/login.png" name="img_Login"
						style="cursor: pointer" onClick="login()">
				</div>
			</form>
		</div>
	</div>
</body>
</html>