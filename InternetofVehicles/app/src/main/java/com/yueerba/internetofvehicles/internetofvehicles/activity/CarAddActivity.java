package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Car;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.db.CarDao;

public class CarAddActivity extends Activity {

    private String result;
    private String str[];

    private TextView ACarPingPai;
    private TextView ACarCheXing;
    private TextView ACarChePai;
    private TextView ACarYouXiang;
    private TextView ACarFaDongJiHao;
    private TextView ACarJiBie;
    private TextView ACarLiChengShu;
    private TextView ACarFZT;
    private TextView ACarBSZT;
    private TextView ACarCDZT;
    private TextView ACarShengYuYouliang;

    private Button ASubmit;

    private Car car;
    private CarDao carDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);

        findView();

        Intent intent=this.getIntent();
        result=intent.getStringExtra("result");
        str=result.split("\\|");

        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

        ACarPingPai.setText(str[0]);
        ACarCheXing.setText(str[1]);
        ACarChePai.setText(str[2]);
        ACarYouXiang.setText(str[3]);
        ACarFaDongJiHao.setText(str[4]);
        ACarJiBie.setText(str[5]);
        ACarLiChengShu.setText(str[6]);
        ACarFZT.setText(str[7]);
        ACarBSZT.setText(str[8]);
        ACarCDZT.setText(str[9]);
        ACarShengYuYouliang.setText(str[10]);

        car=new Car(R.drawable.baoma,str[0],str[1],str[2],str[3],str[4],
                str[5],str[6],str[7],str[8],str[9],str[10]);

        ASubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent send=new Intent(CarAddActivity.this,CarActivity.class);
                send.putExtra("car",car);
                carDao=new CarDao(CarAddActivity.this);
                carDao.insertCar(car);
                startActivity(send);
                finish();
            }
        });

    }

    public void findView(){
        ACarPingPai=(TextView) findViewById(R.id.ACarPingPai);
        ACarCheXing=(TextView) findViewById(R.id.ACarCheXing);
        ACarChePai=(TextView) findViewById(R.id.ACarChePai);
        ACarYouXiang=(TextView) findViewById(R.id.ACarYouXiang);
        ACarFaDongJiHao=(TextView) findViewById(R.id.ACarFaDongJiHao);
        ACarJiBie=(TextView) findViewById(R.id.ACarJiBie);
        ACarLiChengShu=(TextView) findViewById(R.id.ACarLiChengShu);
        ACarFZT=(TextView) findViewById(R.id.ACarFZT);
        ACarBSZT=(TextView) findViewById(R.id.ACarBSZT);
        ACarCDZT=(TextView) findViewById(R.id.ACarCDZT);
        ACarShengYuYouliang=(TextView) findViewById(R.id.ACarShengYuYouliang);
        ASubmit=(Button) findViewById(R.id.ASubmit);
    }
}
