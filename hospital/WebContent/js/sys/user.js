// 关闭时清空表单
$(function() {
	$('#dlg').dialog({
		onClose : function() {
			//closeUserDialog();
			$("#fm").form('clear');
		}
	});
});

var url;
// 条件搜索用户信息
function searchUser() {
	$('#dg').datagrid('load', {
		name : $("#s_name").val(),
		roleId : $('#s_roleId').combobox("getValue")
	});
}

// 打开新增用户信息对话框
function openUserAddDialog() {
	$("#dlg").dialog("open").dialog("setTitle", "添加用户信息");
	$("#name").removeAttr("readonly");
	$("#password").val("123456");
	url = 'reserveUser.htm';
}

// 打开修改用户信息对话框
function openUserUpdateDialog() {
	var selectedRows = $("#dg").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一条要编辑的数据！');
		return;
	}
	var row = selectedRows[0];
	$("#dlg").dialog("open").dialog("setTitle", "修改用户信息");
	$("#fm").form("load", row);
	$("#name").attr("readonly", "readonly");
	url = "reserveUser.htm?id=" + row.id;
	$("#deptid").combobox("setValue",row.dept.id);
}

// 保存用户信息
function reserveUser() {
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
						closeUserDialog();
						$("#dg").datagrid("reload");
					}
				}
			});
}

// 关闭添加修改角色对话框
function closeUserDialog() {
	$("#fm").form('clear');
	$("#dlg").dialog("close");
}

// 打开选择角色对话框
function openRoleChooseDialog() {
	$("#dlg2").dialog("open").dialog("setTitle", "选择角色");
}

// 搜索角色
function searchRole() {
	$('#dg2').datagrid('load', {
		roleName : $("#s_roleName").val()
	});
}

// 关闭选择角色对话框
function closeRoleDialog() {
	$("#s_roleName").val("");
	$('#dg2').datagrid('load', {
		s_roleName : ""
	});
	$("#dlg2").dialog("close");
}

// 选择角色
function chooseRole() {
	var selectedRows = $("#dg2").datagrid('getSelections');
	if (selectedRows.length != 1) {
		$.messager.alert('系统提示', '请选择一个角色！');
		return;
	}
	var row = selectedRows[0];
	$("#roleId").val(row.roleId);
	$("#roleName").val(row.roleName);
	closeRoleDialog();
}

//双击选中
$(function() {
	$("#dg2").datagrid({
		onDblClickRow : function(rowIndex, rowData) {
			chooseRole();
		}
	});
})

// 删除用户
function deleteUser() {
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
			$.post("deleteUser.htm", {
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

// 加载选择角色数据  并添加 请选择
$(function() {
	var relation_id_sign = 0;
	$("#s_roleId").combobox({
		url : path + '/role/roleCombobox.htm',
		method : 'post',
		valueField : 'roleId',
		textField : 'roleName',
		editable : false,
		panelHeight : 'auto',
		onLoadSuccess : function() {
			if (relation_id_sign == 0) {
				var data = $(this).combobox('getData');
				data.unshift({
					'roleId' : '',
					'roleName' : '-----全部-----'
				});
				relation_id_sign++;
				$("#s_roleId").combobox("loadData", data);//重新加载数据，且当 relation_id_sign==1时加载
			}
		}
	});
});
