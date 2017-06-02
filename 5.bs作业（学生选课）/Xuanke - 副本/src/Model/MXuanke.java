package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MXuanke {
	Connection connection = DBConnect.getConnection(); 
	ResultSet rs = null;
	String res[][];
	int num=0,ii=0;
	int r=0;
	//根据学生id 删除所有与该学生有关的课程信息
	public boolean DelXuanKeById(int user_id){
		String sql="delete from xuanke where user_id="+user_id+"";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			r=stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(r>=0)
			return true;
		else {
			return false;
		}
	}
	//根据课程id和学生id 修改学生成绩
	public boolean ChangeStuScore(int course_id,int user_id,int score){
		String sql="update xuanke set score="+score+" where course_id="+course_id+" and user_id="+user_id+"";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			r=stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(r>0)
			return true;
		else {
			return false;
		}
	}
	//
	public boolean Xuanke(int user_id,int course_id){
		String sql="insert into xuanke values("+course_id+","+user_id+",0)";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			r=stmt.executeUpdate(sql);
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		if(r>0)
			return true;
		else {
			return false;
		}
	}
	//看看 该学生已经选了哪门课
	public String[][] ShowAll(int id){
		String sql="select b.name,xuefen,des,score from user as a,course as b ,xuanke as c where a.id=c.user_id and b.id=course_id and a.id="+id+"";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sql);
			rs.last();
			num=rs.getRow();
			res=new String[num][4];
			rs.beforeFirst();
			while(rs.next()){
				res[ii][0]=rs.getString(1);
				res[ii][1]=rs.getString(2);
				res[ii][2]=rs.getString(3);
				res[ii][3]=rs.getString(4);
				ii++;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	//根据课程id看所有的选课数据
	public String[][] ShowAllElectiveByCourseId(int id){
		String sql="select b.id,b.name,a.id,a.name,score from user as a,course as b ,xuanke as c where a.id=c.user_id and b.id=course_id and b.id="+id+"";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sql);
			rs.last();
			num=rs.getRow();
			res=new String[num][5];
			rs.beforeFirst();
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
	//这次看看所有的学生
	public String[][] ShwoAllElective(){
		String sql="select b.id,b.name,a.id,a.name,score from user as a,course as b ,xuanke as c where a.id=c.user_id and b.id=course_id";
		System.out.println("sql语句是"+sql);
		try{
			Statement stmt = connection.createStatement();
			rs=stmt.executeQuery(sql);
			rs.last();
			num=rs.getRow();
			res=new String[num][5];
			rs.beforeFirst();
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
	//根据课程id删除 与该课程有关的数据
	public boolean DelXuanByCourseId(int course_id){
		String sql="delete from xuanke where course_id="+course_id+"";
		System.out.println("sql语句是"+sql);
		try{
			int r=-1;
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
