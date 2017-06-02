package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class MAdmin{
	Connection connection = DBConnect.getConnection(); 
	 ResultSet rs = null;                      
	public String GetUserPassword(String name){
		String password="";
		String sql="select password from admin where name='"+name+"'";
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
}