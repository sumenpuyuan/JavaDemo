package View;

import java.util.LinkedList;
import "../Model/News,java";
import Controller.GetNews;


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
