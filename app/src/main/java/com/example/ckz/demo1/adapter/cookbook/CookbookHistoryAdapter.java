package com.example.ckz.demo1.adapter.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.db.CookbookHistoryBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by CKZ on 2017/6/19.
 */

public class CookbookHistoryAdapter extends BaseAdapter {

    private Context mContext;
    private List<CookbookHistoryBean> mData;

    public CookbookHistoryAdapter(Context mContext,List<CookbookHistoryBean> mData){
        this.mContext = mContext;
        this.mData  = mData;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        HistoryViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cookbook_history,parent,false);
            holder = new HistoryViewHolder();
            holder.mHistory = (TextView) convertView.findViewById(R.id.history_text);
            holder.mDelete = (ImageView) convertView.findViewById(R.id.history_delete);
            convertView.setTag(holder);
        }else {
            holder = (HistoryViewHolder) convertView.getTag();
        }
        holder.mHistory.setText(mData.get(position).getHistory());
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(CookbookHistoryBean.class,"history = ?",mData.get(position).getHistory());
                mData.remove(position);
                notifyDataSetChanged();

            }
        });
        return convertView;
    }

    class HistoryViewHolder{
        TextView mHistory;
        ImageView mDelete;
    }
}
