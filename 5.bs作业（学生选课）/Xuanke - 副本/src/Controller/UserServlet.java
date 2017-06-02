package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public UserServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	 
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); //这行放在流输出前才好使
		PrintWriter out = response.getWriter();
		HttpSession session=request.getSession();
		
    	String res[][];
    	String type=request.getParameter("sub");
    	out.print("type="+type);
    	CUser cUser=new CUser();
    	//如果是注销
    	if(type.equals("logout")){
    		session.invalidate();
    		response.sendRedirect("/XuankeNew/index.jsp");     
    	}
    	//如果是选课
    	else if(type.equals("xuan")){
    		int id=Integer.parseInt((String)session.getAttribute("id"));
    		
    		
    		System.out.println("选课函数");
    		String rString[]=null;
    		rString=(String[])request.getParameterValues("course");
    		if(rString == null || rString.length==0){
    			System.out.println("您没选中任何课程");
    			response.setHeader("Refresh","5;URL=/XuankeNew/Front/View.jsp");
    		}
    		else{
	    		for(int ii=0;ii<rString.length;ii++){
	    			if(cUser.Xuanke(id, Integer.parseInt(rString[ii]))){
	    				System.out.println("课程id是"+rString[ii]+"选课成功");
	    			}else{
	    				System.out.println("课程id是"+rString[ii]+"选课失败");
	    			}
	    		}
	    		response.setHeader("Refresh","5;URL=/XuankeNew/Front/View.jsp");
    		}
    		
    		
    	}
    	//如果是修改函数
    	else if(type.equals("change")){
    		String sid=request.getParameter("id");
    		int id=Integer.parseInt(sid);
    		String name=request.getParameter("name");
    		String password=request.getParameter("password");
    		name = new String(name.getBytes("ISO-8859-1"), "UTF-8");   
    		
    		System.out.println("修改函数,id="+sid+"姓名是"+name+"密码是"+password);
    		if(cUser.ChangeStudent(id, name, password)){
    			System.out.println("修改成功 3秒后返回");
    			response.setHeader("Refresh","3;URL=/XuankeNew/Admin/ChangeStu.jsp");
    		}else{
    			System.out.println("修改失败 3秒后返回");
    			response.setHeader("Refresh","3;URL=/XuankeNew/Admin/ChangeStu.jsp");
    		}
    	}
    	//如果是添加学生
    	else if(type.equals("add")){
        	
   		 
    		String name=request.getParameter("name");
    		String password=request.getParameter("password");
    		name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
    		
    		System.out.println("添加函数，姓名是"+name+"密码是"+password);
    		if(cUser.AddStudent(name, password)){
    			System.out.println("ok");
    			response.setHeader("Refresh","3;URL=/XuankeNew/Admin/ChangeStu.jsp");
    		}else{
    			System.out.println("wrong");
    			response.setHeader("Refresh","3;URL=/XuankeNew/Admin/ChangeStu.jsp");
    		}
    	}
    	//如果是删除学生函数
    	else if(type.trim().equals("delStudent")){
    		String sid=request.getParameter("id");
    		System.out.println("您进入了删除函数,id是"+sid);
    		int id=Integer.parseInt(sid);
    		if(cUser.DelXuanKeById(id) && cUser.DelStudent(id)  ){
    			System.out.println("删除成功.3秒后返回");
    			response.setHeader("Refresh","3;URL=/XuankeNew/Admin/ChangeStu.jsp");
    		}else{
    			System.out.println("删除失败 3秒后返回");
    			response.setHeader("Refresh","3;URL=/XuankeNew/Admin/ChangeStu.jsp");
    		}
    	}
		
		
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8"); //这行放在流输出前才好使
		request.setCharacterEncoding("utf-8");
		
		
		PrintWriter out = response.getWriter();
		
		HttpSession session=request.getSession();
		
    	String res[][];
    	String type=request.getParameter("sub");
    	

    	CUser cUser=new CUser();
    	
    	//如果是登录
    	if(type.equals("login")){	
    		//先判断验证码是否正确
    		
    		 String inputCode = request.getParameter("inputCode");  
    		    String verifyCode = (String)session.getAttribute("validateCode");  
    		    if(inputCode!=null && verifyCode!=null){  
    		       // out.print("真正的验证码：" + verifyCode + "<br/>" + "用户输入的验证码：" + inputCode + "<br/>");  
    		        inputCode = inputCode.toUpperCase();//不区分大小写  
    		        //out.print("比较验证码证明用户输入 " + (inputCode.equals(verifyCode)?"正确":"错误") + "！");  
    		        if(inputCode.equals(verifyCode)){
    		        	//out.print("验证码输入正确");
    		        }else{
    		        	out.print("验证码输入错误");
    		        	return ;
    		        }
    		    }  
    		String name=request.getParameter("name");
    		String password=request.getParameter("password");
  			if(cUser.CheckUser(name,password) == true){
  				
  				session.setAttribute("user",name);
  				session.setAttribute("id",cUser.GetUserId(name));
  				
  				out.println("您已经登录成功 3秒后跳入后台页面");	
  				response.setHeader("Refresh","5;URL=/XuankeNew/Front/index.jsp");
  			}else{
  				out.println("您登录失败 3秒后返回主页面");
  				response.setHeader("Refresh","5;URL=/XuankeNew/index.jsp");
  			}
    	}
    	
    	
    	else if(type.equals("query")){
    		response.setContentType("text/html;charset=UTF-8"); //这行放在流输出前才好使
    		String name=request.getParameter("name");
    		System.out.println("查询函数");
    		//out.println("1523"+name);
    		String row[]=cUser.QueryStudent(name);
    		System.out.print("{\"id\":\""+row[0]+"\",\"name\":\""+row[1]+"\",\"password\":\""+row[2]+"\"}");

    		out.print("{\"id\":\""+row[0]+"\",\"name\":\""+row[1]+"\",\"password\":\""+row[2]+"\"}");
    		//out.println("{\"id\":\""+row[0]+"\",\"name\":\""+row[1]+"\",\"password\":\""+row[2]+"\"}");

    	}
    	//如果是选课
    	else if(type.equals("xuan")){
    		int id=Integer.parseInt((String)session.getAttribute("id"));
    		
    		
    		System.out.println("选课函数");
    		String rString[]=null;
    		rString=(String[])request.getParameterValues("course");
    		if(rString == null || rString.length==0){
    			out.println("您没选中任何课程");
    			response.setHeader("Refresh","5;URL=/XuankeNew/Front/View.jsp");
    		}
    		else{
	    		for(int ii=0;ii<rString.length;ii++){
	    			if(cUser.Xuanke(id, Integer.parseInt(rString[ii]))){
	    				out.println("课程id是"+rString[ii]+"选课成功");
	    			}else{
	    				out.println("课程id是"+rString[ii]+"选课失败");
	    			}
	    		}
	    		response.setHeader("Refresh","5;URL=/XuankeNew/Front/View.jsp");
    		}
    		
    		
    	}
    	
    	
    	
    	
	
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
