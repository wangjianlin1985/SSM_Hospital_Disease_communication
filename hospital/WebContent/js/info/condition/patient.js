// 关闭时清空表单
$(function() {
	$('#dlg').dialog({
		onClose : function() {
			$("#fm").form('clear');
		}
	});
});

var url;

// 打开新增病情信息对话框
function openConditionAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle", "添加病情信息");
	url = 'reserveCondition.htm';
}

// 打开修改病情信息对话框
function openConditionUpdateDialog() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改病情信息");
	$("#fm").form("load", row);
	url = "reserveCondition.htm?id=" + row.id;
	$("#deptid").combobox("setValue",row.dept.id);
}

// 保存病情信息
function reserveCondition() {
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
						closeConditionDialog();
						$("#dg").datagrid("reload");
					}
				}
			});
}

function closeConditionDialog() {
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}




function deleteCondition() {
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
			$.post("deleteCondition.htm", {
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


