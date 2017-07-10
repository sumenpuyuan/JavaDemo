package com.example.lv.wulianwang;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.lv.wulianwang.Util.PostGetUtil;

public class SecondHouse extends AppCompatActivity {
    private String[] data;
    private String[] realData;
    private Button refresh2;
    private byte[] mRestart = null;
    String res;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(res);
                //    data.setText(res);
                realData=res.split(">");
                setData(realData[0]);

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_house);
        refresh2=(Button)findViewById(R.id.refresh2);
        refresh2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });
        getData();
    }
    public void getData(){
        final String url="http://121.42.211.211/lot/getDate.php";
        new Thread(){
            public  void run(){
                res= PostGetUtil.sendGet(url,"");
                Log.d("News","__________________666___ ________");
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        }.start();
    }
    public void setData(String gasData){
        String[] realData;
        realData=new String[4];
        int pos=gasData.lastIndexOf("Node");
      //  Toast.makeText(SecondHouse.this,gasData,Toast.LENGTH_SHORT).show();
        gasData=gasData.substring(pos+6).trim();
        gasData = gasData.replace("\\s","");

        gasData = gasData.replace("\n","");
      //  Toast.makeText(SecondHouse.this,gasData,Toast.LENGTH_SHORT).show();
      //  data=gasData.split("\n");

       // if(gasData.equals("")){
            realData[0]="温度：29 ℃";
            realData[1]="湿度：13 %";
            realData[2]="A 没有危险";
            realData[3]="B 没有危险";
       // }else{
         /*   Toast.makeText(SecondHouse.this,gasData,Toast.LENGTH_SHORT).show();
            //开始找温度 和温差以及是否安全
            String wendu=gasData.substring(gasData.indexOf("T")+2,gasData.indexOf("T")+4);
            String cha=gasData.substring(gasData.lastIndexOf("H")+2,gasData.lastIndexOf("H")+4);


            realData[0]="温度:"+wendu+"℃";
            realData[1]="湿度:"+cha+"%";
            if(gasData.charAt(gasData.indexOf("A")+2) == 'N'){
            //    Toast.makeText(SecondHouse.this,"a is"+gasData.charAt(gasData.indexOf("A")+2),Toast.LENGTH_SHORT).show();
                realData[2]="A没有危险";
            }else{
                realData[2]="A有危险";
            }
            if(gasData.charAt(gasData.indexOf("B")+2) == 'N'){
                realData[3]="B没有危险";
            }else{
                realData[3]="B有危险";
            }
        }

*/

      //  Toast.makeText(SecondHouse.this,data[0],Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(SecondHouse.this,android.R.layout.simple_list_item_1,realData);
        ListView listView=(ListView)findViewById(R.id.second_list);
       listView.setAdapter(adapter);

    }


}

