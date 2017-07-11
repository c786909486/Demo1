package com.example.ckz.demo1.adapter.express;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.express.ExpressProgressBean;
import com.example.ckz.demo1.util.MyTimeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/24.
 */

public class ExpressProgressAdapter extends BaseAdapter {
    private List<ExpressProgressBean.ResultBean.ListBean> mData;
    private Context mContext;

    public ExpressProgressAdapter(Context mContext){
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void addAll(List<ExpressProgressBean.ResultBean.ListBean> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProgressViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_express_progress,parent,false);
            holder = new ProgressViewHolder();
            holder.mUpdateTime = (TextView) convertView.findViewById(R.id.update_time);
            holder.mProgress = (TextView) convertView.findViewById(R.id.progress);
            holder.mState = (ImageView) convertView.findViewById(R.id.express_state);
            convertView.setTag(holder);
        }else {
            holder = (ProgressViewHolder) convertView.getTag();
        }
        holder.mUpdateTime.setText(MyTimeUtils.getYewstoday(mData.get(position).getTime()));
        holder.mProgress.setText(mData.get(position).getStatus());
        if (position == 0){
            holder.mProgress.setSelected(true);
            holder.mUpdateTime.setSelected(true);
            holder.mState.setSelected(true);
        }else {
            holder.mProgress.setSelected(false);
            holder.mUpdateTime.setSelected(false);
            holder.mState.setSelected(false);
        }
        return convertView;
    }

    class ProgressViewHolder{
        TextView mUpdateTime;
        TextView mProgress;
        ImageView mState;
    }
}
