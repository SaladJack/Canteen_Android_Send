package com.kai.distribution.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kai.distribution.R;

/**
 * Created by tusm on 16/7/22.
 */
public class GifTest extends Activity {
    private SimpleDraweeView afterScanningGif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.fragment_afterscanning);

        afterScanningGif = (SimpleDraweeView)findViewById(R.id.after_scanning_gif);

        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)//自动播放动画
                .setUri(Uri.parse("asset://com.kai.distribution.activity/waiting.gif"))//路径
                .build();
        afterScanningGif.setController(draweeController);
    }
}
