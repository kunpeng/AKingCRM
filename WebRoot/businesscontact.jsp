<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="contactDialog"
	data-dojo-id="contactDialog" title="编辑业务联系">
	<form data-dojo-type="dijit/form/Form" id="contactForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox"> <input type="hidden"
				name="contactid" value="" data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="activityTheme">洽谈主题</label></td>
					<td><div id="activityTheme" style="width: 100%"></div></td>
					<td><label for="contactWay">洽谈方式</label></td>
					<td><div id="contactWay" style="width: 100%"></div>
				</tr>
				<tr>
					<td><label for="contactDate">洽谈日期</label></td>
					<td colspan=2><input type="text" name="contactDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="chargeUser">跟进人</label></td>
					<td><div id="chargeUser"></div></td>
					<td><label for="recordUser">登记人员</label></td>
					<td><div id="recordUser"></div></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveContactButton">提交</button>
			<button type="button" id="cancelContactButton">取消</button>
		</div>
	</form>
</div>
