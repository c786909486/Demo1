package com.example.ckz.demo1.adapter.pagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.cookbook.CookbookByIdBean;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.nie.ngallerylibrary.adater.MyPageradapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/13.
 */

public class CookProgressPagerAdapter extends MyPageradapter {
    private static final int COOK = 0;
    private static final int NET = 1;
    private static final int EMPTY = 2;

    private List<Object> mData;
    private Activity activity;

    public CookProgressPagerAdapter(Activity activity){
        this.activity = activity;
        mData = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof CookbookDataBean.ResultBean.ListBean.ProcessBean){
            return COOK;
        }else if (mData.get(position) instanceof CookbookByIdBean.ResultBean.ProcessBean){
            return NET;
        }
        else {
            return EMPTY;
        }
    }

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(View view,int position);
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            switch (getItemViewType(i)){
                case COOK:
                case NET:
                    view = LayoutInflater.from(activity).inflate(R.layout.item_pager_cook_progress,viewGroup,false);
                    holder = new ViewHolder();
                    holder.mProgressPic = (ImageView) view.findViewById(R.id.progress_pic);
                    view.setTag(holder);
                    break;
            }
        }else {
            switch (getItemViewType(i)){
                case COOK:
                case NET:
                    holder = (ViewHolder) view.getTag();
                    break;
            }

        }
        switch (getItemViewType(i)){
            case COOK:
                CookbookDataBean.ResultBean.ListBean.ProcessBean bean = (CookbookDataBean.ResultBean.ListBean.ProcessBean) mData.get(i);
                Glide.with(activity).load(bean.getPic()).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                        .into(holder.mProgressPic);
                final View finalView = view;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(finalView,i);
                    }
                });
                break;
            case NET:
                CookbookByIdBean.ResultBean.ProcessBean processBean = (CookbookByIdBean.ResultBean.ProcessBean) mData.get(i);
                Glide.with(activity).load(processBean.getPic()).diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                        .into(holder.mProgressPic);
                final View finalView1 = view;
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOnItemClickListener.onItemClick(finalView1,i);
                    }
                });
        }


        return view;
    }

    @Override
    public int getCount() {
        return mData.size();
    }
    class ViewHolder{
        ImageView mProgressPic;
    }
}
