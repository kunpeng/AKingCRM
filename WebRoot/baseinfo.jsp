<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div data-dojo-type="dijit/layout/TabContainer" id="baseinfoContainer"
	data-dojo-props="nested:true" style="padding: 0px">
	<div data-dojo-type="dijit/layout/ContentPane" style="padding: 0px"
		id="info_category_pane" data-dojo-props="title:'行业分类'">
		<div data-dojo-type="dijit/layout/BorderContainer"
			data-dojo-props="design:'sidebar'">
			<div data-dojo-type="dijit/Toolbar" data-dojo-props="region:'top'">
				<div data-dojo-type="dijit/form/Button" id="newCategoryButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconNewPage'">新增</div>
				<div data-dojo-type="dijit/form/Button" id="delCategoryButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconDelete'">删除</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'right',splitter:true" style="width: 300px;">
				<div data-dojo-type="dijit/form/Form" id="categoryForm">
					<input type="hidden" data-dojo-type="dijit/form/TextBox"
						id="categoryid" name="categoryid" value="" />
					<p>
						<input type="hidden" data-dojo-type="dijit/form/TextBox"
							id="parentid" name="parentid" value="" />
					<table>
						<tr>
							<td><label for="parentname">上级代码</label></td>
							<td><input type="text" name="parentname" readonly
								data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
						</tr>
						<tr>
							<td><label for="name">行业分类</label></td>
							<td><input type="text" name="name"
								data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
						</tr>
						<tr>
							<td><label for="text">分类标签</label></td>
							<td><input type="text" name="text"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td><label for="memo">备注</label></td>
							<td><input type="text" name="memo"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td colspan=2><button data-dojo-type="dijit/form/Button"
									id="saveCategoryButton">保存</button>
								<button data-dojo-type="dijit/form/Button"
									id="cancelCategoryButton">清空</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'center'" style="padding: 0px;">
				<div id="categoryTreeDiv"></div>
			</div>
		</div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" style="padding: 0px"
		id="info_customertype_pane" data-dojo-props="title:'客户类型'">
		<div data-dojo-type="dijit/layout/BorderContainer"
			data-dojo-props="design:'sidebar'">
			<div data-dojo-type="dijit/Toolbar" data-dojo-props="region:'top'">
				<div data-dojo-type="dijit/form/Button" id="newCustomerTypeButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconNewPage'">新增</div>
				<div data-dojo-type="dijit/form/Button" id="delCustomerTypeButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconDelete'">删除</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'right',splitter:true" style="width: 300px;">
				<div data-dojo-type="dijit/form/Form" id="customerTypeForm">
					<input type="hidden" data-dojo-type="dijit/form/TextBox"
						id="customertypeid" name="customertypeid" value="">
					<table>
						<tr>
							<td><label for="name">客户类型</label></td>
							<td><input type="text" name="name"
								data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
						</tr>
						<tr>
							<td><label for="code">类型代码</label></td>
							<td><input type="text" name="code"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td><label for="memo">备注</label></td>
							<td><input type="text" name="memo"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td colspan=2><button data-dojo-type="dijit/form/Button"
									id="saveCustomerTypeButton">保存</button>
								<button data-dojo-type="dijit/form/Button"
									id="cancelCustomerTypeButton">清空</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'center'" style="padding: 0px;">
				<div id="customerTypeGridDiv">数据加载出错，请联系管理员!</div>
			</div>
		</div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" style="padding: 0px"
		id="info_customersource_pane" data-dojo-props="title:'客户来源'">
		<div data-dojo-type="dijit/layout/BorderContainer"
			data-dojo-props="design:'sidebar'">
			<div data-dojo-type="dijit/Toolbar" data-dojo-props="region:'top'">
				<div data-dojo-type="dijit/form/Button" id="newCustomerSourceButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconNewPage'">新增</div>
				<div data-dojo-type="dijit/form/Button" id="delCustomerSourceButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconDelete'">删除</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'right',splitter:true" style="width: 300px;">
				<div data-dojo-type="dijit/form/Form" id="customerSourceForm">
					<input type="hidden" data-dojo-type="dijit/form/TextBox"
						id="customersourceid" name="customersourceid" value="">
					<table>
						<tr>
							<td><label for="name">客户来源</label></td>
							<td><input type="text" name="name"
								data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
						</tr>
						<tr>
							<td><label for="code">来源代码</label></td>
							<td><input type="text" name="code"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td><label for="memo">备注</label></td>
							<td><input type="text" name="memo"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td colspan=2><button data-dojo-type="dijit/form/Button"
									id="saveCustomerSourceButton">保存</button>
								<button data-dojo-type="dijit/form/Button"
									id="cancelCustomerSourceButton">清空</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'center'" style="padding: 0px;">
				<div id="customerSourceGridDiv">数据加载出错，请联系管理员!</div>
			</div>
		</div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" style="padding: 0px"
		id="info_activitytheme_pane" data-dojo-props="title:'洽谈主题'">
		<div data-dojo-type="dijit/layout/BorderContainer"
			data-dojo-props="design:'sidebar'">
			<div data-dojo-type="dijit/Toolbar" data-dojo-props="region:'top'">
				<div data-dojo-type="dijit/form/Button" id="newActivityThemeButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconNewPage'">新增</div>
				<div data-dojo-type="dijit/form/Button" id="delActivityThemeButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconDelete'">删除</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'right',splitter:true" style="width: 300px;">
				<div data-dojo-type="dijit/form/Form" id="activityThemeForm">
					<input type="hidden" data-dojo-type="dijit/form/TextBox"
						id="activitythemeid" name="activitythemeid" value="">
					<table>
						<tr>
							<td><label for="name">主题名称</label></td>
							<td><input type="text" name="name"
								data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
						</tr>
						<tr>
							<td><label for="code">主题代码</label></td>
							<td><input type="text" name="code"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td><label for="memo">备注</label></td>
							<td><input type="text" name="memo"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td colspan=2><button data-dojo-type="dijit/form/Button"
									id="saveActivityThemeButton">保存</button>
								<button data-dojo-type="dijit/form/Button"
									id="cancelActivityThemeButton">清空</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'center'" style="padding: 0px;">
				<div id="activityThemeGridDiv">数据加载出错，请联系管理员!</div>
			</div>
		</div>
	</div>
	<div data-dojo-type="dijit/layout/ContentPane" style="padding: 0px"
		id="info_contactway_pane" data-dojo-props="title:'洽谈方式'">
		<div data-dojo-type="dijit/layout/BorderContainer"
			data-dojo-props="design:'sidebar'">
			<div data-dojo-type="dijit/Toolbar" data-dojo-props="region:'top'">
				<div data-dojo-type="dijit/form/Button" id="newContactWayButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconNewPage'">新增</div>
				<div data-dojo-type="dijit/form/Button" id="delContactWayButton"
					data-dojo-props="iconClass:'dijitEditorIcon dijitEditorIconDelete'">删除</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'right',splitter:true" style="width: 300px;">
				<div data-dojo-type="dijit/form/Form" id="contactWayForm">
					<input type="hidden" data-dojo-type="dijit/form/TextBox"
						id="contactwayid" name="contactwayid" value="">
					<table>
						<tr>
							<td><label for="name">方式名称</label></td>
							<td><input type="text" name="name"
								data-dojo-type="dijit/form/ValidationTextBox" required="true"></td>
						</tr>
						<tr>
							<td><label for="code">方式代码</label></td>
							<td><input type="text" name="code"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td><label for="memo">备注</label></td>
							<td><input type="text" name="memo"
								data-dojo-type="dijit/form/TextBox"></td>
						</tr>
						<tr>
							<td colspan=2><button data-dojo-type="dijit/form/Button"
									id="saveContactWayButton">保存</button>
								<button data-dojo-type="dijit/form/Button"
									id="cancelContactWayButton">清空</button></td>
						</tr>
					</table>
				</div>
			</div>
			<div data-dojo-type="dijit/layout/ContentPane"
				data-dojo-props="region:'center'" style="padding: 0px;">
				<div id="contactWayGridDiv">数据加载出错，请联系管理员!</div>
			</div>
		</div>
	</div>
</div>
