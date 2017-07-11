package com.example.ckz.demo1.fragment.cookbook;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.adapter.cookbook.CookbookHistoryAdapter;
import com.example.ckz.demo1.bean.cookbook.CookbookByIdBean;
import com.example.ckz.demo1.bean.db.CookbookHistoryBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.view.NestedListView;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/19.
 */

public class CookbookHistoryFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    /**
     * 标签
     */
    private TagFlowLayout mTag;
    private LinearLayout mTagLayout;
    private TextView mChange;

    private NestedListView mList;
    private TextView mClear;

    private CallBackValue callBackValue;

    private List<CookbookHistoryBean> mData;
    private CookbookHistoryAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookbook_history,container,false);
        initView(view);
        setData();
        return view;
    }

    private void setData(){
        mData = new ArrayList<>();
        mAdapter = new CookbookHistoryAdapter(getContext(),mData);
        mList.setAdapter(mAdapter);
    }

    private void initView(View view) {
        mTag = (TagFlowLayout) view.findViewWithTag(R.id.hot_tag);
        mTagLayout = (LinearLayout) view.findViewById(R.id.tag_layout);
        mChange = (TextView) view.findViewById(R.id.change_another);

        mList = (NestedListView) view.findViewById(R.id.history_list);
        mClear = (TextView) view.findViewById(R.id.clear_all);
        mClear.setOnClickListener(this);
        mChange.setOnClickListener(this);
        mList.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackValue = (CallBackValue) getActivity();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        callBackValue.getMessage(mData.get(position).getHistory());
    }

    //定义一个回掉接口
    public interface CallBackValue{
        public void getMessage(String value);
    }

    private void getHistory(){
        mData.clear();
        List<CookbookHistoryBean> list = DataSupport.findAll(CookbookHistoryBean.class);
        if (list.size() == 0){
            mClear.setVisibility(View.GONE);
        }else {
            mData.addAll(list);
            mClear.setVisibility(View.VISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStart() {
        super.onStart();
        getHistory();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clear_all:
                //清除历史记录
                DataSupport.deleteAll(CookbookHistoryBean.class);
                mData.clear();
                mAdapter.notifyDataSetChanged();
                mClear.setVisibility(View.GONE);
                break;
            case R.id.change_another:
                //换一批标签

                break;
        }

    }
}
