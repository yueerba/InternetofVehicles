package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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


public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.login_btn_register)
    Button loginBtnRegister;
    @BindView(R.id.login_btn_login)
    Button loginBtnLogin;
    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.login_edit_pwd)
    EditText loginEditPwd;
    @BindView(R.id.login_edit_account)
    EditText loginEditAccount;
    @BindView(R.id.login_btn_cancle)
    Button loginBtnCancle;
    @BindView(R.id.Login_Remember)
    CheckBox LoginRemember;
    @BindView(R.id.login_text_change_pwd)
    TextView loginTextChangePwd;
    @BindView(R.id.login_success_show)
    TextView loginSuccessShow;

    private String username;
    private String password;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj.toString().trim().equals("Succss")) {
                Intent intent = new Intent(HomeActivity.this, OrderHomeActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(HomeActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
        }
    };

    /*http://192.168.3.112:8080/Internet%20of%20Vehicles/LogLet?username=gege&password=gege*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 注意顺序
        setContentView(R.layout.activity_home);
        /*setSupportActionBar((Toolbar) findViewById(R.id.toolbar));//用于设置title的*/
        ButterKnife.bind(this);

        if (UserUtil.username != null && UserUtil.password != null) {
            loginEditAccount.setText(UserUtil.username);
            loginEditPwd.setText(UserUtil.password);
        }

        if (UserUtil.checked == true) {
            LoginRemember.setChecked(true);
        } else {
            LoginRemember.setChecked(false);
            loginEditAccount.setText("");
            loginEditPwd.setText("");
        }
    }

    @OnClick({R.id.login_btn_register, R.id.login_btn_login, R.id.login_btn_cancle,
            R.id.Login_Remember, R.id.login_text_change_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btn_register:
                Intent intent = new Intent(HomeActivity.this, HomeRegistActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.login_btn_login:
                UserUtil.username = String.valueOf(loginEditAccount.getText());
                UserUtil.password = String.valueOf(loginEditPwd.getText());

                getKey();
                break;
            case R.id.login_btn_cancle:
                UserUtil.username = null;
                UserUtil.password = null;
                finish();
                break;
            case R.id.Login_Remember:
                if (LoginRemember.isChecked()) {
                    UserUtil.checked = true;
                } else {
                    UserUtil.checked = false;
                }
                break;
            case R.id.login_text_change_pwd:
                intent = new Intent(HomeActivity.this, HomePwdActivity.class);
                startActivity(intent);
                finish();
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
                final String url = "http://192.168.3.112:8080/Internet%20of%20Vehicles/LogLet?" +
                        "username=" + UserUtil.username + "&password=" + UserUtil.password + "";
                String key = (String) getData(url);
                Message msg = Message.obtain();
                msg.obj = key;
                handler.sendMessage(msg);
            }
        }.start();
    }
}
