<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String userId = (String) session.getAttribute("userid");
	String userCode = (String) session.getAttribute("usercode");
	String userName = (String) session.getAttribute("username");
	String roleType = (String) session.getAttribute("roletype");
	String isDefaultAdmin = (String) session.getAttribute("isdefaultadmin");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<link rel="shortcut icon" href="favicon.ico" />
<link rel="stylesheet" type="text/css" href="css/main.css">
<link rel="stylesheet" type="text/css" href="css/user.css">
<link rel="stylesheet" type="text/css" href="css/customer.css">
<link rel="stylesheet" type="text/css" href="css/businesscontact.css">
<link rel="stylesheet" type="text/css" href="css/contactman.css">
<link rel="stylesheet" type="text/css" href="css/product.css">
<link rel="stylesheet" type="text/css" href="css/productquote.css">
<link rel="stylesheet" type="text/css" href="css/dealrecord.css">
<link rel="stylesheet" type="text/css" href="css/aftervisit.css">
<link rel="stylesheet" type="text/css" href="css/complainback.css">
<link rel="stylesheet" type="text/css" href="css/affairremind.css">
<script type="text/javascript">
	var dojoConfig = {
		baseUrl : "js/",
		tlmSiblingOfDojo : false,
		packages : [ {
			name : "dojo",
			location : "lib/dojo"
		}, {
			name : "dijit",
			location : "lib/dijit"
		}, {
			name : "dojox",
			location : "lib/dojox"
		}, {
			name : "crm",
			location : "crm"
		}, {
			name : "cbtree",
			location : "lib/cbtree"
		} ]
	}
</script>
<script type="text/javascript" src="js/lib/dojo/dojo.js"
	data-dojo-config="async:true,isDebug:true"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript">
	require([ "crm/Constant" ], function(Constant) {
		Constant.userId ="<%=userId%>";
		Constant.userCode ="<%=userCode%>";
		Constant.userName ="<%=userName%>";
		Constant.roleType ="<%=roleType%>";
		Constant.isDefaultAdmin ="<%=isDefaultAdmin%>";
	})
</script>
</head>
<body class="claro">
	<div id="preloader">正在加载...</div>
	<div data-dojo-type="dijit/layout/BorderContainer" id="mainContainer">
		<!-- 顶部菜单 -->
		<div data-dojo-type="dijit/layout/ContentPane" id="headerPane"
			data-dojo-props="region:'top'">
			<div style="float: right; padding-right: 10px; padding-top: 5px;">
				当前用户:<%=userName%></div>
		</div>
		<!-- 行业分类树形显示 -->
		<div data-dojo-type="dijit/layout/ContentPane" id="menuPane"
			style="padding: 0px;"
			data-dojo-props="splitter:true, minSize:200, region:'leading'"></div>
		<!-- 客户信息主界面 -->
		<div data-dojo-type="dijit/layout/TabContainer" id="mainPane"
			data-dojo-props="region:'center'">
			<div data-dojo-type="dijit/layout/BorderContainer"
				id="customerContainer" data-dojo-props="title:'客户列表'">
				<!-- 客户信息列表Grid -->
				<div data-dojo-type="dijit/layout/BorderContainer"
					id="customerGridContainer"
					data-dojo-props="region:'center',gutters:false">
					<div data-dojo-type="dijit/layout/ContentPane" style="padding: 0px"
						data-dojo-props="region:'top'">
						<div id="customerGridToolbar"></div>
					</div>
					<div data-dojo-type="dijit/layout/ContentPane"
						style="padding: 0px; border: 0px"
						data-dojo-props="region:'center'">
						<div id="customerGridDiv"></div>
					</div>
				</div>
				<!-- 客户详细信息 -->
				<div data-dojo-type="dijit/layout/ContentPane" id="detailForm"
					data-dojo-props="splitter:true, region:'right'"></div>
				<!-- 客户信息操作 -->
				<div data-dojo-type="dijit/layout/TabContainer"
					id="operateContainer"
					data-dojo-props="splitter:true, region:'bottom',tabPosition:'left-h'">
					<div data-dojo-type="dijit/layout/BorderContainer"
						id="contactGridContainer"
						data-dojo-props="title:'业务联系',gutters : false">
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px" data-dojo-props="region:'top'">
							<div id="contactGridToolbar"></div>
						</div>
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px; border: 0px"
							data-dojo-props="region:'center'">
							<div id="contactGridDiv"></div>
						</div>
					</div>
					<div data-dojo-type="dijit/layout/BorderContainer"
						id="contactManGridContainer"
						data-dojo-props="title:'联系人',gutters:false">
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px" data-dojo-props="region:'top'">
							<div id="contactManGridToolbar"></div>
						</div>
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px; border: 0px"
							data-dojo-props="region:'center'">
							<div id="contactManGridDiv"></div>
						</div>
					</div>
					<div data-dojo-type="dijit/layout/BorderContainer"
						id="productGridContainer"
						data-dojo-props="title:'客户产品',gutters:false">
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px" data-dojo-props="region:'top'">
							<div id="productGridToolbar"></div>
						</div>
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px; border: 0px"
							data-dojo-props="region:'center'">
							<div id="productGridDiv"></div>
						</div>
					</div>
					<div data-dojo-type="dijit/layout/BorderContainer"
						id="productQuoteGridContainer"
						data-dojo-props="title:'商品报价',gutters:false">
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px" data-dojo-props="region:'top'">
							<div id="productQuoteGridToolbar"></div>
						</div>
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px; border: 0px"
							data-dojo-props="region:'center'">
							<div id="productQuoteGridDiv"></div>
						</div>
					</div>
					<div data-dojo-type="dijit/layout/BorderContainer"
						id="dealRecordGridContainer"
						data-dojo-props="title:'成交记录',gutters:false">
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px" data-dojo-props="region:'top'">
							<div id="dealRecordGridToolbar"></div>
						</div>
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px; border: 0px"
							data-dojo-props="region:'center'">
							<div id="dealRecordGridDiv"></div>
						</div>
					</div>
					<div data-dojo-type="dijit/layout/BorderContainer"
						id="afterVisitGridContainer"
						data-dojo-props="title:'售后回访',gutters:false">
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px" data-dojo-props="region:'top'">
							<div id="afterVisitGridToolbar"></div>
						</div>
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px; border: 0px"
							data-dojo-props="region:'center'">
							<div id="afterVisitGridDiv"></div>
						</div>
					</div>
					<div data-dojo-type="dijit/layout/BorderContainer"
						id="complainBackGridContainer"
						data-dojo-props="title:'投诉反馈',gutters:false">
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px" data-dojo-props="region:'top'">
							<div id="complainBackGridToolbar"></div>
						</div>
						<div data-dojo-type="dijit/layout/ContentPane"
							style="padding: 0px; border: 0px"
							data-dojo-props="region:'center'">
							<div id="complainBackGridDiv"></div>
						</div>
					</div>
					<!-- 
					<div data-dojo-type="dijit/layout/ContentPane"
						id="affairRemindGridContainer" data-dojo-props="title:'业务提醒'">
						<div id="affairRemindGridDiv"></div>
					</div>
					 -->
				</div>
			</div>
		</div>
		<!-- 底部状态栏 -->
		<div data-dojo-type="dijit/layout/ContentPane" id="footerPane"
			data-dojo-props="region:'bottom'">
			<div style="float: left">
				<label>当前操作：</label>
			</div>
			<div style="float: right">
				<label>Copyright © AKing Studio Powered by <a
					target="_blank" href="http://www.isoftsky.com/">AKing</a>&nbsp;&nbsp;@<a
					target="_blank" href="http://www.isoftsky.com/">AKing CRM</a>
				</label>
			</div>
		</div>
	</div>
	<div style="display: none"><%@ include file="customer.jsp"%></div>
	<div style="display: none"><%@ include file="businesscontact.jsp"%></div>
	<div style="display: none"><%@ include file="contactman.jsp"%></div>
	<div style="display: none"><%@ include file="product.jsp"%></div>
	<div style="display: none"><%@ include file="productquote.jsp"%></div>
	<div style="display: none"><%@ include file="dealrecord.jsp"%></div>
	<div style="display: none"><%@ include file="aftervisit.jsp"%></div>
	<div style="display: none"><%@ include file="complainback.jsp"%></div>
	<div style="display: none"><%@ include file="docs/about.jsp"%></div>
	<div style="display: none"><%@ include file="userinfo.jsp"%></div>
</body>
</html>