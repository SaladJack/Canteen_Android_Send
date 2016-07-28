package com.kai.distribution.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kai.distribution.R;
import com.kai.distribution.app.Constants;
import com.kai.distribution.app.MyApplication;
import com.kai.distribution.utils.RsSharedUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class Changed_Password_Activity extends Activity {
    private Button password_return;
    private EditText old_password;
    private EditText new_password_1;
    private EditText new_password_2;
    private Button new_password_comfirm;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_password);

        initView();
        initClick();
    }

    private void initView() {
        password_return = (Button) findViewById(R.id.password_return);
        old_password = (EditText) findViewById(R.id.old_password);
        new_password_1 = (EditText) findViewById(R.id.new_password_1);
        new_password_2 = (EditText) findViewById(R.id.new_password_2);
        new_password_comfirm = (Button) findViewById(R.id.new_password_comfirm);
    }

    private void initClick() {
        password_return.setOnClickListener(click);
        new_password_comfirm.setOnClickListener(click);
    }

    private OnClickListener click = new OnClickListener() {

        @Override
        public void onClick(View view) {
            // TODO Auto-generated method stub
            switch (view.getId()) {
                case R.id.password_return:
                    finish();
                    break;

                case R.id.new_password_comfirm:
                    if (TextUtils.isEmpty(old_password.getText().toString())) {
                        Toast.makeText(Changed_Password_Activity.this, "请输入原密码", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(new_password_1.getText().toString())) {
                        Toast.makeText(Changed_Password_Activity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isEmpty(new_password_2.getText().toString())) {
                        Toast.makeText(Changed_Password_Activity.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    } else if (!new_password_1.getText().toString().equals(new_password_2.getText().toString())) {
                        Toast.makeText(Changed_Password_Activity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog = new ProgressDialog(Changed_Password_Activity.this);
//                        dialog.setMessage("...");
                        dialog.setCancelable(true);
                        dialog.show();

                        changePassword(old_password.getText().toString(),
                                new_password_1.getText().toString(),
                                String.valueOf(RsSharedUtil.getInt(Changed_Password_Activity.this, Constants.KEY.WORK_ID)),
                                RsSharedUtil.getString(Changed_Password_Activity.this, Constants.KEY.USER_CODE));
                    }

                    break;
            }
        }
    };

    private void changePassword(String oldPassword, String newPassword, String workerID, String code) {
        Log.e("change_password","changePassword()");
        JSONObject jsonObject = new JSONObject();
        String url = Constants.URL.CHANGE_PASSWORD;

        try {
            jsonObject.put("oldPassword", oldPassword);
            jsonObject.put("newPassword", newPassword);
            jsonObject.put("workerId", workerID);
            jsonObject.put("code", code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("change_password", response.toString());
                            String res = null;
                            try {
                                dialog.dismiss();
                                res = response.getString("result");
                                if (res.equals("updatesuccess")) {
                                    Toast.makeText(Changed_Password_Activity.this, "密码修改成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else if(res.equals("wrongPassword")){
                                    Toast.makeText(Changed_Password_Activity.this, "原密码错误", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                    Toast.makeText(Changed_Password_Activity.this, "请求错误,请检查网络是否打开", Toast.LENGTH_SHORT).show();
                    Log.e("change_password","error: " + error.toString());
                }
            });
            jsonObjectRequest.setTag("Change_Password_Activity");
            MyApplication.getRequestQueue().add(jsonObjectRequest);
        }


    @Override
    protected void onDestroy () {
        super.onDestroy();
        MyApplication.getRequestQueue().cancelAll("Change_Password_Activity");
    }
}
