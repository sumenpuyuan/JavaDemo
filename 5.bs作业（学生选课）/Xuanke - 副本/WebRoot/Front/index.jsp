<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

 <%@ include file="/Util/checkUserSession.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	--> 
	<link rel="stylesheet" href="/XuankeNew/Public/Css/front.css">

  </head>
  
  <body>
  <div class="top">
	  <p class="title">这是前台管理</p>
	  <a href="/XuankeNew/servlet/UserServlet?sub=logout">&nbsp;注销</a>
	  <p class="status">欢迎你:<% out.println(session.getAttribute("user"));%></p>
  	  
  </div>
  <br/><br/>
  <div class="middle">
   		<center>
	   <a href="Front/View.jsp">选课</a>
	   <a href="Front/Elective.jsp">查看自己的成绩</a>
	   
	   </center>
   </div>
  </body>
</html>
