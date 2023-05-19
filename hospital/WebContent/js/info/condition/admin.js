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
		title : $("#s_title").val(),
		realname : $("#s_realname").val(),
		deptid : $("#s_deptid").combobox("getValue")
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

$(function() {
	var relation_id_sign = 0;
	$("#s_deptid").combobox({
		url : path + '/dept/deptData.htm',
		method : 'post',
		valueField : 'id',
		textField : 'name',
		editable : false,
		panelHeight : 'auto',
		onLoadSuccess : function() {
			if (relation_id_sign == 0) {
				var data = $(this).combobox('getData');
				data.unshift({
					'id' : '',
					'name' : '-----全部-----'
				});
				relation_id_sign++;
				$("#s_deptid").combobox("loadData", data);//重新加载数据，且当 relation_id_sign==1时加载
			}
		}
	});
});


function clearReply(){
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
	$.messager.confirm("系统提示", "您确认要清空恢复吗？", function(r) {
		if (r) {
			$.post("clearReply.htm", {
				ids : ids
			}, function(result) {
				if (result.success) {
					$.messager.alert('系统提示', "清除成功");
					$("#dg").datagrid("reload");
				} else {
					$.messager.alert('系统提示', result.errorMsg);
				}
			}, "json");
		}
	});
}