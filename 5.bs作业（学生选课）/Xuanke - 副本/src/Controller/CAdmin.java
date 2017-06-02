package Controller;
import Model.*;
public class CAdmin {
	MAdmin mAdmin=new MAdmin();
	MCouse mCouse=new MCouse();
	MXuanke mXuanke=new MXuanke();
	String res[][];
	public boolean CheckUser(String name,String password){
		System.out.println("输入密码是"+password+"从数据库那到底是"+mAdmin.GetUserPassword(name));
		
		if(password.trim().equals(mAdmin.GetUserPassword(name))){
			return true;
		}else{
			return false;
		}
	}
	//根据课程id显示出来选课表里的信息
	public String[][] ShowAllElectiveByCourseId(int id){
		return mXuanke.ShowAllElectiveByCourseId(id);
	}
	public String[][] ShowAllOnlyCourse(){
		return mCouse.ShowAllOnlyCourse();
	}
	public String[][] ShwoAllElective(){
		return mXuanke.ShwoAllElective();
	}
	//修改学生成绩
	public boolean ChangeStuScore(int course_id,int user_id,int score){
		return mXuanke.ChangeStuScore(course_id, user_id, score);
	}
	
	
	//下面是修改课程表的一些功能
	public boolean DelCourse(int course_id){
		if(mXuanke.DelXuanByCourseId(course_id) && mCouse.DelCourseById(course_id)){
			return true;
		}else{
			return false;
		}
	}
}
