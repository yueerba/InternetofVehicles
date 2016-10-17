package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.util.UserUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeRegistActivity extends AppCompatActivity {

    @BindView(R.id.resetpwd_edit_name)
    EditText resetpwdEditName;
    @BindView(R.id.resetpwd_edit_pwd_old)
    EditText resetpwdEditPwdOld;
    @BindView(R.id.register_btn_cancel)
    Button registerBtnCancel;
    @BindView(R.id.resetpwd_edit_pwd_new)
    EditText resetpwdEditPwdNew;
    @BindView(R.id.register_btn_sure)
    Button registerBtnSure;


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj.toString().trim().equals("Succss")) {
                Toast.makeText(HomeRegistActivity.this, "恭喜你，注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomeRegistActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(HomeRegistActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_regist);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.register_btn_cancel, R.id.register_btn_sure})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_cancel:
                Intent intent = new Intent(HomeRegistActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.register_btn_sure:
                if(String.valueOf(resetpwdEditPwdOld.getText()).
                        equals(String.valueOf(resetpwdEditPwdNew.getText()))) {
                    UserUtil.username = String.valueOf(resetpwdEditName.getText());
                    UserUtil.password = String.valueOf(resetpwdEditPwdOld.getText());
                    if(!UserUtil.username.equals("")&&!UserUtil.password.equals("")) {
                        getKey();
                    }else{
                        Toast.makeText(this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private String getData(String urlString) {

        String key = null;
        //获取网页返回的字符串
        HttpURLConnection urlConnection = null;
        BufferedReader bufr = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = urlConnection.getInputStream();
            bufr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            key = bufr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return key;
    }

    public void getKey() {
        new Thread() {
            @Override
            public void run() {
                final String url = "http://192.168.3.112:8080/Internet%20of%20Vehicles/RegLet?" +
                        "username=" + UserUtil.username + "&password=" + UserUtil.password + "";
                String key = (String) getData(url);
                Message msg = Message.obtain();
                msg.obj = key;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
