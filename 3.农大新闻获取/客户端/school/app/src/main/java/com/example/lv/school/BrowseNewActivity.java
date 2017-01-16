package com.example.lv.school;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by lv on 2016/12/19.
 */

public class BrowseNewActivity extends Activity {
    private WebView webView;
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_news);
        webView=(WebView)findViewById(R.id.webView);
        String url=getIntent().getStringExtra("content_url");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setTextSize(WebSettings.TextSize.LARGEST);
        //开启文件下载
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(url);
    }
    private class MyWebViewDownLoadListener implements DownloadListener {
        @Override
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,
                                    long contentLength) {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
    }
}
//
