define([ "dojox/grid/EnhancedGrid", "dojo/data/ObjectStore", "dojo/store/JsonRest", "dojo/request",
		"dojo/dom", "dojo/json", "dijit/form/Select", "dojo/request/xhr", "dojo/on", "dojo/dom-form",
		"crm/BusinessContact", "crm/ContactMan", "crm/Product", "crm/ProductQuote", "crm/DealRecord",
		"crm/AfterVisit", "crm/ComplainBack" ], function(EnhancedGrid, ObjectStore, JsonRest, request, dom,
		Json, Select, xhr, on, domForm, BusinessContact, ContactMan, Product, ProductQuote, DealRecord,
		AfterVisit, ComplainBack) {
	var currentRowItem = "";
	return {
		custumerInit : function() {
			// 创建菜单
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var customerGridToolbar = new Toolbar({}, "customerGridToolbar");
				// 新增菜单
				customerGridToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						dijit.byId("customerForm").reset();
						dijit.byId("newCustomerDialog").show();
					}
				}));
				// 删除菜单
				customerGridToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						require([ "crm/customer" ], function(Customer) {
							Customer.delCustomer();
						})
					}
				}));
				// 修改菜单
				customerGridToolbar.addChild(new Button({
					label : '修改',
					iconClass : "dijitEditorIcon dijitEditorIconCopy",
					onClick : function() {
						require([ "crm/customer" ], function(Customer) {
							Customer.modifyCustomer();
						})
					}
				}));
			});

			// 加载客户列表
			var customerStore = new JsonRest({
				target : "customer/loadCustomer.do",
				idProperty : "id"
			});

			var layout = [ {
				name : "客户名称",
				field : "name",
				width : "100px"
			}, {
				name : "助记码",
				field : "code",
				width : "50px"
			}, {
				name : "行业分类",
				field : "category"
			}, {
				name : "客户类型",
				field : "customerType"
			}, {
				name : "公司地址",
				field : "address",
				width : "200px"
			}, {
				name : "联系电话",
				field : "officetel",
				width : "80px"
			}, {
				name : "联系邮箱",
				field : "email",
				width : "80px"
			}, {
				name : "主要产品",
				field : "mainProduct"
			} ];

			// 创建客户列表
			var customergrid = new EnhancedGrid({
				id : "customergrid",
				store : dataStore = new ObjectStore({
					objectStore : customerStore
				}),
				structure : layout,
				// plugins : {
				// menus : {
				// headerMenu : "fileMenu",
				// rowMenu : "customerRowMenu"
				// cellMenu : "fileMenu"
				// }
				// },
				onSelected : function(rowIndex) {
					require([ "crm/customer" ], function(Customer) {
						var item = customergrid.getItem(rowIndex);
						currentRowItem = item;
						// console.debug(item.id);
						// var customerid =
						// customergrid.store.getValue(item,
						// "id");
						var customerid = currentRowItem.id;
						// currentCustomerId = customerid;
						Customer.refleshCustomerDetail(customerid);

						// 刷新当前客户的各项资料
						BusinessContact.loadByCustomer(customerid);
						ContactMan.loadByCustomer(customerid);
						Product.loadByCustomer(customerid);
						ProductQuote.loadByCustomer(customerid);
						DealRecord.loadByCustomer(customerid);
						AfterVisit.loadByCustomer(customerid);
						ComplainBack.loadByCustomer(customerid);
					});
				}
			}, "customerGridDiv");
			// grid.placeAt(dom.byId("customerGid"));
			customergrid.startup();

			// 初始化各数据
			// 客户类型
			var typeStore = new JsonRest({
				target : "customerType/loadForComboAction.do"
			});

			var customerTypeSelect = new Select({
				// id : "customertype1",
				name : "customertype",
				required : "true",
				store : new ObjectStore({
					objectStore : typeStore
				})
			}, "customertype");
			customerTypeSelect.startup();

			// 行业分类
			var categoryStore = new JsonRest({
				target : "category/loadForComboAction.do"
			});

			var categorySelect = new Select({
				// id : "category",
				name : "category",
				required : "true",
				store : new ObjectStore({
					objectStore : categoryStore
				})
			}, "category");

			// 客户来源
			var sourceStore = new JsonRest({
				target : "customerSource/loadForComboAction.do"
			});

			var sourceSelect = new Select({
				// id : "customersource",
				name : "customersource",
				required : "true",
				store : new ObjectStore({
					objectStore : sourceStore
				})
			}, "customersource");

			// 国家
			var countryStore = new JsonRest({
				target : "country/loadForCombo.do"
			});
			var countrySelect = new Select({
				// id : "country",
				name : "country",
				required : "true",
				store : new ObjectStore({
					objectStore : countryStore
				}),
				// searchAttr : "name",
				onChange : function(country) {
					// console.debug(country);
					var province = dijit.byId("province");
					// province.attr("value", "");
					province.setStore(new ObjectStore({
						objectStore : new JsonRest({
							target : "province/loadAll4Combo.do?countryid=" + country
						})
					}));
				}
			}, "country");

			// 省份
			var provinceStore = new JsonRest({
				target : "province/loadAll4Combo.do"
			});
			var provinceSelect = new Select({
				// id : "province",
				name : "province",
				required : "true",
				store : new ObjectStore({
					objectStore : provinceStore
				}),
				onChange : function(province) {
					// console.debug(province);
					var city = dijit.byId("city");
					city.setStore(new ObjectStore({
						objectStore : new JsonRest({
							target : "city/loadAll4Combo.do?provinceid=" + province
						})
					}));
				}
			}, "province");

			// 城市
			var citySelect = new Select({
				// id : "city",
				name : "city",
				required : "true",
				store : new ObjectStore({
					objectStore : new JsonRest({
						target : "city/loadAll4Combo.do"
					})
				})
			}, "city");

			// 用户
			var userStore = new JsonRest({
				target : "user/loadForCombo.do"
			});

			var operateUserSelect = new Select({
				// id : "customersource",
				name : "operateUser",
				store : new ObjectStore({
					objectStore : userStore
				})
			}, "operateUser");

			// 绑定提交事件
			on(dom.byId("saveCustomerButton"), "click", function() {
				if (!dijit.byId("customerForm").validate()) {
					return;
				}
				xhr("customer/savecustomer.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("customerForm"))
				}).then(function(data) {
					dijit.byId("newCustomerDialog").hide();
					customergrid.setStore(new ObjectStore({
						objectStore : customerStore
					}));
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelCustomerButton"), "click", function() {
				dijit.byId("newCustomerDialog").hide();
			});

			// on(dom.byId("delCustomerMenu"), "click", function() {
			// console.log("info", "click", "OK");
			// });
		},
		refleshCustomerDetail : function(customerid) {
			request.post("customer/loadCustomerById.do", {
				data : {
					id : customerid
				}
			}).then(function(data) {
				var detail = Json.parse(data);
				// alert(data);
				// 构建客户详细信息
				var table = document.createElement("table");
				dojo.setAttr(table, {
					class : 'details'
				});
				dojo.query("#detailForm table").empty();
				// dom.byId("detailForm").query("table").empty();
				require([ "crm/customer" ], function(customer) {
					customer.insertRow2Table(table, "开户行", detail.bank);
					customer.insertRow2Table(table, "银行账号", detail.bankAccount);
					customer.insertRow2Table(table, "国家", detail.country);
					customer.insertRow2Table(table, "省份", detail.province);
					customer.insertRow2Table(table, "城市", detail.city);
					customer.insertRow2Table(table, "传真", detail.fax);
					customer.insertRow2Table(table, "主页", detail.homepage);
					customer.insertRow2Table(table, "QQ", detail.qq);
					customer.insertRow2Table(table, "法人代表", detail.represent);
					customer.insertRow2Table(table, "税号", detail.tax);
					customer.insertRow2Table(table, "邮编", detail.postcode);
					customer.insertRow2Table(table, "客户来源", detail.customerSource);
					customer.insertRow2Table(table, "是否可用", detail.isvalid = "true" ? "是" : "否");
					customer.insertRow2Table(table, "备注", detail.memo);
					customer.insertRow2Table(table, "录入日期", detail.operateDate);
					customer.insertRow2Table(table, "业务员", detail.operateUserName);
				});
				dom.byId("detailForm").appendChild(table);
			}, function(err) {
				// TODO
			}, function(evt) {
				// TODO
			});
		},
		insertRow2Table : function(table, name, value) {
			var row = table.insertRow(-1);
			var label = row.insertCell(0);
			label.style.width = "50%";
			label.appendChild(document.createTextNode(name));
			row.insertCell(1).appendChild(document.createTextNode(value));
		},
		delCustomer : function() {
			if (!currentRowItem) {
				alert("请选择需要删除的用户!");
				return;
			}
			if (!confirm("请确认是否删除当前用户？")) {
				return;
			}
			xhr("customer/delCustomerByIDAction.do", {
				handleAs : "json",
				method : "POST",
				data : {
					customerid : currentRowItem.id
				}
			}).then(function(data) {
				var customerStore = new JsonRest({
					target : "customer/loadCustomer.do",
					idProperty : "id"
				});
				dijit.byId("customergrid").setStore(new ObjectStore({
					objectStore : customerStore
				}));
			}, function(err) {
				// TODO
			}, function(evt) {
				// TODO
			});
		},
		// 更新客户信息
		modifyCustomer : function() {
			dijit.byId("newCustomerDialog").show();
			var form = dijit.byId("customerForm");
			// dijit.byId("customertype").set("value",
			// currentRowItem.customerTypeId);
			// TODO
			// DOJO 2.0 will remove the method of dijit/form/Form :
			// setValues(), instead of set();
			form.setValues({
				customerid : currentRowItem.id,
				customername : currentRowItem.name,
				customercode : currentRowItem.code,
				customertype : currentRowItem.customerTypeId,
				category : currentRowItem.category,
				customersource : currentRowItem.customersource,
				country : currentRowItem.countryId,
				province : currentRowItem.provinceId,
				city : currentRowItem.cityId,
				officetel : currentRowItem.officetel,
				postcode : currentRowItem.postcode,
				address : currentRowItem.address,
				fax : currentRowItem.fax,
				email : currentRowItem.email,
				homepage : currentRowItem.homepage,
				represent : currentRowItem.represent,
				tax : currentRowItem.tax,
				bank : currentRowItem.bank,
				bankAccount : currentRowItem.bankAccount,
				mainProduct : currentRowItem.mainProduct,
				demand : currentRowItem.demand,
				memo : currentRowItem.memo,
				isvalid : currentRowItem.isvalid,
				operateDate : currentRowItem.operateDate,
				operateUser : currentRowItem.operateUserId
			});
		}
	};
});