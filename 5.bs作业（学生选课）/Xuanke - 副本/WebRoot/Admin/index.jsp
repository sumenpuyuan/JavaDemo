<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
 <%@ include file="/Util/checkAdminSession.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>后台管理</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <link rel="stylesheet" href="/XuankeNew/Public/Css/admin.css">

  </head>
  
  <body>
  <div class="top">这是后台管理</div>
   <br/><br/>
   
   <div class="middle">
   		<center>
	   <a href="Admin/ChangeStu.jsp">修改学生表</a>
	   <a href="Admin/ChangeCourse.jsp">修改课程信息表</a>
	   <a href="Admin/ChangeXuan.jsp">查看选课信息表</a>
	   
	   </center>
   </div>
  </body>
</html>
