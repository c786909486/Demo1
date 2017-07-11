package com.example.ckz.demo1.activity.cookbook;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.adapter.pagerAdapter.CookProgressPagerAdapter;
import com.example.ckz.demo1.bean.cookbook.CookbookByIdBean;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.util.StatuBarColorUtil;

public class ShowCookProgressActivity extends BaseActivity implements View.OnClickListener {
    private static final int VISIABLE = 0;
    private static final int GONE = 1;
    private int currentState = VISIABLE;

    private int current;
    /**
     * topUI
     */
    private RelativeLayout mTop;
    private ImageView mBack;

    /**
     * bottomUI
     */
    private RelativeLayout mBottom;
    private TextView mProgressText;

    private ViewPager mPager;
    private CookProgressPagerAdapter mAdapter;

    private int position;
    private CookbookDataBean.ResultBean.ListBean listBean;
    private CookbookByIdBean.ResultBean resultBean;

    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cook_progress);
        mContext = ShowCookProgressActivity.this;
        StatuBarColorUtil.setStatuBarColor(this, R.color.black);
        getData();
        initView();
    }

    private void getData() {
        Bundle bundle = getIntent().getBundleExtra("progress");
        position = bundle.getInt("position");
        current = bundle.getInt("current");
        if (current == 0){
            listBean = (CookbookDataBean.ResultBean.ListBean) bundle.getSerializable("progressData");
        }else {
            resultBean = (CookbookByIdBean.ResultBean) bundle.getSerializable("progressData");
        }

    }

    private void initView() {
        mTop = (RelativeLayout) findViewById(R.id.top_layout);
        mBack = (ImageView) findViewById(R.id.back_btn);
        mBack.setOnClickListener(this);

        mBottom = (RelativeLayout) findViewById(R.id.bottom_layout);
        mProgressText = (TextView) findViewById(R.id.progress_text);

        mPager = (ViewPager) findViewById(R.id.progress_view_pager);
        mAdapter = new CookProgressPagerAdapter(this);
        mPager.setAdapter(mAdapter);
        if (current == 0){
            mAdapter.addAll(listBean.getProcess());
        }else {
            mAdapter.addAllNet(resultBean.getProcess());
        }

        mPager.setCurrentItem(position);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               if (current == 0) {
                   mProgressText.setText(Html.fromHtml(listBean.getProcess().get(position).getPcontent()));
               }else {
                   mProgressText.setText(Html.fromHtml(resultBean.getProcess().get(position).getPcontent()));
               }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mAdapter.setOnItemClickListener(new CookProgressPagerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               setVisiable();
            }
        });

    }

    private void setVisiable(){
        if (currentState == VISIABLE){
            mTop.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.top_out));
            mBottom.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.bottom_out));
            mTop.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mTop.setVisibility(View.GONE);
                }
            },1000);
            mBottom.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBottom.setVisibility(View.GONE);
                }
            },1000);
            currentState = GONE;
        }else {
            mTop.setVisibility(View.VISIBLE);
            mBottom.setVisibility(View.VISIBLE);
            mTop.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.top_in));
            mBottom.startAnimation(AnimationUtils.loadAnimation(mContext,R.anim.bottom_in));
            currentState = VISIABLE;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
