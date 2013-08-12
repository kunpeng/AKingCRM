<%@page import="com.aking.model.constant.License"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>系统注册</title>
<style type="text/css">
body {
	font-size: 12px;
}
</style>
</head>
<body>
	<div style="margin-top: 200px; margin-left: 200px; width: 60%">
		<form action="license/registerServer.do">
			<table>
				<tr>
					<td align="right">申请号：</td>
					<td style="color: blue"><%=License.machineCode%></td>
				</tr>
				<tr>
					<td valign="top" align="right">注册步骤：</td>
					<td>1.在产品申请页面(http://www.isoftsky.com/?p=226)中留言 <br>留言格式如下：<br>
						申请号：XXXXXXXXXXXX<br> 申请邮箱：XXXXXXX@XXX.XXX<br>
						2.您收到授权码以后，请将此授权码填入下面的输入框中，通过点击注册按钮完成注册！
					</td>
				</tr>
				<tr>
					<td valign="top">版本说明：</td>
					<td>临时授权码可免费申请，默认有效期为一个月！<br>授权码到期后，请重新申请！<br> <font
						color="red">初始用户/初始密码：admin/isoftsky;cs/cs1</font>
					</td>
				</tr>
				<tr>
					<td valign="top" align="right">授权码：</td>
					<td><textarea name="licenseCode" rows="5" cols="70"></textarea>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2"><input type="submit" value="注册"> <input
						type="reset" value="清空"></td>
				</tr>
			</table>
			<!-- <div style="text-align: center;">
				<input type="submit" value="注册"> <input type="reset"
					value="清空">
			</div>
			 -->
		</form>
	</div>
</body>
</html>