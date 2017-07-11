package com.example.ckz.demo1.adapter.cookbook;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.cookbook.CookbookItemBean;
import com.example.ckz.demo1.bean.cookbook.ResultBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/6.
 */

public class CookbookTypeAdapter extends BaseAdapter {
    private List<ResultBean> mData;
    private Context mContext;
    private int  selectItem = 0;

    public  void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    public CookbookTypeAdapter(Context mContext){
        this.mContext = mContext;
        mData = new ArrayList<>();
    }
    public void addAll(List<ResultBean> resultBeen){
        mData.addAll(resultBeen);
        notifyDataSetChanged();
    }

    public List<ResultBean> getList(){
        return mData;
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
        TypeViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_cookbook_type,parent,false);
            holder = new TypeViewHolder();
            holder.typeItem = (TextView) convertView.findViewById(R.id.cook_type_item);
            convertView.setTag(holder);
        }else {
            holder = (TypeViewHolder) convertView.getTag();
        }
        holder.typeItem.setText(mData.get(position).getName());
        if (selectItem == position){
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.cookbook_type_select_bg));
        }else {
            convertView.setBackground(mContext.getResources().getDrawable(R.drawable.cookbook_type_bg));
        }
        return convertView;
    }

    class TypeViewHolder{
        TextView typeItem;
    }

}
