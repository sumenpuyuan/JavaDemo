package Controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AdminServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public AdminServlet() {
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

		
    	String name=request.getParameter("name");
    	String password=request.getParameter("password");
    	String res[][];
    	String type=request.getParameter("sub");
    	
    	CAdmin cAdmin=new CAdmin();
		
		//如果是修改选课表中 的学生成绩
    	if(type.equals("changeStuScore")){
    		out.println("我是修改学生成绩函数");
    		int course_id=Integer.parseInt((String)request.getParameter("course_id"));
    		int user_id=Integer.parseInt((String)request.getParameter("user_id"));
    		int score=Integer.parseInt((String)request.getParameter("score"));
    		out.println(course_id+user_id+score);
    		if(cAdmin.ChangeStuScore(course_id, user_id, score)){
    			out.println("修改成功");
    			response.setHeader("Refresh","5;URL=/XuankeNew/Admin/ChangeXuan.jsp");
    		}else{
    			out.println("修改失败");
    			response.setHeader("Refresh","5;URL=/XuankeNew/Admin/ChangeXuan.jsp");
    		}
    		
    	}
    	//下面是修改课程表的信息
    	
    	//如果是删除课程信息
    	
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=UTF-8"); //这行放在流输出前才好使
		HttpSession session=request.getSession();
		PrintWriter out = response.getWriter();
		
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
  				response.setHeader("Refresh","5;URL=/XuankeNew/Admin/index.jsp");
  			}else{
  				out.println("您登录失败 3秒后返回主页面");
  				response.setHeader("Refresh","5;URL=/XuankeNew/admin.jsp");
  			}
    	}
    	
    	//根据课程id显示课程的选课信息
    	else if(type.equals("showCouserById")){
    		response.setContentType("text/xml;charset=UTF-8"); //这行放在流输出前才好使
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
	    				System.out.println(res[ii][1]);
	    		}
	    		int ii=res.length-1;
	    		out.print("{\"course_id\":\""+res[ii][0]+"\",\"course_name\":\""+res[ii][1]+"\",\"user_id\":\""+res[ii][2]+"\",\"user_name\":\""+res[ii][3]+"\",\"score\":\""+res[ii][4]+"\"}");
	    		out.print("]");
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
