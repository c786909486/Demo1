package com.example.ckz.demo1.adapter.news;

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
import com.example.ckz.demo1.bean.user.news.NewsSaveNetBean;

import java.util.List;

/**
 * Created by CKZ on 2017/5/24.
 */

public class NewsCollectionAdapter extends BaseAdapter {
    private static final int IMAGE = 0;
    private static final int TEXT = 1;

    private List<NewsSaveNetBean> mData;
    private Context mContext;

    public NewsCollectionAdapter(Context mContext,List<NewsSaveNetBean> mData){
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getPic().equals("")){
            return TEXT;
        }else {
            return IMAGE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
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
        NewsImageHolder imageHolder = null;
        NewsTextHolder textHolder = null;
        if (convertView == null){
            switch (getItemViewType(position)){
                case IMAGE:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image_news,parent,false);
                    imageHolder = new NewsImageHolder();
                    imageHolder.newsPic = (ImageView) convertView.findViewById(R.id.news_pic);
                    imageHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
                    imageHolder.sendTime = (TextView) convertView.findViewById(R.id.send_time);
                    imageHolder.from = (TextView) convertView.findViewById(R.id.from);
                    convertView.setTag(imageHolder);
                    break;
                case TEXT:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_text_news,parent,false);
                    textHolder = new NewsTextHolder();
                    textHolder.newsTitle = (TextView) convertView.findViewById(R.id.news_title);
                    textHolder.sendTime = (TextView) convertView.findViewById(R.id.send_time);
                    textHolder.from = (TextView) convertView.findViewById(R.id.from);
                    convertView.setTag(textHolder);
                    break;
            }
        }else {
            switch (getItemViewType(position)){
                case IMAGE:
                    imageHolder = (NewsImageHolder) convertView.getTag();
                    break;
                case TEXT:
                    textHolder = (NewsTextHolder) convertView.getTag();
                    break;
            }
        }
        switch (getItemViewType(position)){
            case IMAGE:
                setImage(imageHolder.newsPic,position);
                setTitle(imageHolder.newsTitle,imageHolder.sendTime,imageHolder.from,position);
                break;
            case TEXT:
                setTitle(textHolder.newsTitle,textHolder.sendTime,textHolder.from,position);
                break;
        }
        return convertView;
    }

    /**
     * 设置图片
     */
    private void setImage(ImageView image,int position){
        Glide.with(mContext).load(mData.get(position).getPic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.bg_activities_item_end_transparent).into(image);
    }

    /**
     * 设置标题、发布时间以及来源
     */

    private void setTitle(TextView title,TextView sendTime,TextView from,int position){
        if (!mData.get(position).getTitle().equals(""))title.setText(mData.get(position).getTitle());
        if (!mData.get(position).getTime().equals(""))sendTime.setText(mData.get(position).getTime().substring(0,10));
        if (!mData.get(position).getSrc().equals(""))from.setText("来自:"+mData.get(position).getSrc());
    }

    class NewsImageHolder{
        private ImageView newsPic;
        private TextView newsTitle;
        private TextView sendTime;
        private TextView from;
    }

    class  NewsTextHolder{
        private TextView newsTitle;
        private TextView sendTime;
        private TextView from;
    }
}
