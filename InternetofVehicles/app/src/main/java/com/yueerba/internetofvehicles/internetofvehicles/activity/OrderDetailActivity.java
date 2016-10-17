package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Order;
import com.yueerba.internetofvehicles.internetofvehicles.R;

public class OrderDetailActivity extends Activity {

    private TextView Dtime;
    private TextView Daddress;
    private TextView Dtype;
    private TextView Dprice;
    private TextView Damount;
    private TextView Dtotalprice;
    private TextView Dstatus;
    private TextView Dusername;
    private String time;
    private String address;
    private String type;
    private String price;
    private String amount;
    private String totalprice;
    private String status;
    private String username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        findView();

        findData();

        setText();
    }

    public void findView() {
        Dtime = (TextView) findViewById(R.id.Dtime);
        Daddress = (TextView) findViewById(R.id.Daddress);
        Dtype = (TextView) findViewById(R.id.Dtype);
        Dprice = (TextView) findViewById(R.id.Dprice);
        Damount = (TextView) findViewById(R.id.Damount);
        Dtotalprice = (TextView) findViewById(R.id.Dtotalprice);
        Dstatus = (TextView) findViewById(R.id.Dstatus);
        Dusername = (TextView) findViewById(R.id.Dusername);
    }

    public void findData(){
        Intent intent = this.getIntent();
        Order order=new Order();
        order=intent.getParcelableExtra("detail");
        time=order.getTime();
        address=order.getAddress();
        type=order.getType();
        price= String.valueOf(order.getPrice());
        amount= String.valueOf(order.getAmount());
        totalprice= String.valueOf(order.getTotalprice());
        status=order.getStatus();
        username=order.getUsername();
    }

    public void setText() {
        Dtime.setText(time);
        Daddress.setText(address);
        Dtype.setText(type);
        Dprice.setText(price);
        Damount.setText(amount);
        Dtotalprice.setText(totalprice);
        Dstatus.setText(status);
        Dusername.setText(username);
    }
}
