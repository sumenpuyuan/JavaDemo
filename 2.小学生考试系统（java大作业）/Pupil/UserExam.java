/*
 * 用户答题界面
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.corba.se.spi.orbutil.fsm.Action;
import Controller.*;
public class UserExam {
	String[][] questions=new String[5][3];
	JFrame frame=new JFrame("答题界面");
	JLabel que[]=new JLabel[5];
	JTextField userAnswer[]=new JTextField[5];
	String userAns[]=new String[5];
	JLabel ifOk[]=new JLabel[5];//用户做题后是否正确，正确输出输入 回答正确，否则是输出回答错误
	JLabel rightAns[]=new JLabel[5];//如果用户输入错误，提示正确答案
	JButton submit=new JButton("提交");
	JButton backToMain=new JButton("返回用户主界面");
	CQuestion cQuestion;//处理问题类
	CStudent cStudent;//处理用户类
	String name;//用户姓名
	int score=0;
	JLabel scoreLab;
	Font scoreFont=new Font("Serief", Font.BOLD, 28);
	//我先来"看看能不能拿到数据
	public UserExam(String name){
		scoreLab=new JLabel();
		submit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				if(arg0.getSource() == submit){
					ClickSubmit();
					System.out.print("点击了提交");
				}
			}
		});
		backToMain.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getSource() == backToMain){
					ClickBackToMain();
				}
				
			}
		});
		for(int ii=0;ii<5;ii++){
			ifOk[ii]=new JLabel("");
			rightAns[ii]=new JLabel("");
		}
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		this.name=name;
		cQuestion=new CQuestion();
		cStudent=new CStudent(name);
		questions=cQuestion.GetFiveQuestion();
		for(int ii=0;ii<5;ii++){
			que[ii]=new JLabel("");
			que[ii].setText(ii+"       "+questions[ii][1]);
		}
		frame.setLayout(null);
		int y=50;
		for(int ii=0;ii<5;ii++){
			userAnswer[ii]=new JTextField();
			que[ii].setBounds(130, y, 400, 40);
			userAnswer[ii].setBounds(130, y+40, 400, 40);
			ifOk[ii].setBounds(540, y+40, 100, 40);
			rightAns[ii].setBounds(650, y+40, 200, 40);
			frame.add(ifOk[ii]);
			frame.add(rightAns[ii]);
			frame.add(que[ii]);
			frame.add(userAnswer[ii]);
			y+=80;
		}
		//添加左边的用户是否回答正确，和-正确答案标签
		
		
		submit.setBounds(130,y+40,100,20);
		backToMain.setBounds(250, y+40, 150, 20);
		scoreLab.setBounds(800, 10, 200, 200);
		frame.add(scoreLab);
		frame.add(submit);
		frame.add(backToMain);
		frame.setSize(1000, 600);
		frame.setLocation(screenSize.width/2-1000/2,screenSize.height/2-600/2);
		frame.setVisible(true);
	}
	public void ClickSubmit(){
		submit.setEnabled(false);
		for(int ii=0;ii<5;ii++){
			userAns[ii]=userAnswer[ii].getText();
			userAnswer[ii].setEditable(false);
		}
		int flag[]=new int[5];
		flag=cQuestion.JudgeUserQuestion(questions, userAns);
		for(int ii=0;ii<5;ii++){
			if(flag[ii] == 1){
				ifOk[ii].setIcon(new ImageIcon("ok.png"));
				score+=20;
			}else{
				ifOk[ii].setIcon(new ImageIcon("wrong.jpg"));
				rightAns[ii].setText("正确答案是："+questions[ii][2]);
			}
		}
		scoreLab.setText("得分："+score);
		scoreLab.setFont(scoreFont);
		//是不是应该去插入记录粗略表和记录详细表了
		int id=cStudent.GetUserId();
		
		cQuestion.InsertRoughRecord(id,score);
		//插入详细记录表
		cQuestion.InsertDetailRecord(id,questions,userAns,flag);
		cQuestion=null;
		cStudent=null;
	}
	public void ClickBackToMain(){
		cQuestion=null;
		cStudent=null;
		new UserMain(name);
		frame.dispose();
	}
}
