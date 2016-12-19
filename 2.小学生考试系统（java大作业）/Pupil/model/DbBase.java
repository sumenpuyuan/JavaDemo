package model;

import java.sql.DriverManager;
import java.sql.*;


/**
 *
 * @author lv
 * 这个文件是数据库的基础文件，连接mysql数据库，关闭数据库
 *
 */
public class DbBase {
	//用的Class.froName方法加载驱动程序
	Connection connection;
	final static String url="jdbc:mysql://localhost:3306/pupil?useUnicode=true&characterEncoding=utf8";
	final static String dbType="com.mysql.jdbc.Driver";
	final static String username="root";
	final static String password="123";
	public DbBase(){
		try{
			Class.forName(dbType);
			System.out.println("成功加载MySQL驱动！");
			connection=DriverManager.getConnection(url, username, password);
		//	Statement stmt = connection.createStatement(); //创建Statement对象
            System.out.println("成功连接到数据库！");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void Close(){
		try{
			connection.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
