define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	var complainBackGrid = "";
	var currentComplainBack = "";
	var currentcustomerid = "";
	return {
		initComplainBack : function() {
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var complainBackGridToolbar = new Toolbar({}, "complainBackGridToolbar");
				// 新增菜单
				complainBackGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentcustomerid) {
							alert("请先选择一个客户!");
							return;
						}
						dom.byId("complainBackForm").reset();
						dijit.byId("complainBackForm").setValues({
							customerid : currentcustomerid
						});
						dijit.byId("complainBackDialog").show();
					}
				}));
				// 删除菜单
				complainBackGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!(currentComplainBack && currentcustomerid)) {
							alert("请选择客户和反馈纪录!");
							return;
						}
						if (!confirm("确定要删除当前选择的反馈纪录？")) {
							return;
						}
						xhr("complainBack/delComplainByID.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentComplainBack.id
							}
						}).then(function(data) {
							var complainBackStore = new JsonRest({
								target : "complainBack/loadByCustomer.do?customerid=" + currentcustomerid,
								idProperty : "id"
							});

							complainBackGrid.setStore(new ObjectStore({
								objectStore : complainBackStore
							}));
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
				// 修改菜单
				complainBackGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						if (!(currentComplainBack && currentcustomerid)) {
							alert("请选择客户和反馈纪录!");
							return;
						}
						dijit.byId("complainBackForm").setValues({
							customerid : currentcustomerid,
							complainbackid : currentComplainBack.id,
							complainDate : currentComplainBack.complainDate,
							receiveUser : currentComplainBack.receiveUserId,
							complainContent : currentComplainBack.complainContent,
							disposeResult : currentComplainBack.disposeResult,
							disposeDate : currentComplainBack.disposeDate,
							disposeUser : currentComplainBack.disposeUserId
						});
						dijit.byId("complainBackDialog").show();
					}
				}));
			});

			var layout = [ {
				name : "日期",
				field : "complainDate"
			}, {
				name : "接待人员",
				field : "receiveUser"
			}, {
				name : "投诉及反馈内容",
				field : "complainContent"
			}, {
				name : "投诉处理结果",
				field : "disposeResult"
			}, {
				name : "处理日期",
				field : "disposeDate"
			}, {
				name : "处理人员",
				field : "disposeUser"
			} ];

			// 创建客户列表
			complainBackGrid = new EnhancedGrid({
				id : "complainBackGrid",
				structure : layout,
				onSelected : function(rowIndex) {
					// require([ "crm/customer" ], function(Customer) {
					// var item = customergrid.getItem(rowIndex);
					// currentRowItem = item;
					// var customerid = currentRowItem.id;
					// Customer.refleshCustomerDetail(customerid);
					// });
				}
			}, "complainBackGridDiv");
			complainBackGrid.startup();

			dojo.connect(complainBackGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentComplainBack = complainBackGrid.getItem(rowIndex);
			});

			// 用户
			var userStore = new JsonRest({
				target : "user/loadForCombo.do"
			});

			var receiveUserSelect = new Select({
				// id : "customersource",
				name : "receiveUser",
				store : new ObjectStore({
					objectStore : userStore
				})
			}, "receiveUser");

			var disposeUserSelect = new Select({
				// id : "customersource",
				name : "disposeUser",
				store : new ObjectStore({
					objectStore : userStore
				})
			}, "disposeUser");

			// 绑定提交事件
			on(dom.byId("saveComplainBackButton"), "click", function() {
				xhr("complainBack/saveComplain.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("complainBackForm"))
				}).then(function(data) {
					dijit.byId("complainBackDialog").hide();
					var complainBackStore = new JsonRest({
						target : "complainBack/loadByCustomer.do?customerid=" + currentcustomerid,
						idProperty : "id"
					});

					complainBackGrid.setStore(new ObjectStore({
						objectStore : complainBackStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelComplainBackButton"), "click", function() {
				dijit.byId("complainBackDialog").hide();
			});
		},
		loadByCustomer : function(customerid) {
			currentcustomerid = customerid;
			var complainBackStore = new JsonRest({
				target : "complainBack/loadByCustomer.do?customerid=" + customerid,
				idProperty : "id"
			});

			complainBackGrid.setStore(new ObjectStore({
				objectStore : complainBackStore
			}));
		}
	}
})