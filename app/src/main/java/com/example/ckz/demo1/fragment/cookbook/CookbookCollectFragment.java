package com.example.ckz.demo1.fragment.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookDetileActivity;
import com.example.ckz.demo1.adapter.cookbook.CookbookCollectAdapter;
import com.example.ckz.demo1.bean.db.CookbookSaveBean;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/14.
 */

public class CookbookCollectFragment extends Fragment implements AdapterView.OnItemClickListener{
    private ListView mCookList;
    private List<CookbookSaveBean> mData;
    private CookbookCollectAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookbook_collect,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mData = new ArrayList<>();
        mCookList = (ListView) view.findViewById(R.id.cook_list);
        mAdapter = new CookbookCollectAdapter(getContext(),mData);
        mCookList.setAdapter(mAdapter);
        mCookList.setOnItemClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        mData.clear();
        mData.addAll(DataSupport.findAll(CookbookSaveBean.class));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), CookbookDetileActivity.class);
        intent.putExtra("classId",mData.get(position).getCookId());
        startActivity(intent);

    }
}
