define([ "dojo/dom", "dojo/store/Memory", "dijit/tree/ObjectStoreModel", "dijit/Tree", "dojo/store/JsonRest",
		"dojo/data/ObjectStore", ], function(dom, Memory, ObjectStoreModel, Tree, JsonRest, ObjectStore) {
	return {
		cateInit : function() {

			var cateTreeStore = new JsonRest({
				target : "category/loadCateByPid.do",
				idProperty : "id",
				getChildren : function(object) {
					return this.query({
						parentid : object.id
					});
				}
			});

			var model = new ObjectStoreModel({
				store : cateTreeStore,
				root : {
					id : "root",
					name : "行业分类"
				},
				mayHaveChildren : function(object) {
					if (object.children == "true") {
						return false;
					} else {
						return true;
					}
				}
			});

			// 创建树
			var tree = new Tree({
				model : model,
				onClick : function(item, node, evt) {
					console.debug(item.id);
					var customerStore = new JsonRest({
						target : "customer/loadByCate.do?cateid=" + item.id,
						idProperty : "id"
					});
					dijit.byId("customergrid").setStore(new ObjectStore({
						objectStore : customerStore
					}));
				}
			}).placeAt(dom.byId("menuPane"));
			
			tree.expandAll();
		}

	};
});