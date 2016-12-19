import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import Controller.CQuestion;
import Controller.CStudent;

public class UserDetailRecord {
	JFrame frame;
	CQuestion cQuestion;
	String name;
	int examNum;
	int id;
	CStudent cStudent;
	String[][] detailRecord;
	JTextArea jTextArea;
	String string;
	JButton back;
	Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	public UserDetailRecord(String name,int examNum){
		back=new JButton("返回上一级");
		jTextArea=new JTextArea();
		this.name=name;
		this.examNum=examNum;
		cQuestion=new CQuestion();
		cStudent=new CStudent(name);
		this.id=cStudent.GetUserId();
		//数据
		detailRecord=cQuestion.GetDetailRecord(id, examNum);
		System.out.println("拿到的详细记录数据位");
		for(int ii=0;ii<detailRecord.length;ii++){
			for(int ij=0;ij<detailRecord[ii].length;ij++){
				System.out.print("  "+detailRecord[ii][ij]);
			}
			System.out.println();
		}
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == back){
					new UserRecord(name);
					cQuestion=null;
					cStudent=null;
					frame.dispose();
					
				}
			}
		});
		String title=string.format("%-100s%-50s%-20s%-50s\n","题目内容","正确答案","是否正确","用户输入答案");
		string=String.format("%-100s%-50s%-20s%-50s\n%-100s%-50s%-20s%-50s\n%-100s%-50s%-20s%-50s\n%-100s%-50s%-20s%-50s\n%-100s%-50s%-20s%-50s\n",
				detailRecord[0][0],detailRecord[0][1].trim(),
				detailRecord[0][2],detailRecord[0][3],
						detailRecord[1][0],detailRecord[1][1],
						detailRecord[1][2],detailRecord[1][3],
						detailRecord[2][0],detailRecord[2][1],
						detailRecord[2][2],detailRecord[2][3],
						detailRecord[3][0],detailRecord[3][1],
						detailRecord[3][2],detailRecord[3][3],
						detailRecord[4][0],detailRecord[4][1],
						detailRecord[4][2],detailRecord[4][3]);
		jTextArea.append(title);
		jTextArea.append(string);
		jTextArea.setEditable(false);
		frame=new JFrame("用户详细页面");
		frame.setLayout(null);
		jTextArea.setBounds(0,0,1000,500);
		back.setBounds(400, 500, 100, 100);
		frame.add(jTextArea);
		frame.add(back);
		frame.setSize(1000, 600);
		frame.setLocation(screenSize.width/2-1000/2,screenSize.height/2-600/2);
		frame.setVisible(true);
	}
}