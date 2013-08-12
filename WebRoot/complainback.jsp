<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="complainBackDialog"
	data-dojo-id="complainBackDialog" title="编辑联系人信息">
	<form data-dojo-type="dijit/form/Form" id="complainBackForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox"> <input type="hidden"
				name="complainbackid" value="" data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="complainDate">回访日期</label></td>
					<td><input type="text" name="complainDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="receiveUser">回访人员</label></td>
					<td><div id="receiveUser"></div></td>
				</tr>
				<tr>
					<td><label for="complainContent">回访内容</label></td>
					<td><input type="text" name="complainContent"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="disposeResult">回访结果</label></td>
					<td><input type="text" name="disposeResult"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="disposeDate">处理日期</label></td>
					<td><input type="text" name="disposeDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="disposeUser">处理人员</label></td>
					<td><div id="disposeUser"></div></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveComplainBackButton">提交</button>
			<button type="button" id="cancelComplainBackButton">取消</button>
		</div>
	</form>
</div>
