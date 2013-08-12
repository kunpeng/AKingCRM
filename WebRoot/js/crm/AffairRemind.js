define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	return {
		initAffairRemind : function() {
			var layout = [ {
				name : "提醒日期",
				field : "affairDate"
			}, {
				name : "提醒时间",
				field : "affairTime"
			}, {
				name : "事务主题",
				field : "theme"
			}, {
				name : "详细描述",
				field : "memo"
			}, {
				name : "录入日期",
				field : "recordDateTime"
			}, {
				name : "录入人员",
				field : "recordUser"
			} ];

			// 创建客户列表
			var affairRemindGrid = new EnhancedGrid({
				id : "affairRemindGrid",
				structure : layout,
				onSelected : function(rowIndex) {
					// require([ "crm/customer" ], function(Customer) {
					// var item = customergrid.getItem(rowIndex);
					// currentRowItem = item;
					// var customerid = currentRowItem.id;
					// Customer.refleshCustomerDetail(customerid);
					// });
				}
			}, "affairRemindGridDiv");
			affairRemindGrid.startup();
		}
	}
})