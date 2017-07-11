package com.example.ckz.demo1.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.adapter.news.NewsCollectionAdapter;
import com.example.ckz.demo1.bean.user.NewsSaveNetBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
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

public class UserNewsCollectFragment extends BaseFragment {
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
        return view;
    }

    private void initView(View view) {
        mRefresh = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);
        mList = (ListView) view.findViewById(R.id.news_list);
    }
    private void setData(){
        mData = new ArrayList<>();
        mAdapter = new NewsCollectionAdapter(getContext(),mData);
        mList.setAdapter(mAdapter);
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
                                mRefresh.finishRefreshing();
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
                                mRefresh.finishLoadmore();
                            }else {
                                Toast.makeText(getContext(),"获取数据失败，请检查网络",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}
