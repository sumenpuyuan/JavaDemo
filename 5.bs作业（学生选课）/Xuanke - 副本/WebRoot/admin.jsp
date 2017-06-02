<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="/XuankeNew/Public/Css/admin.css">
    <title>My JSP 'admin.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<div class="top">后台登录</div>
   	<br/>
  	<div id=Login>
  	<h3>这是后台登录</h3>
  	<div class="loginCon">
	    <form id="myform" action="/XuankeNew/servlet/AdminServlet" method="post">
	    	<p class="txt" ><input type="text" name="name" placeholder="姓名"/><br/></p>
	    	<p class="txt" ><input type="text" name="password" placeholder="密码"/><br/></p>
	    	<input class="but" type="submit" value="登录" name="sub"/>
	    </form>
    </div>
    </div>
  </body>
</html>
