
    // 关闭角色添加页面的时候清空表单
	$(function(){
		$('#dlg').dialog({
		    onClose:function(){
				$("#fm").form('clear');
		    }
		});
	});

    // 角色查询
	function searchRole(){
		$('#dg').datagrid('load',{
			roleName:$("#s_roleName").val()
		});
	}


	// 删除角色
	function deleteRole(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert('系统提示','请选择要删除的数据！');
			return;
		} 
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].roleId);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("deleteRole.htm",{ids:ids},function(result){
					if(result.success){
						$.messager.alert('系统提示',"您已成功删除<font color=red>"+strIds.length+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示','<font color=red>'+selectedRows[result.other].roleName+'</font>'+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	var url;

	//打开角色新增框
	function openRoleAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加角色信息");
		$("#roleName").removeAttr("readonly");
		url="reserveRole.htm";
	}

	// 打开角色修改框
	function openRoleUpdateDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要编辑的数据！');
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","修改角色信息");
		$("#fm").form("load",row);
		$("#roleName").attr("readonly","readonly");
		url="reserveRole.htm?roleId="+row.roleId;
	}

	// 保存角色
	function reserveRole(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.errorMsg){
					$.messager.alert('系统提示',"<font color=red>"+result.errorMsg+"</font>");
					return;
				}else{
					$.messager.alert('系统提示','保存成功');
					closeRoleDialog();
					$("#dg").datagrid("reload");
				}
			}
		});
	}

	// 关闭新增修改框
	function closeRoleDialog(){
		$("#dlg").dialog("close");
		$("#fm").form('clear');
	}

	
	// 打开授权页面
	function openMenuDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert('系统提示','请选择一条要授权的角色！');
			return;
		}
		var row=selectedRows[0];
		roleId=row.roleId;
		$("#dlg2").dialog("open").dialog("setTitle","角色授权");
		url="chooseMenu.htm?parentId=-1&roleId="+roleId;
		// 加载所有权限的树
		$("#tree").tree({
			lines:true,
			url:url,
			checkbox:true,
			cascadeCheck:false,
			onLoadSuccess:function(){
				$("#tree").tree('expandAll');
			},
			onCheck:function(node,checked){
				if(checked){
					checkNode($('#tree').tree('getParent',node.target));
				}
			}
		});
	}

	// 递归检查勾选他的上级
	function checkNode(node){
		if(!node){
			return;
		}else{
			checkNode($('#tree').tree('getParent',node.target));
			$('#tree').tree('check',node.target);
		}
	}

	// 关闭授权框
	function closeMenuDialog(){
		$("#dlg2").dialog("close");
	}

	// 授权
	function saveMenu(){
		var nodes=$('#tree').tree('getChecked');
		var menuArrIds=[];
		for(var i=0;i<nodes.length;i++){
			menuArrIds.push(nodes[i].id);
		}
		var menuIds=menuArrIds.join(",");
		$.post("updateRoleMenu.htm",{menuIds:menuIds,roleId:roleId},function(result){
			if(result.success){
				$.messager.alert('系统提示','授权成功！');
				closeMenuDialog();
			}else{
				$.messager.alert('系统提示',result.errorMsg);
			}
		},"json");
	}