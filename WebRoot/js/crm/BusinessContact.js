define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	var contactGrid = "";
	var currentcustomerid = "";
	var currentContact = "";
	return {
		initBusinessContact : function() {
			// 创建菜单
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var contactGridToolbar = new Toolbar({}, "contactGridToolbar");
				// 新增菜单
				contactGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentcustomerid) {
							alert("请先选择一个客户!");
							return;
						}
						dijit.byId("contactForm").setValues({
							customerid : currentcustomerid
						});
						dijit.byId("contactDialog").show();
					}
				}));
				// 删除菜单
				contactGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!(currentContact && currentcustomerid)) {
							alert("请选择客户和业务联系!");
							return;
						}
						xhr("businessContact/delContactByID.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentContact.id
							}
						}).then(function(data) {
							var businessContactStore = new JsonRest({
								target : "businessContact/loadByCustomer.do?customerid=" + currentcustomerid,
								idProperty : "id"
							});
							contactGrid.setStore(new ObjectStore({
								objectStore : businessContactStore
							}));
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
				// 修改菜单
				contactGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						if (!(currentContact && currentcustomerid)) {
							alert("请选择客户和业务联系!");
							return;
						}
						dijit.byId("contactForm").setValues({
							customerid : currentcustomerid,
							contactid : currentContact.id,
							activityTheme : currentContact.activityThemeId,
							contactWay : currentContact.contactWayId,
							contactDate : currentContact.contactDate,
							chargeUser : currentContact.chargeUserId,
							recordUser : currentContact.recordUserId
						});
						dijit.byId("contactDialog").show();
					}
				}));
			});

			var layout = [ {
				name : "日期",
				field : "contactDate"
			}, {
				name : "主题",
				field : "activityTheme"
			}, {
				name : "洽谈方式",
				field : "contactWay"
			}, {
				name : "跟进人",
				field : "chargeUser"
			}, {
				name : "登记人员",
				field : "recordUser"
			} ];

			// 创建客户列表
			contactGrid = new EnhancedGrid({
				id : "contactGrid",
				structure : layout
			}, "contactGridDiv");
			contactGrid.startup();

			dojo.connect(contactGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentContact = contactGrid.getItem(rowIndex);
			});

			// 初始化下拉列表
			// 洽谈主题
			var activityThemeStore = new JsonRest({
				target : "activityTheme/loadForCombo.do"
			});

			var themeSelect = new Select({
				// id : "customersource",
				name : "activityTheme",
				store : new ObjectStore({
					objectStore : activityThemeStore
				})
			}, "activityTheme");

			// 洽谈方式
			var contactWayStore = new JsonRest({
				target : "contactWay/loadForCombo.do"
			});

			var contactWaySelect = new Select({
				// id : "customersource",
				name : "contactWay",
				store : new ObjectStore({
					objectStore : contactWayStore
				})
			}, "contactWay");

			// 用户
			var userStore = new JsonRest({
				target : "user/loadForCombo.do"
			});

			var chargeUserSelect = new Select({
				// id : "customersource",
				name : "chargeUser",
				store : new ObjectStore({
					objectStore : userStore
				})
			}, "chargeUser");

			var recordUserSelect = new Select({
				// id : "customersource",
				name : "recordUser",
				store : new ObjectStore({
					objectStore : userStore
				})
			}, "recordUser");

			// 绑定提交事件
			on(dom.byId("saveContactButton"), "click", function() {
				xhr("businessContact/saveContact.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("contactForm"))
				}).then(function(data) {
					dijit.byId("contactDialog").hide();
					var businessContactStore = new JsonRest({
						target : "businessContact/loadByCustomer.do?customerid=" + currentcustomerid,
						idProperty : "id"
					});
					contactGrid.setStore(new ObjectStore({
						objectStore : businessContactStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelContactButton"), "click", function() {
				dijit.byId("contactDialog").hide();
			});
		},
		loadByCustomer : function(customerid) {
			currentcustomerid = customerid;
			var businessContactStore = new JsonRest({
				target : "businessContact/loadByCustomer.do?customerid=" + customerid,
				idProperty : "id"
			});

			contactGrid.setStore(new ObjectStore({
				objectStore : businessContactStore
			}));
		}
	}
})