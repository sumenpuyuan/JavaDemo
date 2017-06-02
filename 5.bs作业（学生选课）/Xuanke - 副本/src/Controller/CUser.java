package Controller;
import Model.*;
public class CUser {
	MUser mUser=new MUser();
	MXuanke mXuanke=new MXuanke();
	MCouse mCouse=new MCouse();
	public boolean CheckUser(String name,String password){
		System.out.println("输入密码是"+password+"从数据库那到底是"+mUser.GetUserPassword(name));
		
		if(password.trim().equals(mUser.GetUserPassword(name))){
			return true;
		}else{
			return false;
		}
	}
	public boolean DelXuanKeById(int user_id){
		return mXuanke.DelXuanKeById(user_id);
	}
	public boolean Xuanke(int user_id,int course_id){
		return mXuanke.Xuanke(user_id, course_id);
	}
	public String[][] ShowAllScore(int id){
		return mXuanke.ShowAll(id);
	}
	public String[][] ShowAllCourse(int id){
		return mCouse.ShowAllCourse(id);
	}
	public String GetUserId(String name){
		return mUser.GetUserId(name);
	}
	public String[] QueryStudent(String name){
		return mUser.QueryStudent(name);
	}
	public String[][] GetAllStudent(){
		return mUser.GetAllStudent();
	}
	public boolean DelStudent(int id){
		return mUser.DelStudent(id);
	}
	public boolean ChangeStudent(int id,String name,String password){
		return mUser.ChangeStudent(id, name, password);
	}
	public boolean AddStudent(String name,String password){
		return mUser.AddStudnet(name, password);
	}
}
