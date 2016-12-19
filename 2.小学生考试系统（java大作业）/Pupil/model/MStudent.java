package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import com.mysql.jdbc.PreparedStatement;

/*
 * 负责获得学生的数据信息
 */
public class MStudent extends DbBase{
	String sql;
	ResultSet rs=null;
	Statement stmt;
	String[] res;
	int id;
	public MStudent(){
		super();
	}
	public String[] QueryUser(String name,String password){
		try{
			res=new String[3];
			sql="select  * from students where name=\'"+name+"\' and password=\'"+password+"\'";
			//sql="select * from students";
			System.out.println(sql);
			//String sql="insert";
			stmt = connection.createStatement(); 
			rs = stmt.executeQuery(sql);
			for(int ii=0;ii<3;ii++){
				res[ii]=new String();
			}
			if(rs.next()){
				res[0]=rs.getString(1);
				res[1]=rs.getString(2);
				res[2]=rs.getString(3);
			}else{
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res;
	}
	public int GetUserId(String name){
		try{
			sql="select id from students where name="+"\'"+name+"\'";
			System.out.print(sql);
			stmt = connection.createStatement(); 
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				id=rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return id;
	}
	public void Close() {
		try{
			super.Close();
			rs.close();
	        stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
