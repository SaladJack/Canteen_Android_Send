package com.kai.distribution.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kai.distribution.R;
import com.zxing.activity.CaptureActivity;

/**
 * Created by tusm on 16/7/13.
 */
public class ScanActivity extends Activity implements View.OnClickListener {


    private Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        btnScan = (Button)findViewById(R.id.bt_scan);
        btnScan.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.bt_scan:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent,0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK){
            String result = data.getExtras().getString("result");
            Toast.makeText(this,result,Toast.LENGTH_LONG).show();
        }
    }
}
