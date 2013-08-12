define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	var productQuoteGrid = "";
	var currentProductQuote = "";
	var currentcustomerid = "";
	return {
		initProductQuote : function() {
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var productQuoteGridToolbar = new Toolbar({}, "productQuoteGridToolbar");
				// 新增菜单
				productQuoteGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentcustomerid) {
							alert("请先选择一个客户!");
							return;
						}
						dom.byId("productQuoteForm").reset();
						dijit.byId("productQuoteForm").setValues({
							customerid : currentcustomerid
						});
						dijit.byId("productQuoteDialog").show();
					}
				}));
				// 删除菜单
				productQuoteGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!(currentProductQuote && currentcustomerid)) {
							alert("请选择客户和产品报价!");
							return;
						}
						if (!confirm("确定要删除当前选择的产品报价？")) {
							return;
						}
						xhr("productQuote/delQuoteByID.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentProductQuote.id
							}
						}).then(function(data) {
							var productQuoteStore = new JsonRest({
								target : "productQuote/loadByCustomer.do?customerid=" + currentcustomerid,
								idProperty : "id"
							});

							productQuoteGrid.setStore(new ObjectStore({
								objectStore : productQuoteStore
							}));
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
				// 修改菜单
				productQuoteGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						if (!(currentProductQuote && currentcustomerid)) {
							alert("请选择客户和产品报价!");
							return;
						}
						dijit.byId("productQuoteForm").setValues({
							customerid : currentcustomerid,
							productquoteid : currentProductQuote.id,
							productquote_product : currentProductQuote.productId,
							price : currentProductQuote.price,
							quotationNo : currentProductQuote.quotationNo
						});
						dijit.byId("productQuoteDialog").show();
					}
				}));
			});

			var layout = [ {
				name : "报价日期",
				field : "quoteDate"
			}, {
				name : "品名",
				field : "productname"
			}, {
				name : "规格",
				field : "standard"
			}, {
				name : "类型",
				field : "type"
			}, {
				name : "单价",
				field : "price"
			}, {
				name : "报价单号",
				field : "quotationNo"
			} ];

			// 创建客户列表
			productQuoteGrid = new EnhancedGrid({
				id : "productQuoteGrid",
				structure : layout
			}, "productQuoteGridDiv");
			productQuoteGrid.startup();

			dojo.connect(productQuoteGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentProductQuote = productQuoteGrid.getItem(rowIndex);
			});

			var productStore = new JsonRest({
				target : "product/load4Combo.do"
			});

			var productSelect = new Select({
				name : "productquote_product",
				store : new ObjectStore({
					objectStore : productStore
				})
			}, "productquote_product");

			// 绑定提交事件
			on(dom.byId("saveProductQuoteButton"), "click", function() {
				xhr("productQuote/savaQuote.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("productQuoteForm"))
				}).then(function(data) {
					dijit.byId("productQuoteDialog").hide();
					var productQuoteStore = new JsonRest({
						target : "productQuote/loadByCustomer.do?customerid=" + currentcustomerid,
						idProperty : "id"
					});

					productQuoteGrid.setStore(new ObjectStore({
						objectStore : productQuoteStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelProductQuoteButton"), "click", function() {
				dijit.byId("productQuoteDialog").hide();
			});
		},
		loadByCustomer : function(customerid) {
			currentcustomerid = customerid;
			var productQuoteStore = new JsonRest({
				target : "productQuote/loadByCustomer.do?customerid=" + customerid,
				idProperty : "id"
			});

			productQuoteGrid.setStore(new ObjectStore({
				objectStore : productQuoteStore
			}));
		}
	}
})