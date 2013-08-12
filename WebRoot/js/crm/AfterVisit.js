define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	var afterVisitGrid = "";
	var currentAfterVisit = "";
	var currentcustomerid = "";
	return {
		initAfterVisit : function() {
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var afterVisitGridToolbar = new Toolbar({}, "afterVisitGridToolbar");
				// 新增菜单
				afterVisitGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentcustomerid) {
							alert("请先选择一个客户!");
							return;
						}
						dom.byId("afterVisitForm").reset();
						dijit.byId("afterVisitForm").setValues({
							customerid : currentcustomerid
						});
						dijit.byId("afterVisitDialog").show();
					}
				}));
				// 删除菜单
				afterVisitGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!(currentAfterVisit && currentcustomerid)) {
							alert("请选择客户和回访纪录!");
							return;
						}
						if (!confirm("确定要删除当前选择的回访纪录？")) {
							return;
						}
						xhr("afterVisit/delVisitByID.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentAfterVisit.id
							}
						}).then(function(data) {
							var afterVisitStore = new JsonRest({
								target : "afterVisit/loadByCustomer.do?customerid=" + currentcustomerid,
								idProperty : "id"
							});

							afterVisitGrid.setStore(new ObjectStore({
								objectStore : afterVisitStore
							}));
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
				// 修改菜单
				afterVisitGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						if (!(currentAfterVisit && currentcustomerid)) {
							alert("请选择客户和成交记录!");
							return;
						}
						dijit.byId("afterVisitForm").setValues({
							customerid : currentcustomerid,
							visitid : currentAfterVisit.id,
							visitDate : currentAfterVisit.visitDate,
							visitUser : currentAfterVisit.visitUserId,
							content : currentAfterVisit.content,
							result : currentAfterVisit.result
						});
						dijit.byId("afterVisitDialog").show();
					}
				}));
			});

			var layout = [ {
				name : "回访日期",
				field : "visitDate"
			}, {
				name : "回访人员",
				field : "visitUser"
			}, {
				name : "回访内容",
				field : "content"
			}, {
				name : "回访结果",
				field : "result"
			} ];

			// 创建客户列表
			afterVisitGrid = new EnhancedGrid({
				id : "afterVisitGrid",
				structure : layout
			}, "afterVisitGridDiv");
			afterVisitGrid.startup();

			dojo.connect(afterVisitGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentAfterVisit = afterVisitGrid.getItem(rowIndex);
			});

			// 用户
			var userStore = new JsonRest({
				target : "user/loadForCombo.do"
			});

			var visitUserSelect = new Select({
				// id : "customersource",
				name : "visitUser",
				store : new ObjectStore({
					objectStore : userStore
				})
			}, "visitUser");

			// 绑定提交事件
			on(dom.byId("saveAfterVisitButton"), "click", function() {
				xhr("afterVisit/saveVisit.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("afterVisitForm"))
				}).then(function(data) {
					dijit.byId("afterVisitDialog").hide();
					var afterVisitStore = new JsonRest({
						target : "afterVisit/loadByCustomer.do?customerid=" + currentcustomerid,
						idProperty : "id"
					});

					afterVisitGrid.setStore(new ObjectStore({
						objectStore : afterVisitStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelAfterVisitButton"), "click", function() {
				dijit.byId("afterVisitDialog").hide();
			});
		},
		loadByCustomer : function(customerid) {
			currentcustomerid = customerid;
			var afterVisitStore = new JsonRest({
				target : "afterVisit/loadByCustomer.do?customerid=" + customerid,
				idProperty : "id"
			});

			afterVisitGrid.setStore(new ObjectStore({
				objectStore : afterVisitStore
			}));
		}
	}
})