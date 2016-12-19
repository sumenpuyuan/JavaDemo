package Controller;


import model.*;
public class CLogin {
	MStudent mStudent;
	String[] res;
	public CLogin(){
		mStudent=new MStudent();
	}
	public boolean CheckUser(String name,String password){
		res=mStudent.QueryUser(name, password);
		if(res[0].equals("")){
			return false;
		}else{
			return true;
		}	
	}
	public void Close(){
		mStudent.Close();
	}
}
