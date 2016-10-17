package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapdemo.JuHeSearchActivity;
import com.mapdemo.MapActivity;
import com.yueerba.internetofvehicles.internetofvehicles.Bean.GasResult;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.api.GasService;
import com.yueerba.internetofvehicles.internetofvehicles.model.GasModel;
import com.yueerba.internetofvehicles.internetofvehicles.view.MyImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MyImageView map;
    private MyImageView gas;
    private MyImageView music;
    private MyImageView myCar;
    private MyImageView deny;
    private MyImageView home;

    private MyImageView.OnViewClickListener m1;

    private String TAG="JYLBActivity";

    private GasService gasService;
    private ArrayList<GasResult.ResultBean.DataBean> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 注意顺序
        setContentView(R.layout.activity_main);

        findView();

        m1 = new MyImageView.OnViewClickListener() {
            @Override
            public void onViewClick(MyImageView v) {
                // TODO Auto-generated method stub
                switch (v.getId())
                {
                    case R.id.map:
                        startActivity(new Intent(MainActivity.this, MapActivity.class));
                        break;
                    case R.id.gas:
                        Intent intent=new Intent(MainActivity.this,JuHeSearchActivity.class);
                        String jsonData = new Gson().toJson(arrayList, new TypeToken<ArrayList<GasResult.ResultBean.DataBean>>(){}.getType());
                        intent.putExtra("DataBeanList",jsonData);
                        startActivity(intent);
                        break;
                    case R.id.music:
                        startActivity(new Intent(MainActivity.this, MusicActivity.class));
                        break;
                    case R.id.myCar:
                        startActivity(new Intent(MainActivity.this, CarActivity.class));
                        break;
                    case R.id.deny:
                        startActivity(new Intent(MainActivity.this, WebViewBaseActivity.class));
                        break;
                    case R.id.myHome:
                        intent = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        };

        setListener();

        gasService = GasModel.getModel().getService();
        getMedList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void findView() {
        map = (MyImageView) findViewById(R.id.map);
        gas = (MyImageView) findViewById(R.id.gas);
        music = (MyImageView) findViewById(R.id.music);
        myCar = (MyImageView) findViewById(R.id.myCar);
        deny = (MyImageView) findViewById(R.id.deny);
        home = (MyImageView) findViewById(R.id.myHome);
    }

    public void setListener() {
        map.setOnClickIntent(m1);
        gas.setOnClickIntent(m1);
        music.setOnClickIntent(m1);
        myCar.setOnClickIntent(m1);
        deny.setOnClickIntent(m1);
        home.setOnClickIntent(m1);
    }

    public void getMedList() {
        Call<GasResult> gasCall = gasService.getGasResult("11af0a3b955891bdc68176f262163204",115.032438,30.214695,3000);//输入查询参数
        gasCall.enqueue(new Callback<GasResult>() {
            @Override
            public void onResponse(Call<GasResult> call, Response<GasResult> response) {
                if (response.isSuccessful())
                {
                    GasResult gasResult = response.body();
                    if (gasResult != null && gasResult.getError_code() == 0)
                    {
                        List<GasResult.ResultBean.DataBean> list = gasResult.getResult().getData();
                        arrayList = (ArrayList<GasResult.ResultBean.DataBean>) list;
                        for (int i = 0; i < list.size(); i++)
                        {
                            System.out.println(list.get(i).getAddress());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<GasResult> call, Throwable t) {
                for (int i = 0; i < 10; i++)
                {
                    Log.d(TAG, "错误信息来了: " + t.getMessage());
                }

            }
        });
    }

}
