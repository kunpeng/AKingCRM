define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	var productGrid = "";
	var currentProduct = "";
	var currentcustomerid = "";
	return {
		initProduct : function() {
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var productGridToolbar = new Toolbar({}, "productGridToolbar");
				// 新增菜单
				productGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentcustomerid) {
							alert("请先选择一个客户!");
							return;
						}
						dom.byId("productForm").reset();
						dijit.byId("productForm").setValues({
							customerid : currentcustomerid
						});
						dijit.byId("productDialog").show();
					}
				}));
				// 删除菜单
				productGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!(currentProduct && currentcustomerid)) {
							alert("请选择客户和产品!");
							return;
						}
						if (!confirm("确定要删除当前选择的产品？")) {
							return;
						}
						xhr("product/delProductByID.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentProduct.id
							}
						}).then(function(data) {
							var productStore = new JsonRest({
								target : "product/loadByCustomer.do?customerid=" + currentcustomerid,
								idProperty : "id"
							});

							productGrid.setStore(new ObjectStore({
								objectStore : productStore
							}));
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
				// 修改菜单
				productGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						if (!(currentProduct && currentcustomerid)) {
							alert("请选择客户和业务联系!");
							return;
						}
						dijit.byId("productForm").setValues({
							customerid : currentcustomerid,
							productid : currentProduct.id,
							productname : currentProduct.name,
							standard : currentProduct.standard,
							type : currentProduct.type,
							productAbility : currentProduct.productAbility,
							stockQuantity : currentProduct.stockQuantity,
							memo : currentProduct.memo
						});
						dijit.byId("productDialog").show();
					}
				}));
			});

			var layout = [ {
				name : "品名",
				field : "name"
			}, {
				name : "规格",
				field : "standard"
			}, {
				name : "型号",
				field : "type"
			}, {
				name : "生产能力",
				field : "productAbility"
			}, {
				name : "年进货量",
				field : "stockQuantity"
			}, {
				name : "备注",
				field : "memo"
			} ];

			// 创建客户列表
			productGrid = new EnhancedGrid({
				id : "productGrid",
				structure : layout
			}, "productGridDiv");
			productGrid.startup();

			dojo.connect(productGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentProduct = productGrid.getItem(rowIndex);
			});

			// 绑定提交事件
			on(dom.byId("saveProductButton"), "click", function() {
				xhr("product/saveProduct.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("productForm"))
				}).then(function(data) {
					dijit.byId("productDialog").hide();
					var productStore = new JsonRest({
						target : "product/loadByCustomer.do?customerid=" + currentcustomerid,
						idProperty : "id"
					});

					productGrid.setStore(new ObjectStore({
						objectStore : productStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelProductButton"), "click", function() {
				dijit.byId("productDialog").hide();
			});
		},
		loadByCustomer : function(customerid) {
			currentcustomerid = customerid;
			var productStore = new JsonRest({
				target : "product/loadByCustomer.do?customerid=" + customerid,
				idProperty : "id"
			});

			productGrid.setStore(new ObjectStore({
				objectStore : productStore
			}));
		}
	}
})