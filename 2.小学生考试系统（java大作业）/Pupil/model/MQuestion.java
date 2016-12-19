package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MQuestion extends DbBase{
	ResultSet rs=null;
	Statement stmt;
	String sql;
	String[] res;
	String content;
	public MQuestion(){
		super();
		res=new String[4];
		for(int ii=0;ii<4;ii++){
			res[ii]=new String();
		}
	}
	public String[] GetOneQuestion(int id){
		try{
			sql="select * from questions where id="+id+"";
			stmt=connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				res[0]=rs.getString(1);
				res[1]=rs.getString(2);
				res[2]=rs.getString(3);
				res[3]=rs.getString(4);
				System.out.print(res[0]+"  "+res[1]+"  "+res[2]+"   "+res[3]);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return res;
	}
	public String GetOneQuestionContent(int id){
		try{
			sql="select content from questions where id="+id+"";
			stmt=connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				content=rs.getString(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return content;
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
