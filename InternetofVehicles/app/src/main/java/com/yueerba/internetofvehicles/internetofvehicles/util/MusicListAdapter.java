package com.yueerba.internetofvehicles.internetofvehicles.util;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Music;
import com.yueerba.internetofvehicles.internetofvehicles.R;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by Administrator on 2016/8/24.
 */


public class MusicListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private Context mContext;
    private ArrayList<Music> mArrayList;
    private LayoutInflater inflater;

    public MusicListAdapter(Context t, ArrayList l) {
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
        ViewHolder holder = new ViewHolder();
        Log.d(TAG, "getView: " + "哥哥到此一游" + position);
        if (convertView == null)
        {
            convertView = inflater.inflate(R.layout.my_adapter_simple_item, null);
            holder.singer = (TextView) convertView.findViewById(R.id.tv1);
            holder.song = (TextView) convertView.findViewById(R.id.tv2);
            convertView.setTag(holder);
        } else
        {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.singer.setText(((Music) mArrayList.get(position)).getName());
        holder.song.setText(((Music) mArrayList.get(position)).getMusicName());


        //我在这里用String取值，取得是循环后最后的值，是个定值，而我要
        //传入事件监听器的是一个定值，因此我得不到动态的效果，在这里我
        //有定义了一个变值（数组），然后将另一个变值赋值给它，相当于
        //多定义了一个变量结构来传递另一个变量结构，谓之多余


        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }

    public static class ViewHolder {
        TextView singer;
        TextView song;
    }
}

