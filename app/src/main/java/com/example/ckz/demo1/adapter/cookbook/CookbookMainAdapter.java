package com.example.ckz.demo1.adapter.cookbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookTypeActivity;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/2.
 */

public class CookbookMainAdapter extends BaseAdapter {
    private List<CookbookDataBean> mData;
    private Context mContext;
    private LayoutInflater inflater;

    public CookbookMainAdapter(Context mContext,List<CookbookDataBean> mData){
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = inflater.inflate(R.layout.item_cookbook_main,parent,false);
            holder = new ViewHolder();
            holder.mType = (TextView) convertView.findViewById(R.id.type_title);
            holder.mSearchMore = (TextView) convertView.findViewById(R.id.search_more);
            holder.mShowItem = (RecyclerView) convertView.findViewById(R.id.show_cook);
            holder.mShowItem.setLayoutManager(new GridLayoutManager(mContext,2));
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        setType(holder.mType,position);
        setIntent(holder.mSearchMore,position);
        setItemData(holder.mShowItem,position);
        return convertView;
    }

    /**
     * 设置类别
     *
     */
    private void setType(TextView type,int position){
        String id = mData.get(position).getResult().getList().get(0).getClassid();
        if (id.equals("224")) type.setText("川菜");
        if (id.equals("225")) type.setText("湘菜");
        if (id.equals("226")) type.setText("粤菜");
        if (id.equals("228")) type.setText("浙菜");
    }
    /**
     * 设置更多点击跳转
     */
    private void setIntent(TextView more, final int position){
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CookbookTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("classId",mData.get(position).getResult().getList().get(0).getClassid());
                intent.putExtra("cookData",bundle);
                mContext.startActivity(intent);
            }
        });
    }

    /**
     * 设置recyclerview
     */
    private void setItemData(RecyclerView recyclerView,int position){
//        int space = 16;
//        recyclerView.addItemDecoration(new SpacesItemDecoration(space));
        recyclerView.setNestedScrollingEnabled(false);
        CookbookItemAdapter adapter = new CookbookItemAdapter(mContext);
        recyclerView.setAdapter(adapter);
        adapter.addAll(mData.get(position).getResult().getList());

    }
    class ViewHolder{
        TextView mType;
        TextView mSearchMore;
        RecyclerView mShowItem;
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;

        public SpacesItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;

            // Add top margin only for the first item to avoid double space between items
            if (parent.getChildPosition(view) %2== 0)
                outRect.left = space;
        }
    }
}
