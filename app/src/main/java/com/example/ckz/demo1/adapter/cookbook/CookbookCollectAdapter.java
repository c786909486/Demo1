package com.example.ckz.demo1.adapter.cookbook;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.db.CookbookSaveBean;

import java.util.List;

/**
 * Created by CKZ on 2017/6/14.
 */

public class CookbookCollectAdapter extends BaseAdapter {
    private Context mContext;
    private List<CookbookSaveBean> mData;

    public CookbookCollectAdapter(Context mContext,List<CookbookSaveBean> mData){
        this.mContext = mContext;
        this.mData = mData;
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
        SaveViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cook_collect,parent,false);
            holder = new SaveViewHolder();
            holder.mCookName = (TextView) convertView.findViewById(R.id.cook_name);
            holder.mCookPic = (ImageView) convertView.findViewById(R.id.cook_pic);
            holder.mTag = (LinearLayout) convertView.findViewById(R.id.tag_layout);
            convertView.setTag(holder);
        }else {
            holder = (SaveViewHolder) convertView.getTag();
        }
        //图片
        Glide.with(mContext).load(mData.get(position).getPicUrl()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate().into(holder.mCookPic);
        //菜名
        holder.mCookName.setText(mData.get(position).getCookName());
        String[] tagArray = mData.get(position).getTag().split(",");
        if (holder.mTag.getChildCount()<3){
            if (tagArray.length>3 ){
                setTag(holder.mTag,3,tagArray);
            }else {
                setTag(holder.mTag,tagArray.length,tagArray);
            }
        }
        return convertView;
    }

    private void setTag(LinearLayout mTag,int limited,String[] array ){
        for (int i =0;i<limited;i++){
            TextView textView = new TextView(mContext);
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.tag_bg));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#A4D3EE"));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(8,0,8,0);
            textView.setLayoutParams(layoutParams);
            textView.setText(array[i]);
            mTag.addView(textView);
        }
    }

    class SaveViewHolder{
        ImageView mCookPic;
        TextView mCookName;
        LinearLayout mTag;
    }
}
