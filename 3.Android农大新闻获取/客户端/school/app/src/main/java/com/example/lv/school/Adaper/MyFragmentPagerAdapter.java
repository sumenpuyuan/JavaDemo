package com.example.lv.school.Adaper;



import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.lv.school.Community;
import com.example.lv.school.Map;
import com.example.lv.school.News;
import com.example.lv.school.UserInfo;


/**
 * Created by lv on 2016/12/17.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"新闻", "地图", "社团","个人中心"};

    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return new Map();
        }
        else if (position == 2) {
            return new Community();

        }else if (position==3){
            return new UserInfo();
        }
        return new News();
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}