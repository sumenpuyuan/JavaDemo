package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class MUser{
	Connection connection = DBConnect.getConnection(); 
	 ResultSet rs = null;
	 String res[][];
	 String row[]=new String[3];
	 String id;
	public String GetUserId(String name){
		String sql="select id from user where name='"+name+"'";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				id=rs.getString(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return id;
	}
	public String GetUserPassword(String name){
		String password="";
		String sql="select password from user where name='"+name+"'";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				password=rs.getString(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return password;
	}
	public String[] QueryStudent(String name){
		String sql="select * from user where name='"+name+"'";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				row[0]=rs.getString(1);
				row[1]=rs.getString(2);
				row[2]=rs.getString(3);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return row;
	}
	public boolean AddStudnet(String name,String password){
		int r=-1;
		String sql="insert into user(name,password) values ('"+name+"','"+password+"')";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			r=stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(r>0){
			return true;
		}else{
			return false;
		}
	}
	public boolean ChangeStudent(int id,String name,String password){
		int r=-1;
		String sql="update user set name='"+name+"',password="+password+" where id="+id+"";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			r=stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(r>0){
			return true;
		}else{
			return false;
		}
		
		
	}
	public boolean DelStudent(int id){
		int r=-1;
		String sql="delete from user where id="+id+"";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			r=stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(r>0){
			return true;
		}else{
			return false;
		}
		
	}
	public String[][] GetAllStudent(){
		int num=0,ii=0;
		String sql="select * from user";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sql);
			rs.last();
			num=rs.getRow();
			res=new String[num][3];
			
			rs.beforeFirst();
			
			while(rs.next()){
				res[ii][0]=rs.getString(1);
				//System.out.println("拿到的")
				res[ii][1]=rs.getString(2);
				res[ii][2]=rs.getString(3);
				ii++;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
}