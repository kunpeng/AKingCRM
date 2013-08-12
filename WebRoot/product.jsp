<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="productDialog"
	data-dojo-id="productDialog" title="编辑联系人信息">
	<form data-dojo-type="dijit/form/Form" id="productForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox"> <input type="hidden"
				name="productid" value="" data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="productname">品名</label></td>
					<td><input type="text" name="productname"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="standard">规格</label></td>
					<td><input type="text" name="standard"
						data-dojo-type="dijit/form/TextBox" style="width: 100%" /></td>
				</tr>
				<tr>
					<td><label for="type">型号</label></td>
					<td><input type="text" name="type"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td><label for="productAbility">生产能力</label></td>
					<td><input type="text" name="productAbility"
						data-dojo-type="dijit/form/NumberTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="stockQuantity">年进货量</label></td>
					<td colspan=3><input type="text" name="stockQuantity"
						data-dojo-type="dijit/form/NumberTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="memo">备注</label></td>
					<td colspan=3><input type="text" name="memo"
						data-dojo-type="dijit/form/Textarea" style="width: 100%"></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveProductButton">提交</button>
			<button type="button" id="cancelProductButton">取消</button>
		</div>
	</form>
</div>
