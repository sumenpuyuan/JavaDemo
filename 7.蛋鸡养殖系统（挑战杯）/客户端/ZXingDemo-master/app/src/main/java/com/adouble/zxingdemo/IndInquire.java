package com.adouble.zxingdemo;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adouble.zxingdemo.Adapter.TableAdapter;
import com.adouble.zxingdemo.Adapter.TableAdapter.TableCell;
import com.adouble.zxingdemo.Adapter.TableAdapter.TableRow;
import com.adouble.zxingdemo.Util.PostGetUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class IndInquire extends AppCompatActivity {

    private EditText chickenId;
    private TextView startTime;
    private TextView endTime;
    private Button query;
    WebView webView;
    String res;

    ListView lv;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(res);
                Toast.makeText(IndInquire.this,res, Toast.LENGTH_LONG).show();
                try{
                    if(res.trim().equals("null")){
                        Toast.makeText(IndInquire.this,"没有找到数据 请核对您的条件", Toast.LENGTH_LONG).show();
                    }else{

                        setTable();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    public void setTable(){
        lv = (ListView) this.findViewById(R.id.ListView01);
        ArrayList<TableRow> table = new ArrayList<TableRow>();
        TableCell[] titles = new TableCell[7];// 每行7个单元
        int width = 300;
        // 定义标题
        String titleStr[]={"      日期     ","鸡号","耗料量","产蛋个数","产蛋时间","蛋重","料蛋比"};
        for (int i = 0; i < titles.length; i++) {
            if(i==4){
                titles[i] = new TableCell(titleStr[i],
                        width + 100 * i,
                        LayoutParams.MATCH_PARENT,
                        TableCell.STRING);
            }else{
                titles[i] = new TableCell(titleStr[i],
                        width + 8 * i,
                        LayoutParams.MATCH_PARENT,
                        TableCell.STRING);
            }

        }
        table.add(new TableRow(titles));
        // 每行的数据

        try{

            JSONArray jsonArray=new JSONArray(res);
            for(int ii=0;ii<jsonArray.length();ii++){
                JSONObject object=jsonArray.getJSONObject(ii);

                TableCell[] cells = new TableCell[7];// 每行7个单元
                String tian[]={object.getString("riqi"),object.getString("id"),object.getString("haoke"),
                object.getString("danNum"),object.getString("danTime"),object.getString("danZhong"),
                object.getString("bi")};

                for(int ij=0;ij<tian.length;ij++){
                    cells[ij] = new TableCell(tian[ij],
                            titles[ij].width,
                            LayoutParams.MATCH_PARENT,
                            TableCell.STRING);
                }
                table.add(new TableRow(cells));




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
        setContentView(R.layout.activity_ind_inquire);


        startTime=(TextView) findViewById(R.id.start_time);
        endTime=(TextView) findViewById(R.id.end_time);
        chickenId=(EditText)findViewById(R.id.chicken_id);
        query=(Button)findViewById(R.id.query);
        webView=(WebView)findViewById(R.id.webview);
       // webView.loadUrl("http://www.baidu.com");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl("http://www.baidu.com");
                return true;
            }
        });






        startTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                showDialogPick((TextView)view);
            }
        });
        endTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

               // Toast.makeText(IndInquire.this,"123",Toast.LENGTH_LONG).show();
                 showDialogPick((TextView) view);
            }
        });

        //查询函数
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开启线程 获取数据
                final String url="http://121.42.211.211/yujie/IndividualQuery.php";
                final String para="startTime="+startTime.getText()+"&endTime="+endTime.getText()+"&id="+chickenId.getText();
                new Thread(){
                    public  void run(){
                        res= PostGetUtil.sendPost(url,para);
                        Log.d("News","__________________666___________");
                        GetNewsHandler.sendEmptyMessage(0x123);
                    }
                }.start();
            }
        });

        //以下代码是表格listview
        this.setTitle("个体查询功能");

    }
    class ItemClickEvent implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Toast.makeText(IndInquire.this, "选中第"+ String.valueOf(arg2)+"行", Toast.LENGTH_LONG).show();
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(IndInquire.this, new DatePickerDialog.OnDateSetListener() {
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
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();


    }
}
