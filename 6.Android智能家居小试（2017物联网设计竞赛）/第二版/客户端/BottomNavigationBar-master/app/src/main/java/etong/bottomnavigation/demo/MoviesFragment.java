package etong.bottomnavigation.demo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import java.io.InputStream;

import etong.bottomnavigation.demo.Util.PostGetUtil;


public class MoviesFragment extends Fragment {
    String res;
    ImageView weatherImg;
    TextView temperture;
    TextView position;
    Button keting;
    Button shufang;
    Button woshi;
    View mView=null;
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = null;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                System.out.print(res);
               // Toast.makeText(getActivity(),res,Toast.LENGTH_SHORT).show();
                String real[]=res.split(",");
                temperture.setText(real[1]+"℃  ");
                real[0]=real[0].trim();
                position.append(real[0]);
                try {
                    InputStream is=getActivity().getAssets().open(real[2]+".png");
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    weatherImg.setImageBitmap(bitmap);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    public MoviesFragment() {
        // Required empty public constructor
    }

    public static MoviesFragment newInstance() {
        MoviesFragment fragment = new MoviesFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }
        View view = inflater.inflate(R.layout.fragment_movies,container, false);//把layout布局文件转换成View对象
        mView=view;
        weatherImg=(ImageView)view.findViewById(R.id.weather_img);
        temperture=(TextView)view.findViewById(R.id.temperture);
        position=(TextView)view.findViewById(R.id.position);
        keting=(Button)view.findViewById(R.id.keting);
        shufang=(Button)view.findViewById(R.id.shufang);
        woshi=(Button)view.findViewById(R.id.woshi);
        try {
            InputStream is=getActivity().getAssets().open("0.png");
            Bitmap bitmap= BitmapFactory.decodeStream(is);
            weatherImg.setImageBitmap(bitmap);
        }catch (Exception e){
            e.printStackTrace();
        }
        keting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),KetingActivity.class);
                startActivity(intent);
            }
        });
        shufang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),shufangActivity.class);
                startActivity(intent);
            }
        });
        woshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),woshiActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getData();
        initView();

        //初始化定位
        initLocation();
        startLocation();
    }
    public void getData(final  String city){
        final String url="http://121.42.211.211/lot/GetWeather.php";
        new Thread(){
            public  void run(){
                res= PostGetUtil.sendGet(url,"city="+city+"");
                Log.d("News","__________________666___ ________");
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        }.start();
    }
    /*
    下面是定位的一些函数
     */
    //初始化控件
    private void initView(){
        if (null == locationOption) {
            locationOption = new AMapLocationClientOption();
        }
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLocation();
    }
    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(getActivity().getApplicationContext());
        locationOption = getDefaultOption();
        //设置定位参数
        locationClient.setLocationOption(locationOption);
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            if (null != location) {

                StringBuffer sb = new StringBuffer();
                //errCode等于0代表定位成功，其他的为定位失败，具体的可以参照官网定位错误码说明
                if(location.getErrorCode() == 0){
                    //sb.append("定位成功" + "\n");
                   // sb.append("定位类型: " + location.getLocationType() + "\n");
                   // sb.append("经    度    : " + location.getLongitude() + "\n");
                  //  sb.append("纬    度    : " + location.getLatitude() + "\n");
                  //  sb.append("精    度    : " + location.getAccuracy() + "米" + "\n");
                  //  sb.append("提供者    : " + location.getProvider() + "\n");

                  //  sb.append("速    度    : " + location.getSpeed() + "米/秒" + "\n");
                 //   sb.append("角    度    : " + location.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                   // sb.append("星    数    : " + location.getSatellites() + "\n");
                   // sb.append("国    家    : " + location.getCountry() + "\n");
                    sb.append( location.getProvince() + " ");
                    sb.append(location.getCity() + " ");
                   // sb.append("城市编码 : " + location.getCityCode() + "\n");
                    sb.append(location.getDistrict() + " ");
                   // sb.append("区域 码   : " + location.getAdCode() + "\n");
                   // sb.append("地    址    : " + location.getAddress() + "\n");
                   // sb.append("兴趣点    : " + location.getPoiName() + "\n");
                    //定位完成的时间
                    // sb.append("定位时间: " + Utils.formatUTC(location.getTime(), "yyyy-MM-dd HH:mm:ss") + "\n");
                    getData(location.getCity());
                } else {
                    //定位失败
                    sb.append("定位失败" + "\n");
                    sb.append("错误码:" + location.getErrorCode() + "\n");
                    sb.append("错误信息:" + location.getErrorInfo() + "\n");
                    sb.append("错误描述:" + location.getLocationDetail() + "\n");
                }
                //定位之后的回调时间
                //  sb.append("回调时间: " + Utils.formatUTC(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss") + "\n");

                //解析定位结果，
                String result = sb.toString();
                position.setText(result);
            } else {
                position.setText("定位失败，loc is null");
            }
            stopLocation();
        }
    };

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }
}
