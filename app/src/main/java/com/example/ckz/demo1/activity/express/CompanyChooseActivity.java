package com.example.ckz.demo1.activity.express;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.adapter.express.CompanyAdapter;
import com.example.ckz.demo1.bean.express.CompanyResultBean;
import com.example.ckz.demo1.util.StatuBarColorUtil;
import com.example.ckz.demo1.view.SideBar;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class CompanyChooseActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private SideBar mSideBar;
    private TextView mShowSide;
    private ListView mList;
    private CompanyAdapter mAdapter;
    private List<CompanyResultBean> mData;

    private int dataSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_choose);
        StatuBarColorUtil.setStatuBarColor(this,R.color.blue_dark);
        initView();

//        dataSize = getIntent().getIntExtra("dataSize",0);
//        Toast.makeText(this,DataSupport.findAll(CompanyResultBean.class).size()+"",Toast.LENGTH_SHORT).show();

    }

    private void initView() {
        mShowSide = (TextView) findViewById(R.id.show_side);
        mShowSide.setVisibility(View.GONE);
        mSideBar = (SideBar) findViewById(R.id.side_bar);
        mList = (ListView) findViewById(R.id.company_list);
        mData = new ArrayList<>();
        mAdapter = new CompanyAdapter(this,mData);
        mList.setAdapter(mAdapter);
        setShowText();
        setData();
        setClick();
        mList.setOnItemClickListener(this);
    }

    private void setData(){
        mData.addAll(DataSupport.findAll(CompanyResultBean.class));
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 设置点击
     */
    private void setClick(){
        findViewById(R.id.back_btn).setOnClickListener(this);
    }

    /**
     * 1、点击sidebar显示文字
     * 2、滑动列表
     */
    private void setShowText(){
       mSideBar.setOnTouchLetterChangeListenner(new SideBar.OnTouchLetterChangeListenner() {
           @Override
           public void onTouchLetterChange(boolean isTouched, String s) {
               if (isTouched){
                   mShowSide.setVisibility(View.VISIBLE);
                   mShowSide.setText(s);
                   int position = mAdapter.getPositionForSection(s.charAt(0));
                   if(position != -1){
                       mList.setSelection(position);
                   }
               }else {
                   mShowSide.setVisibility(View.GONE);
               }
           }
       });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CompanyResultBean bean = (CompanyResultBean) mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.putExtra("companyData",bean);
        setResult(RESULT_OK,intent);
        finish();
    }
}
