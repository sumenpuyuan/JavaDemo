package com.example.lv.gaode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.example.lv.gaode.route.BusRouteActivity;


import java.util.Date;

import static android.R.id.progress;

public class MainActivity extends AppCompatActivity implements
        LocationSource,AMapLocationListener,RadioGroup.OnCheckedChangeListener{
    String response;
    //声明mLocationOption对象
    private AMap aMap;
    MapView mMapView = null;
    private Button blindAddress;
    private Button navi;
    private Button bus;
    private OnLocationChangedListener mListener;
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient;
    double blindPersonN;
    double blindPersonE;
    double myAddressN;
    double myAddressE;

    private RadioGroup mGPSModeGroup;
    private  TextView mLocationErrText;

    @RequiresApi(api = Build.VERSION_CODES.N)
    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            if(msg.what == 0x123)
            {
                // 设置show组件显示服务器响应
                //show.setText(response);
                Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();
                String[] address=response.split(",");
                blindPersonN=Double.parseDouble(address[0]);
                blindPersonE=Double.parseDouble(address[1]);
                LatLng marker1 = new LatLng(blindPersonN, blindPersonE);
                Toast.makeText(MainActivity.this,address[0]+address[1],Toast.LENGTH_LONG).show();
                //aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
               // aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
                final Marker marker = aMap.addMarker(new MarkerOptions().
                        position(marker1).
                        title("盲人的位置").
                        snippet("盲人的位置"));

                CircleOptions circleOptions=new CircleOptions();
                circleOptions .center(marker1);
                circleOptions.radius(50);
                circleOptions.strokeWidth(8);
                circleOptions.strokeColor(Color.RED);
                //circleOptions.fillColor(Color.BLUE);
                aMap.addCircle(circleOptions);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        blindAddress=(Button)findViewById(R.id.blindAddress);
        navi=(Button)findViewById(R.id.navi);
        bus=(Button)findViewById(R.id.bus);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mMapView.onCreate(savedInstanceState);
        init();
        //必须先拿到盲人位置，进行标记
        locationBlindPerson();
        blindAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBlindAddress();
            }
        });
        navi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,GPSNaviActivity.class);
                intent.putExtra("myAddressN",myAddressN);
                intent.putExtra("myAddressE",myAddressE);
                intent.putExtra("blindPersonN",blindPersonN);
                intent.putExtra("blindPersonE",blindPersonE);
                startActivity(intent);
            }
        });
        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, BusRouteActivity.class);
                startActivity(intent);
            }
        });




    }
    //移动到盲人的位置
    public void showBlindAddress(){
        LatLng marker1 = new LatLng(blindPersonN, blindPersonE);
       // Toast.makeText(MainActivity.this,address[0]+address[1],Toast.LENGTH_LONG).show();
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }
    //初始化
    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mMapView.getMap();
            setUpMap();
        }
        mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
        mGPSModeGroup.setOnCheckedChangeListener(this);
        mLocationErrText = (TextView)findViewById(R.id.location_errInfo_text);
        mLocationErrText.setVisibility(View.GONE);
    }
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
    }

    public void locationBlindPerson(){
        //是不是应该开一个线程，让他一直一直从数据库取数据

        //开启post请求，获取数据到数据库中
        new Thread()
        {
            @Override
            public void run()
            {
                response = PostUtil.sendPost(
                        "http://121.42.211.211/cane/getAddress.php"
                        ,"");
                // 发送消息通知UI线程更新UI组件
                handler.sendEmptyMessage(0x123);
            }
        }.start();
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.gps_locate_button:
                // 设置定位的类型为定位模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                blindAddress.setEnabled(true);
                break;
            case R.id.gps_follow_button:
                // 设置定位的类型为 跟随模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                blindAddress.setEnabled(false);
                break;
            case R.id.gps_rotate_button:
                // 设置定位的类型为根据地图面向方向旋转
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                blindAddress.setEnabled(false);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }
    public void onLocationChanged(AMapLocation amapLocation){
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mLocationErrText.setVisibility(View.GONE);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                myAddressN=amapLocation.getLatitude();
                myAddressE=amapLocation.getLongitude();
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);
            }
        }

    }

    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }
}
