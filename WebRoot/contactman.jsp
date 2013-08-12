<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="contactManDialog"
	data-dojo-id="contactManDialog" title="编辑联系人信息">
	<form data-dojo-type="dijit/form/Form" id="contactManForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox"> <input type="hidden"
				name="contactmanid" value="" data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="manname">姓名</label></td>
					<td><input type="text" name="manname"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="isCharge">主联系人</label></td>
					<td><input name="isCharge"
						data-dojo-type="dijit/form/CheckBox" /></td>
				</tr>
				<tr>
					<td><label for="department">部门</label></td>
					<td><input type="text" name="department"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="position">职务</label></td>
					<td><input type="text" name="position"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="officetel">办公电话</label></td>
					<td><input type="text" name="officetel"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="phone">手机</label></td>
					<td><input type="text" name="phone"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="homePhone">家庭电话</label></td>
					<td><input type="text" name="homePhone"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="email">电子邮箱</label></td>
					<td><input type="text" name="email"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="fax">传真</label></td>
					<td><input type="text" name="fax"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="qq">QQ</label></td>
					<td><input type="text" name="qq"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="address">详细地址</label></td>
					<td colspan=3><input type="text" name="address"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="birthday">出生日期</label></td>
					<td><input type="text" name="birthday"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
					<td><label for="hobby">爱好</label></td>
					<td><input type="text" name="hobby"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveContactManButton">提交</button>
			<button type="button" id="cancelContactManButton">取消</button>
		</div>
	</form>
</div>
