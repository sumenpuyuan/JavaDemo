package model;

import java.awt.peer.TrayIconPeer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.crypto.provider.RSACipher;
import com.sun.org.apache.regexp.internal.recompile;

public class MRecord extends DbBase{
	String sql;
	Statement stmt;
	ResultSet rs=null;
	MQuestion mQuestion;
	public MRecord(){
		super();
		mQuestion=new MQuestion();
	}
	public void InsertRoughRecord(int id,int examNum,int score,String time){
		try{
			sql="insert into roughRecord values("+id+","+examNum+","+"\'"+time+"\',"+score+")";
			System.out.println("插入大概记录光标"+sql);
			stmt=connection.createStatement();
			stmt.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public void InsertDetailRecord(int id,int questionNum,String righAns,int ifOk,String userAns,int examNum){
		
		try{
			sql="insert into detailRecord values ("+id+","+questionNum+","+"\'"+righAns+"\',"+ifOk+","+"\'"+userAns+"\',"+examNum+")";
			System.out.println("插入具体记录"+sql);
			stmt=connection.createStatement();
			stmt.executeUpdate(sql);
		}catch(SQLException e){
			e.printStackTrace();
		}
		
	}
	public int GetUserMaxExamNum(int id){
		int maxExamNum=0;
		try{
			sql="select MAX(examNum) from roughRecord where id="+id+"";
			stmt=connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				maxExamNum=rs.getInt(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return maxExamNum+1;
	}
	//拿到某个大概记录表的所有数据
	public String[][] GetRoughRecord(int id){
		String roughtRecord[][]=null;
		//需要拿到有多少行数据，然后为字符串数组申请空间
		try{
			sql="select count(*) from roughRecord group by id having id="+id+"";
			stmt=connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				roughtRecord=new String[rs.getInt(1)][4];
			}
			
			//然后往字符串数组里加入数据
			sql="select * from roughRecord where id="+id+"";
			rs=stmt.executeQuery(sql);
			int ii=0;
			while(rs.next()){
				roughtRecord[ii]=new String[4];
				roughtRecord[ii][0]=rs.getString(1);
				roughtRecord[ii][1]=rs.getString(2);
				roughtRecord[ii][2]=rs.getString(3);
				roughtRecord[ii][3]=rs.getString(4);
				ii++;
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return roughtRecord;
	}
	//拿到某个用户的详细数据
	public String[][] GetDetailRecord(int id,int examNum){
		//题目内容 正确答案 用户是否正确 用户答案
		String[][] detailRecord=new String[5][4];
		int ii=0;
		try{
			sql="select * from detailRecord where id="+id+" and examNum="+examNum+"";
			System.out.println("拿到用户的详细数据sql'"+sql);
			stmt=connection.createStatement();
			rs=stmt.executeQuery(sql);
			while(rs.next()){
				detailRecord[ii]=new String[4];
				detailRecord[ii][0]=mQuestion.GetOneQuestionContent(rs.getInt(2));
				detailRecord[ii][1]=rs.getString(3);
				detailRecord[ii][2]=rs.getString(4);
				detailRecord[ii][3]=rs.getString(5);
				ii++;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return detailRecord;
		 
	}
}
