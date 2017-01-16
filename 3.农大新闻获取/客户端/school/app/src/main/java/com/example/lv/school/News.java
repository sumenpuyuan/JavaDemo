package com.example.lv.school;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.MapView;
import com.example.lv.school.Adaper.NewsAdapter;
import com.example.lv.school.Model.New;
import com.example.lv.school.Util.PostGetUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lv on 2016/12/17.
 */

public class News extends Fragment implements AdapterView.OnItemClickListener{
    private ListView lvNews;
    private NewsAdapter adapter;
    private List<New>newsList;
    TextView textView;
    String newsData;
    UserMain userMain=(UserMain)getActivity();
    public static final String GET_NEWS_URL="http://121.42.211.211/school/news/GetNews.php";

    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(newsData);
                Log.d("News","__________________________hehhehheh____________");
                //Toast.makeText(userMain,"hehhe",Toast.LENGTH_LONG).show();
                //textView.setText(newsData);
                try{
                    JSONArray jsonArray=new JSONArray(newsData);
                    for(int ii=0;ii<jsonArray.length();ii++){
                        JSONObject object=jsonArray.getJSONObject(ii);
                        String title=object.getString("title");
                        String content_url=object.getString("content_url");
                        //textView.append(title+"   "+content_url+"\n");
                        newsList.add(new New(title,content_url));//存入arraylist数组
                    }
                    adapter.notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    };
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        newsList=new ArrayList<New>();
        View view = inflater.inflate(R.layout.school_news,container, false);//把layout布局文件转换成View对象
       lvNews=(ListView)view.findViewById(R.id.lvNews);
        //开启线程，获取内容
        new Thread(){
            public  void run(){
                newsData= PostGetUtil.sendGet(GET_NEWS_URL,"");
                Log.d("News","__________________666___________");
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        }.start();
        //
        adapter=new NewsAdapter(this.getActivity(),newsList);
        textView=(TextView)view.findViewById(R.id.textId);

        lvNews.setAdapter(adapter);
        lvNews.setOnItemClickListener(this);

        return view;
    }
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //处理单击事件
        New n=newsList.get(position);
        //postion这个位置的新闻的url 传入下一个活动
        Intent intent=new Intent(this.getActivity(),BrowseNewActivity.class);
        intent.putExtra("content_url",n.getContent_url());
        startActivity(intent);
    }
}