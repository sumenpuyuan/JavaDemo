package com.example.lv.school;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.GridView;

import com.example.lv.school.Adaper.ImageAdapter;
import com.example.lv.school.DetailCommunity.DetailCommunity;

/**
 * Created by lv on 2016/12/17.
 */

public class Community extends Fragment{
    GridView gridView;
    UserMain userMain;
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.school_community,container, false);//把layout布局文件转换成View对象
        gridView=(GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new ImageAdapter(this.getActivity()));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                userMain=(UserMain)getActivity();
                String stuId=userMain.stuId;
                String commuId=position+"";
                Intent intent=new Intent(userMain, DetailCommunity.class);
                intent.putExtra("stuId",stuId);
                intent.putExtra("commuId",commuId);
                startActivity(intent);
            }
        });
        return view;
    }
}
