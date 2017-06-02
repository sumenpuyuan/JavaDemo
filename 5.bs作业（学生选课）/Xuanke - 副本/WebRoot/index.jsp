<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <link rel="stylesheet" href="/XuankeNew/Public/Css/front.css">
    <title>前台登录界面</title>
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
	<div class="top">前台登录</div>
   	<br/>
  	<div id=Login>
  	<h3>这是前台登录</h3>
  	<div class="loginCon">
	    <form id="myform" action="/XuankeNew/servlet/UserServlet" method="post">
	    	<p class="txt" ><input type="text" name="name" placeholder="姓名"/><br/></p>
	    	<p class="txt" ><input type="text" name="password" placeholder="密码"/><br/></p>
	    	<img src="/XuankeNew/servlet/ValidateServlet" align="middle" title="看不清，请点我"  onclick="javascript:refresh(this);" onmouseover="mouseover(this)"/><br/>
	    	<br/>
	    	<p class="txt"><input name="inputCode" placeholder="验证码" value=""/></p>   
  			
	    	<input type="hidden" name="sub" value="login"/>
	    	<input class="but" type="submit" value="登录" />
	    	
	    </form>
    </div>
    </div>
  </body>
  <script>  
  
    function refresh(obj){  
         obj.src = "/XuankeNew/servlet/ValidateServlet?" + Math.random();  
    }  
      
    function mouseover(obj){  
        obj.style.cursor = "pointer";  
    }  
    
</script>  
</html>
