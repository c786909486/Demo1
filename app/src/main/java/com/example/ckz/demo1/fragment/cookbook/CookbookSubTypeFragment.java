package com.example.ckz.demo1.fragment.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.cookbook.CookbookTypeActivity;
import com.example.ckz.demo1.bean.cookbook.ListBean;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * Created by CKZ on 2017/6/7.
 */

public class CookbookSubTypeFragment extends BaseFragment {
    private TagFlowLayout mContainer;

    private List<ListBean> mData;

    private String classId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cookbook_sub_type,container,false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mContainer = (TagFlowLayout) view.findViewById(R.id.container);
        mContainer.setAdapter(new TagAdapter<ListBean>(mData) {
            @Override
            public View getView(FlowLayout parent, int position, ListBean listBean) {
                View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_cookbook_sub_type,mContainer,false);
                TextView textView = (TextView) itemView.findViewById(R.id.sub_tag);
                textView.setText(mData.get(position).getName());
                return itemView;
            }
        });
        mContainer.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(getContext(), CookbookTypeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("classId",mData.get(position).getClassid());
                bundle.putString("typeName",mData.get(position).getName());
                intent.putExtra("cookData",bundle);
                startActivity(intent);
                return true;
            }
        });

    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setmData(List<ListBean> mData) {
        this.mData = mData;
    }
}
