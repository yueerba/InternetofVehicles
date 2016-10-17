package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Car;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.db.CarDao;
import com.yueerba.internetofvehicles.internetofvehicles.util.CarListAdapter;

import java.util.ArrayList;


public class CarActivity extends Activity {

    private ListView listView;
    private Button ShaoMiao;
    private ArrayList<Car> arrayList;
    private Car car;
    private CarDao carDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 注意顺序
        setContentView(R.layout.activity_car); // 注意顺序
        arrayList=new ArrayList<>();
        /*car=new Car(R.drawable.huangche, "POS","911","鄂D3638",
                "60.0","WDDNG5GB0AA","中型车","50000.0","YES","YES","YES","30.0");
        arrayList.add(car);*/


        carDao=new CarDao(CarActivity.this);
        arrayList=carDao.listCar();

        listView=(ListView) findViewById(R.id.carListView);
        listView.setAdapter(new CarListAdapter(this,arrayList));

        ShaoMiao=(Button) findViewById(R.id.ShaoMiao);
        ShaoMiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CarActivity.this, CarZxingActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
