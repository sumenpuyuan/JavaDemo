import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.*;



/*
 * 数据库基础连接文件
 * */
class DbBase {
	//加载数据库驱动
	Connection connection;
	final static String url="jdbc:mysql://121.42.211.211:3306/school?useUnicode=true&characterEncoding=utf8";
	final static String dbType="com.mysql.jdbc.Driver";
	final static String username="root";
	final static String password="Xicheng2016";
	public DbBase(){
		try{
			Class.forName(dbType);
			System.out.println("成功MySQL驱动");
			connection=DriverManager.getConnection(url, username, password);
		//	Statement stmt = connection.createStatement(); //����Statement����
            System.out.println("成功连接到数据库");
			
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
/*
 * 
 * 新闻实体类
 * */
class News {
	private String title;
	private String content_url;
	public News(String title,String content_url){
		this.title=title;
		this.content_url=content_url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent_url() {
		return content_url;
	}
	public void setContent_url(String content_url) {
		this.content_url = content_url;
	}
	
}
/*
 * 操作新闻表 数据层类
 * */
class MNews extends DbBase{
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
			System.out.println("sql语句是"+sql);
			stmt=connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
}
/*
 * 
 * 控制层
 * */
class GetNews {
	LinkedList<News> ready;
	MNews mNews;
	public GetNews(){
		mNews=new MNews();
	}
	/*
	 * 生成html文件
	 * */
	public void writeHtml(String filePath, String content){  
		try{
			 File file = new File(filePath);  
		        synchronized (file) {  
		            FileWriter fw = new FileWriter(filePath);  
		            fw.write(content);  
		            fw.close();  
		        } 
		}catch(IOException e){
			e.printStackTrace();
		}
        
    }  
    /**
     * 获取html内容
     */
    public String getHtmlContent(String htmlurl) {
        URL url;
        String temp;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(htmlurl);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));// ��ȡ��ҳȫ������
            while ((temp = in.readLine()) != null) {
                sb.append(temp);
            }
            in.close();
        } catch (final MalformedURLException me) {
            System.out.println("获取网页失败!");
            me.getMessage();
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    /*
     * 获取新闻详细内容 生成html文件
     * */
    public void GetContext(String id,String s){
    	String match="";
    	String pattern="<table\\swidth='90%'\\salign='center'\\sstyle=\"margin-top:10px;\"(.*?)<meta\\sname=\"ContentEnd\">(.*?)</table>";
    	Pattern pattern2=Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
    	Matcher matcher=pattern2.matcher(s);
    	while(matcher.find()){
    		int start=matcher.start();
    		int end=matcher.end();
    		match=s.substring(start,end);
    	}
    	//替换图片路径
    	match=match.replace("src=\"", "src=\"http://www.hebau.edu.cn");
    	//替换附件路径
    	match=match.replace("<a href=\"", "<a href=\"http://www.hebau.edu.cn");
    	//替换正文字体大小
    	match=match.replace("16px","25px");
    	match=match.replace("18px","25px");
    	System.out.println(match.length()+"   "+match);
   
    	writeHtml(id+".html", match);
    	
    	
    }
    /*
     * 获取新闻标题 url
     * */
    public LinkedList<News> GetNew(String s){
    	int ij=0;
    	ready=new LinkedList<News>();
    	String pattern="<a\\s+href=\'(.*?)\'\\sclass='bt_link'\\stitle=\'(.*?)\'";
    	Pattern pattern2=Pattern.compile(pattern,Pattern.CASE_INSENSITIVE);
    	Matcher matcher=pattern2.matcher(s);
    	while(matcher.find()){
    		int start=matcher.start();
    		int end=matcher.end();
    		String match=s.substring(start,end);
    		String[] reStrings=match.split("'");
    		for(int ii=0;ii<reStrings.length;ii++){
    			System.out.print(reStrings[ii]+"   ");
    		}
    		System.out.println();
    		reStrings[1]="http://www.hebau.edu.cn"+reStrings[1];
    		if(ij<15 && ij>=1){
        		String  detail=getHtmlContent(reStrings[1]);
        		GetContext(ij+"",detail);
        		
        		String url="http://121.42.211.211/school/linuxGetNews/"+ij+".html";
        		ready.add(new News(reStrings[5], url));
        		System.out.println("标题"+reStrings[5]+"url是"+reStrings[1]+"\n");
    		}
    		
    		
    		ij++;
    	}
    	System.out.println("reay大小是"+ready.size());
    	for(int ii=0;ii<ready.size();ii++)
    		mNews.UpdateNews(ready.get(ii), ii+1);
    	
    	return ready;
    }
}
/*
 * 入口
 * */
public class Main {
	public static void main(String[] args) {
		 LinkedList<News> ready;
	       GetNews t = new GetNews();
	       String content = t.getHtmlContent("http://www.hebau.edu.cn/");
	        System.out.println(content);
	        ready=t.GetNew(content);
	        for(int ii=0;ii<ready.size();ii++){
	        	System.out.println(ready.get(ii).getTitle()+"  "+ready.get(ii).getContent_url());	
	        }
	        
	    }

}
