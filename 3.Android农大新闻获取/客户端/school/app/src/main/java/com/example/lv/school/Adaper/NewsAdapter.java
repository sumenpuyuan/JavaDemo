package com.example.lv.school.Adaper;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lv.school.Model.New;
import com.example.lv.school.News;
import com.example.lv.school.R;

import java.util.List;

/**
 * Created by lv on 2016/12/18.
 */

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<New> newList;
    public NewsAdapter(Context context,List<New> newsList)
    {
        this.context = context;
        this.newList=newsList;
    }
    @Override
    public int getCount() {
        return newList.size();
    }
    @Override
    public New getItem(int position) {
        return newList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.news_item,null);

        }
        //通过位置取出相应的 新闻实体
        New n=newList.get(position);
        TextView tvTitle=(TextView) convertView.findViewById(R.id.tvTitle);
        //填充 标题数据
        tvTitle.setText(n.getTitle());
        return convertView;

    }
}
