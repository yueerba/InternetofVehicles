package com.mapdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.yueerba.internetofvehicles.internetofvehicles.Bean.GasResult;
import com.yueerba.internetofvehicles.internetofvehicles.R;

public class JYDDActivity extends Activity {

    private TextView JYChe;
    private TextView JYZhan;
    private TextView JYPing;
    private TextView JYNum;

    GasResult.ResultBean.DataBean dataBean;

    LatLng latLngStart;
    LatLng latLngEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jydd);
        Intent intent = this.getIntent();
        dataBean= (GasResult.ResultBean.DataBean) intent.getSerializableExtra("dataBean");
        latLngStart=intent.getParcelableExtra("latLngStart");
        latLngEnd=intent.getParcelableExtra("latLngEnd");
        findView();
        setText();
    }

    public void onYY(View v){
        Intent intent = new Intent(JYDDActivity.this,
                JYEWMActivity.class);
        intent.putExtra("JYZxing",JYChe.getText()+"|"+JYZhan.getText()+"|"
                +JYPing.getText()+"|"+JYNum.getText());
        intent.putExtra("latLngEnd",latLngEnd);
        intent.putExtra("latLngStart",latLngStart);
        startActivity(intent);
    }
    public void findView(){
        JYChe=(TextView) findViewById(R.id.JYChe);
        JYZhan=(TextView) findViewById(R.id.JYZhan);
        JYPing=(TextView) findViewById(R.id.JYPing);
        JYNum=(TextView) findViewById(R.id.JYNum);
    }
    public void setText(){
        JYZhan.setText(dataBean.getBrandname());
        JYPing.setText(dataBean.getPrice().getE97());
    }
}
