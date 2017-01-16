package com.example.lv.school;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lv.school.Model.New;
import com.example.lv.school.Util.PostGetUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lv on 2016/12/17.
 */

public class UserInfo extends Fragment {

    ImageView head;
    TextView name;
    TextView id;
    TextView college;
    TextView major;
    TextView user_class;
    UserMain userMain;
    String response;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                try{
                    Log.d("UserInfo",response);

                        JSONObject object=new JSONObject(response);

                        Log.d("UserInfo","++++++++++++++++"+object.getString("name"));
                        setPicBitmap(head,object.getString("head"));
                        name.setText(object.getString("name"));

                        id.setText(object.getString("id"));
                        college.setText(object.getString("college"));
                        major.setText(object.getString("major"));
                        user_class.setText(object.getString("class"));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view=inflater.inflate(R.layout.user_info,container,false);
        head=(ImageView)view.findViewById(R.id.head);
        name=(TextView)view.findViewById(R.id.name);
        id=(TextView)view.findViewById(R.id.id);
        college=(TextView)view.findViewById(R.id.college);
        major=(TextView)view.findViewById(R.id.major);
        user_class=(TextView)view.findViewById(R.id.user_class);
        userMain=(UserMain)getActivity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                response=PostGetUtil.sendPost("http://121.42.211.211/school/user/GetUserInfo.php","id="+userMain.stuId);
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        }).start();
        return view;

    }
    //设置头像
    public void setPicBitmap(final ImageView ivPic,final String pic_url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url=new URL(pic_url);
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.connect();
                    InputStream is=connection.getInputStream();
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    ivPic.setImageBitmap(bitmap);
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
}