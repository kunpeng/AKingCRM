<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="userDialog"
	data-dojo-id="userDialog" title="修改个人信息">
	<div class="dijitDialogPaneContentArea">
		<form data-dojo-type="dijit/form/Form" id="updateUserForm" method=""
			action="">
			<input type="hidden" name="userid" value=""
				data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="usercode">用户代码</label></td>
					<td><input type="text" name="usercode" readonly
						data-dojo-type="dijit/form/ValidationTextBox" required="true">
				</tr>
				<tr>
					<td><label for="username">用户名称</label></td>
					<td><input type="text" name="username" readonly
						data-dojo-type="dijit/form/ValidationTextBox" required="true"
						style="width: 100%" /></td>
				</tr>
				<tr>
					<td><label for="olduserpass">原密码</label></td>
					<td><input type="oldpassword" name="olduserpass"
						data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
				</tr>
				<tr>
					<td><label for="userpass">新密码</label></td>
					<td><input type="password" name="userpass"
						data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
				</tr>
				<tr>
					<td><label for="userpassconfirm">确认新密码</label></td>
					<td><input type="password" name="userpassconfirm"
						data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="dijitDialogPaneActionBar">
		<button type="button" id="updateUserBtn">确定</button>
	</div>
</div>