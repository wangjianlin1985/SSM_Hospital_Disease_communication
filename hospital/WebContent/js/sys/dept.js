// 关闭时清空表单
$(function() {
	$('#dlg').dialog({
		onClose : function() {
			$("#fm").form('clear');
		}
	});
});

var url;

// 打开新增科室信息对话框
function openDeptAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle", "添加科室信息");
	url = 'reserveDept.htm';
}

// 打开修改科室信息对话框
function openDeptUpdateDialog() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改科室信息");
	$("#fm").form("load", row);
	url = "reserveDept.htm?id=" + row.id;
}

// 保存科室信息
function reserveDept() {
	$("#fm").form(
			"submit",
			{
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
						closeDeptDialog();
						$("#dg").datagrid("reload");
					}
				}
			});
}

// 关闭添加修改角色对话框
function closeDeptDialog() {
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}


// 删除科室
function deleteDept() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length == 0) {
		$.messager.alert('系统提示', '请选择要删除的数据！');
		return;
	}
	var strIds = [];
	for ( var i = 0; i < selectedRows.length; i++) {
		strIds.push(selectedRows[i].id);
	}
	var ids = strIds.join(",");
	$.messager.confirm("系统提示", "您确认要删除这<font color=red>" + selectedRows.length+ "</font>条数据吗？", function(r) {
		if (r) {
			$.post("deleteDept.htm", {
				ids : ids
			}, function(result) {
				if (result.success) {
					$.messager.alert('系统提示', "您已成功删除<font color=red>"	+ strIds.length + "</font>条数据！");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert('系统提示', result.errorMsg);
				}
			}, "json");
		}
	});
}


