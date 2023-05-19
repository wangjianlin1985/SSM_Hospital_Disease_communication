<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/common.jsp"%>

<!DOCTYPE html>
<html>
  <head>
    <title>班级主页</title>
   	<script type="text/javascript" src="${path }/js/sys/dept.js"></script>
    </head>
 
 
<body style="margin:1px">

<!-- 加载数据表格 -->
<table  id="dg" class="easyui-datagrid" fitColumns="true"
   				 pagination="true" rownumbers="true" url="deptList.htm" fit="true" toolbar="#tb">
        <thead>
            	<tr>
            		<th data-options="field:'ck',checkbox:true"></th>
                	<th data-options="field:'id',hidden:'false'">科室编号</th>
                	<th field="name" width="60" align="center">名称</th>
                	<th field="sort" width="60" align="center">排序</th>
            	</tr>
        </thead>
</table>
    	
<!-- 数据表格上的搜索和添加等操作按钮 -->
<div id="tb" >
	<div class="updownInterval"> </div>
	<div>
		<privilege:operation operationId="73740dfbdfdd497ab949b0052e972b" clazz="easyui-linkbutton" onClick="openDeptAddDialog()" name="添加"  iconCls="icon-add" ></privilege:operation>
		<privilege:operation operationId="56d57a539e5b4a9bbada9ce9024f68" clazz="easyui-linkbutton" onClick="openDeptUpdateDialog()" name="修改"  iconCls="icon-edit" ></privilege:operation>
		<privilege:operation operationId="6806a8021f1a41bba56753da2c83ab" clazz="easyui-linkbutton" onClick="deleteDept()" name="删除"  iconCls="icon-remove" ></privilege:operation>
	</div>
	<div class="updownInterval"> </div>
</div>




<!-- 新增和修改对话框 -->
<div id="dlg" class="easyui-dialog" style="text-align:right;width: 400px;height: 220px;padding: 10px 20px"
  closed="true" buttons="#dlg-buttons">
 <form id="fm" method="post">
 	<table cellspacing="5px;">
  		<tr>
  			<td>科室名称：</td>
  			<td><input type="text" id="name" name="name" class="easyui-validatebox" required="true"/></td>
  		</tr>
  		<tr>
  			<td>排序：</td>
  			<td><input type="text" id="sort" name="sort" class="easyui-numberbox" required="true"/></td>
  		</tr>
  	</table>
 </form>
</div>
<div id="dlg-buttons" style="text-align:center">
	<a href="javascript:reserveDept()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript:closeDeptDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>