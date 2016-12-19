package Controller;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.Random;
import java.util.*;

import model.*;
public class CQuestion {
	MQuestion mQuestion;
	MRecord mRecord;
	String[] res;
	public CQuestion(){
		mQuestion=new MQuestion();
		mRecord=new MRecord();
	}
	public void Close(){
		mQuestion.Close();
	}
	public String[][] GetFiveQuestion(){
		String[][] questions=new String[5][4];
		Random random=new Random();
		LinkedList<Integer> ready=new LinkedList<Integer>();
		int id;
		for(int ii=0;ii<5;ii++){
			id=random.nextInt(14)+1;
			if(!ready.contains(id)){
				ready.add(id);
			}else{
				ii--;
			}
		}
		for(int ii=0;ii<5;ii++){
			//还要保证不重复
			//id=random.nextInt(14)+1;
			//questions[ii]=mQuestion.GetOneQuestion(ready.get(ii));
			for(int ij=0;ij<4;ij++){
				//不要id，应该要id，因为之后插入记录详细表里的题号需要他
				questions[ii][ij]=mQuestion.GetOneQuestion(ready.get(ii))[ij];
			}
		}
		return questions;
	}
	public int[] JudgeUserQuestion(String[][] questions,String[] userAns){
		int flag[]=new int[5];
		for(int ii=0;ii<5;ii++){
			if(questions[ii][2].equals(userAns[ii]))
				flag[ii]=1;
		}
		return flag;
	}
	public void InsertRoughRecord(int id,int score){
		//拿到当前时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date(System.currentTimeMillis())));// new Date()为获取当前系统时间
		String time=df.format(new Date(System.currentTimeMillis()));
		//拿到大概记录表立，的那个第几次考试
		int examNum=mRecord.GetUserMaxExamNum(id);
		mRecord.InsertRoughRecord(id, examNum, score, time);
		//mRecord.InsertDetailRecord(id, questionNum, righAns, ifOk, userAns, examNum);
	}
	public void InsertDetailRecord(int id,String[][] questions,String[] userAns,int[] flag){
		//我这里是不是应该一下吧5道题都加进去
		int examNum=mRecord.GetUserMaxExamNum(id)-1;
		for(int ii=0;ii<5;ii++){
			mRecord.InsertDetailRecord(id, Integer.parseInt(questions[ii][0]), questions[ii][2], flag[ii], userAns[ii], examNum);
		}	
	}
	//获得粗略记录数据
	public String[][] GetRoughRecord(int id){
		String[][] roughRecord=mRecord.GetRoughRecord(id);
		return roughRecord;
	}
	//获得详细记录数据
	public String[][] GetDetailRecord(int id,int examNum){
		String[][] detailRecord;
		detailRecord=mRecord.GetDetailRecord(id, examNum);
		return detailRecord;
	}
}
