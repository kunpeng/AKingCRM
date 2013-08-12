require([ "dojo/_base/fx", "dojo/dom-style", "dojo/parser", "crm/menu", "crm/category", "crm/customer",
		"crm/BusinessContact", "crm/ContactMan", "crm/Product", "crm/ProductQuote", "crm/DealRecord",
		"crm/AfterVisit", "crm/ComplainBack", "crm/AffairRemind", "crm/user", "dojo/ready" ], function(
		baseFx, domStyle, parser, Menu, Category, Customer, BusinessContact, ContactMan, Product,
		ProductQuote, DealRecord, AfterVisit, ComplainBack, AffairRemind, User, ready) {

	ready(function() {
		// 等待页面加载完成后显示主界面
		parser.parse().then(function(objects) {
			baseFx.fadeOut({ // Get rid of the loader once parsing is
				// done
				node : "preloader",
				onEnd : function() {
					domStyle.set("preloader", "display", "none");
				}
			}).play();
		});

		// 加载行业分类树形
		// xhr("category/loadAll4Tree.do", {
		// handleAs : "json"
		// }).then(function(data) {
		// dom.byId("menuPane").innerHTML = data;
		// 创建树形Store

		// var treeModel = new ForestStoreModel();
		// });

		// 创建菜单
		Menu.menuInit();

		// 创建树
		Category.cateInit();

		// 创建客户列表
		Customer.custumerInit();

		// 初始化业务联系界面
		BusinessContact.initBusinessContact();

		// 初始化联系人界面
		ContactMan.initContactMan();

		// 初始化产品界面
		Product.initProduct();

		// 初始化商品报价界面
		ProductQuote.initProductQuote();

		// 初始化成交记录界面
		DealRecord.initDealRecord();

		// 初始化售后回访界面
		AfterVisit.initAfterVisit();

		// 初始化投诉反馈界面
		ComplainBack.initComplainBack();

		// 初始化业务提醒界面
		AffairRemind.initAffairRemind();

		// 绑定修改信息的按钮事件
		User.bindUpdateUserBtnEvt();

		// var treeStore = new JsonRest({
		// target : "category/loadAll4Tree.do",
		// idProperty : "id"
		// });
		//
		// var treeModel = new ForestStoreModel({
		// store : treeStore
		// });
		//
		// var tree = new Tree({
		// model : treeModel
		// });
		//
		// tree.placeAt(dom.getById("menuPane"));
		// tree.startup();
	});
});