package com.example.ckz.demo1.adapter.express;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.express.CompanyResultBean;

import java.util.List;

/**
 * Created by CKZ on 2017/6/22.
 */

public class CompanyAdapter extends BaseAdapter implements SectionIndexer {
    private List<CompanyResultBean> list = null;
    private Context mContext;

    public CompanyAdapter(Context mContext, List<CompanyResultBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        ItemViewHolder holder ;
        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_company,parent,false);
            holder = new ItemViewHolder();
            holder.mLetter = (TextView) view.findViewById(R.id.company_letter);
            holder.mName = (TextView) view.findViewById(R.id.company_name);
            holder.mTel = (TextView) view.findViewById(R.id.company_tel);
            view.setTag(holder);
        }else {
            holder = (ItemViewHolder) view.getTag();
        }
        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if(position == getPositionForSection(section)){
            holder.mLetter.setVisibility(View.VISIBLE);
            holder.mLetter.setText(list.get(position).getLetter());
        }else{
            holder.mLetter.setVisibility(View.GONE);
        }
        holder.mName.setText(list.get(position).getName()+"（"+list.get(position).getType()+"）");
        holder.mTel.setText(list.get(position).getTel());
        return view;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getLetter().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }


    @Override
    public Object[] getSections() {
        return null;
    }

    class ItemViewHolder{
        TextView mName;
        TextView mTel;
        TextView mLetter;
    }
}
