package com.example.ckz.demo1.adapter.news;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.news.NewsBean;

import java.util.List;

/**
 * Created by CKZ on 2017/5/16.
 */

public class NewsContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int IMAGE = 0;
    private final int TEXT = 1;
    private List<NewsBean.ResultBean.ListBean> mData;
    private Context mContext;
    private LayoutInflater inflater;
    public NewsContentAdapter(Context mContext, List<NewsBean.ResultBean.ListBean> mData){
        this.mContext = mContext;
        this.mData = mData;
        inflater = LayoutInflater.from(mContext);
    }

    /**
     * item点击监听
     */
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

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position).getPic().equals("")){
            return TEXT;
        }else {
            return IMAGE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case IMAGE:
                view = inflater.inflate(R.layout.item_image_news,parent,false);
                holder = new NewsImageViewHolder(view);
                break;
            case TEXT:
                view = inflater.inflate(R.layout.item_text_news,parent,false);
                holder = new NewsTextViewHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case IMAGE:
                NewsImageViewHolder imageHolder = (NewsImageViewHolder) holder;
                setImage(imageHolder.newsPic,position);
                setTitle(imageHolder.newsTitle,imageHolder.sendTime,imageHolder.from,position);
                break;
            case TEXT:
                NewsTextViewHolder textHolder = (NewsTextViewHolder) holder;
                setTitle(textHolder.newsTitle,textHolder.sendTime,textHolder.from,position);
                break;
        }
        if (holder.itemView!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView,position);
                }
            });

        }

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

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class NewsImageViewHolder extends RecyclerView.ViewHolder{
        private ImageView newsPic;
        private TextView newsTitle;
        private TextView sendTime;
        private TextView from;

        public NewsImageViewHolder(View itemView) {
            super(itemView);
            newsPic = (ImageView) itemView.findViewById(R.id.news_pic);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            sendTime = (TextView) itemView.findViewById(R.id.send_time);
            from = (TextView) itemView.findViewById(R.id.from);

        }
    }

    class NewsTextViewHolder extends RecyclerView.ViewHolder{
        private TextView newsTitle;
        private TextView sendTime;
        private TextView from;
        public NewsTextViewHolder(View itemView) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.news_title);
            sendTime = (TextView) itemView.findViewById(R.id.send_time);
            from = (TextView) itemView.findViewById(R.id.from);
        }
    }
}
