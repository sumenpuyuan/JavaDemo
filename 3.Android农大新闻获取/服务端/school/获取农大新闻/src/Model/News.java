package Model;

public class News {
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
