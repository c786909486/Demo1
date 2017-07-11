package com.example.ckz.demo1.adapter.pagerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookDetileActivity;
import com.example.ckz.demo1.adapter.news.NewsContentAdapter;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.bean.cookbook.CookbookItemBean;
import com.example.ckz.demo1.util.FastBlurUtil;
import com.nie.ngallerylibrary.adater.MyPageradapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/1.
 */

public class GalleryPagerAdapter extends MyPageradapter {
    private List<CookbookDataBean.ResultBean.ListBean> mData;
    private Context mContext;

    public GalleryPagerAdapter (Context context){
        this.mContext = context;
        mData = new ArrayList<>();
    }

    public void addAll(List<CookbookDataBean.ResultBean.ListBean> list){
        mData.addAll(list);
        notifyDataSetChanged();
    }


    @SuppressLint("ViewHolder")
    @Override
    public View getView(final int i,  View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null){
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_scroll_image,viewGroup,false);
            holder.mCircleBg = (ImageView) view.findViewById(R.id.circle_bg);
            holder.mCircleImage = (ImageView) view.findViewById(R.id.circle_image);
            holder.mCookName = (TextView) view.findViewById(R.id.cook_name);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        setImage(holder.mCircleImage,i);
        setCookName(holder.mCookName,i);
        setBlurBg(holder.mCircleBg,i);
        holder.mCircleBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,CookbookDetileActivity.class);
                intent.putExtra("cookData",mData.get(i));
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    /**
     * 菜品图片
     */
    private void setImage(ImageView image,int position){
        Glide.with(mContext).load(mData.get(position).getPic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                .into(image);
    }

    /**
     * 菜品名字
     */
    private void setCookName(TextView textView,int position){
        textView.setText(mData.get(position).getName());
    }

    /**
     * 高斯模糊背景
     */
    private void setBlurBg(final ImageView imageView, int position){
        Glide.with(mContext).load(mData.get(position).getPic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap,3);
                        imageView.setImageBitmap(blurBitmap);
                    }
                });
    }

    @Override
    public int getCount() {
        return mData.size();
    }
    class ViewHolder{
        ImageView mCircleBg;
        ImageView mCircleImage;
        TextView mCookName;
    }
}
