<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="afterVisitDialog"
	data-dojo-id="afterVisitDialog" title="编辑联系人信息">
	<form data-dojo-type="dijit/form/Form" id="afterVisitForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox"> <input type="hidden"
				name="visitid" value="" data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="visitDate">回访日期</label></td>
					<td><input type="text" name="visitDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="visitUser">回访人员</label></td>
					<td><div id="visitUser"></div></td>
				</tr>
				<tr>
					<td><label for="content">回访内容</label></td>
					<td><input type="text" name="content"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="result">回访结果</label></td>
					<td><input type="text" name="result"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveAfterVisitButton">提交</button>
			<button type="button" id="cancelAfterVisitButton">取消</button>
		</div>
	</form>
</div>
