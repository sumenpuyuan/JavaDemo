<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@  page  import="Controller.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="/XuankeNew/Public/Css/front.css">
    <title>My JSP 'Elective.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <!-- 选课页面 -->
  <body>
     <div class="top">
	  <p class="title">这是课程成绩页面</p>
	  <p class="status">欢迎你:<% out.println(session.getAttribute("user"));%></p>
  </div>
  <br/>
  <%
	  request.setCharacterEncoding("utf-8");
	  CUser cUser=new CUser();
	  int id=Integer.parseInt((String)session.getAttribute("id"));
	  String res[][]=cUser.ShowAllScore(id);
	  out.println("<table class='biaoge'>");
      out.println("<tr class='shou'><td>课程名称</td><td>学分</td><td>简介</td><td>成绩</td></tr>");
	  for(int ii=0;ii<res.length;ii++){
	  	out.println("<tr>");
	  	for(int ij=0;ij<res[ii].length;ij++){
	  		out.println("<td>"+res[ii][ij]+"</td>");
	  	}
	  	out.println("</tr>");
	  	
	  }
	  out.println("</table>");
   %>
   <br/>
   <div class="bottom">
   	<a href="/XuankeNew/Front/index.jsp">返回主页面</a>
   </div>
   
  </body>
</html>
