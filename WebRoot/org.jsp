<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>机构基础信息</h1>
<form data-dojo-type="dijit/form/Form" id="orgForm" method="" action="">
	<input type="hidden" name="orgid" value=""
		data-dojo-type="dijit/form/TextBox"> <input type="hidden"
		name="parentid" value="" data-dojo-type="dijit/form/TextBox">
	<table>
		<tr>
			<td><label for="parentname">上级机构</label></td>
			<td><input type="text" name="parentname"
				data-dojo-type="dijit/form/ValidationTextBox" required="true"
				readonly style="width: 100%;" /></td>
		</tr>
		<tr>
			<td><label for="name">机构名称</label></td>
			<td><input type="text" name="name"
				data-dojo-type="dijit/form/ValidationTextBox" required="true"
				style="width: 100%" /></td>
		</tr>
		<tr>
			<td><label for="code">机构代码</label></td>
			<td><input type="text" name="code"
				data-dojo-type="dijit/form/ValidationTextBox" required="true" /></td>
		</tr>
	</table>
</form>
<div>
	<button type="button" id="saveOrgButton">保存</button>
	<button type="button" id="cancelOrgButton">取消</button>
</div>