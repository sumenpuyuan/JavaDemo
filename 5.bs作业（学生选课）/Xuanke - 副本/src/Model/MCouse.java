package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MCouse {
	Connection connection = DBConnect.getConnection(); 
	ResultSet rs = null;
	ResultSet newrs=null;
	String res[][];
	int num=0,ii=0;
	//仅仅显示全部课程
	public String[][] ShowAllOnlyCourse(){
		String sql="select * from course";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			Statement stmt2=connection.createStatement();
			rs=stmt.executeQuery(sql);
			rs.last();
			num=rs.getRow();
			rs.beforeFirst();
			res=new String[num][5];
			
			while(rs.next()){
				res[ii][0]=rs.getString(1);
				res[ii][1]=rs.getString(2);
				res[ii][2]=rs.getString(3);
				res[ii][3]=rs.getString(4);
				res[ii][4]=rs.getString(5);
				
				ii++;
			}
			
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	//传入的是学生id 拿到改学生对于所有课程的状态
	public String[][] ShowAllCourse(int id){
		String sql="select * from course";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			Statement stmt2=connection.createStatement();
			rs=stmt.executeQuery(sql);
			rs.last();
			num=rs.getRow();
			rs.beforeFirst();
			res=new String[num][6];
			
			while(rs.next()){
				res[ii][0]=rs.getString(1);
				res[ii][1]=rs.getString(2);
				res[ii][2]=rs.getString(3);
				res[ii][3]=rs.getString(4);
				res[ii][4]=rs.getString(5);
				//等于-1表示该学生没有选这门课
				res[ii][5]="-1";
				
				sql="select * from xuanke where course_id="+res[ii][0]+" and user_id="+id+"";
				System.out.println("新的sql是"+sql);
				newrs=stmt2.executeQuery(sql);
				while(newrs.next()){
					res[ii][5]="1";
				}
				newrs=null;
				ii++;
			}
			
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	//根据课程id删除课程
	public boolean DelCourseById(int id){
		int r=-1;
		String sql="delete from course where id="+id+"";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			r=stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(r>=0){
			return true;
		}else{
			return false;
		}
	}
}
