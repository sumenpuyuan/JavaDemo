package com.example.lv.school.Adaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.lv.school.R;

/**
 * Created by lv on 2016/12/31.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private Integer[] mThumbIds={
           R.drawable.xiexie,R.drawable.jiaodian,
            R.drawable.xuesheng
    };
    public ImageAdapter(Context c){
        mContext=c;
    }
    public int getCount(){
        return mThumbIds.length;
    }
    public Object getItem(int position){
        return null;
    }
    public long getItemId(int position){
        return 0;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(315, 315));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

}
