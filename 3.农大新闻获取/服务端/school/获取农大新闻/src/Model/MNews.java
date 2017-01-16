package Model;

import java.sql.Statement;
import java.sql.SQLException;

public class MNews extends DbBase{
	String title;
	String content_url;
	String sql;
	Statement stmt;
	public MNews(){
		super();
	}
	public void UpdateNews(News news,int id){
		title=news.getTitle();
		content_url=news.getContent_url();
		try {
			sql="update news set  title='"+title+"',content_url='"+content_url+"' where id="+id+"";
			System.out.println("我们的sql是"+sql);
			stmt=connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
