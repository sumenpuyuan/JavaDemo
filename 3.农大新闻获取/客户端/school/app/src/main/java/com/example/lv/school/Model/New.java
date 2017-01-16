package com.example.lv.school.Model;

/**
 * Created by lv on 2016/12/19.
 */

public class New {
    private String title;
    private String content_url;
    public New(String title,String content_url){
        setContent_url(content_url);
        setTitle(title);
    }
    public void setContent_url(String content_url) {
        this.content_url = content_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public String getContent_url() {
        return content_url;
    }


}
