package com.example.lv.gaode;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;

public class GPSNaviActivity extends BaseActivity {
    double myAddressN;
    double myAddressE;
    double blindPersonN;
    double blindPersonE;
   // protected NaviLatLng mEndLatlng;
   // protected NaviLatLng mStartLatlng;
    public GPSNaviActivity(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_navi);
        Intent intent=getIntent();

        myAddressN=intent.getDoubleExtra("myAddressN",37.8507147180);
        myAddressE=intent.getDoubleExtra("myAddressE",114.4834260848);
        blindPersonN=intent.getDoubleExtra("blindPersonN",37.8507147180);
        blindPersonE=intent.getDoubleExtra("blindPersonE",37.8507147180);
        mStartLatlng=new NaviLatLng(myAddressN,myAddressE);
        mEndLatlng=new NaviLatLng(blindPersonN,blindPersonE);
        String value=myAddressN+" "+myAddressE+"      "+blindPersonN+" "+blindPersonE;
        Toast.makeText(this,value,Toast.LENGTH_LONG).show();
        //现在我已成功拿到我的位置和盲人位置
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        // 下面的东西本来是父类里的东西
        //实例化语音引擎
        mTtsManager = TTSController.getInstance(getApplicationContext());
        mTtsManager.init();
        //
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.addAMapNaviListener(mTtsManager);

        //设置模拟导航的行车速度
        mAMapNavi.setEmulatorNaviSpeed(75);
        sList.add(mStartLatlng);
        eList.add(mEndLatlng);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);

    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
