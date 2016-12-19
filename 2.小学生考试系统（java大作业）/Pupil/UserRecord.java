import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import com.sun.xml.internal.ws.api.server.Container;

import Controller.*;
import javafx.scene.shape.QuadCurve;
public class UserRecord{
	String name;
	JFrame frame;
	CStudent cStudent;
	CQuestion cQuestion;
	String[][] roughRecord;
	JList list1=null;
	java.awt.Container cont;
	int id;
	JButton detail=new JButton("查看详细信息");
	JButton backToMain=new JButton("返回用户主界面");
	Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	public UserRecord(String name)
	{
		
		detail.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getSource() == detail){
					cQuestion=null;
					cStudent=null;
					int temp=list1.getSelectedIndex();
					temp++;
					System.out.print(temp);
					new UserDetailRecord(name, temp);
					frame.dispose();
				}
			}
		});
		backToMain.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == backToMain){
					cQuestion=null;
					cStudent=null;
					frame.dispose();
					new UserMain(name);
				}
			}
		});
		this.name=name;
		cStudent=new CStudent(name);
		id=cStudent.GetUserId();
		cQuestion=new CQuestion();
		roughRecord=cQuestion.GetRoughRecord(id);//拿到数据
		
		System.out.println("我们前台拿到的大概记录数据为");
		if(roughRecord !=null){
			for(int ii=0;ii<roughRecord.length;ii++){
				for(int ij=0;ij<roughRecord[ii].length;ij++)
					System.out.print(roughRecord[ii][ij]+"  ");
				System.out.println();
			}
		}else{
			System.out.println("wrong");
		}
		
		
		
		frame=new JFrame(name+":统计界面");
		frame.setLayout(new GridLayout());
		if(roughRecord == null){
			System.out.println("没有数据");
			frame.add(backToMain);
		}else{
			cont=frame.getContentPane();
			//cont.add(new JScrollPane(this.list1));//加入滚动条
			list1=new JList(new UserRecordModel(this.roughRecord));
			list1.setBorder(BorderFactory.createTitledBorder("用户记录"));
			list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			cont.add(this.list1);
			cont.add(this.detail);
			cont.add(this.backToMain);
		}
		
		
		//frame.add(detail);
		frame.setSize(600, 400);
		frame.setLocation(screenSize.width/2-600/2,screenSize.height/2-400/2);
		frame.setVisible(true);
	}
}
class UserRecordModel extends AbstractListModel{
	String[][] roughtRecord;
	
	public UserRecordModel(String[][] roughtRecord) {
		// TODO Auto-generated constructor stub
		this.roughtRecord=roughtRecord;
	} 
	@Override
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		if(arg0 < this.roughtRecord.length){
			String reString="";
			for(int ii=0;ii<3;ii++){
				reString=reString+"   "+roughtRecord[arg0][ii+1];
			}
			return reString;//返回一个一维字符串数组
		}
		else {
			return null;
		}
		
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return this.roughtRecord.length;
		//return 0;
	}
	
}
