package com.adouble.zxingdemo;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.adouble.zxingdemo.Adapter.TableAdapter;
import com.adouble.zxingdemo.Util.PostGetUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class QueryInfo extends AppCompatActivity {
    String res;
    ListView lv;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(res);
                Toast.makeText(QueryInfo.this,res, Toast.LENGTH_LONG).show();
                setTable();
            }
        }
    };
    public void setTable(){
        lv = (ListView) this.findViewById(R.id.ListView02);
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
                String tian[]={object.getString("id"),"2017-4-5","2017-4-6",
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

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        String id=intent.getStringExtra("res");
        setContentView(R.layout.activity_query_info);
        Toast.makeText(QueryInfo.this,id,Toast.LENGTH_LONG).show();

        final String url="http://121.42.211.211/yujie/QueryInfo.php";
        final String para="id="+id+"";

        new Thread(){
            public  void run(){
                res= PostGetUtil.sendPost(url,para);
                Log.d("News","__________________666___________");
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        }.start();
    }
}
