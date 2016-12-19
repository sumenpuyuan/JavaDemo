import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * 用户登录成功的主窗口
 * */
public class UserMain {
	String name;
	Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
	JFrame frame=new JFrame("用户主界面");
	JLabel welcome;
	JButton write=new JButton("我要做题");
	JButton record=new JButton("查看我的记录");
	JButton exit=new JButton("退出系统");
	JLabel bg=new JLabel(new ImageIcon("bg.jpg"));
	public UserMain(String name){
		write.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getSource() == write){
					new UserExam(name);
					frame.dispose();
				}
				
			}	
		});
		record.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == record){
					new UserRecord(name);
					frame.dispose();
				}
			}
		});
		exit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == exit){
					new UserLogin();
					frame.dispose();
				}
			}
		});
		welcome=new JLabel("欢迎你:"+name+"!");
		this.name=name;
		frame.setLayout(null);
		welcome.setBounds(130,50,150,40);
		write.setBounds(130, 90, 150, 40);
		record.setBounds(130, 150, 150, 40);
		exit.setBounds(130, 200, 150, 40);
		bg.setBounds(0, 0, 600, 400);
		frame.add(welcome);
		frame.add(write);
		frame.add(record);
		frame.add(exit);
		frame.add(bg);
		frame.setSize(600,400);
		frame.setLocation(screenSize.width/2-600/2,screenSize.height/2-400/2);
		frame.setVisible(true);
	}
	
}
