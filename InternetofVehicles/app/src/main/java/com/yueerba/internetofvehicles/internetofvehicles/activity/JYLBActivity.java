package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yueerba.internetofvehicles.internetofvehicles.Bean.GasResult;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.api.GasService;
import com.yueerba.internetofvehicles.internetofvehicles.util.JYListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JYLBActivity extends Activity {
    @BindView(R.id.jyList)
    ListView jyList;
    @BindView(R.id.nihao)
    Button nihao;

    private String TAG="JYLBActivity";

    private GasService gasService;
    ArrayList<GasResult.ResultBean.DataBean> dataBeanList;

    LatLng latLngStart;
    LatLng latLngEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jylb);
        ButterKnife.bind(this);

        Intent intent = this.getIntent();
        String jsonData = intent.getStringExtra("DataBeanList");
        dataBeanList = new Gson().fromJson(jsonData, new TypeToken<ArrayList<GasResult.ResultBean.DataBean>>() {
        }.getType());
        latLngStart=intent.getParcelableExtra("latLngStart");
        latLngEnd=intent.getParcelableExtra("latLngEnd");

        nihao.setVisibility(View.GONE);
        jyList.setAdapter(new JYListAdapter(JYLBActivity.this, dataBeanList,latLngStart,latLngEnd));
       /* gasService = GasModel.getModel().getService();
        getMedList();*/
    }

   /* public void getMedList() {
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
    }*/

    @OnClick(R.id.nihao)
    public void onClick() {

    }
}
