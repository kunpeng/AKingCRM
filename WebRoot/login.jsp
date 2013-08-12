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
<BODY>
	<DIV id=loginbox align="center" style="padding-top: 10%;">
		<form action="user/userlogin.do" method="post" name="loginform"
			id="loginform">
			<TABLE cellSpacing=0 cellPadding=0 width=720 border=0>
				<TBODY>
					<TR>
						<TD width=30><IMG height=258 src="images/login_img01.gif"
							width=30></TD>
						<TD width=364 background="images/login_bg01.gif">
							<TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
								<TR>
									<TD vAlign=top width=330>
										<TABLE class=font_9p cellSpacing=2 cellPadding=2 width=350
											align=center border=0>
											<TBODY>
												<TR vAlign=top>
													<TD class=title height=25>&nbsp;</TD>
													<TD class=word_10p height=25><FONT color=#00458a>用户登录</FONT></TD>
												</TR>
												<TR>
													<TD class=word><FONT color=#000000>用户名</FONT></TD>
													<TD><INPUT style="width: 270px;" id="username"
														name="username" onKeyDown="nameKeyDown(event);"></TD>
												</TR>
												<TR>
													<TD class=word><FONT color=#000000>密码</FONT></TD>
													<TD><INPUT type=password style="width: 270px;"
														id="password" name="password"
														onKeyDown="passKeyDown(event);"></TD>
												</TR>
												<tr>
													<td></td>
													<td>
														<%
															if (loginerrinfo != null && loginerrinfo.length() != 0) {
														%> <label><font color="red" size="1"><%=loginerrinfo%></font></label>
														<%
															}
														%>
													</td>
												</tr>
												<TR>
													<TD>&nbsp;</TD>
													<TD><img id="img_login" src="images/login.png"
														name="img_Login" style="cursor: pointer" onClick="login()"></TD>
												</TR>
											</TBODY>
										</TABLE>
									</TD>
								</TR>
								</TBODY>
							</TABLE>
						</TD>
						<TD vAlign=top background="images/login_bg02.gif"></TD>
						<TD width=30><IMG height=258 src="images/login_img02.gif"
							width=30></TD>
					</TR>
			</TABLE>
		</FORM>
	</DIV>
</BODY>
</html>