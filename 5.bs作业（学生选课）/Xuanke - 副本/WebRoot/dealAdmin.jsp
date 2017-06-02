<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@  page  import="Controller.*"%>

     <%
    	request.setCharacterEncoding("utf-8");
    	String name=request.getParameter("name");
    	String password=request.getParameter("password");
    	String res[][];
    	String type=request.getParameter("sub");
    	
    	CAdmin cAdmin=new CAdmin();
    	
    	
    	//如果是登录
    	if(type.equals("登录")){	
  			if(cAdmin.CheckUser(name,password) == true){
  			
  				session.setAttribute("admin",name);
  				
  				out.println("您是管理员 您已经登录成功 3秒后跳入后台页面");	
  				response.setHeader("Refresh","5;URL=Admin/index.jsp");
  			}else{
  				out.println("您登录失败 3秒后返回主页面");
  				response.setHeader("Refresh","5;URL=admin.jsp");
  			}
    	}
    	//如果是修改选课表中 的学生成绩
    	else if(type.equals("changeStuScore")){
    		out.println("我是修改学生成绩函数");
    		int course_id=Integer.parseInt((String)request.getParameter("course_id"));
    		int user_id=Integer.parseInt((String)request.getParameter("user_id"));
    		int score=Integer.parseInt((String)request.getParameter("score"));
    		out.println(course_id+user_id+score);
    		if(cAdmin.ChangeStuScore(course_id, user_id, score)){
    			out.println("修改成功");
    			response.setHeader("Refresh","5;URL=Admin/ChangeXuan.jsp");
    		}else{
    			out.println("修改失败");
    			response.setHeader("Refresh","5;URL=Admin/ChangeXuan.jsp");
    		}
    		
    	}
    	//根据课程id显示课程的选课信息
    	else if(type.equals("showCouserById")){
    		System.out.println("我是showCourseById函数");
    		int course_id=Integer.parseInt((String)request.getParameter("course_id"));
    		System.out.println("拿到的课程id是"+course_id);
    	
    		if(course_id == 0){
    			out.print("{\"action\":\"refresh\"}");
    		}
    		else{
    			res=cAdmin.ShowAllElectiveByCourseId(course_id);
    			if(res.length == 0){
    				out.print("{\"action\":\"remove\"}");
    				return ;
    			}
	    		out.print("[");
	    		for(int ii=0;ii<res.length-1;ii++){
	    			
	    				out.print("{\"course_id\":\""+res[ii][0]+"\",\"course_name\":\""+res[ii][1]+"\",\"user_id\":\""+res[ii][2]+"\",\"user_name\":\""+res[ii][3]+"\",\"score\":\""+res[ii][4]+"\"},");
	    				
	    		}
	    		int ii=res.length-1;
	    		out.print("{\"course_id\":\""+res[ii][0]+"\",\"course_name\":\""+res[ii][1]+"\",\"user_id\":\""+res[ii][2]+"\",\"user_name\":\""+res[ii][3]+"\",\"score\":\""+res[ii][4]+"\"}");
	    		out.print("]");
    		}
    		
    	}
    
    	
     %>

