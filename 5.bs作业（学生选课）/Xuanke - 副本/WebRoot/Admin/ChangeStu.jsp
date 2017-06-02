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
  <title>My JSP 'ChangeStu.jsp' starting page</title>
    
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
	<div class="top">修改学生表</div>
   <br/><br/>
    <%
    	request.setCharacterEncoding("utf-8");
    	CUser cUser=new CUser();
    	String res[][]=cUser.GetAllStudent();
    	out.println("<table class='biaoge'>");
    	out.println("<tr class='shou'><td>id</td><td>姓名</td><td>密码</td><td>操作</td><td>操作</td><td>操作</td></tr>");
    	for(int ii=0;ii<res.length;ii++){
    		 
    		for(int ij=0;ij<res[ii].length;ij++){	
    			out.println("<td>"+res[ii][ij]+"</td>");
    		}
    		out.println("<td><button onClick='del("+res[ii][0]+")'>删除</button></td>");
    		out.println("<td><button onClick='change("+res[ii][0]+")'>修改</button></td>");
    		out.println("<td><button onClick=\"tchange('"+res[ii][0]+"','"+res[ii][1]+"','"+res[ii][2]+"')\">修改2</button></td>");
    		out.println("</tr>");
    	}
    	out.println("</table>");
     %>
    
     
     
	
	<br/>
	<div class="bottom">
	 	<button class="create-use">创建新用户</button>
	  	<input type="text" name="query-name" id="query-name" placeholder="请输入名字查询"/>
	  	<button onClick="query()">精确查询</button>
	  	<br/><br/>
	  	<table class="biaoge">
	  		<tr>
	  			<td class="res-id"></td>
	  			<td class="res-name"></td>
	  			<td class="res-password"></td>
	  		</tr>
	  	</table>
	  	<br/>
	  	<a href="/XuankeNew/Admin/index.jsp">返回到主页</a>
 	</div>	
 	
 	
 	<div id="dialog-for" title="创建新用户">
	 
	  <form>
	  <fieldset>
	    <label for="name">名字</label>
	    <input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all">
	   	<label for="password">密码</label>
	    <input type="password" name="password" id="password" value="" class="text ui-widget-content ui-corner-all">
	  </fieldset>
	  </form>
	</div>
	
	
	<div id="dialog-change" title="修改新用户">
	  
	  <form>
	  <fieldset>
	   <label for="id">id</label>
	    <input type="text" name="id" id="id" readonly class="text ui-widget-content ui-corner-all">
	   	
	    <label for="name">名字</label>
	    <input type="text" name="name" id="cname" class="text ui-widget-content ui-corner-all">
	   	<label for="password">密码</label>
	    <input type="text" name="password" id="cpassword" value="" class="text ui-widget-content ui-corner-all">
	 
	  </fieldset>
	  </form>
	</div>
	

	
	
	
  </body>
  <script>
	function query(){
		$.post("/XuankeNew/servlet/UserServlet",{
			name:$("#query-name").val(),
			sub:"query"
		},function(data,textStatus){
			alert("我是查询jquery查询函数");
			alert(data);
			var exp=data.name;
			
			if (exp == "null")
			{
			    $(".res-id").html("没有数据");
			}
			else
			{
				alert("hehheh");
				$(".res-id").html(data.id);
				$(".res-name").html(data.name);
				$(".res-password").html(data.password);
			}
			
		},"json");
 	}
  	function del(id) { 
	 	var msg = "您真的确定要删除吗？\n\n请确认！"; 
		if (confirm(msg)==true){ 
			window.location.href='/XuankeNew/servlet/UserServlet?sub=delStudent&id='+id+'';
			//var str='/dealAction?sub=delStudent&id='+id+'';
	  		//alert(str);
	  		return true; 
	 	}else{ 
	  		return false; 
	 	} 
	} 
	function change(id){
		var msg = "您真的确定要修改吗？\n\n请确认！"; 
		if (confirm(msg)==true){ 
			//window.location.href='/XuankeNew/deal.jsp?sub=delStudent&id='+id+'';
			//var str='/dealAction?sub=delStudent&id='+id+'';
	  		//alert(str);
	  		var name=prompt("请输入修改后的姓名","");
    		var password=prompt("请输入修改后的密码","");
	  		window.location.href='/XuankeNew/servlet/UserServlet?sub=change&id='+id+'&name='+name+'&password='+password+'';

	  		return true; 
	 	}else{ 
	  		return false; 
	 	} 
	}
	function tchange(id,name,password){
		$("#cname").val(name);
		$("#cpassword").val(password);
		$("#id").val(id);
		alert("dddd"+name);
        $( "#dialog-change" ).dialog( "open" );
        
	}

	 $( "#dialog-for" ).dialog({
      autoOpen: false,
      height: 300,
      width: 350,
      modal: true,
      buttons: {
        "创建一个帐户": function() {
      		//这里应该写逻辑吧
      		//alert("123");
      		//alert($("#name").val());
      		var name=$("#name").val();
      		var password=$("#password").val();
      		window.location.href='/XuankeNew/servlet/UserServlet?sub=add&name='+name+'&password='+password+'';
      		$( this ).dialog( "close" );
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {
        allFields.val( "" ).removeClass( "ui-state-error" );
        $( this ).dialog( "close" );
      }
    });
 	$( "#dialog-change" ).dialog({
      autoOpen: false,
      height: 300,
      width: 350,
      modal: true,
      
      buttons: {
        "保存": function() {
      		//这里应该写逻辑吧
      		//alert("123");
      		alert($("#cname").val());
      		var name=$("#cname").val();
      		var password=$("#cpassword").val();
      		var id=$("#id").val();
      		window.location.href="/XuankeNew/servlet/UserServlet?sub=change&id="+id+"&name="+name+"&password="+password+"";
      		$( this ).dialog( "close" );
        },
        Cancel: function() {
          $( this ).dialog( "close" );
        }
      },
      close: function() {
     
        $( this ).dialog( "close" );
      }
    });
    $( ".create-use" )
      
      .click(function() {
        $( "#dialog-for" ).dialog( "open" );
      });

  </script>
</html>
