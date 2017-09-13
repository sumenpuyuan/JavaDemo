package etong.bottomnavigation.demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import etong.bottomnavigation.demo.Util.PostGetUtil;


public class NewsstandFragment extends Fragment {

    private RecyclerView recycleView;
    ImageView head;
    TextView name;
    TextView id;
    TextView college;
    TextView major;
    TextView user_class;
    MainActivity userMain;
    String response;
    View mView=null;
    private Handler GetNewsHandler=new Handler(){
        public  void handleMessage(Message msg){
            if(msg.what == 0x123){
                try{
                  //  Log.d("UserInfo",response);

                    JSONObject object=new JSONObject(response);

                    Log.d("UserInfo","++++++++++++++++"+object.getString("name"));
                    setPicBitmap(head,object.getString("head"));
                    name.setText(object.getString("name"));

                    id.setText(object.getString("id"));
                    college.setText(object.getString("college"));
                    major.setText(object.getString("major"));
                    user_class.setText(object.getString("class"));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };
    public NewsstandFragment() {
        // Required empty public constructor
    }

    public static NewsstandFragment newInstance() {
        NewsstandFragment fragment = new NewsstandFragment();
        return fragment;
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();

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

        View contentView = inflater.inflate(R.layout.fragment_newsstand, container, false);
        mView=contentView;
        head=(ImageView)contentView.findViewById(R.id.head);
        name=(TextView)contentView.findViewById(R.id.name);
        id=(TextView)contentView.findViewById(R.id.id);
        college=(TextView)contentView.findViewById(R.id.college);
        major=(TextView)contentView.findViewById(R.id.major);
        user_class=(TextView)contentView.findViewById(R.id.user_class);
    //    userMain=(MainActivity) getActivity();


        return contentView;
    }
    public  void getData(){
        final  Bundle bundle=getArguments();
        new Thread(new Runnable() {
            @Override
            public void run() {
                response= PostGetUtil.sendPost("http://121.42.211.211/school/user/GetUserInfo.php","id="+bundle.getString("id"));
                GetNewsHandler.sendEmptyMessage(0x123);
            }
        }).start();
    }
    //设置头像
    public void setPicBitmap(final ImageView ivPic,final String pic_url){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL url=new URL(pic_url);
                    HttpURLConnection connection=(HttpURLConnection)url.openConnection();
                    connection.connect();
                    InputStream is=connection.getInputStream();
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    ivPic.setImageBitmap(bitmap);
                    is.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private class BookAdapter extends RecyclerView.Adapter<ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
