package com.example.ckz.demo1.adapter.cookbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.cookbook.CookbookByIdBean;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/11.
 */

public class CookMaterialAdapter extends BaseAdapter {
    private static final int LOCALDATA = 0;
    private static final int NETDATA = 1;
    private List<Object> mData;
    private Context mContext;

    public CookMaterialAdapter(Context mContext){
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) instanceof CookbookDataBean.ResultBean.ListBean.MaterialBean){
            return LOCALDATA;
        }else {
            return NETDATA;
        }
    }

    public void addALL(List<CookbookDataBean.ResultBean.ListBean.MaterialBean> data){
        mData.addAll(data);
        notifyDataSetChanged();
    }
    public void addAllNet(List<CookbookByIdBean.ResultBean.MaterialBean> data){
        mData.addAll(data);
        notifyDataSetChanged();
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
        MaterialViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_material,parent,false);
            holder = new MaterialViewHolder();
            holder.materialName = (TextView) convertView.findViewById(R.id.material_name);
            holder.materialNum = (TextView) convertView.findViewById(R.id.material_num);
            convertView.setTag(holder);
        }else {
            holder = (MaterialViewHolder) convertView.getTag();
        }
        switch (getItemViewType(position)){
            case LOCALDATA:
                CookbookDataBean.ResultBean.ListBean.MaterialBean data = (CookbookDataBean.ResultBean.ListBean.MaterialBean) mData.get(position);
                holder.materialName.setText(data.getMname());
                holder.materialNum.setText(data.getAmount());
                break;
            case NETDATA:
                CookbookByIdBean.ResultBean.MaterialBean data1 = (CookbookByIdBean.ResultBean.MaterialBean) mData.get(position);
                holder.materialName.setText(data1.getMname());
                holder.materialNum.setText(data1.getAmount());
                break;
        }
        return convertView;
    }



    class MaterialViewHolder{
        TextView materialName;
        TextView materialNum;
    }
}
