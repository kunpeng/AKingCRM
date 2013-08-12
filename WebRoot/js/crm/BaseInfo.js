define([ "dojo/dom", "cbtree/models/ForestStoreModel", "cbtree/Tree", "dojo/data/ItemFileWriteStore",
		"dojo/data/ObjectStore", "dojo/request/xhr", "dojo/store/JsonRest", "dojox/grid/EnhancedGrid",
		"dojo/on", "dojo/dom-form", "dojo/parser", "dijit/layout/TabContainer", "dijit/layout/ContentPane",
		"dojo/ready" ], function(dom, ForestStoreModel, Tree, ItemFileWriteStore, ObjectStore, xhr, JsonRest,
		EnhancedGrid, on, domForm, parser, TabContainer, ContentPane, ready) {
	var currentCategory = "";
	var currentCustomerType = "";
	var currentCustomerSource = "";
	var currentActivityTheme = "";
	var currentContactWay = "";
	return {
		initBaseInfo : function(target) {
			var mainTabs = dijit.byId("mainPane");
			var baseinfoPane = dijit.byId("baseinfoPane");
			if (baseinfoPane) {
				// mainTabs.addChild(userContainer);
				mainTabs.selectChild(baseinfoPane);
				var baseinfoContainer = dijit.byId("baseinfoContainer");
				if (target == "info_customertype_pane") {
					baseinfoContainer.selectChild(dijit.byId("info_customertype_pane"));
				}
				if (target == "info_category_pane") {
					baseinfoContainer.selectChild(dijit.byId("info_category_pane"));
				}
				if (target == "info_customersource_pane") {
					baseinfoContainer.selectChild(dijit.byId("info_customersource_pane"));
				}
				if (target == "info_activitytheme_pane") {
					baseinfoContainer.selectChild(dijit.byId("info_activitytheme_pane"));
				}
				if (target == "info_contactway_pane") {
					baseinfoContainer.selectChild(dijit.byId("info_contactway_pane"));
				}
				return;
			}
			baseinfoPane = new ContentPane({
				id : "baseinfoPane",
				title : "基础信息维护",
				closable : true,
				href : "baseinfo.jsp",
				style : "padding:0px;",
				onDownloadEnd : function() {
					var baseinfoContainer = dijit.byId("baseinfoContainer");
					if (target == "info_customertype_pane") {
						baseinfoContainer.selectChild(dijit.byId("info_customertype_pane"));
					}
					if (target == "info_category_pane") {
						baseinfoContainer.selectChild(dijit.byId("info_category_pane"));
					}
					if (target == "info_customersource_pane") {
						baseinfoContainer.selectChild(dijit.byId("info_customersource_pane"));
					}
					if (target == "info_activitytheme_pane") {
						baseinfoContainer.selectChild(dijit.byId("info_activitytheme_pane"));
					}
					if (target == "info_contactway_pane") {
						baseinfoContainer.selectChild(dijit.byId("info_contactway_pane"));
					}
					require([ "crm/BaseInfo" ], function(BaseInfo) {
						BaseInfo.initCategory();
						BaseInfo.initCustomerType();
						BaseInfo.initCustomerSource();
						BaseInfo.initActivityTheme();
						BaseInfo.initContactWay();
					});
				}
			});

			// baseinfoContainer = new TabContainer({
			// id : "baseinfoContainer",
			// title : "基础信息维护",
			// closable : true,
			// nested : true
			// });
			//
			// var info_customertype_pane = new ContentPane({
			// id : "info_customertype_pane",
			// title : '客户类型'
			// });
			// baseinfoContainer.addChild(info_customertype_pane);
			//
			// var info_category_pane = new ContentPane({
			// id : "info_category_pane",
			// title : '行业分类'
			// })
			// baseinfoContainer.addChild(info_category_pane);

			mainTabs.addChild(baseinfoPane);
			mainTabs.selectChild(baseinfoPane);
		},
		initCategory : function() {
			console.debug("initCategory");

			require([ "crm/BaseInfo" ], function(BaseInfo) {
				BaseInfo.getCategoryTree();
			});

			// dojo.connect(customerTypeGrid, "onRowClick", function(e) {
			// var rowIndex = e.rowIndex;
			// currentCustomerType = customerTypeGrid.getItem(rowIndex);
			// dijit.byId("customerTypeForm").setValues({
			// customertypeid : currentCustomerType.id,
			// name : currentCustomerType.name,
			// code : currentCustomerType.code,
			// memo : currentCustomerType.memo
			// });
			// });

			on(dom.byId("saveCategoryButton"), "click", function() {
				xhr("category/saveCategory.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("categoryForm"))
				}).then(function(data) {
					dijit.byId("categoryForm").setValues({
						categoryid : data.id
					});
					require([ "crm/BaseInfo" ], function(BaseInfo) {
						BaseInfo.getCategoryTree();
					});
				});
			});

			on(dom.byId("delCategoryButton"), "click", function() {
				if (!currentCategory) {
					alert("请选择一个需删除的行业分类!");
					return;
				}
				if (currentCategory.root) {
					alert("不能删除根节点!");
					return;
				}
				if (!confirm("请确认是否删除当前选择的行业分类?")) {
					return;
				}
				xhr("category/delCategory.do", {
					handleAs : "json",
					method : "POST",
					data : {
						ids : currentCategory.id
					}
				}).then(function(data) {
					require([ "crm/BaseInfo" ], function(BaseInfo) {
						BaseInfo.getCategoryTree();
					});
				});
			});

			on(dom.byId("newCategoryButton"), "click", function() {
				if (!currentCategory) {
					alert("请选择一个需删除的行业分类!");
				}
				dom.byId("categoryForm").reset();
				dijit.byId("categoryForm").setValues({
					parentid : currentCategory.id,
					parentname : currentCategory.name
				});
			});
		},
		getCategoryTree : function() {
			dojo.query("#categoryTreeDiv").empty();
			// if (dijit.byId("categoryTreeDiv")) {
			// var v = dojo.query("#categoryTreeDiv");
			// dijit.byId("categoryTreeDiv").destroy();
			// }
			xhr("category/loadAll4Tree.do", {
				handleAs : "json",
				method : "POST"
			}).then(function(data) {
				var treedata = {
					"identifier" : "id",
					"label" : "name",
					"items" : data
				};
				// console.debug(treedata);

				var categoryStore = new ItemFileWriteStore({
					data : treedata
				});

				var model = new ForestStoreModel({
					store : categoryStore,
					query : {
						type : "parent"
					},
					// rootLabel : "行业分类",
					mayHaveChildren : function(object) {
						if (object.type == "parent" || object.root) {
							return true;
						} else {
							return false;
						}
					}
				});
				var tree = new Tree({
					model : model,
					onCheckBoxClick : function(item, nodeWidget, evt) {
					},
					onClick : function(item, node, evt) {
						currentCategory = item;
						dijit.byId("categoryForm").setValues({
							parentid : currentCategory.parentId,
							parentname : currentCategory.parentName,
							categoryid : currentCategory.id,
							name : currentCategory.name,
							text : currentCategory.text,
							memo : currentCategory.memo
						});
					}
				}).placeAt(dom.byId("categoryTreeDiv"));
				// .placeAt(dom.byId("categoryTreeDiv"));
				tree.startup();
				tree.expandAll();
			}, function(err) {
				console.log("2", err, err);
			}, function(evt) {
				console.log("3", evt, evt);
			});
		},
		initCustomerType : function() {
			console.debug("initCustomerType");
			var layout = [ {
				name : "客户类型",
				field : "name",
				width : "100px"
			}, {
				name : "类型代码",
				field : "code",
				width : "60px"
			}, {
				name : "备注",
				field : "memo",
				width : "300px"
			} ];

			var customerTypeStore = new JsonRest({
				target : "customerType/loadForListAction.do",
				idProperty : "id"
			});

			var customerTypeGrid = new EnhancedGrid({
				// id : "customerTypeGrid",
				structure : layout,
				style : "border:0px;",
				store : new ObjectStore({
					objectStore : customerTypeStore
				})
			}, "customerTypeGridDiv");
			customerTypeGrid.startup();

			dojo.connect(customerTypeGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentCustomerType = customerTypeGrid.getItem(rowIndex);
				dijit.byId("customerTypeForm").setValues({
					customertypeid : currentCustomerType.id,
					name : currentCustomerType.name,
					code : currentCustomerType.code,
					memo : currentCustomerType.memo
				});
			});

			on(dom.byId("saveCustomerTypeButton"), "click", function() {
				xhr("customerType/saveCustomerType.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("customerTypeForm"))
				}).then(function(data) {
					dijit.byId("customerTypeForm").setValues({
						customertypeid : data.id
					});
					customerTypeGrid.setStore(new ObjectStore({
						objectStore : customerTypeStore
					}));
				});
			});

			on(dom.byId("delCustomerTypeButton"), "click", function() {
				xhr("customerType/delCustomerType.do", {
					handleAs : "json",
					method : "POST",
					data : {
						ids : currentCustomerType.id
					}
				}).then(function(data) {
					customerTypeGrid.setStore(new ObjectStore({
						objectStore : customerTypeStore
					}));
				});
			});

			on(dom.byId("newCustomerTypeButton"), "click", function() {
				dom.byId("customerTypeForm").reset();
			});
		},
		initCustomerSource : function() {
			console.debug("initCustomerSource");
			var layout = [ {
				name : "客户来源",
				field : "name",
				width : "100px"
			}, {
				name : "来源代码",
				field : "code",
				width : "60px"
			}, {
				name : "备注",
				field : "memo",
				width : "300px"
			} ];

			var customerSourceStore = new JsonRest({
				target : "customerSource/loadForListAction.do",
				idProperty : "id"
			});

			var customerSourceGrid = new EnhancedGrid({
				// id : "customerTypeGrid",
				structure : layout,
				style : "border:0px;",
				store : new ObjectStore({
					objectStore : customerSourceStore
				})
			}, "customerSourceGridDiv");
			customerSourceGrid.startup();

			dojo.connect(customerSourceGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentCustomerSource = customerSourceGrid.getItem(rowIndex);
				dijit.byId("customerSourceForm").setValues({
					customersourceid : currentCustomerSource.id,
					name : currentCustomerSource.name,
					code : currentCustomerSource.code,
					memo : currentCustomerSource.memo
				});
			});

			on(dom.byId("saveCustomerSourceButton"), "click", function() {
				xhr("customerSource/saveCustomerSource.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("customerSourceForm"))
				}).then(function(data) {
					dijit.byId("customerSourceForm").setValues({
						customersourceid : data.id
					});
					customerSourceGrid.setStore(new ObjectStore({
						objectStore : customerSourceStore
					}));
				});
			});

			on(dom.byId("delCustomerSourceButton"), "click", function() {
				xhr("customerSource/delCustomerSource.do", {
					handleAs : "json",
					method : "POST",
					data : {
						ids : currentCustomerSource.id
					}
				}).then(function(data) {
					customerSourceGrid.setStore(new ObjectStore({
						objectStore : customerSourceStore
					}));
				});
			});

			on(dom.byId("newCustomerSourceButton"), "click", function() {
				dom.byId("customerSourceForm").reset();
			});
		},
		initActivityTheme : function() {
			console.debug("initActivityTheme");
			var layout = [ {
				name : "洽谈主题",
				field : "name",
				width : "100px"
			}, {
				name : "主题代码",
				field : "code",
				width : "60px"
			}, {
				name : "备注",
				field : "memo",
				width : "300px"
			} ];

			var activityThemeStore = new JsonRest({
				target : "activityTheme/loadForList.do",
				idProperty : "id"
			});

			var activityThemeGrid = new EnhancedGrid({
				// id : "customerTypeGrid",
				structure : layout,
				style : "border:0px;",
				store : new ObjectStore({
					objectStore : activityThemeStore
				})
			}, "activityThemeGridDiv");
			activityThemeGrid.startup();

			dojo.connect(activityThemeGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentActivityTheme = activityThemeGrid.getItem(rowIndex);
				dijit.byId("activityThemeForm").setValues({
					activitythemeid : currentActivityTheme.id,
					name : currentActivityTheme.name,
					code : currentActivityTheme.code,
					memo : currentActivityTheme.memo
				});
			});

			on(dom.byId("saveActivityThemeButton"), "click", function() {
				xhr("activityTheme/saveActivityTheme.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("activityThemeForm"))
				}).then(function(data) {
					dijit.byId("activityThemeForm").setValues({
						activitythemeid : data.id
					});
					activityThemeGrid.setStore(new ObjectStore({
						objectStore : activityThemeStore
					}));
				});
			});

			on(dom.byId("delActivityThemeButton"), "click", function() {
				xhr("activityTheme/delActivityTheme.do", {
					handleAs : "json",
					method : "POST",
					data : {
						ids : currentActivityTheme.id
					}
				}).then(function(data) {
					activityThemeGrid.setStore(new ObjectStore({
						objectStore : activityThemeStore
					}));
				});
			});

			on(dom.byId("newActivityThemeButton"), "click", function() {
				dom.byId("activityThemeForm").reset();
			});
		},
		initContactWay : function() {
			console.debug("initContactWay");
			var layout = [ {
				name : "洽谈方式",
				field : "name",
				width : "100px"
			}, {
				name : "方式代码",
				field : "code",
				width : "60px"
			}, {
				name : "备注",
				field : "memo",
				width : "300px"
			} ];

			var contactWayStore = new JsonRest({
				target : "contactWay/loadForList.do",
				idProperty : "id"
			});

			var contactWayGrid = new EnhancedGrid({
				// id : "customerTypeGrid",
				structure : layout,
				style : "border:0px;",
				store : new ObjectStore({
					objectStore : contactWayStore
				})
			}, "contactWayGridDiv");
			contactWayGrid.startup();

			dojo.connect(contactWayGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentContactWay = contactWayGrid.getItem(rowIndex);
				dijit.byId("contactWayForm").setValues({
					contactwayid : currentContactWay.id,
					name : currentContactWay.name,
					code : currentContactWay.code,
					memo : currentContactWay.memo
				});
			});

			on(dom.byId("saveContactWayButton"), "click", function() {
				xhr("contactWay/saveContactWay.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("contactWayForm"))
				}).then(function(data) {
					dijit.byId("contactWayForm").setValues({
						contactwayid : data.id
					});
					contactWayGrid.setStore(new ObjectStore({
						objectStore : contactWayStore
					}));
				});
			});

			on(dom.byId("delContactWayButton"), "click", function() {
				xhr("contactWay/delContactWay.do", {
					handleAs : "json",
					method : "POST",
					data : {
						ids : currentContactWay.id
					}
				}).then(function(data) {
					contactWayGrid.setStore(new ObjectStore({
						objectStore : contactWayStore
					}));
				});
			});

			on(dom.byId("newContactWayButton"), "click", function() {
				dom.byId("contactWayForm").reset();
			});
		}
	}
});