<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/priveliege" prefix="privilege" %>
<!--  uri="http://java.sun.com/jsp/jstl/core"   -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	//设定context path
	String path = request.getContextPath();
	if("/".equals(path.trim())) path = "";
	pageContext.setAttribute("path",path);
%>
<script>
    var path = '${path}';
</script>
<!-- 上述设置之后。项目中既可以用${path} 也可以直接用path -->


<!-- CSS -->
<link rel="stylesheet" type="text/css" href="${path }/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${path }/jquery-easyui-1.3.3/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${path }/style/main.css">

<!-- JAVASCRIPT -->
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${path }/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
