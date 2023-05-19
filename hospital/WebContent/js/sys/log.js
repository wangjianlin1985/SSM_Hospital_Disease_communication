	
	// 条件搜索日志信息
	function searchLog() {
		$('#dg').datagrid('load', {
			userName : $("#s_userName").val(),
			module : $("#s_module").val(),
			operation : $("#s_operation").val(),
			start : $('#s_start').datetimebox('getValue'),
			end : $('#s_end').datetimebox('getValue')
		});
	}


	
	// 日志删除
	function deleteLog() {
		var selectedRows = $("#dg").datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert('系统提示', '请选择要删除的数据！');
			return;
		}
		var strIds = [];
		for ( var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].logId);
		}
		var ids = strIds.join(",");
		$.messager.confirm("系统提示", "您确认要删除这<font color=red>" + selectedRows.length
				+ "</font>条数据吗？", function(r) {
			if (r) {
				$.post("deleteLog.htm", {ids : ids}, function(result) {
					if (result.success) {
						$.messager.alert('系统提示', "您已成功删除<font color=red>" + strIds.length + "</font>条数据！");
						$("#dg").datagrid("reload");
					} else {
						$.messager.alert('系统提示', result.errorMsg);
					}
				}, "json");
			}
		});
	}

