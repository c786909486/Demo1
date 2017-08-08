package com.example.ckz.demo1.fragment.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.news.MainActivity;
import com.example.ckz.demo1.activity.news.NewsDetilActivity;
import com.example.ckz.demo1.activity.user.LoginAcrivity;
import com.example.ckz.demo1.adapter.news.NewsCollectionAdapter;
import com.example.ckz.demo1.bean.news.NewsBean;

import com.example.ckz.demo1.bean.user.news.CommentNews;
import com.example.ckz.demo1.bean.user.news.NewsSaveNetBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.DataChangeUtil;
import com.example.ckz.demo1.view.LoadListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by CKZ on 2017/5/24.
 */

public class NewsCollectionFragment extends BaseFragment implements View.OnClickListener,AdapterView.OnItemClickListener{
    private ImageView mCenterOut;
    private ImageView mDeleteAll;
    private LoadListView mNewsList;
    private SwipeRefreshLayout mRefresh;

    private List<NewsSaveNetBean> mData;
    private NewsCollectionAdapter mAdapter;

    private int num = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_collection,container,false);
        initView(view);
        setData();
        return view;
    }

    private void initView(View view) {
        mCenterOut = (ImageView) view.findViewById(R.id.center_out);
        mDeleteAll = (ImageView) view.findViewById(R.id.delete_all);
        mNewsList = (LoadListView) view.findViewById(R.id.news_list_view);
        mRefresh = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        mCenterOut.setOnClickListener(this);
        mDeleteAll.setOnClickListener(this);
        mNewsList.setOnItemClickListener(this);

        mRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNetData(true);
            }
        });

        mNewsList.setOnLoadListener(new LoadListView.ILoadListener() {
            @Override
            public void onLoad() {
                getNetData(false);
            }
        });
    }

    private void setData(){
        mData = new ArrayList<>();
        mAdapter = new NewsCollectionAdapter(getContext(),mData);
        mNewsList.setAdapter(mAdapter);
    }

    private void getNetData(boolean isRefresh){
        BmobQuery<NewsSaveNetBean> query = new BmobQuery<NewsSaveNetBean>();
        if (isRefresh){
            query.addWhereEqualTo("userPhone", BmobUser.getCurrentUser().getMobilePhoneNumber()).order("-createdAt")
                    .setLimit(10).setSkip(0)
                    .findObjects(new FindListener<NewsSaveNetBean>() {
                        @Override
                        public void done(List<NewsSaveNetBean> list, BmobException e) {
                            if (e == null){
                                list.removeAll(mData);
                                mData.addAll(list);
                                mAdapter.notifyDataSetChanged();
                                mRefresh.setRefreshing(false);
                            }else {
                                Toast.makeText(getContext(),"获取数据失败，请检查网络",Toast.LENGTH_SHORT).show();
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
                                mNewsList.loadComplete();
                            }else {
                                Toast.makeText(getContext(),"获取数据失败，请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.center_out:
                MainActivity.mDrawer.openDrawer(GravityCompat.START);
                break;
            case R.id.delete_all:
                //清空
                break;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (MyUserModule.getCurrentUser()!=null){
            getNetData(true);
        }else {
            startActivity(new Intent(getContext(), LoginAcrivity.class));
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
