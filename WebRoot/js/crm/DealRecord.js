define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojox/grid/enhanced/plugins/Menu",
		"dojo/store/JsonRest", "dojo/request", "dojo/dom", "dojo/json", "dijit/form/Select",
		"dojo/request/xhr", "dojo/on", "dojo/dom-form" ], function(EnhancedGrid, ObjectStore, Menu, JsonRest,
		request, dom, Json, Select, xhr, on, domForm) {
	var dealRecordGrid = "";
	var currentDealRecord = "";
	var currentcustomerid = "";
	return {
		initDealRecord : function() {
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var dealRecordGridToolbar = new Toolbar({}, "dealRecordGridToolbar");
				// 新增菜单
				dealRecordGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentcustomerid) {
							alert("请先选择一个客户!");
							return;
						}
						dom.byId("dealRecordForm").reset();
						dijit.byId("dealRecordForm").setValues({
							customerid : currentcustomerid
						});
						dijit.byId("dealRecordDialog").show();
					}
				}));
				// 删除菜单
				dealRecordGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!(currentDealRecord && currentcustomerid)) {
							alert("请选择客户和成交纪录!");
							return;
						}
						if (!confirm("确定要删除当前选择的成交记录？")) {
							return;
						}
						xhr("dealRecord/delRecordByID.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentDealRecord.id
							}
						}).then(function(data) {
							var dealRecordStore = new JsonRest({
								target : "dealRecord/loadByCustomer.do?customerid=" + currentcustomerid,
								idProperty : "id"
							});

							dealRecordGrid.setStore(new ObjectStore({
								objectStore : dealRecordStore
							}));
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
				// 修改菜单
				dealRecordGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						if (!(currentDealRecord && currentcustomerid)) {
							alert("请选择客户和成交记录!");
							return;
						}
						dijit.byId("dealRecordForm").setValues({
							customerid : currentcustomerid,
							recordid : currentDealRecord.id,
							dealDate : currentDealRecord.dealDate,
							dealrecord_product : currentDealRecord.productId,
							quantity : currentDealRecord.quantity,
							price : currentDealRecord.price,
							businessCost : currentDealRecord.businessCost,
							invoice : currentDealRecord.invoice,
							payCondition : currentDealRecord.payCondition,
							deliverDate : currentDealRecord.deliverDate,
							memo : currentDealRecord.memo
						});
						dijit.byId("dealRecordDialog").show();
					}
				}));
			});

			var layout = [ {
				name : "日期",
				field : "dealDate"
			}, {
				name : "品名",
				field : "productName"
			}, {
				name : "型号",
				field : "standard"
			}, {
				name : "数量",
				field : "quantity"
			}, {
				name : "单价",
				field : "price"
			}, {
				name : "金额",
				field : "sum"
			}, {
				name : "业务量",
				field : "businessCost"
			}, {
				name : "开票情况",
				field : "invoice"
			}, {
				name : "付款情况",
				field : "payCondition"
			}, {
				name : "发货时间",
				field : "deliverDate"
			}, {
				name : "备注",
				field : "memo"
			} ];

			// 创建客户列表
			dealRecordGrid = new EnhancedGrid({
				id : "dealRecordGrid",
				structure : layout
			}, "dealRecordGridDiv");
			dealRecordGrid.startup();

			dojo.connect(dealRecordGrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				currentDealRecord = dealRecordGrid.getItem(rowIndex);
			});

			var productStore = new JsonRest({
				target : "product/load4Combo.do"
			});

			var productSelect = new Select({
				name : "dealrecord_product",
				store : new ObjectStore({
					objectStore : productStore
				})
			}, "dealrecord_product");

			// 绑定提交事件
			on(dom.byId("saveDealRecordButton"), "click", function() {
				xhr("dealRecord/saveRecord.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("dealRecordForm"))
				}).then(function(data) {
					dijit.byId("dealRecordDialog").hide();
					var dealRecordStore = new JsonRest({
						target : "dealRecord/loadByCustomer.do?customerid=" + currentcustomerid,
						idProperty : "id"
					});

					dealRecordGrid.setStore(new ObjectStore({
						objectStore : dealRecordStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelDealRecordButton"), "click", function() {
				dijit.byId("dealRecordDialog").hide();
			});
		},
		loadByCustomer : function(customerid) {
			currentcustomerid = customerid;
			var dealRecordStore = new JsonRest({
				target : "dealRecord/loadByCustomer.do?customerid=" + customerid,
				idProperty : "id"
			});

			dealRecordGrid.setStore(new ObjectStore({
				objectStore : dealRecordStore
			}));
		}
	}
})