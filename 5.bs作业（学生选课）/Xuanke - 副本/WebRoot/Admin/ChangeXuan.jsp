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
    
    <title>My JSP 'ChangeXuan.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="/XuankeNew/Public/Css/admin.css">
  	<script src="/XuankeNew/Public/Js/jquery.js"></script>
  	
  	
  	<link rel="stylesheet" href="//apps.bdimg.com/libs/jqueryui/1.10.4/css/jquery-ui.min.css">
  <script src="//apps.bdimg.com/libs/jquery/1.10.2/jquery.min.js"></script>
  <script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com/resources/demos/style.css">
  <style>
     
     body { font-size: 62.5%; }
    label,#dialog-change input { display:block; }
    #dialog-change input.text { margin-bottom:12px; width:95%; padding: .4em; }
    fieldset { padding:0; border:0; margin-top:25px; }
    h1 { font-size: 1.2em; margin: .6em 0; }
    div#users-contain { width: 350px; margin: 20px 0; }
    div#users-contain table { margin: 1em 0; border-collapse: collapse; width: 100%; }
    div#users-contain table td, div#users-contain table th { border: 1px solid #eee; padding: .6em 10px; text-align: left; }
    .ui-dialog .ui-state-error { padding: .3em; }
    .validateTips { border: 1px solid transparent; padding: 0.3em; }
  </style>
  </head>
  
  <body>
    <div class="top">这是后台管理</div>
    <br/>
    <%
    	CAdmin cAdmin=new CAdmin();
    	String res[][]=cAdmin.ShowAllOnlyCourse();
    	
    	out.println("<input onclick=\"show(0)\" type='radio' name='course'  checked value='-1'>总表&nbsp;&nbsp;&nbsp;");
    	for(int ii=0;ii<res.length;ii++){
    		out.println("<input onclick=\"show("+res[ii][0]+")\" type='radio' name='course' value='"+res[ii][0]+"'>"+res[ii][1]+"&nbsp;&nbsp;&nbsp;");
    	}
    	out.println("<br/><br/>");
    	String row[][]=cAdmin.ShwoAllElective();
    	out.println("<div class='space'></div>");
    	out.println("<table class='biaoge'>");
    	out.println("<tr class='shou'><td>课程id</td><td>课程名称</td><td>学生id</td><td>学生姓名</td><td>成绩</td><td>操作</td></tr>");
    	
    	for(int ii=0;ii<row.length;ii++){
    		 out.println("<tr>");
    		for(int ij=0;ij<row[ii].length;ij++){	
    			out.println("<td>"+row[ii][ij]+"</td>");
    		}
    		
    		out.println("<td><button onClick=\"change('"+row[ii][0]+"','"+row[ii][1]+"','"+row[ii][2]+"','"+row[ii][3]+"','"+row[ii][4]+"')\">修改成绩</button></td></tr>");
    	}
    	out.println("</table>");
    	
    	
    	
    %>
    <br/>
    <div class="bottom">
    	<a href="Admin/index.jsp">返回主页面</a>
    </div>
    <div id="dialog-change" title="修改成绩">
	  
	  <form>
	  <fieldset>
	   <label for="course-name">课程名称 </label>
	    <input type="text" name="course-name" id="course-name" readonly class="text ui-widget-content ui-corner-all">
	   	
	    <label for="user-name">学生姓名</label>
	    <input type="text" name="user-name" id="user-name" readonly class="text ui-widget-content ui-corner-all">
	   	<label for="score">成绩</label>
	    <input type="text" name="score" id="score" value="" class="text ui-widget-content ui-corner-all">
	 	<input type="hidden" id="user-id"/>
	 	<input type="hidden" id="course-id"/>
	  </fieldset>
	  </form>
	</div>
    <script>
    	function change(course_id,course_name,user_id,user_name,score){
    		alert("456");
    		alert(course_id);
    		$("#course-name").val(course_name);
    		$("#user-name").val(user_name);
    		$("#score").val(score);
    		
    		$("#course-id").val(course_id);
    		$("#user-id").val(user_id);
    		$( "#dialog-change" ).dialog( "open" );
    	}
    	$( "#dialog-change" ).dialog({
		      autoOpen: false,
		      height: 300,
		      width: 350,
		      modal: true,
		      
		      buttons: {
		        "保存": function() {
		      		//这里应该写逻辑吧
		      		//alert("123");
		      		
		      		var course_id=$("#course-id").val();
		      		var user_id=$("#user-id").val();
		      		var score=$("#score").val();
		      		window.location.href="/XuankeNew/servlet/AdminServlet?sub=changeStuScore&course_id="+course_id+"&user_id="+user_id+"&score="+score+"";
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
    	//传的是课程id
    	function show(id){
    		//alert(id);
    		$.post("/XuankeNew/servlet/AdminServlet",{
				course_id:id,
				sub:"showCouserById"
				},function(data,textStatus){
				//alert(textStatus);
				//如果点击总表 那么直接刷新页面
					if(data.action == "refresh"){
						history.go(0) ;
						//alert("refresh");
					}
					else{
						$(".biaoge").remove();
						alert(data.length);
						var txtHtml="<table class='biaoge'><tr class='shou'><td>课程id</td><td>课程名称</td><td>学生id</td><td>学生姓名</td><td>成绩</td><td>操作</td></tr>";
						
						for(var ii=0;ii<data.length;ii++){
							txtHtml=txtHtml+"<tr>";
							txtHtml=txtHtml+"<td>"+data[ii].course_id+"</td>";
							txtHtml=txtHtml+"<td>"+data[ii].course_name+"</td>";
							alert(encodeURI(data[ii].course_name));
							txtHtml=txtHtml+"<td>"+data[ii].user_id+"</td>";
							txtHtml=txtHtml+"<td>"+data[ii].user_name+"</td>";
							txtHtml=txtHtml+"<td>"+data[ii].score+"</td>";
							txtHtml+="<td><button onClick=\"change('"+data[ii].course_id+"','"+data[ii].course_name+"','"+data[ii].user_id+"','"+data[ii].user_name+"','"+data[ii].score+"')\">修改成绩</button></td></tr>";
							
						}
						txtHtml+="</table>";
						//alert(txtHtml);
						$(".space").html(txtHtml);
					}
					
					
					
				},"json");
    	}
    	$(function(){
    		//alert("123");
    		
    	});
    </script>
  </body>
</html>
