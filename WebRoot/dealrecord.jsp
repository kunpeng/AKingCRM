<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="dealRecordDialog"
	data-dojo-id="dealRecordDialog" title="编辑联系人信息">
	<form data-dojo-type="dijit/form/Form" id="dealRecordForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox"> <input type="hidden"
				name="recordid" value="" data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="dealDate">成交日期</label></td>
					<td><input type="text" name="dealDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
					<td><label for="dealrecord_product">产品名称</label></td>
					<td><div id="dealrecord_product"></div></td>
				</tr>
				<tr>
					<td><label for="quantity">数量</label></td>
					<td><input type="text" name="quantity"
						data-dojo-type="dijit/form/NumberTextBox" style="width: 100%"></td>
					<td><label for="price">单价</label></td>
					<td><input type="text" name="price"
						data-dojo-type="dijit/form/NumberTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="businessCost">业务量</label></td>
					<td><input type="text" name="businessCost"
						data-dojo-type="dijit/form/NumberTextBox" style="width: 100%"></td>
					<td><label for="invoice">开票情况</label></td>
					<td><input type="text" name="invoice"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="payCondition">付款情况</label></td>
					<td><input type="text" name="payCondition"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="deliverDate">发货日期</label></td>
					<td><input type="text" name="deliverDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="memo">备注</label></td>
					<td colspan=3><input type="text" name="memo"
						data-dojo-type="dijit/form/Textarea" style="width: 100%"></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveDealRecordButton">提交</button>
			<button type="button" id="cancelDealRecordButton">取消</button>
		</div>
	</form>
</div>
