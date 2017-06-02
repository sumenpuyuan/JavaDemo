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
    <title>My JSP 'View.jsp' starting page</title>
    
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
     
     <div class="top">
	  <p class="title">这是选课页面</p>
	  <p class="status">欢迎你:<% out.println(session.getAttribute("user"));%></p>
  	</div>
  	<br/>
  	<%
  		CUser cUser=new CUser();
  		int id=Integer.parseInt((String)session.getAttribute("id"));
  		String res[][]=cUser.ShowAllCourse(id);
  		out.println("<form action='/XuankeNew/servlet/UserServlet?sub=xuan' method='post'>");
  		out.println("<table class='biaoge'>");
     	out.println("<tr class='shou'><td></td><td>课程id</td><td>课程名称</td><td>学时</td><td>学分</td><td>简介</td><td>状态</td></tr>");
	  	for(int ii=0;ii<res.length;ii++){
	  		out.println("<tr>");
	  		if(res[ii][res[ii].length-1].equals("1")){
	  			out.println("<td><input type='checkbox' checked disabled name='course'></td>");
	  		}else{
	  			out.println("<td><input type='checkbox'  value='"+res[ii][0]+"' name='course'></td>");
	  		}
	  		
	  		for(int ij=0;ij<res[ii].length-1;ij++){
	  			out.println("<td>"+res[ii][ij]+"</td>");
	  		}
	  		if(res[ii][res[ii].length-1].equals("1")){
	  			out.println("<td>已选</td>");
	  		}else{
	  			out.println("<td>未选</td>");
	  		}
	  		out.println("</tr>");
	  	}
	  out.println("</table>");
	 
  	 %>
  	 <br/>
  	 <div class="bottom">
  	 	<a href="Front/index.jsp">返回主页</a>
  	 	&nbsp;&nbsp;    
  	 	<input type="submit" value="保存" class="but"/>
  	 </div>
  	 
  	 <%
  	 	 out.println("</from>");
  	  %>
  	
  	
  </body>
</html>
