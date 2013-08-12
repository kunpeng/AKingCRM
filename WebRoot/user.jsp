<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>用户基础信息</h1>
<form data-dojo-type="dijit/form/Form" id="userForm" method="" action="">
	<input type="hidden" id="userid" name="userid" value=""
		data-dojo-type="dijit/form/TextBox"> <input type="hidden"
		id="orgid" name="orgid" value="" data-dojo-type="dijit/form/TextBox">
	<table>
		<tr>
			<td><label for="org">所属机构</label></td>
			<td><input type="text" name="org"
				data-dojo-type="dijit/form/ValidationTextBox" required="true"
				readonly style="width: 100%;" /></td>
		</tr>
		<tr>
			<td><label for="usercode">用户代码</label></td>
			<td><input type="text" name="usercode"
				data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
			<td><label for="username">用户名称</label></td>
			<td><input type="text" name="username"
				data-dojo-type="dijit/form/ValidationTextBox" required="true"
				style="width: 100%" /></td>
		</tr>
		<tr>
			<td><label for="userpass">用户密码</label></td>
			<td><input type="password" name="userpass"
				data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
			<td><label for="userpassconfirm">确认密码</label></td>
			<td><input type="password" name="userpassconfirm"
				data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
		</tr>
		<tr>
			<td><label for="roletype">用户类型</label></td>
			<td colspan=2><select type="text" name="roletype"
				data-dojo-type="dijit/form/Select" required="true"
				style="width: 100%">
					<option value="NORMAL" selected>普通用户</option>
					<option value="ADMIN">管理员</option>
			</select></td>
		</tr>
		<tr>
			<td>是否启动</td>
			<td colspan=3><input name="isvalid" type="checkbox" checked></td>
		</tr>
	</table>
</form>
<div>
	<button type="button" id="saveUserButton">保存</button>
	<button type="button" id="cancelUserButton">取消</button>
</div>