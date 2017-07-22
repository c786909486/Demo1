package com.example.ckz.demo1.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.news.NewsDetilActivity;
import com.example.ckz.demo1.adapter.news.NewsCollectionAdapter;
import com.example.ckz.demo1.bean.user.news.CommentNews;
import com.example.ckz.demo1.bean.user.news.NewsSaveNetBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.interfaces.OnRefreshSucess;
import com.example.ckz.demo1.util.DataChangeUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by CKZ on 2017/7/7.
 */

public class UserNewsCollectFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    private TwinklingRefreshLayout mRefresh;
    private ListView mList;

    private List<NewsSaveNetBean> mData;
    private NewsCollectionAdapter mAdapter;

    private int num = 10;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_news_data,container,false);
        initView(view);
        setData();
        return view;
    }

    private void initView(View view) {
        mRefresh = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);
        mList = (ListView) view.findViewById(R.id.news_list);
        mList.setOnItemClickListener(this);
    }
    private void setData(){
        mData = new ArrayList<>();
        mAdapter = new NewsCollectionAdapter(getContext(),mData);
        mList.setAdapter(mAdapter);
        mRefresh.setEnableRefresh(false);
        getNetData(true);
        mRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getNetData(true);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
               getNetData(false);
            }
        });

    }



    private OnRefreshSucess mSucess;

    public void setOnRefreshSucessListener(OnRefreshSucess mSucess){
        this.mSucess = mSucess;
    }

    private void getNetData(boolean isRefresh){
        BmobQuery<NewsSaveNetBean> query = new BmobQuery<NewsSaveNetBean>();
        if (isRefresh){
            mSucess.onRefreshStaet();
            query.addWhereEqualTo("userPhone", BmobUser.getCurrentUser().getMobilePhoneNumber()).order("-createdAt")
                    .setLimit(10).setSkip(0)
                    .findObjects(new FindListener<NewsSaveNetBean>() {
                        @Override
                        public void done(List<NewsSaveNetBean> list, BmobException e) {
                            if (e == null){
                                list.removeAll(mData);
                                mData.addAll(list);
                                mAdapter.notifyDataSetChanged();
                                mRefresh.finishRefreshing();
                                mSucess.onRefreshSucess(true);
                            }else {
                                Toast.makeText(getContext(),"获取数据失败，请检查网络",Toast.LENGTH_SHORT).show();
                                mSucess.onRefreshSucess(false);
                            }
                        }
                    });
        }else {
            query.addWhereEqualTo("userPhone", BmobUser.getCurrentUser().getMobilePhoneNumber()).order("-createdAt")
                    .setLimit(10).setSkip(num)
                    .findObjects(new FindListener<NewsSaveNetBean>() {
                        @Override
                        public void done(List<NewsSaveNetBean> list, BmobException e) {
                            if (e == null){
                                list.removeAll(mData);
                                mData.addAll(list);
                                mAdapter.notifyDataSetChanged();
                                num+=10;
                                mRefresh.finishLoadmore();
                            }else {
                                Toast.makeText(getContext(),"获取数据失败，请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CommentNews listBean = DataChangeUtil.save2news(mData.get(position));

        Intent intent = new Intent(getContext(), NewsDetilActivity.class);
        intent.putExtra("NewsData",listBean);
        startActivity(intent);
    }
}
