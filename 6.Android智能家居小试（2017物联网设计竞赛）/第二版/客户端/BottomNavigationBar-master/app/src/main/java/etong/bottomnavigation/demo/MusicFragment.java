package etong.bottomnavigation.demo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import etong.bottomnavigation.lib.BottomNavigationBar;


public class MusicFragment extends Fragment {


    private LinearLayout lineaLayout;

    private BottomNavigationBar navigationBar;
    View mView=null;
    public MusicFragment() {
        // Required empty public constructor
    }

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null) {
                parent.removeView(mView);
            }
            return mView;
        }
        View contentView = inflater.inflate(R.layout.fragment_music, container, false);
        navigationBar = (BottomNavigationBar) contentView.findViewById(R.id.navigationBar);
        mView=contentView;
        return contentView;
    }

    void setUpNavigationBar(){

    }
}
