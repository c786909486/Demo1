package com.example.ckz.demo1.adapter.cookbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.cookbook.CookbookByIdBean;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.util.IntToStringUtils;
import com.example.ckz.demo1.view.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/12.
 */

public class CookProgressAadapter extends BaseAdapter {
    private static final int LOCALDATA = 0;
    private static final int NETDATA = 1;
    private List<Object> mData;
    private Context mContext;

    public CookProgressAadapter(Context mContext){
        this.mContext = mContext;
        mData = new ArrayList<>();
    }
    public void addAll(List<CookbookDataBean.ResultBean.ListBean.ProcessBean> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }
    public void addAllNet(List<CookbookByIdBean.ResultBean.ProcessBean> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof CookbookDataBean.ResultBean.ListBean.ProcessBean){
            return LOCALDATA;
        }else {
            return NETDATA;
        }
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProgressViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cook_progress,parent,false);
            holder = new ProgressViewHolder();
            holder.mProgressContent = (TextView) convertView.findViewById(R.id.progress_content);
            holder.mProgressPick = (ImageView) convertView.findViewById(R.id.progress_pic);
            convertView.setTag(holder);
        }else {
            holder = (ProgressViewHolder) convertView.getTag();
        }
        switch (getItemViewType(position)){
            case LOCALDATA:
                CookbookDataBean.ResultBean.ListBean.ProcessBean data = (CookbookDataBean.ResultBean.ListBean.ProcessBean) mData.get(position);
                setContent(data.getPic(),IntToStringUtils.position2text(position+1)+data.getPcontent(),holder);
                break;
            case NETDATA:
                CookbookByIdBean.ResultBean.ProcessBean data1 = (CookbookByIdBean.ResultBean.ProcessBean) mData.get(position);
                setContent(data1.getPic(),IntToStringUtils.position2text(position+1)+data1.getPcontent(),holder);
                break;
        }


        return convertView;
    }
    private void setContent(String picUrl,String progress,ProgressViewHolder holder){
        Glide.with(mContext).load(picUrl).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                .into(holder.mProgressPick);
//        holder.mProgressContent.setText(IntToStringUtils.position2text(position+1)+mData.get(position).getPcontent());
        holder.mProgressContent.setText(progress);
    }

    class ProgressViewHolder{
        ImageView mProgressPick;
        TextView mProgressContent;
    }
}
