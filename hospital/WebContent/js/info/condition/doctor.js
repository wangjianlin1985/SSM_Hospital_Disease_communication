// 关闭时清空表单
$(function() {
	$('#dlg').dialog({
		onClose : function() {
			$("#fm").form('clear');
		}
	});
});

var url;
function searchCondition() {
	$('#dg').datagrid('load', {
		title : $("#s_title").val()
	});
}

// 查看详情
function openConditionUpdateDialog() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要查看的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "查看病情信息");
	$("#fm").form("load", row);
	$("#deptid").combobox("setValue",row.dept.id);
}


function closeConditionDialog() {
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}

