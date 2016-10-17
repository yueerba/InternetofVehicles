package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Car;
import com.yueerba.internetofvehicles.internetofvehicles.R;

public class CarDetailActivity extends Activity {

    private TextView DCarPingPai;
    private TextView DCarCheXing;
    private TextView DCarChePai;
    private TextView DCarYouXiang;
    private TextView DCarFaDongJiHao;
    private TextView DCarJiBie;
    private TextView DCarLiChengShu;
    private TextView DCarFZT;
    private TextView DCarBSZT;
    private TextView DCarCDZT;
    private TextView DCarShengYuYouliang;
    private Car car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);

        findView();

        car=new Car();
        Intent intent=this.getIntent();
        car=intent.getParcelableExtra("carDetail");

        DCarPingPai.setText(car.getPingPai());
        DCarCheXing.setText(car.getCheXing());
        DCarChePai.setText(car.getChePai());
        DCarYouXiang.setText(car.getYouXiang());
        DCarFaDongJiHao.setText(car.getFaDongJiHao());
        DCarJiBie.setText(car.getJiBie());
        DCarLiChengShu.setText(car.getLiChengShu());
        DCarFZT.setText(car.getFZT());
        DCarBSZT.setText(car.getBSZT());
        DCarCDZT.setText(car.getCDZT());
        DCarShengYuYouliang.setText(car.getShengYuYouliang());
    }

    public void findView(){
        DCarPingPai=(TextView) findViewById(R.id.DCarPingPai);
        DCarCheXing=(TextView) findViewById(R.id.DCarCheXing);
        DCarChePai=(TextView) findViewById(R.id.DCarChePai);
        DCarYouXiang=(TextView) findViewById(R.id.DCarYouXiang);
        DCarFaDongJiHao=(TextView) findViewById(R.id.DCarFaDongJiHao);
        DCarJiBie=(TextView) findViewById(R.id.DCarJiBie);
        DCarLiChengShu=(TextView) findViewById(R.id.DCarLiChengShu);
        DCarFZT=(TextView) findViewById(R.id.DCarFZT);
        DCarBSZT=(TextView) findViewById(R.id.DCarBSZT);
        DCarCDZT=(TextView) findViewById(R.id.DCarCDZT);
        DCarShengYuYouliang=(TextView) findViewById(R.id.DCarShengYuYouliang);
    }
}
