package com.mapdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.yueerba.internetofvehicles.internetofvehicles.R;

import java.util.ArrayList;
import java.util.List;

public class MapFuJingActivity extends FragmentActivity implements
        OnGetPoiSearchResultListener, OnGetSuggestionResultListener {

    private AutoCompleteTextView keyWorldsView;
    private ArrayAdapter<String> sugAdapter = null;
    private SuggestionSearch mSuggestionSearch = null;
    private List<String> suggest;
    String city;
    String jiayou;
    String tingche;

    LatLng latLngMain=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_fu_jing);
        Intent intent=this.getIntent();
        city=intent.getStringExtra("city");
        latLngMain=intent.getParcelableExtra("latLngMain");
        jiayou="加油站";
        tingche="停车场";

        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);

        keyWorldsView=(AutoCompleteTextView) findViewById(R.id.searchkey);
        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        keyWorldsView.setAdapter(sugAdapter);
        keyWorldsView.setThreshold(1);

        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0) {
                    return;
                }

                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city(city));
            }
        });
        Toast.makeText(this, city, Toast.LENGTH_SHORT).show();
    }

    public void fuJinJiaYou(View v){
        Intent intent=new Intent(MapFuJingActivity.this,MapSearchActivity.class);
        intent.putExtra("city",city);
        intent.putExtra("keyword",jiayou);
        intent.putExtra("latLngMain",latLngMain);
        startActivity(intent);
    }

    public void fuJinTingChe(View v){
        Intent intent=new Intent(MapFuJingActivity.this,MapSearchActivity.class);
        intent.putExtra("city",city);
        intent.putExtra("keyword",tingche);
        intent.putExtra("latLngMain",latLngMain);
        startActivity(intent);
    }

    public void onssBt(View v){
        Intent intent=new Intent(MapFuJingActivity.this,MapSearchActivity.class);
        intent.putExtra("city",city);
        String str=keyWorldsView.getText().toString();
        intent.putExtra("latLngMain",latLngMain);
        intent.putExtra("keyword",str);
        startActivity(intent);
    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        suggest = new ArrayList<String>();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null) {
                suggest.add(info.key);
            }
        }
        sugAdapter = new ArrayAdapter<String>(MapFuJingActivity.this, android.R.layout.simple_dropdown_item_1line, suggest);
        keyWorldsView.setAdapter(sugAdapter);
        sugAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        mSuggestionSearch.destroy();
        super.onDestroy();
    }
}
