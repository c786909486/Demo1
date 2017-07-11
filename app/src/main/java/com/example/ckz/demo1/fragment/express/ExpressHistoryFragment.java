package com.example.ckz.demo1.fragment.express;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.express.ExpressProgressActivity;
import com.example.ckz.demo1.adapter.CommonAdapter;
import com.example.ckz.demo1.bean.db.ExpressSaveBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.util.ShowPopwindowHelper;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CKZ on 2017/6/24.
 */

public class ExpressHistoryFragment extends BaseFragment implements AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private ListView mList;
    public CommonAdapter<ExpressSaveBean> mAdapter;
    private List<ExpressSaveBean> mData;
    private RelativeLayout mParent;
    private View parentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView  = inflater.inflate(R.layout.fragment_express_history,container,false);
        initView(parentView);
        LogUtils.d("TAG",DataSupport.findAll(ExpressSaveBean.class).size()+"");
        return parentView;
    }

    private void initView(View view) {
        mList = (ListView) view.findViewById(R.id.history_list);
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<ExpressSaveBean>((ArrayList<ExpressSaveBean>) mData,R.layout.item_express_history) {
            @Override
            public void bindView(ViewHolder holder, ExpressSaveBean obj) {
                holder.setText(R.id.company_name,obj.getCompanyName());
                holder.setText(R.id.express_code,obj.getExpressCode());

            }
        };
        mParent = (RelativeLayout) view.findViewById(R.id.parent_content);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
        mList.setOnItemLongClickListener(this);

    }

    private void getLocalData(){
        mData.clear();
        if (state.equals("全部")){
            mData.addAll(DataSupport.findAll(ExpressSaveBean.class));
            mAdapter.notifyDataSetChanged();
        }else if (state.equals("运输中")){
            mData.addAll(DataSupport.where("expressState = ? or expressState = ?","1","2").find(ExpressSaveBean.class));
            mAdapter.notifyDataSetChanged();
        }else if (state.equals("已签收")){
            mData.addAll(DataSupport.where("expressState = ?","3").find(ExpressSaveBean.class));
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        getLocalData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent quIntent = new Intent(getContext(),ExpressProgressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("expressCompany",mData.get(position).getCompanyType());
        bundle.putString("expressCode",mData.get(position).getExpressCode());
        quIntent.putExtra("expressData",bundle);
        startActivity(quIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
        final ShowPopwindowHelper helper = new ShowPopwindowHelper(getContext());
        helper.showBottomPopwindow(R.layout.popwindow_simple,parent);
        helper.setOnClick(R.id.delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(ExpressSaveBean.class,"expressCode = ?",mData.get(position).getExpressCode());
                mData.remove(position);
            }
        });
        helper.setOnClick(R.id.cancle, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.dismiss();
            }
        });
        return true;
    }
}
