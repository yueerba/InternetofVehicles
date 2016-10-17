package com.yueerba.internetofvehicles.internetofvehicles.util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Car;
import com.yueerba.internetofvehicles.internetofvehicles.R;
import com.yueerba.internetofvehicles.internetofvehicles.activity.CarDetailActivity;
import com.yueerba.internetofvehicles.internetofvehicles.activity.CarGeneratectivity;
import com.yueerba.internetofvehicles.internetofvehicles.db.CarDao;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/8/26.
 */

public class CarListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context mContext;
    private ArrayList<Car> mArrayList;
    private LayoutInflater inflater;
    private CarDao carDao;

    public CarListAdapter(Context t, ArrayList l) {
        this.mContext = t;
        this.mArrayList = l;
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
        CarListAdapter.ViewHolder holder = new CarListAdapter.ViewHolder();

        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.car_item, null);
            holder.carIcon = (ImageView) convertView.findViewById(R.id.carIcon);
            holder.carName = (TextView) convertView.findViewById(R.id.carName);
            holder.carId = (TextView) convertView.findViewById(R.id.carId);
            holder.carDetail = (Button) convertView.findViewById(R.id.carDetail);
            holder.carZxing=(Button) convertView.findViewById(R.id.carZxing);
            holder.carDel = (Button) convertView.findViewById(R.id.carDel);
            convertView.setTag(holder);
        } else
        {
            holder = (CarListAdapter.ViewHolder) convertView.getTag();
        }
        holder.carIcon.setImageResource(((Car) mArrayList.get(position)).getPP());
        holder.carName.setText(((Car) mArrayList.get(position)).getCheXing());
        holder.carId.setText(((Car) mArrayList.get(position)).getChePai());
        holder.carDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, CarDetailActivity.class);
                intent.putExtra("carDetail",mArrayList.get(position));
                mContext.startActivity(intent);
            }
        });
        holder.carZxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, CarGeneratectivity.class);
                intent.putExtra("carZxing",mArrayList.get(position));
                mContext.startActivity(intent);
            }
        });
        holder.carDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carDao=new CarDao(mContext);
                carDao.deleteCar(mArrayList.get(position).getId());
                mArrayList.remove(position);
                CarListAdapter.this.notifyDataSetChanged();
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

    public static class ViewHolder {
        ImageView carIcon;
        TextView carName;
        TextView carId;
        Button carDetail;
        Button carZxing;
        Button carDel;
    }
}
