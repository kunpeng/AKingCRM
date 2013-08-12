<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/Dialog" id="newCustomerDialog"
	data-dojo-id="newCustomerDialog" title="新增用户">
	<form data-dojo-type="dijit/form/Form" id="customerForm" method=""
		action="">
		<div class="dijitDialogPaneContentArea">
			<input type="hidden" id="customerid" name="customerid" value=""
				data-dojo-type="dijit/form/TextBox">
			<table>
				<tr>
					<td><label for="customername">客户名称</label></td>
					<td colspan=3><input type="text" name="customername"
						data-dojo-type="dijit/form/ValidationTextBox" required="true"
						style="width: 100%" /></td>
					<td><label>助记码</label></td>
					<td><input type="text" name="customercode"
						data-dojo-type="dijit/form/ValidationTextBox" required="true">
				</tr>
				<tr>
					<td>客户类型</td>
					<td><div id="customertype" style="width: 100%"></div></td>
					<td>行业类别</td>
					<td><div id="category"></div></td>
					<td>客户来源</td>
					<td><div id="customersource"></div></td>
				</tr>
				<tr>
					<td>国家</td>
					<td><div id="country"></div> <!-- <input data-dojo-type="dijit/form/FilteringSelect"
						value="111" data-dojo-type="store:countryStore,serachAttr:'name'"
						id="country"> --></td>
					<td>省份</td>
					<td><div id="province"></div></td>
					<td>城市</td>
					<td><div id="city"></div></td>
				</tr>
				<tr>
					<td>电话</td>
					<td colspan="2"><input type="text" name="officetel"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td>邮编</td>
					<td colspan="2"><input name="postcode"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td>通讯地址</td>
					<td colspan="5"><input name="address"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td>传真</td>
					<td><input name="fax" data-dojo-type="dijit/form/TextBox"
						style="width: 100%"></td>
					<td>电子邮箱</td>
					<td><input name="email"
						data-dojo-props="regExp:'\\w+@\\w+\\.{1}\\w+',invalidMessage:'邮箱地址不合法'"
						data-dojo-type="dijit/form/ValidationTextBox" style="width: 100%"></td>
					<td>网址</td>
					<td><input name="homepage" data-dojo-type="dijit/form/TextBox"
						style="width: 100%"></td>
				</tr>
				<tr>
					<td>法人代表</td>
					<td colspan="2"><input name="represent"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td>税号</td>
					<td colspan="2"><input name="tax"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td>开户银行</td>
					<td colspan="2"><input name="bank"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
					<td>银行账号</td>
					<td colspan="2"><input name="bankAccount"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td>主营产品</td>
					<td colspan="5"><input name="mainProduct"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td>客户需求</td>
					<td colspan="5"><input name="demand"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td>其他信息</td>
					<td colspan="5"><input name="memo"
						data-dojo-type="dijit/form/TextBox" style="width: 100%"></td>
				</tr>
				<tr>
					<td>是否启用</td>
					<td><input name="isvalid" type="checkbox" checked></td>
					<td>录入日期</td>
					<td><input type="text" name="operateDate"
						data-dojo-type="dijit/form/DateTextBox" style="width: 100%"></td>
					<td>业务员</td>
					<td><div id="operateUser"></div></td>
				</tr>
			</table>
		</div>
		<div class="dijitDialogPaneActionBar">
			<button type="button" id="saveCustomerButton">提交</button>
			<button type="button" id="cancelCustomerButton">取消</button>
		</div>
	</form>
</div>
