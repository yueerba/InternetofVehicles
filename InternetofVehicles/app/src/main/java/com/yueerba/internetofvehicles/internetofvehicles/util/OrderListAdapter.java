package com.yueerba.internetofvehicles.internetofvehicles.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yueerba.internetofvehicles.internetofvehicles.Bean.Order;
import com.yueerba.internetofvehicles.internetofvehicles.R;

import java.util.List;

/**
 * Created by Administrator on 2016/8/23.
 */

/**
 * 继承BaseAdapter是最好的习惯
 * Created by geyan on 2016/7/24.
 */
public class OrderListAdapter extends BaseAdapter implements AbsListView.OnScrollListener {
    private List<Order> mList;
    private Context mContext;
    private boolean mIsListViewIdle = true;
    private ListView mListView;

    public OrderListAdapter(Context context, List<Order> data, ListView listView) {
        this.mContext = context;
        this.mList = data;
        this.mListView = listView;
        mListView.setOnScrollListener(this);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.order_item, null);
            viewHolder = new ViewHolder();
            viewHolder.order_time = (TextView) convertView.findViewById(R.id.order_time);
            viewHolder.order_address = (TextView) convertView.findViewById(R.id.order_address);
            viewHolder.orderType = (TextView) convertView.findViewById(R.id.orderType);
            viewHolder.orderPrice = (TextView) convertView.findViewById(R.id.orderPrice);
            viewHolder.order_amount = (TextView) convertView.findViewById(R.id.order_amount);
            viewHolder.order_status = (TextView) convertView.findViewById(R.id.order_status);
            viewHolder.orderTotalPrice = (TextView) convertView.findViewById(R.id.orderTotalPrice);
            viewHolder.erWeiMa = (Button) convertView.findViewById(R.id.erWeiMa);
            viewHolder.delOrder = (Button) convertView.findViewById(R.id.delOrder);
            viewHolder.payOrder = (Button) convertView.findViewById(R.id.payOrder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.order_time.setText(mList.get(position).time);
        viewHolder.order_address.setText(mList.get(position).address);
        viewHolder.orderType.setText(mList.get(position).type);
        viewHolder.orderPrice.setText(mList.get(position).price+"");
        viewHolder.order_amount.setText(mList.get(position).amount+"");
        viewHolder.order_status.setText(mList.get(position).status);
        viewHolder.orderTotalPrice.setText(mList.get(position).totalprice+"");
        return convertView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            mIsListViewIdle = true;
            this.notifyDataSetChanged();
        } else {
            mIsListViewIdle = false;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    class ViewHolder {
        public TextView order_time;
        public TextView order_address;
        public TextView orderType;
        public TextView orderPrice;
        public TextView order_amount;
        public TextView order_status;
        public TextView orderTotalPrice;
        public Button erWeiMa;
        public Button delOrder;
        public Button payOrder;
    }
}
