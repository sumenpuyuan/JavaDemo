<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@  page  import="Controller.*"%>
 <%@ include file="/Util/checkAdminSession.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>

   <link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
  <script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
  <script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
  
  <link rel="stylesheet" href="/XuankeNew/Public/Css/admin.css">
  
   <style>
    body { font-size: 62.5%; }
    label, input { display:block; }
    input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
  </style>
    <title>My JSP 'ChangeCourse.jsp' starting page</title>
    
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
  <div class="top">课程信息管理</div>
 <%
 	CAdmin cAdmin=new CAdmin();
 	String[][] row=cAdmin.ShowAllOnlyCourse();
 	
 	out.println("<table class='biaoge'>");
    	out.println("<tr class='shou'><td>课程id</td><td>课程名称</td><td>学时</td><td>学分</td><td>简介</td><td>操作</td><td>操作</td></tr>");
    	
    	for(int ii=0;ii<row.length;ii++){
    		 out.println("<tr>");
    		for(int ij=0;ij<row[ii].length;ij++){	
    			out.println("<td>"+row[ii][ij]+"</td>");
    		}
    		
    		out.println("<td><button>删除</button></td><td><button onClick=\"change('"+row[ii][0]+"','"+row[ii][1]+"','"+row[ii][2]+"','"+row[ii][3]+"','"+row[ii][4]+"')\">修改</button></td></tr>");
    	}
    	out.println("</table>");
    	
  %>
  </body>
</html>
