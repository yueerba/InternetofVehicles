package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.util.OrderListAdapter;

import java.util.ArrayList;

public class OrderListActivity extends Activity {

    private ListView listView;
    private ArrayList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        list=new ArrayList();
        Intent intent=this.getIntent();
        list=intent.getParcelableArrayListExtra("orderList");

        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new OrderListAdapter(OrderListActivity.this, list, listView));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(OrderListActivity.this, OrderDetailActivity.class);
                intent.putExtra("detail", (Parcelable) list.get(i));
                startActivity(intent);
                finish();
            }
        });
    }
}
