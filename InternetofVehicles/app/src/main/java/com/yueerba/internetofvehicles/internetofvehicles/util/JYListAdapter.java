package com.yueerba.internetofvehicles.internetofvehicles.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.mapapi.model.LatLng;
import com.mapdemo.JYDDActivity;
import com.yueerba.internetofvehicles.internetofvehicles.Bean.GasResult;
import com.yueerba.internetofvehicles.internetofvehicles.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/9/5.
 */

public class JYListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context mContext;
    private ArrayList<GasResult.ResultBean.DataBean> mArrayList;
    private LatLng latLngStart;
    private LatLng latLngEnd;
    private LayoutInflater inflater;

    public JYListAdapter(Context t, ArrayList l,LatLng s,LatLng e) {
        this.mContext = t;
        this.mArrayList = l;
        this.latLngStart = s;
        this.latLngEnd = e;
        inflater = LayoutInflater.from(mContext);
        //所有的对象必须通过new方法或是其他方法在程序开始的时候进行初始化，不然直接加载数据
        //会出现NullPointer的异常
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }
        else if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.map_item, null);
            holder = new ViewHolder(convertView);
            holder.itemTv = (TextView) convertView.findViewById(R.id.item_tv);
            holder.itemBt = (Button) convertView.findViewById(R.id.item_bt);
            convertView.setTag(holder);
        } else
        {
            holder = (JYListAdapter.ViewHolder) convertView.getTag();
        }
        holder.itemTv.setText(((GasResult.ResultBean.DataBean) mArrayList.get(position)).getAddress());
        holder.itemBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(mContext,
                        JYDDActivity.class);
                latLngEnd=new LatLng(Double.parseDouble(mArrayList.get(position).getLat()),
                        Double.parseDouble(mArrayList.get(position).getLon()));
                intent.putExtra("dataBean", mArrayList.get(position));
                intent.putExtra("latLngEnd",latLngEnd);
                intent.putExtra("latLngStart",latLngStart);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    static class ViewHolder {
        @BindView(R.id.item_tv)
        TextView itemTv;
        @BindView(R.id.item_bt)
        Button itemBt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
