package etong.bottomnavigation.demo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import etong.bottomnavigation.demo.Util.BytesUtil;
import etong.bottomnavigation.demo.Util.PostGetUtil;


public class shufangActivity extends AppCompatActivity {
    private String[] data;
    private String[] realData;
    private Button refresh;
    private byte[] mRestart = null;
    private ListView list;
    String res;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(res);
                //  Toast.ma
              //  Toast.makeText(shufangActivity.this,res, Toast.LENGTH_LONG).show();
                //    data.setText(res);
                realData=res.split("\n");
              //    Toast.makeText(shufangActivity.this,realData[1], Toast.LENGTH_LONG).show();
                setData(realData);
                //setImage(realData[1]);

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shufang);
        refresh=(Button)findViewById(R.id.refresh);
        list=(ListView)findViewById(R.id.list);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData();
            }
        });

        getNewDate();
    }
    public void getNewDate(){
        final String url="http://121.42.211.211/lot/getHomeTwo.php";
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                res= PostGetUtil.sendGet(url,"");
                Log.d("News","__________________666___ ________");
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        };
        Timer timer = new Timer();
        // 参数：
        // 1000，延时1秒后执行。
        // 2000，每隔2秒执行1次task。
        timer.schedule(task, 1000, 2000);
    }
    public void getData(){
        final String url="http://121.42.211.211/lot/getHomeTwo.php";
        new Thread(){
            public  void run(){
                res= PostGetUtil.sendGet(url,"");
                Log.d("News","__________________666___ ________");
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        }.start();
    }
    public void setData(String gasData[]){
        //先找温度
        String realData[]=new String[4];
        realData[0]="温度："+gasData[1].substring(2)+"℃";
        realData[1]="湿度："+gasData[2].substring(2)+" %";

        //if(gasData[3].substring(2).equals("N")){
            realData[2]="无烟雾";
        //}else{
        //    realData[2]="有烟雾";
          //  Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            //long [] pattern = {50,400,50,400};   // 停止 开启 停止 开启
            //vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
        //}

        if(gasData[4].substring(2).equals("N")){
            realData[3]="无火情";
        }else{
            realData[3]="有火情";
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            long [] pattern = {50,400,50,400};   // 停止 开启 停止 开启
            vibrator.vibrate(pattern,-1);           //重复两次上面的pattern 如果只想震动一次，index设为-1
        }



        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String,     Object>>();/*在数组中存放数据*/
        int img[]= new  int[]{ R.drawable.d1,R.drawable.d2,R.drawable.d3,R.drawable.d4,
                R.drawable.d5,R.drawable.d6 };
        for(int i=0;i<2;i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", img[i]);//加入图片
            map.put("ItemTitle", realData[i]);

            listItem.add(map);

        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ItemImage", img[4]);//加入图片
        map.put("ItemTitle", realData[2]);

        listItem.add(map);
        map = new HashMap<String, Object>();
        map.put("ItemImage", img[5]);//加入图片
        map.put("ItemTitle", realData[3]);

        listItem.add(map);
        SimpleAdapter mSimpleAdapter = new SimpleAdapter(this,listItem,//需要绑定的数据
                R.layout.data_item,new String[] {"ItemImage"
                ,"ItemTitle"},
                new int[] {R.id.ItemImage,R.id.ItemTitle}
        );

        list.setAdapter(mSimpleAdapter);//为ListView绑定适配器
        list.setOnItemClickListener(new
                                            AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                                                        long arg3) {
                                                    setTitle("你点击了第"+arg2+"行");//设置标题栏显示点击的行
                                                }
                                            });
    }



    public void setImage(String imageStr){

        mRestart = BytesUtil.hexStringToBytes(imageStr);

        Bitmap bmp = BitmapFactory.decodeByteArray(mRestart, 0, mRestart.length);

        ImageView image = (ImageView) findViewById(R.id.image);

        image.setImageBitmap(bmp);
    }
    public void onBackPressed() {
        //code......
        super.onDestroy();
        //  finish();
    }

}


