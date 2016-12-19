package Controller;
import model.*;
public class CStudent{
	MStudent mStudent;
	int id;
	String name;
	public CStudent(String name){
		mStudent=new MStudent();
		this.name=name;
	}
	public int GetUserId(){
		id=mStudent.GetUserId(this.name);
		return  id;
	}
}