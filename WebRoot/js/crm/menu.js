define([ "dijit/Menu", "dijit/MenuItem", "dijit/MenuBar", "dijit/MenuBarItem", "dijit/PopupMenuBarItem",
		"dijit/PopupMenuItem", "dijit/MenuSeparator", "crm/user", "crm/customer", "crm/BaseInfo", "crm/Help",
		"dojo/request/xhr", "crm/Constant" ], function(Menu, MenuItem, MenuBar, MenuBarItem,
		PopupMenuBarItem, PopupMenuItem, MenuSeparator, user, Customer, BaseInfo, Help, xhr, Constant) {
	return {
		// 初始化菜单
		menuInit : function() {
			// 顶部菜单
			var mainMenu = new MenuBar({
				id : "mainMenu",
				region : "top"
			}, "headerPane");

			// 文件菜单
			var fileMenu = new Menu({});

			fileMenu.addChild(new MenuItem({
				label : "注销用户",
				onClick : function(evt) {
					xhr("user/userlogout.do", {
						handleAs : "json",
						method : "POST"
					}).then(function(data) {
						console.debug("logout");
						window.location = "login.jsp";
					});
				}
			}));

			fileMenu.addChild(new MenuItem({
				label : "修改信息",
				onClick : function(evt) {
					user.updateUser();
				}
			}));

			fileMenu.addChild(new MenuSeparator({}));

			fileMenu.addChild(new MenuItem({
				label : "公司信息"
			}));

			mainMenu.addChild(new PopupMenuBarItem({
				label : "常规",
				popup : fileMenu
			}));

			// 编辑菜单
			mainMenu.addChild(new MenuBarItem({
				label : "工具"
			}));

			// 系统菜单
			var systemMenu = new Menu({});

			systemMenu.addChild(new MenuItem({
				label : "用户机构管理",
				onClick : function(evt) {
					user.userInit();
				}
			}));

			systemMenu.addChild(new MenuSeparator({}));

			var baseinfoMenu = new Menu({});
			baseinfoMenu.addChild(new MenuItem({
				label : "行业分类",
				onClick : function(evt) {
					BaseInfo.initBaseInfo("info_category_pane");
				}
			}));
			baseinfoMenu.addChild(new MenuItem({
				label : "客户类型",
				onClick : function(evt) {
					BaseInfo.initBaseInfo("info_customertype_pane");
				}
			}));
			baseinfoMenu.addChild(new MenuItem({
				label : "客户来源",
				onClick : function(evt) {
					BaseInfo.initBaseInfo("info_customersource_pane");
				}
			}));
			baseinfoMenu.addChild(new MenuSeparator({}));
			baseinfoMenu.addChild(new MenuItem({
				label : "洽谈主题",
				onClick : function(evt) {
					BaseInfo.initBaseInfo("info_activitytheme_pane");
				}
			}));
			baseinfoMenu.addChild(new MenuItem({
				label : "洽谈方式",
				onClick : function(evt) {
					BaseInfo.initBaseInfo("info_contactway_pane");
				}
			}));

			systemMenu.addChild(new PopupMenuItem({
				label : "基础信息维护",
				popup : baseinfoMenu
			}))
			systemMenu.addChild(new MenuSeparator({}));
			systemMenu.addChild(new MenuItem({
				label : "系统注册",
				onClick : function(evt) {
					window.open('register.jsp', '_self');
				}
			}));

			if (Constant.roleType == 'ADMIN') {
				mainMenu.addChild(new PopupMenuBarItem({
					label : "系统管理",
					popup : systemMenu
				}));
			}

			// 系统维护菜单
			var maintainMenu = new Menu({});

			var baseDataMenu = new Menu({});
			baseDataMenu.addChild(new MenuItem({
				label : "地区数据初始化",
				onClick : function(evt) {
					xhr("system/initData.do", {
						handleAs : "json",
						method : "POST"
					}).then(function(data) {
						alert("地区数据已初始化至数据库");
					});
				}
			}));

			maintainMenu.addChild(new PopupMenuItem({
				label : "基础数据维护",
				popup : baseDataMenu
			}));

			if (Constant.roleType == 'ADMIN') {
				mainMenu.addChild(new PopupMenuBarItem({
					label : "系统维护",
					popup : maintainMenu
				}));
			}

			// 帮助菜单
			var helpMenu = new Menu({});

			helpMenu.addChild(new MenuItem({
				label : "访问官方主页",
				onClick : function(evt) {
					window.open('http://www.isoftsky.com', '_blank');
				}
			}));

			helpMenu.addChild(new MenuSeparator({}));

			helpMenu.addChild(new MenuItem({
				label : "在线帮助",
				onClick : function(evt) {
					window.open('http://www.isoftsky.com', '_blank');
				}
			}));

			helpMenu.addChild(new MenuItem({
				label : "本地使用手册",
				onClick : function(evt) {
					Help.initManual();
				}
			}));

			helpMenu.addChild(new MenuSeparator({}));

			helpMenu.addChild(new MenuItem({
				label : "更新日志",
				onClick : function(evt) {
					Help.initLatest();
				}
			}));

			helpMenu.addChild(new MenuItem({
				label : "关于",
				onClick : function(evt) {
					Help.showAbout();
				}
			}));

			mainMenu.addChild(new PopupMenuBarItem({
				label : "帮助",
				popup : helpMenu
			}));

			mainMenu.startup();
			// fileMenu.startup();

			// 用户列表行菜单
			// var customerRowMenu = new Menu({
			// id : "customerRowMenu"
			// });
			// customerRowMenu.addChild(new MenuItem({
			// label : "新增用户",
			// onClick : function() {
			// dijit.byId("customerForm").reset();
			// dijit.byId("newCustomerDialog").show();
			// }
			// }));
			// customerRowMenu.addChild(new MenuItem({
			// label : "修改用户",
			// onClick : function() {
			// Customer.modifyCustomer();
			// }
			// }));
			// customerRowMenu.addChild(new MenuItem({
			// label : "删除用户",
			// id : "delCustomerMenu",
			// onClick : function() {
			// Customer.delCustomer();
			// }
			// }));
			//
			// customerRowMenu.startup();
		}
	};
});