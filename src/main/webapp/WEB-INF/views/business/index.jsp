<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>OMS</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

<!-- BEGIN GLOBAL MANDATORY STYLES -->
<head>
<link rel="stylesheet" type="text/css"
	href="../js/common/plugins/jquery-easyui-1.5/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="../js/common/plugins/jquery-easyui-1.5/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="../css/plugins/font-awesome/css/font-awesome.min.css">

<script type="text/javascript"
	src="../js/common/plugins/jquery-easyui-1.5/jquery.min.js"></script>
<script type="text/javascript"
	src="../js/common/plugins/jquery-easyui-1.5/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="../js/common/plugins/jquery.cookie.js"></script>

<script type="text/javascript" src="../js/custom/framework/common.js"></script>
<script type="text/javascript" src="../js/custom/framework/index.js"></script>

<style>
.listLi {
	list-style: none
}

ul {
	-webkit-padding-start: 3px;
}

.l-btn {
	text-align: left;
}

.buttonMenu {
	
}

.tabs-panels {
	border-width: 0px;
}
</style>
</head>
<body class="easyui-layout">
	<div id="topDiv" data-options="region:'north',border:false"
		style="height: 50px; background: #E6EEF8;">
		<table>
			<tr>
				<td><img src="../image/logo.png" data-options="fit:true"></td>
				<td></td>
				<td><a id="operateButton" href="#" class="easyui-menubutton"
					data-options="menu:'#menusSelect'"
					style="width: 100%; height: 100%;"><span id="userNameLabel">测试用户</span></a>
				<div id="menusSelect" style="width: 10%;">
					<div>账号设置</div>
					<div>退出</div>
				</div></td>
			</tr>
		</table>
	</div>
	<div data-options="region:'west',split:true,title:'菜单'"
		style="width: 200px;" id="westRegion">
		<div id="menuDiv" class="easyui-accordion"
			data-options="fit:true,border:false"></div>
	</div>
	<div id="bottomDiv" data-options="region:'south',border:false"
		style="height: 30px; background: #E6EEF8; padding: 10px;">south
		region</div>
	<div id="centerDiv" data-options="region:'center',title:''"
		class="easyui-layout">
		<div id="tabs" class="">
			<div id="iframe0" title="起始页" data-options="fit:true"></div>
			<!-- <div value="321" title="Documents"
				data-options="closable:true,fit:true">
				<iframe id="content_frame"
					src="http://oms.dhcc.com.cn/fairy/ord/order.do"
					style="height: 100%; width: 100%;" frameborder="no" border="0"
					marginwidth="0" marginheight="0" scrolling="yes"
					allowtransparency="yes"></iframe>
			</div>
			<div title="Help"
				data-options="iconCls:'icon-help',closable:true,fit:true"
				style="padding: 10px">
				This is the help content.<i class="fa fa-plus"></i>
			</div> -->
		</div>
	</div>


	<script type="text/javascript">
		/* $(function() {
			$("#tabs").tabs({
				/*width:$("#tabs").parent().width(),*/
				/*
				height : $("#tabs").parent().height()
			});*/
			/*
			var count = 1;
			$(".layout-button-left").click(function() {
				$(".panel-body").css('width', '');
				$(".tabs-panels").width($("#tabs").parent().width());
				$(".tabs-header").width($("#tabs").parent().width());
				if (count == 1) {
					$(".layout-button-right").click(function() {
						$(".tabs-panels").width($("#tabs").parent().width());
						$(".tabs-header").width($("#tabs").parent().width());
					});
					count = count + 1;
				}
			});
		});*/
		$('#tabs').tabs({
			onBeforeClose : function(title, index) {
				var target = $(this).tabs('getTab', title);
				var id = $(target).attr("id");
				deletePage(id);
				//获取当前选中的Tab的ID
				var selectedTab = $('#tabs').tabs('getSelected');
				var selectedID = $(selectedTab).attr("id");
				if (id == selectedID) {
					var beforeTab = $(this).tabs('getTab', index - 1);
					var beforeID = $(beforeTab).attr("id");
					var iframeID = "iframe" + beforeID;
					setLocalStorageValue("iframeID", iframeID);
				}
			},
			onSelect : function(title, index) {
				var target = $(this).tabs('getTab', title);
				var id = $(target).attr("id");
				var iframeID = "iframe" + id;
				setLocalStorageValue("iframeID", iframeID);

				$("#menuDiv").find("a.buttonMenu").css("background", "white");
				var buttonID = "secondMenu" + id;
				$("#" + buttonID).css("background", "#E0ECFF");

			}
		});

		$(document).ready(function() {
			initialMenu();
			/* var toolList = new Array();
			toolList[0] = "测试";
			createMenus(toolList); */
		});
	</script>

	<script type="text/javascript" src="../js/custom/framework/iframe.js"></script>
</body>
</html>