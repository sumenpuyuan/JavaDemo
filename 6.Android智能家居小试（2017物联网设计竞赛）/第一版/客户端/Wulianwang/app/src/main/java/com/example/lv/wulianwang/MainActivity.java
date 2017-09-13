package com.example.lv.wulianwang;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.lv.wulianwang.Util.BytesUtil;
import com.example.lv.wulianwang.Util.PostGetUtil;

public class MainActivity extends AppCompatActivity {
    private String[] data;
    private String[] realData;
    private Button refresh;
    private byte[] mRestart = null;
    String res;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(res);
              //  Toast.makeText(MainActivity.this,res, Toast.LENGTH_LONG).show();
            //    data.setText(res);
                realData=res.split(">");
              //  Toast.makeText(MainActivity.this,realData[1], Toast.LENGTH_LONG).show();
                setData(realData[0]);
                setImage(realData[1]);

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh=(Button)findViewById(R.id.refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
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
        String[] realData=new String[6];
        gasData=gasData.substring(0,gasData.lastIndexOf("Node")).trim();
        data=gasData.split("\n");
        int co=Integer.parseInt(data[1].substring(3));
        if(co > 1000){
            realData[2]="CO浓度："+co+" dppm  浓度超标";
        }else{
            realData[2]="CO浓度："+co+" dppm";
        }

        double n=Integer.parseInt(data[2].substring(3))/10000;
        realData[3]="NH3浓度："+n+" mg/m3";

        int yan=Integer.parseInt(data[3].substring(3));
        if(yan>2500){
            realData[4]="有烟雾";
        }
        else{
            realData[4]="无烟雾";
        }
        int huo=Integer.parseInt(data[4].substring(3));
        if(huo <500){
            realData[5]="有火情";
        }else{
            realData[5]="无火情";
        }

        int wen=Integer.parseInt(data[5].substring(3));
        realData[0]="温度："+wen+"℃";

        realData[1]="湿度："+data[6].substring(3)+" %";







        //    Toast.makeText(MainActivity.this,data[1],Toast.LENGTH_LONG).show();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,realData);
        ListView listView=(ListView)findViewById(R.id.list);
        listView.setAdapter(adapter);

    }
    public void setImage(String imageStr){

                mRestart = BytesUtil.hexStringToBytes(imageStr);

        Bitmap bmp = BitmapFactory.decodeByteArray(mRestart, 0, mRestart.length);

        ImageView image = (ImageView) findViewById(R.id.image);

        image.setImageBitmap(bmp);
    }

}

