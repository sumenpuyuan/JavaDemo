package com.example.lv.school.DetailCommunity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lv.school.LoginActivity;
import com.example.lv.school.R;
import com.example.lv.school.UserMain;
import com.example.lv.school.Util.PostGetUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by lv on 2016/12/31.
 */

public class DetailCommunity extends AppCompatActivity{
    String stuId;
    String commuId;
    String response;
    TextView commu_name;
    TextView commu_desc;
    TextView likeNums;
    Button support;

    Handler handler=new Handler(){
        public void handleMessage(Message msg){
            if(msg.what == 0x123){
               // Toast.makeText(DetailCommunity.this,response,Toast.LENGTH_LONG).show();
                try{
                    JSONArray jsonArray=new JSONArray(response);
                    for(int ii=0;ii<jsonArray.length();ii++){
                        JSONObject object=jsonArray.getJSONObject(ii);
                        String name=object.getString("name");
                        String description=object.getString("description");
                        String like=object.                 getString("likeNums");
                        String ifLike=object.getString("ifLike");
                        commu_name.setText(name);
                        commu_desc.setText(description);
                        likeNums.setText(like);
                        if(ifLike.equals("1")){
                            support.setEnabled(false);
                            support.setText("已支持");
                        }else{
                            support.setEnabled(true);
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if(msg.what ==0x234){
                Toast.makeText(DetailCommunity.this,"支持成功",Toast.LENGTH_LONG).show();
            }
        }
    };
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_community);
        commu_name=(TextView)findViewById(R.id.commu_name);
        commu_desc=(TextView)findViewById(R.id.commu_desc);
        likeNums=(TextView)findViewById(R.id.likeNums);
        support=(Button)findViewById(R.id.support);
        Intent intent=getIntent();
        stuId=intent.getStringExtra("stuId");
        commuId=intent.getStringExtra("commuId");
        final String date="stuId="+stuId+"&commuId="+commuId;
        //Toast.makeText(DetailCommunity.this,stuId+"  "+commuId,Toast.LENGTH_LONG).show();
        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //先更新界面
                int like=Integer.parseInt((String) likeNums.getText());
                like++;
                likeNums.setText(like+"");
                support.setEnabled(false);
                //再更新数据库
                UpdateLikeNums(stuId,commuId);
            }
        });
        new Thread(){
            public void run(){
                response= PostGetUtil.sendPost("http://121.42.211.211/school/community/GetDetailContent.php",date);
                //    response=PostGetUtil.sendGet("http://www.baidu.com","");
                handler.sendEmptyMessage(0x123);
            }
        }.start();
    }
    public void UpdateLikeNums(String stuId,String commuId){
        final String date="stuId="+stuId+"&commuId="+commuId;
        new Thread(){
            public void run(){
                PostGetUtil.sendPost("http://121.42.211.211/school/community/UpdateLikeNums.php",date);
                handler.sendEmptyMessage(0x234);
            }
        }.start();
    }
}
