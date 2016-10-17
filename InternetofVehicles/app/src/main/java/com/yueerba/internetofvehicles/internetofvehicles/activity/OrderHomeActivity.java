package com.yueerba.internetofvehicles.internetofvehicles.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Order;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.util.UserUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class OrderHomeActivity extends Activity {

    private TextView userNameTvOrder;
    private TextView noNum;
    private TextView totalNum;

    final String url="http://192.168.3.112:8080/Internet%20of%20Vehicles/OrderSelectServlet?username="+ UserUtil.username+"";
    final String urlStatus="http://192.168.3.112:8080/Internet%20of%20Vehicles/OrderSelectStatusServlet?username="+ UserUtil.username+"&&status=no";
    List<Order> newsBeanList;
    List<Order> newsStatusList;

    private Handler nototalHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            noNum.setText(msg.arg1+"");
        }
    };
    private Handler totalHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            totalNum.setText(msg.arg1+"");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_home);

        findView();

        getList();
    }

    public void findView(){
        userNameTvOrder=(TextView) findViewById(R.id.userNameTvOrder);
        noNum=(TextView) findViewById(R.id.noNum);
        totalNum=(TextView) findViewById(R.id.totalNum);
    }

    public void noOrder(View v){
        Intent intent = new Intent(OrderHomeActivity.this, OrderListActivity.class);
        intent.putParcelableArrayListExtra("orderList", (ArrayList<? extends Parcelable>) newsStatusList);
        startActivity(intent);
        finish();
    }

    public void yesOrder(View v){
        Intent intent = new Intent(OrderHomeActivity.this, OrderListActivity.class);
        intent.putParcelableArrayListExtra("orderList", (ArrayList<? extends Parcelable>) newsBeanList);
        startActivity(intent);
        finish();
    }

    public void fanHui(View v){
        Intent intent = new Intent(OrderHomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //获取Json数据，添加到List集合中
    private ArrayList getJsonData(String urlString) {

        List<Order> newsBeanList = new ArrayList<Order>();
        ArrayList mlist=new ArrayList();
        //获取网页返回的字符串
        HttpURLConnection urlConnection = null;
        BufferedReader bufr = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream is = urlConnection.getInputStream();
            bufr = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = "";
            while ((line = bufr.readLine()) != null) {
                sb.append(line);
            }

            //解析Json数据
            JSONObject jsonObject = null;
            Order newsBean = null;
            try {
                jsonObject = new JSONObject(sb.toString());
                int total=jsonObject.getInt("total");
                JSONArray jsonArray = jsonObject.getJSONArray("row");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    newsBean = new Order();
                    newsBean.time = jsonObject.getString("time");
                    newsBean.address = jsonObject.getString("address");
                    newsBean.type = jsonObject.getString("type");
                    newsBean.price = jsonObject.getDouble("price");
                    newsBean.amount = jsonObject.getDouble("amount");
                    newsBean.totalprice = jsonObject.getDouble("totalprice");
                    newsBean.status = jsonObject.getString("status");
                    newsBean.username = jsonObject.getString("username");
                    newsBeanList.add(newsBean);
                }
                mlist.add(newsBeanList);
                mlist.add(total);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mlist;
    }

    public void getList(){
        newsBeanList=new ArrayList<>();
        newsStatusList=new ArrayList<>();
        new Thread(){
            @Override
            public void run() {
                newsBeanList = (List<Order>) getJsonData(url).get(0);
                int i= (int) getJsonData(url).get(1);
                Message msg=Message.obtain();
                msg.arg1=i;
                totalHandler.sendMessage(msg);
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                newsStatusList = (List<Order>) getJsonData(urlStatus).get(0);
                int i= (int) getJsonData(urlStatus).get(1);
                Message msg=Message.obtain();
                msg.arg1=i;
                nototalHandler.sendMessage(msg);
            }
        }.start();
    }

}
