    var url;
	$(function(){

		
		// 加载左树菜单
		$("#tree").tree({
			lines:true,
			url:'menuTree.htm?parentId=-1',
			onLoadSuccess:function(){
				$("#tree").tree('expandAll');
			},	
			onClick:function(node){
				if(node.attributes.menuUrl){
					openTab(node);	
				}
			}
		});


		/**左边的菜单点击显示
			如果存在（即已经打开了），选中他
			如果不存在则打开他
		*/		
		function openTab(node){
			if($("#tabs").tabs("exists",node.text)){
				$("#tabs").tabs("select",node.text);
			}else{
				var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src="+node.attributes.menuUrl+"></iframe>"
				$("#tabs").tabs("add",{
					title:node.text,
					iconCls:node.iconCls,
					closable:true,
					content:content
				});
			}
			bindTabEvent();
		}


		bindTabEvent();
		bindTabMenuEvent();
		 
		
	});

	function bindTabEvent(){
		$(".tabs-inner").dblclick(function(){
			var subtitle = $(this).children(".tabs-title").text();
			$('#tabs').tabs('close',subtitle);
		})
		$(".tabs-inner").bind('contextmenu',function(e){
			
			$('#mm').menu('show', {
				left: e.pageX,
				top: e.pageY
			});
			var subtitle =$(this).children(".tabs-title").text();
			$('#mm').data("currtab",subtitle);
			return false;
		});
	}
			

	function bindTabMenuEvent(){
		var temp = $('#tabs');
		$('#mm-tabrefresh').click(function(){
			var currtab_title = $('#mm').data("currtab");
			var frame = temp.tabs('getTab', currtab_title).find('iframe')
			frame.attr('src',frame.attr('src'));
		});
		//关闭当前
		$('#mm-tabclose').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('#tabs').tabs('close',currtab_title);
		})
		//全部关闭
		$('#mm-tabcloseall').click(function(){
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				$('#tabs').tabs('close',t);
			});	
		});
		//关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function(){
			var currtab_title = $('#mm').data("currtab");
			$('.tabs-inner span').each(function(i,n){
				var t = $(n).text();
				if(t!=currtab_title)
					$('#tabs').tabs('close',t);
			});	
		});
		//关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function(){
			var nextall = $('.tabs-selected').nextAll();
			if(nextall.length==0){
				//msgShow('系统提示','后边没有啦~~','error');
				alert('后边没有啦~~');
				return false;
			}
			nextall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#tabs').tabs('close',t);
			});
			return false;
		});
		//关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function(){
			var prevall = $('.tabs-selected').prevAll();
			if(prevall.length==0){
				alert('到头了，前边没有啦~~');
				return false;
			}
			prevall.each(function(i,n){
				var t=$('a:eq(0) span',$(n)).text();
				$('#tabs').tabs('close',t);
			});
			return false;
		});

	}
	
	
	// 退出登录
	function logout(){
		$.messager.confirm('系统提示','您确定要退出系统吗？',function(r){
			if(r){
				window.location.href='logout.htm';
			}
		});
	}

	// 打开修改密码的窗体
	function openPasswordUpdateDialog(){
		url="updatePassword.htm";
		$("#dlg").dialog("open").dialog("setTitle","修改密码");
	}
	
	// 修改密码提交
	function updatePassword(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				var oldPassword=$("#oldPassword").val();
				var newPassword=$("#newPassword").val();
				var newPassword2=$("#newPassword2").val();
				if(!$(this).form("validate")){  //先进行easyui验证
					return false;
				}
				if(newPassword!=newPassword2){
					$.messager.alert('系统提示','确认密码输入错误！');
					return false;
				}
				if(oldPassword!=$("#opd").val()){
					$.messager.alert('系统提示','原密码错误！');
					return false;
				}
				
				return true;
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.errorMsg){
					$.messager.alert('系统提示',result.errorMsg);
					return;
				}else{
					$.messager.alert('系统提示','密码修改成功，下一次登录生效！');
					closePasswordUpdateDialog();
				}
			}
		});
	}

	//  关闭修改密码框
	function closePasswordUpdateDialog(){
		$("#dlg").dialog("close");
		$("#oldPassword").val("");
		$("#newPassword").val("");
		$("#newPassword2").val("");
	}
	
	