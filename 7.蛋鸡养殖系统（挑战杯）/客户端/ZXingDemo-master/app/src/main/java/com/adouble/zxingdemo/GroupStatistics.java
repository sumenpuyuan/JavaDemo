package com.adouble.zxingdemo;

import android.app.DatePickerDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;



import com.adouble.zxingdemo.Adapter.TableAdapter;
import com.adouble.zxingdemo.Util.PostGetUtil;
import com.adouble.zxingdemo.fragment.BarChartFragment;
import com.adouble.zxingdemo.fragment.CombineChartFragment;
import com.adouble.zxingdemo.fragment.CurveChartFragment;
import com.adouble.zxingdemo.fragment.LineChartFragment;
import com.adouble.zxingdemo.fragment.PieChartFragment;
import com.adouble.zxingdemo.fragment.RadarChartFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class GroupStatistics extends AppCompatActivity {
    private EditText startId;
    private EditText endId;
    private TextView startTime;
    private TextView endTime;
    private Button groupQuery;
    ListView lv;
    String res;

    private FrameLayout chartFragments;
    private TabLayout topTabs;
    private Fragment barChartFragment,lineChartFragment,curveChartFragment,combineChartFragment,
            radarChartFragment,pieChartFragment;
    private FragmentTransaction fragmentTransaction;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(res);
                Toast.makeText(GroupStatistics.this,res, Toast.LENGTH_LONG).show();
                setTable();
                initView();
            }
        }
    };
    public void setTable(){
        lv = (ListView) this.findViewById(R.id.ListView01);
        ArrayList<TableAdapter.TableRow> table = new ArrayList<TableAdapter.TableRow>();
        TableAdapter.TableCell[] titles = new TableAdapter.TableCell[10];// 每行7个单元
        int width = 300;
        // 定义标题
        String titleStr[]={"鸡只代码","开始日期","结束日期","耗料总量(g)","日均耗料量(g)","产蛋总数(个)","产蛋总重(g)","最大蛋重(g)","最小蛋重(g)","平均蛋重(g)"};
        for (int i = 0; i < titles.length; i++) {
            if(i==4){
                titles[i] = new TableAdapter.TableCell(titleStr[i],
                        width + 100 * i,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        TableAdapter.TableCell.STRING);
            }else{
                titles[i] = new TableAdapter.TableCell(titleStr[i],
                        width + 8 * i,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        TableAdapter.TableCell.STRING);
            }

        }
        table.add(new TableAdapter.TableRow(titles));
        //每行的数据
        try{

            JSONArray jsonArray=new JSONArray(res);
            for(int ii=0;ii<jsonArray.length();ii++){
                JSONObject object=jsonArray.getJSONObject(ii);

                TableAdapter.TableCell[] cells = new TableAdapter.TableCell[10];// 每行7个单元
                String tian[]={object.getString("id"),(String)startTime.getText(),(String) endTime.getText(),
                    object.getString("sumHao"),object.getString("avgHao"),object.getString("sumDanNum"),
                        object.getString("sumDanZhong"),
                        object.getString("maxDanZhong"),object.getString("minDanZhong"),object.getString("avgDan")
                };

                for(int ij=0;ij<tian.length;ij++){
                    cells[ij] = new TableAdapter.TableCell(tian[ij],
                            titles[ij].width,
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            TableAdapter.TableCell.STRING);
                }
                table.add(new TableAdapter.TableRow(cells));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        TableAdapter tableAdapter = new TableAdapter(this, table);
        lv.setAdapter(tableAdapter);
        lv.setOnItemClickListener(new ItemClickEvent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_statistics);


        startId=(EditText)findViewById(R.id.start_id);
        endId=(EditText)findViewById(R.id.end_id);
        startTime=(TextView)findViewById(R.id.startTime);
        endTime=(TextView)findViewById(R.id.endTime);
        groupQuery=(Button)findViewById(R.id.groupQuery);



        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialogPick((TextView) view);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showDialogPick((TextView) view);
            }
        });
        groupQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启线程 获取数据
                final String url="http://121.42.211.211/yujie/GroupStatistics.php";
                final String para="startTime="+startTime.getText()+"&endTime="+endTime.getText()+"&startId="+startId.getText()+"&endId="+endId.getText()+"";
                new Thread(){
                    public  void run(){
                        res= PostGetUtil.sendPost(url,para);
                        Log.d("News","__________________666___ ________");
                        GetNewsHandler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });


    }


    public void test(){
        Toast.makeText(GroupStatistics.this,"123",Toast.LENGTH_LONG).show();
    }
    class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Toast.makeText(GroupStatistics.this, "选中第"+ String.valueOf(arg2)+"行", Toast.LENGTH_LONG).show();
        }
    }
    //将两个选择时间的dialog放在该函数中

    private void showDialogPick(final TextView timeText) {
        final StringBuffer time = new StringBuffer();
        //获取Calendar对象，用于获取当前时间
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        //实例化DatePickerDialog对象
        DatePickerDialog datePickerDialog = new DatePickerDialog(GroupStatistics.this, new DatePickerDialog.OnDateSetListener() {
            //选择完日期后会调用该回调函数
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //因为monthOfYear会比实际月份少一月所以这边要加1
                time.append(year + "-" + (monthOfYear+1) + "-" + dayOfMonth);
                //选择完日期后弹出选择时间对话框
                //timePickerDialog.show();
                timeText.setText(time);
            }
        }, year, month, day);
        //弹出选择日期对话框
        datePickerDialog.show();
    }




    //下面是图标的代码
    private void initView(){
        chartFragments = (FrameLayout) findViewById(R.id.group_fragments);
        topTabs = (TabLayout) findViewById(R.id.group_top_tabs);
        topTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        topTabs.setTabGravity(TabLayout.GRAVITY_FILL);

        topTabs.addTab(topTabs.newTab().setText("耗料统计"),0);
        topTabs.addTab(topTabs.newTab().setText("蛋重统计"),1);
        topTabs.addTab(topTabs.newTab().setText("产蛋时间统计"),2);
        topTabs.addTab(topTabs.newTab().setText("整群日均统计"),3);
       // topTabs.addTab(topTabs.newTab().setText("环形图"),4);
        //topTabs.addTab(topTabs.newTab().setText("雷达图"),5);

        topTabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentManager manager=getSupportFragmentManager();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                hideFragment(fragmentTransaction);
                switch (tab.getPosition()){
                    case 0:
                        curveChartFragment = manager.findFragmentByTag("Tag0");
                        if (curveChartFragment == null){
                            curveChartFragment = new CurveChartFragment();
                            fragmentTransaction.add(R.id.group_fragments,curveChartFragment,"Tag0");
                        }else {
                            fragmentTransaction.show(curveChartFragment);
                        }
                        break;
                    case 1:
                        barChartFragment = manager.findFragmentByTag("Tag1");
                        if (barChartFragment == null){
                            barChartFragment = new BarChartFragment();
                            fragmentTransaction.add(R.id.group_fragments, barChartFragment,"Tag1");
                        }else {
                            fragmentTransaction.show(barChartFragment);
                        }
                        break;
                    case 2:
                        lineChartFragment = manager.findFragmentByTag("Tag2");
                        if (lineChartFragment == null){
                            lineChartFragment = new LineChartFragment();
                            fragmentTransaction.add(R.id.group_fragments,lineChartFragment,"Tag2");
                        }else {
                            fragmentTransaction.show(lineChartFragment);
                        }
                        break;
                    case 3:
                        combineChartFragment = manager.findFragmentByTag("Tag3");
                        if (combineChartFragment == null){
                            combineChartFragment = new CombineChartFragment();
                            fragmentTransaction.add(R.id.group_fragments,combineChartFragment,"Tag3");
                        }else {
                            fragmentTransaction.show(combineChartFragment);
                        }
                        break;
                    case 4:
                        pieChartFragment = manager.findFragmentByTag("Tag4");
                        if (pieChartFragment == null){
                            pieChartFragment = new PieChartFragment();
                            fragmentTransaction.add(R.id.group_fragments,pieChartFragment,"Tag4");
                        }else {
                            fragmentTransaction.show(pieChartFragment);
                        }
                        break;
                    case 5:
                        radarChartFragment = manager.findFragmentByTag("Tag5");
                        if (radarChartFragment == null){
                            radarChartFragment = new RadarChartFragment();
                            fragmentTransaction.add(R.id.group_fragments,radarChartFragment,"Tag5");
                        }else {
                            fragmentTransaction.show(radarChartFragment);
                        }
                        break;
                }
                fragmentTransaction.commit();//提交事务
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                fragmentTransaction.show(curveChartFragment);
            }
        });

        topTabs.getTabAt(1).select();
        topTabs.getTabAt(0).select();

    }

    private void hideFragment(FragmentTransaction fragmentTransaction){

        if (barChartFragment != null){
            fragmentTransaction.hide(barChartFragment);
        }

        if (lineChartFragment != null){
            fragmentTransaction.hide(lineChartFragment);
        }

        if (curveChartFragment != null){
            fragmentTransaction.hide(curveChartFragment);
        }
        if (combineChartFragment != null){
            fragmentTransaction.hide(combineChartFragment);
        }

        if (radarChartFragment != null){
            fragmentTransaction.hide(radarChartFragment);
        }
        if (pieChartFragment != null){
            fragmentTransaction.hide(pieChartFragment);
        }
    }

}
