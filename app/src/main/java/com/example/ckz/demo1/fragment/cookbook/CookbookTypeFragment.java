package com.example.ckz.demo1.fragment.cookbook;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.adapter.cookbook.CookbookTypeAdapter;
import com.example.ckz.demo1.bean.cookbook.CookbookItemBean;
import com.example.ckz.demo1.bean.cookbook.ListBean;
import com.example.ckz.demo1.bean.cookbook.ResultBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.http.UrlApi;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by CKZ on 2017/6/6.
 */

public class CookbookTypeFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ListView mTypeList;
    private LinearLayout mRoot;
    private ProgressBar mLoading;
    private LinearLayout mNoData;
    private Button mRefreshBtn;
    private CookbookTypeAdapter mAdapter;

    private FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookbook_type,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        fm = getChildFragmentManager();
        mTypeList = (ListView) view.findViewById(R.id.type_list);
        mRoot = (LinearLayout) view.findViewById(R.id.root_layout);
        mLoading = (ProgressBar) view.findViewById(R.id.loading);
        mNoData = (LinearLayout) view.findViewById(R.id.no_data);
        mRefreshBtn = (Button) view.findViewById(R.id.refresh_btn);
        mRefreshBtn.setOnClickListener(this);
        mAdapter = new CookbookTypeAdapter(getContext());
        mTypeList.setAdapter(mAdapter);
        mTypeList.setOnItemClickListener(this);
        getTypeData();


    }

    private void reset(){
        mNoData.setVisibility(View.GONE);
        mRoot.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
    }

    private void getTypeData(){
        List<ResultBean> mData = DataSupport.findAll(ResultBean.class);
        if (mData.size()!=0){
            reset();
            mRoot.setVisibility(View.VISIBLE);
            mAdapter.addAll(mData);
            mTypeList.performItemClick(mTypeList.getChildAt(0),0,mTypeList.getItemIdAtPosition(0));
            Log.d("dataFrom","local");
            Log.d("tag",DataSupport.findAll(ListBean.class).size()+"");
        }else {
            getNetData();
            Log.d("dataFrom","net");
        }

    }

    private void getNetData() {
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.CookbookApi.getItem(),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CookbookItemBean bean = JSON.parseObject(responseObj.toString(),CookbookItemBean.class);
                final List<ResultBean> resultList = bean.getResult();
                mAdapter.addAll(resultList);
                reset();
                mRoot.setVisibility(View.VISIBLE);
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       for (ResultBean result :resultList){
                           result.save();
                           for (ListBean list : result.getList()){
                               list.save();
                           }
                       }
                   }
               }).start();
                mTypeList.performItemClick(mTypeList.getChildAt(0),0,mTypeList.getItemIdAtPosition(0));
            }

            @Override
            public void onFailure(Object reasonObj) {
                reset();
                mNoData.setVisibility(View.VISIBLE);
            }
        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.refresh_btn:
                reset();
                mLoading.setVisibility(View.VISIBLE);
                getNetData();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.setSelectItem(position);
        mAdapter.notifyDataSetInvalidated();
        CookbookSubTypeFragment fragment = new CookbookSubTypeFragment();
        if (mAdapter.getList().get(0).getList() !=null){
           // Log.d("test",mAdapter.getList().get(0).getList().size()+"");
            fragment.setmData(mAdapter.getList().get(position).getList());
        }else {
            List<ListBean> data = DataSupport.where("parentid = ?",mAdapter.getList().get(position).getClassid()).find(ListBean.class);
            fragment.setmData(data);
            Log.d("test",data.size()+"");
        }
        fm.beginTransaction().replace(R.id.sub_type_fragment,fragment).commit();

    }
}
