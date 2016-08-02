package com.kai.distribution.fragment;

import android.app.AlertDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kai.distribution.R;
import com.kai.distribution.utils.DistrbutingUtils;
import com.kai.distribution.utils.QRUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tusm on 16/7/20.
 */
public class Fragment_AfterScanning extends Fragment {
    private View view;

    private Timer timer;

    private SimpleDraweeView afterScanningGif;
    private static final String TAG = "Fragment_AfterScanning";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (getActivity()!=null) {
                    DistrbutingUtils.getDistributedListByHTTP(getActivity().getApplicationContext(), 0);
                }else{
                    timer.cancel();
                }
            }
        },5000,60000);//一分钟请求一次网络


        view=inflater.inflate(R.layout.fragment_afterscanning, container,false);

        afterScanningGif = (SimpleDraweeView) view.findViewById(R.id.after_scanning_gif);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)//自动播放动画
                .setUri(Uri.parse("res://"+getActivity().getPackageName()+"/" + R.drawable.waiting))//路径
                .build();
        afterScanningGif.setController(draweeController);




        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer!=null) {
            timer.cancel();
            timer = null;
        }
    }


}
