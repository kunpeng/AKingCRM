define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	var contactManGrid = "";
	var currentContactMan = "";
	var currentcustomerid = "";
	return {
		initContactMan : function() {
			// 创建菜单
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var contactManGridToolbar = new Toolbar({}, "contactManGridToolbar");
				// 新增菜单
				contactManGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentcustomerid) {
							alert("请先选择一个客户!");
							return;
						}
						dom.byId("contactManForm").reset();
						dijit.byId("contactManForm").setValues({
							customerid : currentcustomerid
						});
						dijit.byId("contactManDialog").show();
					}
				}));
				// 删除菜单
				contactManGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!(currentContactMan && currentcustomerid)) {
							alert("请选择客户和联系人!");
							return;
						}
						if (!confirm("确定要删除当前选择的联系人？")) {
							return;
						}
						xhr("contactMan/delManByID.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentContactMan.id
							}
						}).then(function(data) {
							var comtactManStore = new JsonRest({
								target : "contactMan/loadByCustomer.do?customerid=" + currentcustomerid,
								idProperty : "id"
							});

							contactManGrid.setStore(new ObjectStore({
								objectStore : comtactManStore
							}));
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
				// 修改菜单
				contactManGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						if (!(currentContactMan && currentcustomerid)) {
							alert("请选择客户和业务联系!");
							return;
						}
						dijit.byId("contactManForm").setValues({
							customerid : currentcustomerid,
							contactmanid : currentContactMan.id,
							manname : currentContactMan.name,
							isCharge : currentContactMan.isCharge,
							department : currentContactMan.department,
							position : currentContactMan.position,
							officetel : currentContactMan.officetel,
							phone : currentContactMan.phone,
							homePhone : currentContactMan.homePhone,
							email : currentContactMan.email,
							fax : currentContactMan.fax,
							qq : currentContactMan.qq,
							address : currentContactMan.address,
							birthday : currentContactMan.birthday,
							hobby : currentContactMan.hobby
						});
						dijit.byId("contactManDialog").show();
					}
				}));
			});

			var layout = [ {
				name : "姓名",
				field : "name"
			}, {
				name : "主联系人",
				field : "isCharge"
			}, {
				name : "部门",
				field : "department"
			}, {
				name : "职务",
				field : "position"
			}, {
				name : "办公电话",
				field : "officetel"
			}, {
				name : "手机",
				field : "phone"
			}, {
				name : "家庭电话",
				field : "homePhone"
			}, {
				name : "电子邮箱",
				field : "email"
			}, {
				name : "传真",
				field : "fax"
			}, {
				name : "QQ",
				field : "qq"
			}, {
				name : "详细地址",
				field : "address"
			}, {
				name : "出生日期",
				field : "birthday"
			}, {
				name : "爱好",
				field : "hobby"
			} ];

			// 创建客户列表
			contactManGrid = new EnhancedGrid({
				id : "contactManGrid",
				structure : layout
			}, "contactManGridDiv");
			contactManGrid.startup();

			dojo.connect(contactManGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentContactMan = contactManGrid.getItem(rowIndex);
			});

			// 绑定提交事件
			on(dom.byId("saveContactManButton"), "click", function() {
				xhr("contactMan/saveMan.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("contactManForm"))
				}).then(function(data) {
					dijit.byId("contactManDialog").hide();
					var comtactManStore = new JsonRest({
						target : "contactMan/loadByCustomer.do?customerid=" + currentcustomerid,
						idProperty : "id"
					});
					contactManGrid.setStore(new ObjectStore({
						objectStore : comtactManStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelContactManButton"), "click", function() {
				dijit.byId("contactManDialog").hide();
			});
		},
		loadByCustomer : function(customerid) {
			currentcustomerid = customerid;

			var comtactManStore = new JsonRest({
				target : "contactMan/loadByCustomer.do?customerid=" + customerid,
				idProperty : "id"
			});

			contactManGrid.setStore(new ObjectStore({
				objectStore : comtactManStore
			}));
		}
	}
})