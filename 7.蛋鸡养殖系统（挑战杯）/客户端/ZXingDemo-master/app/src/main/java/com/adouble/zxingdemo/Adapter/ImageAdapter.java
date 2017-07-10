package com.adouble.zxingdemo.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.adouble.zxingdemo.R;


/**
 * Created by lv on 2017/4/4.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    int width;
    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    public ImageAdapter(Context c,int width){
        this.width=width;
        mContext = c;
    }
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);


            imageView.setLayoutParams(new GridView.LayoutParams(width/3, 500));

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.c1, R.drawable.c2,
            R.drawable.c3, R.drawable.c4,
            R.drawable.c5, R.drawable.c6
    };
}
