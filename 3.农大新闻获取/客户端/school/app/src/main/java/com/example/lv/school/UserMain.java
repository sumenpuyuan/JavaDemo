package com.example.lv.school;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by lv on 2016/12/17.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lv.school.Adaper.MyFragmentPagerAdapter;

public class UserMain extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

    private TabLayout.Tab one;
    private TabLayout.Tab two;
    private TabLayout.Tab three;
    private TabLayout.Tab four;
    public String stuId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//隐藏掉整个ActionBar
        setContentView(R.layout.user_main);
        Intent intent=getIntent();
        stuId=intent.getStringExtra("id");
        //初始化视图
        initViews();
        Log.d("UserMain","i am usermian_______________");
        Toast.makeText(UserMain.this,"欢迎你"+stuId,Toast.LENGTH_LONG).show();
    }
    private void initViews() {
        //使用适配器将ViewPager与Fragment绑定在一起
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myFragmentPagerAdapter);
        //将TabLayout与ViewPager绑定在一起
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(mViewPager);
        //指定Tab的位置
        one = mTabLayout.getTabAt(0);
        two = mTabLayout.getTabAt(1);
        three = mTabLayout.getTabAt(2);
        four = mTabLayout.getTabAt(3);
        //设置Tab的图标，假如不需要则把下面的代码删去
        one.setIcon(R.mipmap.news);
        two.setIcon(R.mipmap.map);
        three.setIcon(R.mipmap.commutity);
        four.setIcon(R.mipmap.userinfo);
    }
}