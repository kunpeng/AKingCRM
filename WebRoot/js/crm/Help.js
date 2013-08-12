define([ "dojo/dom", "dijit/layout/BorderContainer", "dijit/layout/ContentPane", "dojo/on" ], function(dom,
		BorderContainer, ContentPane, on) {
	return {
		initManual : function() {
			var mainTabs = dijit.byId("mainPane");
			var manualContainer = dijit.byId("manualContainer");
			if (manualContainer) {
				// mainTabs.addChild(userContainer);
				mainTabs.selectChild(manualContainer);
				return;
			}
			// 第一次打开界面，初始化
			manualContainer = new ContentPane({
				id : "manualContainer",
				title : "在线使用手册",
				closable : true,
				href : "docs/help.html"
			});
			mainTabs.addChild(manualContainer);
			mainTabs.selectChild(manualContainer);
		},
		initLatest : function() {
			var mainTabs = dijit.byId("mainPane");
			var latestContainer = dijit.byId("latestContainer");
			if (latestContainer) {
				// mainTabs.addChild(userContainer);
				mainTabs.selectChild(latestContainer);
				return;
			}
			// 第一次打开界面，初始化
			latestContainer = new ContentPane({
				id : "latestContainer",
				title : "更新日志",
				closable : true,
				href : "docs/latest.html"
			});
			mainTabs.addChild(latestContainer);
			mainTabs.selectChild(latestContainer);
		},
		showAbout : function() {
			on(dom.byId("closeAbout"), "click", function() {
				dijit.byId("aboutDialog").hide();
			});
			dijit.byId("aboutDialog").show();
		}
	};
});