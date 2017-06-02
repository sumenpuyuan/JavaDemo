<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@  page  import="Controller.*"%>
    <%
    	request.setCharacterEncoding("utf-8");
    	String res[][];
    	String type=request.getParameter("sub");

    	CUser cUser=new CUser();
    	
    	//如果是登录
    	if(type.equals("登录")){	
    		String name=request.getParameter("name");
    		String password=request.getParameter("password");
  			if(cUser.CheckUser(name,password) == true){
  				
  				session.setAttribute("user",name);
  				session.setAttribute("id",cUser.GetUserId(name));
  				
  				out.println("您已经登录成功 3秒后跳入后台页面");	
  				response.setHeader("Refresh","5;URL=Front/index.jsp");
  			}else{
  				out.println("您登录失败 3秒后返回主页面");
  				response.setHeader("Refresh","5;URL=index.jsp");
  			}
    	}
    	//如果是注销
    	else if(type.equals("logout")){
    		session.invalidate();
    		response.sendRedirect("index.jsp");     
    	}
    	
    	//如果是选课
    	else if(type.equals("xuan")){
    		int id=Integer.parseInt((String)session.getAttribute("id"));
    		
    		
    		System.out.println("选课函数");
    		String rString[]=null;
    		rString=(String[])request.getParameterValues("course");
    		if(rString == null || rString.length==0){
    			out.println("您没选中任何课程");
    			response.setHeader("Refresh","5;URL=Front/View.jsp");
    		}
    		else{
	    		for(int ii=0;ii<rString.length;ii++){
	    			if(cUser.Xuanke(id, Integer.parseInt(rString[ii]))){
	    				out.println("课程id是"+rString[ii]+"选课成功");
	    			}else{
	    				out.println("课程id是"+rString[ii]+"选课失败");
	    			}
	    		}
	    		response.setHeader("Refresh","5;URL=Front/View.jsp");
    		}
    		
    		
    	}
    	else if(type.equals("query")){
    		String name=request.getParameter("name");
    		System.out.println("查询函数");
    		//out.println("1523"+name);
    		String row[]=cUser.QueryStudent(name);
    		
    		out.println("{\"id\":\""+row[0]+"\",\"name\":\""+row[1]+"\",\"password\":\""+row[2]+"\"}");
    	}
    	else if(type.equals("delStudent")){
    		String sid=request.getParameter("id");
    		out.println("您进入了删除函数,id是"+sid);
    		int id=Integer.parseInt(sid);
    		if(cUser.DelXuanKeById(id) && cUser.DelStudent(id)  ){
    			out.println("删除成功.3秒后返回");
    			response.setHeader("Refresh","3;URL=Admin/ChangeStu.jsp");
    		}else{
    			out.println("删除失败 3秒后返回");
    			response.setHeader("Refresh","3;URL=Admin/ChangeStu.jsp");
    		}
    	}
    	else if(type.equals("change")){
    		String sid=request.getParameter("id");
    		int id=Integer.parseInt(sid);
    		String name=request.getParameter("name");
    		String password=request.getParameter("password");
    		name = new String(name.getBytes("ISO-8859-1"), "UTF-8");   
    		
    		out.println("修改函数,id="+sid+"姓名是"+name+"密码是"+password);
    		if(cUser.ChangeStudent(id, name, password)){
    			out.println("修改成功 3秒后返回");
    			response.setHeader("Refresh","3;URL=Admin/ChangeStu.jsp");
    		}else{
    			out.println("修改失败 3秒后返回");
    			response.setHeader("Refresh","3;URL=Admin/ChangeStu.jsp");
    		}
    	}
    	else if(type.equals("add")){
    	
    		 
    		String name=request.getParameter("name");
    		String password=request.getParameter("password");
    		name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
    		
    		out.println("添加函数，姓名是"+name+"密码是"+password);
    		if(cUser.AddStudent(name, password)){
    			out.println("ok");
    			response.setHeader("Refresh","3;URL=Admin/ChangeStu.jsp");
    		}else{
    			out.println("wrong");
    			response.setHeader("Refresh","3;URL=Admin/ChangeStu.jsp");
    		}
    	}
     %>

