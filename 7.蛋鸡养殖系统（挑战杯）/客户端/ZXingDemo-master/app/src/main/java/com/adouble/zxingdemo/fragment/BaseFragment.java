package com.adouble.zxingdemo.fragment;

import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by Idtk on 2016/6/26.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 * 描述 : 基础类
 */
public class BaseFragment extends Fragment {

    protected float[][] points = new float[][]{{201319,110}, {201320,47}, {201321,11}, {201322,38}, {201323,9},{201324,52}, {201325,14}, {201326,37}, {201327,29}, {201328,31}};
    protected float[][] points2 = new float[][]{{1,52}, {2,13}, {3,51}, {4,20}, {5,19},{6,20}, {7,54}, {8,7}, {9,19}, {10,41}};
    protected int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    protected float pxTodp(float value){
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float valueDP= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,metrics);
        return valueDP;
    }
}
