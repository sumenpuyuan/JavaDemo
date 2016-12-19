import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import Controller.*;
class UserLogin{
		Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
		JFrame frame=new JFrame("小学生测试登录页面");
		JLabel nameLab=new JLabel("用户名");
		JLabel noedLab=new JLabel("密码");
		JLabel infoLab=new JLabel("登录信息");
		JTextField name=new JTextField();
		JPasswordField noed=new JPasswordField();
		JButton submit=new JButton("提交");
		JLabel bg=new JLabel(new ImageIcon("bg.jpg"));
		CLogin cLogin;
		public UserLogin(){
			//放入自己的键盘监听事件
			// 获取按键的code
	          //((KeyEvent) event).getKeyChar();
			// 获取按键的字符
			Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
			    public void eventDispatched(AWTEvent event) {
			        if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
			            //System.out.println();
			            if(((KeyEvent) event).getKeyCode() == 10){
			            	ClickSubmit();
			            }
			        }
			    }
			}, AWTEvent.KEY_EVENT_MASK);
			cLogin =new CLogin();
			submit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(arg0.getSource() == submit){
						ClickSubmit();
					}	
				}	
			});
			frame.addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent arg0){
					System.exit(1);
				}
			});	
			
			frame.setLayout(null);
			nameLab.setBounds(130,90,100,25);
			noedLab.setBounds(130,120,100,25);
			infoLab.setBounds(130,150,100,25);
			name.setBounds(230,90,140,20);
			noed.setBounds(230,120,140,20);
			submit.setBounds(250,150,140,20);
			bg.setBounds(0, 0, 600, 400);
			
			
			frame.add(nameLab);
			frame.add(name);
			frame.add(noedLab);
			frame.add(noed);
			frame.add(infoLab);
			frame.add(submit);
			frame.add(bg);
			frame.setSize(600,400);
			frame.setLocation(screenSize.width/2-600/2,screenSize.height/2-400/2);
			frame.setVisible(true);//�������ʾ
			//frame.addKeyListener(this);
			//nameLab.addKeyListener(this);
			//this.addKeyListener(frame);
		}
		public void ClickSubmit(){
			String tname=name.getText();
			String tpassword=noed.getText();
			//Check log=new Check(tname,tpassword);
			if(cLogin.CheckUser(tname, tpassword)){
				infoLab.setText("成功");
				//在这里关闭资源？成功的话
				cLogin=null;
				frame.dispose();
				new UserMain(tname);
				
			}else{
				infoLab.setText("失败");
				JOptionPane.showMessageDialog(null, "登录失败，请检查您的信息是否正确", "错误信息", JOptionPane.ERROR_MESSAGE);
				
			}
		}
		
		
}

