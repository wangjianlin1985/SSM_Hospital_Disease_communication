var url; // 菜单添加和修改的url
var url2; // 按钮添加和修改的url

$(function() {

	// 加载树形表格菜单
	$('#treeGrid').treegrid({
		url : 'treeGridMenu.htm?parentId=-1',
		onLoadSuccess : function() {
			$("#treeGrid").treegrid('expandAll');
		}
	});

	// 默认图标
	$('#dlg').dialog({
		onClose : function() {
			$("#fm").form('clear');
			$("#iconCls").val("icon-item");
		}
	});

	// 清空按钮的表单
	$('#operationReserveDlg').dialog({
		onClose : function() {
			$("#fm2").form('clear');
		}
	});
});

// 格式化菜单图标，将图标展示出来，暂未用
function formatIconCls(value, row, index) {
	return "<div class=" + value + ">&nbsp;</div>";
}

// 打开菜单添加对话框
function openMenuAddDialog() {
	var node = $('#treeGrid').treegrid('getSelected');
	if (node == null) {
		$.messager.alert('系统提示', '请选择一个父菜单节点！');
		return;
	}
	$("#selIconTD").html("<div onclick='selIcon();' >点击选择</div>");
	$("#dlg").dialog("open").dialog("setTitle", "添加菜单");
	url = "reserveMenu.htm?parentId=" + node.id;
}

// 打开菜单修改对话框
function openMenuUpdateDialog() {
	var node = $('#treeGrid').treegrid('getSelected');
	if (node == null) {
		$.messager.alert('系统提示', '请选择一个要修改的菜单！');
		return;
	}
	/**
	 * 构造菜单的图标，并加上点击事件
	 */
	var str = "";
	str += "<div id='' class='easyui-linkbutton l-btn' iconcls='" + node.iconCls + "' group=''  onclick='selIcon();'>";
	str += "<span class='l-btn-left'>";
	str += "<span class='l-btn-text " + node.iconCls + " l-btn-icon-left'>&nbsp;&nbsp;&nbsp;&nbsp;</span></span></div>";
	$("#selIconTD").html(str);
	$("#dlg").dialog("open").dialog("setTitle", "修改菜单");
	$("#fm").form("load", node);
	$("#menuName").val(node.text);
	url = "reserveMenu.htm?menuId=" + node.id;
}

// 删除菜单
function deleteMenu() {
	var node = $("#treeGrid").treegrid('getSelected');
	if (node == null) {
		$.messager.alert('系统提示', '请选择一个要删除的菜单节点！');
		return;
	}
	var parentNode = $("#treeGrid").treegrid('getParent', node.id);
	$.messager.confirm("系统提示", "您确认要删除这个菜单节点吗?", function(r) {
		if (r) {
			$.post("deleteMenu.htm", {
				menuId : node.id,
				parentId : parentNode.id
			}, function(result) {
				if (result.success) {
					$.messager.alert('系统提示', "您已成功删除这个菜单节点！");
					$("#treeGrid").treegrid("reload");
				} else {
					$.messager.alert('系统提示', result.errorMsg);
				}
			}, "json");
		}
	});
}

// 菜单添加或修改的保存
function reserveMenu() {
	$("#fm").form("submit",{
				url : url,
				onSubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.errorMsg) {
						$.messager.alert('系统提示', "<font color=red>"+ result.errorMsg + "</font>");
						return;
					} else {
						$.messager.alert('系统提示', '保存成功');
						$('#treeGrid').treegrid('reload');
						closeMenuDialog();
					}
				}
			});
}


// 关闭添加或修改菜单页面的对话框
function closeMenuDialog() {
	$("#dlg").dialog("close");
	$("#fm").form('clear');
	$("#iconCls").val("icon-item");
}

// 打开按钮列表对话框，同时datagrid加载按钮数据
function openOperationDialog() {
	var selectedRows = $("#treeGrid").treegrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#operationDlg").dialog("open").dialog("setTitle", "按钮管理");
	var url = path + "/operation/operationList.htm?menuId=" + row.id;
	$('#operationTable').datagrid({
		nowrap : false,// 设置为true，当数据长度超出列宽时将会自动截取
		striped : true,// 设置为true将交替显示行背景。
		collapsible : true,// 显示可折叠按钮
		url : url,// url调用Action方法
		singleSelect : false,// 为true时只能选择单行
		fitColumns : true,// 允许表格自动缩放，以适应父容器
		remoteSort : false,
		pagination : true,// 分页
		rownumbers : true
	// 行数
	});
}

// 打开按钮添加对话框页面
function openOperationAddDialog() {
	var node = $('#treeGrid').treegrid('getSelected');
	if (node == null) {
		$.messager.alert('系统提示', '请选择一个父菜单节点！');
		return;
	}
	$("#o_menuName").val(node.text);
	$("#o_menuId").val(node.id);
	$("#operationReserveDlg").dialog("open").dialog("setTitle", "添加菜单");
	url2 = path + "/operation/reserveOperation.htm";

}

// 打开按钮修改对话框页面
function openOperationUpdateDialog() {
	var selectedRows = $("#operationTable").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#operationReserveDlg").dialog("open").dialog("setTitle", "修改角色信息");
	$("#fm2").form("load", row);
	$("#o_menuName").attr("readonly", "readonly");
	url2 = path + "/operation/reserveOperation.htm?operationId=" + row.operationId;
}

// 按钮的添加或修改的提交保存方法
function reserveOperation() {
	$("#fm2").form("submit",{
				url : url2,
				onSubmit : function() {
					return $(this).form("validate");
				},
				success : function(result) {
					var result = eval('(' + result + ')');
					if (result.errorMsg) {
						$.messager.alert('系统提示', "<font color=red>" + result.errorMsg + "</font>");
						return;
					} else {
						$.messager.alert('系统提示', '保存成功');
						$("#operationTable").datagrid("reload");
						closeOperationReserveDialog();
					}
				}
			});
}

// 关闭按钮添加或修改对话框页面
function closeOperationReserveDialog() {
	$("#operationReserveDlg").dialog("close");
	$("#fm2").form('clear');
}

// 删除按钮
function deleteOperation() {
	var selectedRows = $("#operationTable").datagrid('getSelections');
	if (selectedRows.length == 0) {
		$.messager.alert('系统提示', '请选择要删除的数据！');
		return;
	}
	var strIds = [];
	for ( var i = 0; i < selectedRows.length; i++) {
		strIds.push(selectedRows[i].operationId);
	}
	var ids = strIds.join(",");
	$.messager.confirm("系统提示", "您确认要删除这<font color=red>" + selectedRows.length + "</font>条数据吗？", function(r) {
		if (r) {
			$.post(path + "/operation/deleteOperation.htm", {ids : ids}, 
				function(result) {
					if (result.success) {
						$.messager.alert('系统提示', "您已成功删除<font color=red>"	+ strIds.length + "</font>条数据！");
						$("#operationTable").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
				}
			}, "json");
		}
	});
}

// 打开图标选择列表
function selIcon() {
	$("#iconDiv").dialog("open").dialog("setTitle", "选择图标");

}

/**
 * 选中图标的方法 
 * 	1 将隐藏域中的图标框 赋值 
 * 	2 关闭图标选择列表 
 * 	3 在原有的‘选择图标’处生成选择的图标样式
 */
function subIcon(icon) {
	$("#iconCls").val(icon);
	$("#iconDiv").dialog("close");
	var str = "";
	str += "<div id='' class='easyui-linkbutton l-btn' iconcls='" + icon + "' group='' onclick='selIcon();'>";
	str += "<span class='l-btn-left'>";
	str += "<span class='l-btn-text " + icon + " l-btn-icon-left'>&nbsp;&nbsp;&nbsp;&nbsp;</span></span></div>";
	$("#selIconTD").html(str);
}
