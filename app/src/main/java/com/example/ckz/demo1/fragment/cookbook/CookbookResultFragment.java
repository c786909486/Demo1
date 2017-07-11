package com.example.ckz.demo1.fragment.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookDetileActivity;
import com.example.ckz.demo1.activity.cookbook.CookbookTypeActivity;
import com.example.ckz.demo1.adapter.cookbook.CookbookByTypeAdapter;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.bean.db.CookbookHistoryBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.http.UrlApi;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/19.
 */

public class CookbookResultFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    /**
     * ListUI
     */
    private TwinklingRefreshLayout mRefresh;
    private ListView mList;
    private ProgressBar mLoading;
    private CookbookByTypeAdapter mAdapter;
    /**
     * no data UI
     */
    private RelativeLayout mNoData;
    /**
     * Net Error UI
     */
    private RelativeLayout mNetError;
    private Button mRefreshBtn;


    private String keyword;
    private List<CookbookDataBean.ResultBean.ListBean> mData;
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookbook_result,container,false);
        initView(view);
        hideAll();
        mLoading.setVisibility(View.VISIBLE);
        return view;
    }

    private void initView(View view) {
        //list
        mRefresh = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);
        mRefresh.setEnableLoadmore(false);
        mRefresh.setEnableLoadmore(false);
        mList = (ListView) view.findViewById(R.id.result_list);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
        mAdapter = new CookbookByTypeAdapter(getContext());
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
        //no data
        mNoData = (RelativeLayout) view.findViewById(R.id.no_data_layout);
        //net error
        mNetError = (RelativeLayout) view.findViewById(R.id.net_erro_layout);
        mRefreshBtn = (Button) view.findViewById(R.id.refresh_btn);
        mRefreshBtn.setOnClickListener(this);

        mData = new ArrayList<>();

    }

    private void hideAll(){
        mRefresh.setVisibility(View.GONE);
        mNoData.setVisibility(View.GONE);
        mNetError.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void getSearchResult(){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.CookbookApi.searchCookbook(keyword,20),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                saveKeyword();
                hideAll();
                CookbookDataBean dataBean = JSON.parseObject(responseObj.toString(),CookbookDataBean.class);
                if (dataBean.getMsg().equals("ok")){
                    mRefresh.setVisibility(View.VISIBLE);
                    mAdapter.addAll(dataBean.getResult().getList());
                    mData.addAll(dataBean.getResult().getList());
                }else {
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                hideAll();
                mNetError.setVisibility(View.VISIBLE);
            }
        }));
    }
    private void saveKeyword(){
        List<CookbookHistoryBean> list = DataSupport.where("history = ?",keyword).find(CookbookHistoryBean.class);
        if (list.size() == 0){
           new Thread(new Runnable() {
               @Override
               public void run() {
                   CookbookHistoryBean history = new CookbookHistoryBean();
                   history.setHistory(keyword);
                   history.save();
               }
           }).start();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getSearchResult();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh_btn:
                getSearchResult();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(),CookbookDetileActivity.class);
        intent.putExtra("cookData",mData.get(position));
        startActivity(intent);
    }
}
