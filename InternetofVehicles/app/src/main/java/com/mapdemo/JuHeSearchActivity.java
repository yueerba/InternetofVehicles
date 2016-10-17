package com.mapdemo;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yueerba.internetofvehicles.internetofvehicles.Bean.GasResult;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.activity.JYLBActivity;
import com.yueerba.internetofvehicles.internetofvehicles.util.MyOrientationListener;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JuHeSearchActivity extends AppCompatActivity {

    @BindView(R.id.diTu)
    Button diTu;
    @BindView(R.id.lieBiao)
    Button lieBiao;
    //传感相关
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;


    // 定位相关
    Geocoder geocoder;
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    String city;

    MapView mMapView;
    BaiduMap mBaiduMap;
    boolean isFirstLoc = true; // 是否首次定位

    // UI相关
    Button requestLocButton;
    private InfoWindow mInfoWindow;

    LatLng latLngMain;
    LatLng ll;

    TextView item_tv;
    Button item_bt;
    LinearLayout linearLayout;
    ArrayList<GasResult.ResultBean.DataBean> dataBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ju_he_search);
        ButterKnife.bind(this);

        linearLayout = (LinearLayout) findViewById(R.id.item_ll);
        item_tv = (TextView) findViewById(R.id.item_tv);
        item_bt = (Button) findViewById(R.id.item_bt);

        /*GasResult gasResult=new GasResult();
        final List<GasResult.ResultBean.DataBean> dataBeanList=
                new ArrayList<GasResult.ResultBean.DataBean>();
        GasResult.ResultBean.DataBean dataBean1=new GasResult.ResultBean.DataBean();
        dataBean1.setLon("115.028593");
        dataBean1.setLat("30.216022");
        dataBean1.setName("管吃管喝");
        dataBean1.setAddress("天涯路");
        dataBeanList.add(dataBean1);
        GasResult.ResultBean.DataBean dataBean2=new GasResult.ResultBean.DataBean();
        dataBean2.setLon("115.029162");
        dataBean2.setLat("30.228912");
        dataBean2.setName("怕天怕地");
        dataBean2.setAddress("黄泉路");
        dataBeanList.add(dataBean2);*/

        Intent intent = this.getIntent();
        String jsonData = intent.getStringExtra("DataBeanList");
        dataBeanList = new Gson().fromJson(jsonData, new TypeToken<ArrayList<GasResult.ResultBean.DataBean>>() {
        }.getType());
        //传感相关
        myOrientationListener = new MyOrientationListener(JuHeSearchActivity.this);
        myOrientationListener.setmOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientatioanChange(float x) {
                mCurrentX = x;
            }
        });

        geocoder = new Geocoder(this, Locale.getDefault());
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.juHeMap);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));

        mBaiduMap.clear();
        LatLng latLng = null;
        OverlayOptions overlayOptions = null;
        Marker marker = null;
        BitmapDescriptor bitmap =
                BitmapDescriptorFactory.fromResource(R.drawable.gas_station);
//当然我这里的info是我做的一个项目中，这其实是实现了在一个info数组中的info遍历
        for (GasResult.ResultBean.DataBean info : dataBeanList) {
            // 位置
            latLng = new LatLng(Double.parseDouble(info.getLat()), Double.parseDouble(info.getLon()));
            // 图标
            overlayOptions = new MarkerOptions().position(latLng)
                    .icon(bitmap).zIndex(5);
            marker = (Marker) (mBaiduMap.addOverlay(overlayOptions));
            Bundle bundle = new Bundle();
            bundle.putSerializable("info", info);
            marker.setExtraInfo(bundle);
        }
// 将地图移到到最后一个经纬度位置
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.setMapStatus(u);


        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            @Override
            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                linearLayout.setVisibility(View.GONE);
                mBaiduMap.hideInfoWindow();
            }
        });
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                ll = marker.getPosition();
                GasResult.ResultBean.DataBean dataBean = new GasResult.ResultBean.DataBean();
                linearLayout.setVisibility(LinearLayout.VISIBLE);
                for (GasResult.ResultBean.DataBean info : dataBeanList) {
                    // 位置
                    LatLng latLng = new LatLng(Double.parseDouble(info.getLat()), Double.parseDouble(info.getLon()));
                    // 图标
                    if (latLng.latitude == ll.latitude && latLng.longitude == ll.longitude) {
                        dataBean = info;
                    }
                }
                item_tv.setText(dataBean.getName() + ": " + dataBean.getAddress());
                final GasResult.ResultBean.DataBean finalDataBean = dataBean;
                item_bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(JuHeSearchActivity.this,
                                JYDDActivity.class);
                        intent.putExtra("dataBean", finalDataBean);
                        intent.putExtra("latLngEnd",ll);
                        intent.putExtra("latLngStart",latLngMain);
                        startActivity(intent);
                    }
                });
                return true;
            }
        });
    }

    @OnClick({R.id.diTu, R.id.lieBiao})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diTu:
                Toast.makeText(this, latLngMain.longitude+"----"+latLngMain.latitude, Toast.LENGTH_SHORT).show();
                break;
            case R.id.lieBiao:
                Intent intent=new Intent(JuHeSearchActivity.this,JYLBActivity.class);
                String jsonData = new Gson().toJson(dataBeanList, new TypeToken<ArrayList<GasResult.ResultBean.DataBean>>(){}.getType());
                intent.putExtra("DataBeanList",jsonData);
                intent.putExtra("latLngStart",latLngMain);
                startActivity(intent);
                break;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(50)/*location.getRadius()*/
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(mCurrentX).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                latLngMain = ll;
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }

        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocClient.isStarted()) {
            mLocClient.start();
            //开启方向传感器
            myOrientationListener.start();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocClient.stop();
        //停止方向传感器
        myOrientationListener.stop();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
