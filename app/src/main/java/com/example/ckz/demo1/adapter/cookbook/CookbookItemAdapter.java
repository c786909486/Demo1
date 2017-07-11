package com.example.ckz.demo1.adapter.cookbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookDetileActivity;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.util.ScreenUtils;
import com.example.ckz.demo1.view.SelectableRoundedImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/3.
 */

public class CookbookItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CookbookDataBean.ResultBean.ListBean> mData;
    private Context mContext;

    public CookbookItemAdapter(Context mContext){
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void addAll(List<CookbookDataBean.ResultBean.ListBean> mList){
        mData.addAll(mList);
        notifyDataSetChanged();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_show_cookbook,parent,false);
        ShowItemViewHolder holder = new ShowItemViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        float width = ScreenUtils.getScreenWidth(mContext)/2-mContext.getResources().getDimension(R.dimen.activity_horizontal_margin)-24;
        float rootWidth = ScreenUtils.getScreenWidth(mContext)/2-mContext.getResources().getDimension(R.dimen.activity_horizontal_margin);
        ShowItemViewHolder itemViewHolder = (ShowItemViewHolder) holder;
        itemViewHolder.mPeopleNum.setText(mData.get(position).getPeoplenum());
        itemViewHolder.mCookName.setText(mData.get(position).getName());
        itemViewHolder.mPeopleNum.setMaxWidth((int) width);
        itemViewHolder.mCookName.setMaxWidth((int) width);
        itemViewHolder.mRoot.setMinimumWidth((int) rootWidth);
        itemViewHolder.imageView.setMaxWidth((int) width);
        Glide.with(mContext).load(mData.get(position).getPic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemViewHolder.imageView);
        itemViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CookbookDetileActivity.class);
                intent.putExtra("cookData",mData.get(position));
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ShowItemViewHolder extends RecyclerView.ViewHolder {
        private SelectableRoundedImageView imageView;
        private TextView mCookName;
        private TextView mPeopleNum;
        private RelativeLayout mRoot;
        public ShowItemViewHolder(View itemView) {
            super(itemView);
            imageView = (SelectableRoundedImageView) itemView.findViewById(R.id.cook_image);
            mCookName = (TextView) itemView.findViewById(R.id.cook_name);
            mPeopleNum = (TextView) itemView.findViewById(R.id.people_num);
            mRoot = (RelativeLayout) itemView.findViewById(R.id.root_layout);
        }
    }
}
