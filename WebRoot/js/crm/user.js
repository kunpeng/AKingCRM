define([ "dojo/dom", "dijit/layout/BorderContainer", "dijit/layout/ContentPane",
		"cbtree/models/ForestStoreModel", "cbtree/Tree", "dojo/data/ItemFileWriteStore",
		"dojo/data/ObjectStore", "dojo/request/xhr", "dojo/store/JsonRest", "dojox/grid/EnhancedGrid",
		"dojo/on", "dojo/dom-form", "crm/Constant" ], function(dom, BorderContainer, ContentPane,
		ForestStoreModel, Tree, ItemFileWriteStore, ObjectStore, xhr, JsonRest, EnhancedGrid, on, domForm,
		Constant) {
	var currentOrg = "";
	var saveUserButtonHandle = "";
	var cancelUserButtonHandle = "";
	var currentUser = "";
	return {
		userInit : function() {
			// console.log("click userContainer", "");
			var mainTabs = dijit.byId("mainPane");
			var userContainer = dijit.byId("userContainer");
			if (userContainer) {
				// mainTabs.addChild(userContainer);
				mainTabs.selectChild(userContainer);
				return;
			}
			// 第一次打开界面，初始化
			userContainer = new BorderContainer({
				id : "userContainer",
				title : "用户管理",
				closable : true
			});

			// 机构页面 --分割菜单栏与机构树
			var orgPane = new BorderContainer({
				id : "orgPane",
				region : "left",
				splitter : true,
				gutters : false
			});

			var orgToolbarPane = new ContentPane({
				id : "orgToolbarPane",
				region : "top",
				content : "<div id='orgToolBar'></div>"
			});

			var orgTreePane = new ContentPane({
				id : "orgTreePane",
				region : "center",
				content : "<div id='orgTree'></div>"
			});

			orgPane.addChild(orgToolbarPane);
			orgPane.addChild(orgTreePane);

			// 用户列表页面
			var userLayout = new BorderContainer({
				region : "center"
			});

			// 将用户列表分割为工具栏与列表页面
			var userPane = new BorderContainer({
				id : "userPane",
				region : "left",
				splitter : true,
				gutters : false
			});

			var userToolbarPane = new ContentPane({
				id : "userToolbarPane",
				region : "top",
				content : "<div id='userToolbar'></div>"
			});

			var userGridPane = new ContentPane({
				id : "userGridPane",
				region : "center",
				content : "<div id='userGridDiv'></div>"
			});

			userPane.addChild(userToolbarPane);
			userPane.addChild(userGridPane);

			// 用户信息和权限信息页面
			var infoLayout = new BorderContainer({
				id : "infoLayout",
				region : "center"
			});

			var infoPane = new ContentPane({
				id : "infoPane",
				region : "top",
				href : "org.jsp",
				onDownloadEnd : function() {
					on(dom.byId("saveOrgButton"), "click", function() {
						xhr("org/saveOrg.do", {
							handleAs : "json",
							method : "POST",
							data : domForm.toObject(dom.byId("orgForm"))
						}).then(function(data) {
							dijit.byId("orgForm").setValues({
								orgid : data.id
							});
							require([ "crm/user" ], function(User) {
								User.getOrgTree();
							});
						});
					});
				}
			});

			var authPane = new ContentPane({
				id : "authPane",
				region : "center",
				// splitter : true,
				href : "user.jsp",
				onDownloadEnd : function() {
					// TODO 动态绑定事件
					require([ "crm/user" ], function(User) {
						User.onClick();
					});
				}
			});

			infoLayout.addChild(infoPane);
			infoLayout.addChild(authPane);

			userLayout.addChild(userPane);
			userLayout.addChild(infoLayout);
			// userLayout.addChild(authPane);

			userContainer.addChild(orgPane);
			userContainer.addChild(userLayout);

			mainTabs.addChild(userContainer);
			mainTabs.selectChild(userContainer);

			require([ "crm/user" ], function(User) {
				User.initOrgTree();
			});

			require([ "crm/user" ], function(User) {
				User.initUserGrid();
			});

			// 绑定用户提交事件
			// infoPane.startup();
			// console.debug(dijit.byId("saveUserButton"));

			// require([ "crm/user", "dojo/domReady!" ], function(User) {
			// User.onClick();
			// })
		},
		initOrgTree : function() {
			// 创建菜单
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var orgToolbar = new Toolbar({}, "orgToolBar");
				// 新增用户菜单
				orgToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						if (!currentOrg) {
							alert("请选择所属机构！");
							return;
						}
						var orgForm = dijit.byId("orgForm");
						orgForm.reset();
						orgForm.setValues({
							parentid : currentOrg.id,
							parentname : currentOrg.name
						});
					}
				}));
				// 删除用户菜单
				orgToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!currentOrg) {
							alert("请选择一个机构！");
							return;
						}
						if (currentOrg.root) {
							alert("不能删除根节点!");
							return;
						}
						if (!confirm("确定要删除当前选择的机构吗？")) {
							return;
						}
						xhr("org/delOrg.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentOrg.id
							}
						}).then(function(data) {
							require([ "crm/user" ], function(User) {
								User.getOrgTree();
							});
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
			});

			require([ "crm/user" ], function(User) {
				User.getOrgTree();
			});
		},
		checkboxClicked : function(item, nodeWidget, evt) {
			// console.debug(item);
			// console.debug(nodeWidget);
			// console.debug(evt);
		},
		getOrgTree : function() {
			dojo.query("#orgTree").empty();
			xhr("org/loadAll4Tree.do", {
				handleAs : "json",
				method : "POST"
			}).then(function(data) {
				var treedata = {
					"identifier" : "id",
					"label" : "name",
					"items" : data
				};
				// console.debug(treedata);

				var orgStore = new ItemFileWriteStore({
					data : treedata
				});

				var model = new ForestStoreModel({
					store : orgStore,
					query : {
						type : "parent"
					},
					// rootLabel : "机构列表",
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
						require([ "crm/user" ], function(User) {
							User.checkboxClicked(item, nodeWidget, evt);
						});
					},
					onClick : function(item, node, evt) {
						// console.debug(item.id);
						// currentorgid = item.id;
						currentOrg = item;
						// if (item.root) {
						// return;
						// }
						// dijit.byId("infoPane").set('href', 'org.jsp');
						require([ "crm/user" ], function(User) {
							User.refreshUserGrid(item.id);
						});

						dijit.byId("orgForm").setValues({
							orgid : currentOrg.id,
							parentid : currentOrg.parentid,
							parentname : currentOrg.parentname,
							name : currentOrg.name,
							code : currentOrg.code
						});
					}
				}).placeAt(dom.byId("orgTree"));
				tree.startup();
			}, function(err) {
				console.log("2", err, err);
			}, function(evt) {
				console.log("3", evt, evt);
			});
		},
		initUserGrid : function() {
			// 创建菜单
			require([ "dijit/Toolbar", "dijit/form/Button" ], function(Toolbar, Button) {
				var userToolbar = new Toolbar({}, "userToolbar");
				// 新增用户菜单
				userToolbar.addChild(new Button({
					label : '新增',
					iconClass : "dijitEditorIcon dijitEditorIconNewPage",
					onClick : function() {
						var userForm = dijit.byId("userForm");
						userForm.reset();
						if (!currentOrg) {
							alert("请选择所属机构！");
							return;
						}
						userForm.setValues({
							orgid : currentOrg.id,
							org : currentOrg.code
						});
					}
				}));
				// 删除用户菜单
				userToolbar.addChild(new Button({
					label : '删除',
					iconClass : "dijitEditorIcon dijitEditorIconDelete",
					onClick : function() {
						if (!currentUser) {
							alert("请选择一个用户！");
							return;
						}
						if (!confirm("确定要删除当前选择的用户吗？")) {
							return;
						}
						xhr("user/delUser.do", {
							handleAs : "json",
							method : "POST",
							data : {
								ids : currentUser.id
							}
						}).then(function(data) {
							currentUser = "";
							// 刷新用户列表
							var userStore = new JsonRest({
								target : "user/loadListByOrgAction.do?orgid=" + currentOrg.id,
								idProperty : "id"
							});
							dijit.byId("usergrid").setStore(new ObjectStore({
								objectStore : userStore
							}));
							// dijit.byId("userForm").reset();
						}, function(err) {
							console.log("2", err, err);
						}, function(evt) {
							console.log("3", evt, evt);
						});
					}
				}));
			});

			var layout = [ {
				name : "用户名称",
				field : "name",
				width : "100px"
			}, {
				name : "用户代码",
				field : "code",
				width : "60px"
			} ];

			// 创建用户列表
			var usergrid = new EnhancedGrid({
				id : "usergrid",
				structure : layout
			// onRowClick : function(rowIndex) {
			// console.debug(rowIndex);
			// require([ "crm/user" ], function(User) {
			// currentUser = usergrid.getItem(rowIndex);
			// User.refreshUserForm(currentUser);
			// });
			// },
			// onCellClick : function(e) {
			// console: debug(e);
			// }
			}, "userGridDiv");
			usergrid.startup();

			dojo.connect(usergrid, "onRowClick", function(e) {
				var rowIndex = e.rowIndex;
				// dijit.byId("infoPane").set('href', 'user.jsp');
				require([ "crm/user" ], function(User) {
					currentUser = usergrid.getItem(rowIndex);
					User.refreshUserForm(currentUser);
				});
			});
		},
		refreshUserGrid : function(orgid) {
			var userStore = new JsonRest({
				target : "user/loadListByOrgAction.do?orgid=" + orgid,
				idProperty : "id"
			});
			dijit.byId("usergrid").setStore(new ObjectStore({
				objectStore : userStore
			}));
		},
		refreshUserForm : function(item) {
			// console.debug(item);
			var userform = dijit.byId("userForm");
			userform.setValues({
				userid : item.id,
				org : item.orgCode,
				orgid : item.orgId,
				username : item.name,
				usercode : item.code,
				userpass : item.password,
				userpassconfirm : item.password,
				roletype : item.roleType
			});
		},
		// 绑定按钮事件
		onClick : function() {
			// if (saveUserButtonHandle) {
			// saveUserButtonHandle.remove();
			// }
			// if (cancelUserButtonHandle) {
			// cancelUserButtonHandle.remove();
			// }

			on(dom.byId("saveUserButton"), "click", function() {
				xhr("user/saveUser.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("userForm"))
				}).then(function(data) {
					dijit.byId("userForm").setValues({
						userid : data.id
					});
					// 刷新用户列表
					var userStore = new JsonRest({
						target : "user/loadListByOrgAction.do?orgid=" + currentOrg.id,
						idProperty : "id"
					});
					dijit.byId("usergrid").setStore(new ObjectStore({
						objectStore : userStore
					}));
					// dijit.byId("userForm").reset();
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});

			// 绑定取消按钮事件
			on(dom.byId("cancelUserButton"), "click", function() {
			});
		},
		updateUser : function() {
			dijit.byId("userDialog").show();
			// 显示用户原始信息
			dijit.byId("updateUserForm").setValues({
				userid : Constant.userId,
				usercode : Constant.userCode,
				username : Constant.userName
			});
		},
		bindUpdateUserBtnEvt : function() {
			on(dom.byId("updateUserBtn"), "click", function() {
				var formValues = dijit.byId("updateUserForm").getValues();
				// var olduserpass =
				// dijit.byId("updateUserForm").get('olduserpass');
				// var userpass = dijit.byId("updateUserForm").get('userpass');
				// var userpassconfirm =
				// dijit.byId("updateUserForm").get('userpassconfirm');
				var userpass = formValues.userpass;
				var userpassconfirm = formValues.userpassconfirm;
				if (userpass != userpassconfirm) {
					alert("您输入的两次新密码不一致,请重新输入");
					return;
				}

				xhr("user/updateUser.do", {
					handleAs : "json",
					method : "POST",
					data : domForm.toObject(dom.byId("updateUserForm"))
				}).then(function(data) {
					if (data.success == 'failure') {
						alert(data.info);
						return;
					}
					alert(data.info + "，请使用新密码重新登录！");
					// 需求用户重新登录
					window.open('login.jsp', '_self');
				}, function(err) {
					console.log("2", err, err);
				}, function(evt) {
					console.log("3", evt, evt);
				});
			});
		}
	};
});