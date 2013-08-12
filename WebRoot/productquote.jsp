<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="productQuoteDialog"
	data-dojo-id="productQuoteDialog" title="编辑客户产品报价">
	<form data-dojo-type="dijit/form/Form" id="productQuoteForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox"> <input type="hidden"
				name="productquoteid" value="" data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="quoteDate">报价日期</label></td>
					<td><input type="text" name="quoteDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="productquote_product">产品</label></td>
					<td><div id="productquote_product"></div></td>
				</tr>
				<tr>
					<td><label for="price">单价</label></td>
					<td><input type="text" name="price"
						data-dojo-type="dijit/form/NumberTextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td><label for="quotationNo">报价单号</label></td>
					<td><input type="text" name="quotationNo"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveProductQuoteButton">提交</button>
			<button type="button" id="cancelProductQuoteButton">取消</button>
		</div>
	</form>
</div>
