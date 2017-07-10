package com.example.lv.wulianwang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class XuanHouse extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xuan_house);
        GridView gridview = (GridView) findViewById(R.id.gridview);

        //生成动态数组，并且转入数据
        ArrayList<HashMap<String, Object>> lstImageItem = new ArrayList<HashMap<String, Object>>();

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage",R.drawable.home );//添加图像资源的ID
            map.put("ItemText", "客厅");//按序号做ItemText
            lstImageItem.add(map);

            map = new HashMap<String, Object>();
            map.put("ItemImage",R.drawable.home );//添加图像资源的ID
            map.put("ItemText", "书房");//按序号做ItemText
            lstImageItem.add(map);

        //生成适配器的ImageItem <====> 动态数组的元素，两者一一对应
        SimpleAdapter saImageItems = new SimpleAdapter(this, //没什么解释
                lstImageItem,//数据来源
                R.layout.night_item,//night_item的XML实现

                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemText"},

                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemText});
        //添加并且显示
        gridview.setAdapter(saImageItems);
        //添加消息处理
        gridview.setOnItemClickListener(new ItemClickListener());
    }

    //当AdapterView被单击(触摸屏或者键盘)，则返回的Item单击事件
    class  ItemClickListener implements AdapterView.OnItemClickListener
    {
        public void onItemClick(AdapterView<?> arg0,//The AdapterView where the click happened
                                View arg1,//The view within the AdapterView that was clicked
                                int arg2,//The position of the view in the adapter
                                long arg3//The row id of the item that was clicked
        ) {
        //    Toast.makeText(XuanHouse.this,"12123",Toast.LENGTH_SHORT).show();
            //在本例中arg2=arg3
            HashMap<String, Object> item=(HashMap<String, Object>) arg0.getItemAtPosition(arg2);
            //显示所选Item的ItemText
            setTitle((String)item.get("ItemText"));
            if(arg2 == 1 ){
                Intent intent=new Intent(XuanHouse.this,SecondHouse.class);
                startActivity(intent);
            }else{
                Intent intent=new Intent(XuanHouse.this,MainActivity.class);
                startActivity(intent);
            }

        }

    }
}
