<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>病情管理（管理员）</title>
   	<script type="text/javascript" src="${path }/js/info/condition/admin.js"></script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="adminList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="fidel:'id',hidden:'true'">编号</th>
                	<th field="title" width="240" align="center">标题</th>
                	<th field="br" width="60" align="center" formatter="formatPatient">病人</th>
                	<th field="time" width="60" align="center">时间</th>
                	<th field="deptnAME" width="60" align="center" formatter="formatDept">所属科室</th>
                	<th field="aa" width="60" align="center" formatter="formatJL">交流记录</th>
            	</tr>
        </thead>
</table>
<script type="text/javascript">
	function formatDept(value,row,index){
		if(row.dept != null){
			return row.dept.name;
		}
	}

	function formatPatient(value,row,index){
		if(row.user != null){
			return row.user.realname;
		}
	}
	
	function formatJL(value,row,inedx){
		return "<a onclick='openNew(\""+row.id+"\")'>交流记录</a>";
	}
	
	function openNew(id){
		window.open( path + "/reply/adminIndex.htm?conditionid="+id);
	}
</script>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="387889d24a9242238449e40871daf2" clazz="easyui-linkbutton" onClick="openConditionUpdateDialog()" name="详情"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="05d03b893558479599156bd469a83c" clazz="easyui-linkbutton" onClick="clearReply()" name="清空交流记录"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
	<div>
		&nbsp;标题：&nbsp;<input type="text" name="s_title" id="s_title" size="20" onkeydown="if(event.keyCode==13) searchCondition()"/>
		&nbsp;姓名：&nbsp;<input type="text" name="s_realname" id="s_realname" size="20" onkeydown="if(event.keyCode==13) searchCondition()"/>
		&nbsp;科室：&nbsp;<input class="easyui-combobox" id="s_deptid" name="s_deptid"  />
		<a href="javascript:searchCondition()" class="easyui-linkbutton" iconCls="icon-search" >搜索</a>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 620px;height: 420px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 	<table cellspacing="5px;">
 		<tr>
  			<td>标题：</td>
  			<td><input id="title" class="easyui-textbox" name="title" readonly="readonly" /></td>
  		</tr>
  		<tr><td colspan="2">&nbsp;</td></tr>
  		<tr>
  			<td>所属科室：</td>
  			<td><input id="deptid" class="easyui-combobox" name="dept.id" readonly="readonly"
   					data-options="valueField:'id',textField:'name',url:'${path}/dept/deptData.htm'"></td>
  		</tr>
  		<tr><td colspan="2">&nbsp;</td></tr>
  		<tr>
  			<td valign="top">详细说明：</td>
  			<td >
  				<textarea rows="14" cols="50" name="remarks" readonly="readonly" id="remarks"></textarea>
  			</td>
  		</tr>
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:closeConditionDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>

