package com.example.ckz.demo1.adapter.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.db.SearchHistoryBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by CKZ on 2017/5/26.
 */

public class NewsHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private List<SearchHistoryBean> mData;
    private LayoutInflater inflater;

    public NewsHistoryAdapter(Context mContext,List<SearchHistoryBean> mData){
        this.mContext = mContext;
        this.mData = mData;
        inflater = LayoutInflater.from(mContext);
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
        NewsHistoryHolder holder = null;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_news_history,parent,false);
            holder = new NewsHistoryHolder();
            holder.mShowHistory = (TextView) convertView.findViewById(R.id.text_history);
            holder.mDelete = (ImageView) convertView.findViewById(R.id.delete_history);
            convertView.setTag(holder);
        }else {
            holder = (NewsHistoryHolder) convertView.getTag();
        }
        holder.mShowHistory.setText(mData.get(position).getHistory());
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(SearchHistoryBean.class,"history = ?",mData.get(position).getHistory());
                mData.remove(position);
                notifyDataSetChanged();

            }
        });
        return convertView;
    }
    class NewsHistoryHolder{
        private TextView mShowHistory;
        private ImageView mDelete;
    }
}
