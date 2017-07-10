package com.adouble.zxingdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import com.adouble.zxingdemo.Adapter.ImageAdapter;
import com.adouble.zxingdemo.Service.LongRunningService;
import com.adouble.zxingdemo.zxing.activity.CaptureActivity;

/**
 * Created by lv on 2017/4/4.
 */

public class MainMenu extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_mene);


            Intent intent=new Intent(this, LongRunningService.class);
        startService(intent);

        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(3);
       // gridview.setColumnWidth(width/3);
        gridview.setAdapter(new ImageAdapter(this,width));


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(position == 0){
                    Intent intent=new Intent(MainMenu.this,Test.class);
                    startActivity(intent);
                }
                if(position == 1){
                    //Intent intent=new Intent(MainMenu.this,Zhuxing.class);
                   // startActivity(intent);
                }

                //如果是扫码
                if (position == 3){
                    Intent intent=new Intent(MainMenu.this,CaptureActivity.class);
                    startActivity(intent);
                }
                //如果是个体查询
                if(position == 4){
                    Intent intent=new Intent(MainMenu.this,IndInquire.class);
                    startActivity(intent);

                }
                //如果是群体统计
                else if(position == 5){
                    Intent intent=new Intent(MainMenu.this,GroupStatistics.class);
                    startActivity(intent);
                }

            }
        });
    }
}
