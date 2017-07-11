package com.example.ckz.demo1.activity.cookbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.adapter.cookbook.CookMaterialAdapter;
import com.example.ckz.demo1.adapter.cookbook.CookProgressAadapter;
import com.example.ckz.demo1.bean.cookbook.CookbookByIdBean;
import com.example.ckz.demo1.bean.cookbook.CookbookDataBean;
import com.example.ckz.demo1.bean.db.CookbookSaveBean;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.util.FastBlurUtil;
import com.example.ckz.demo1.util.StatuBarColorUtil;
import com.example.ckz.demo1.view.MyScrollView;
import com.example.ckz.demo1.view.NestedListView;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import org.litepal.crud.DataSupport;

import java.util.List;

public class CookbookDetileActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {
    /**
     * TopUI
     */
    private RelativeLayout mTopLayout;
    private ImageView mTopBack;
    private TextView mTopName;

    /**
     * ScrollUI
     */
    private MyScrollView mScroll;
    private ImageView mBack;
    private RelativeLayout mHeader;
    private TextView mLike;

    //图片、人数、时间、标签、菜名
    private ImageView mBigPic;
    private ImageView mSmallPic;
    private TextView mPeopleNum;
    private TextView  mCookTime;
    private LinearLayout mTagLayout;
    private TextView mShowName;

    //简介
    private TextView mShowContent;
    //所需食材
    private NestedListView mMaterial;
    private CookMaterialAdapter cookMaterialAdapter;
    //过程
    private NestedListView mProgress;
    private CookProgressAadapter progressAadapter;

    /**
     * loading
     */
    private RelativeLayout mLoading;

    /**
     * 获取数据失败
     */
    private RelativeLayout mNoData;
    private Button mRefreshBtn;

    private CookbookDataBean.ResultBean.ListBean listBean;
    private CookbookByIdBean.ResultBean resultBean;

    private String classId;

    private Context mContext;

    private static final int LOCAL = 0;
    private static final int NET = 1;
    private int current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook_detile);
        StatuBarColorUtil.setStatuBarColor(this,R.color.darkWhilte);
        mContext = CookbookDetileActivity.this;
        initView();
        if (getIntent().getSerializableExtra("cookData")!=null){
            listBean = (CookbookDataBean.ResultBean.ListBean) getIntent().getSerializableExtra("cookData");
            classId = listBean.getClassid();
            setUiData(listBean);
            Log.d("from","local");
            current = LOCAL;
            setLikeType(listBean.getName());
        }else {
            classId = getIntent().getStringExtra("classId");
            getNetData(Integer.valueOf(classId));
            Log.d("from",classId);
            current = NET;
        }

    }

    /**
     * 初始化控件
     */
    private void initView() {
        //TopUI
        mTopLayout = (RelativeLayout) findViewById(R.id.top_layout);
        mTopBack = (ImageView) findViewById(R.id.tool_back_btn);
        mTopName = (TextView) findViewById(R.id.cook_name);
        mTopBack.setOnClickListener(this);

        //ScrollUI
        mScroll = (MyScrollView) findViewById(R.id.scroll_view);
        mScroll.smoothScrollBy(0,0);
        mBack = (ImageView) findViewById(R.id.back_btn);
        mHeader = (RelativeLayout) findViewById(R.id.header_layout);
        mLike = (TextView) findViewById(R.id.cookbook_collect);
        setDrawbleTop(mLike,R.drawable.like_select);

        mLike.setOnClickListener(this);

        //图片、人数、时间、标签、菜名
        mBigPic = (ImageView) findViewById(R.id.big_cook_pic);
        mSmallPic = (ImageView) findViewById(R.id.cook_small_pic);
        mPeopleNum = (TextView) findViewById(R.id.people_num);
        mCookTime = (TextView) findViewById(R.id.cook_time);
        mTagLayout = (LinearLayout) findViewById(R.id.tag_layout);
        mShowName = (TextView) findViewById(R.id.cook_show_name);

        //简介
        mShowContent = (TextView) findViewById(R.id.cook_show_content);
        //所需食材
        mMaterial = (NestedListView) findViewById(R.id.material_list);
        cookMaterialAdapter = new CookMaterialAdapter(mContext);
        mMaterial.setAdapter(cookMaterialAdapter);
        //过程
        mProgress = (NestedListView) findViewById(R.id.cook_progress);
        progressAadapter = new CookProgressAadapter(mContext);
        mProgress.setAdapter(progressAadapter);
        mProgress.setOnItemClickListener(this);

        mBack.setOnClickListener(this);
        setScroll();

        //loading
        mLoading = (RelativeLayout) findViewById(R.id.loading_layout);

        //no data
        mNoData = (RelativeLayout) findViewById(R.id.no_data_layout);
        mRefreshBtn = (Button) findViewById(R.id.refresh_btn);
        mRefreshBtn.setOnClickListener(this);
    }

    private static final int NO_COLLECT = 0;
    private static final int COLLECT = 1;
    private int currentState;

    private void setLikeType(String name) {
        List<CookbookSaveBean> saveList = DataSupport.where("cookName = ?",name).find(CookbookSaveBean.class);
        if (saveList.size()==0){
            currentState = NO_COLLECT;
            mLike.setText("点击收藏");
            mLike.setSelected(false);
        }else {
            currentState = COLLECT;
            mLike.setText("取消收藏");
            mLike.setSelected(true);
        }
    }


    /**
     * 获取网络数据
     */
    private void getNetData(int classId) {
        Log.d("url",UrlApi.CookbookApi.getCookbookById(classId));
        mLoading.setVisibility(View.VISIBLE);
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.CookbookApi.getCookbookById(classId),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {

                CookbookByIdBean data = JSON.parseObject(responseObj.toString(),CookbookByIdBean.class);
                resultBean = data.getResult();
                setNetUiData(resultBean);
                setLikeType(resultBean.getName());
                mScroll.setVisibility(View.VISIBLE);
                mLoading.setVisibility(View.GONE);
                mNoData.setVisibility(View.GONE);
                mScroll.smoothScrollBy(0,0);
            }

            @Override
            public void onFailure(Object reasonObj) {
                mLoading.setVisibility(View.GONE);
                mScroll.setVisibility(View.GONE);
                mNoData.setVisibility(View.VISIBLE);

            }
        }));
    }
    private void setNetUiData(CookbookByIdBean.ResultBean data){
        //顶部内容
        setHeaderData(data.getPic(),data.getPeoplenum(),data.getCookingtime(),data.getTag(),data.getName());
        //topLayout内容
        setTopData(data.getName());
        //正文内容
        setContentData(data.getContent());
//        CookbookDataBean.ResultBean.ListBean bean = new CookbookDataBean.ResultBean.ListBean();

        cookMaterialAdapter.addAllNet(data.getMaterial());

        progressAadapter.addAllNet(data.getProcess());
        Log.d("SIZE",data.getProcess().size()+"");

    }

    private void setUiData(CookbookDataBean.ResultBean.ListBean data){
        //顶部内容
        setHeaderData(data.getPic(),data.getPeoplenum(),data.getCookingtime(),data.getTag(),data.getName());
        //topLayout内容
        setTopData(data.getName());
        //正文内容
        setContentData(data.getContent());

        cookMaterialAdapter.addALL(data.getMaterial());

        progressAadapter.addAll(data.getProcess());
        Log.d("SIZE",data.getProcess().size()+"");

    }
    /**
     * 设置图片大小
     */
    private void setDrawbleTop(TextView textView, int resId){
        float density = getResources().getDisplayMetrics().density;
        int size = (int) (25*density);
        Rect rect = new Rect();
        rect.set(0,0,size,size);
        Drawable drawable = getResources().getDrawable(resId);
        drawable.setBounds(rect);
        textView.setCompoundDrawables(null,drawable,null,null);
    }

    /**
     * 设置正文内容
     */
    private void setContentData(String content){
        mShowContent.setText(Html.fromHtml(content));
    }


    /**
     * topLayout内容
     */
    private void setTopData(String name){
        mTopName.setText(name);
    }

    /**
     * 设置图片、人数、时间、以及标签、菜名
     */
    @SuppressLint("SetTextI18n")
    private void setHeaderData(String picUrl,String propleNum,String cookTime,String tags,String name){
        //图片
        Glide.with(mContext).load(picUrl).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).dontAnimate().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                mSmallPic.setImageBitmap(bitmap);
                Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap,3);
                mBigPic.setImageBitmap(blurBitmap);
            }
        });
        //人数
        mPeopleNum.setText("适宜人数：\n"+propleNum);
        //时间
        mCookTime.setText("烹饪时间：\n"+cookTime);
        //标签
        String[] array = tags.split(",");
        if (array.length>3){
            for (int i=0;i<=3;i++){
                TextView textView = new TextView(mContext);
                textView.setPadding(5,3,5,3);
                textView.setText(array[i]);
                mTagLayout.addView(textView);
            }
        }else {
            for (String tag : array){
                TextView textView = new TextView(mContext);
                textView.setPadding(5,3,5,3);
                textView.setText(tag);
                mTagLayout.addView(textView);
            }
        }
        //菜名
        mShowName.setText(name);
    }

    /**
     * 设置scrollview滚动显示toolbar
     */

    private void setScroll(){
        mScroll.setOnScrollChanged(new MyScrollView.OnScrollChanged() {
            @Override
            public void onScroll(int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                int height = mHeader.getHeight();
                float pf = 0f;
                scrollY = scrollY - 100;
                if ((oldScrollY - scrollY) < 0) {

                    float f = (scrollY + 0f) / height;
                    if (Math.abs((f - pf)) >= 0.01) {
                        if (f > 1) {
                            f = 1f;
                            mTopLayout.setVisibility(View.VISIBLE);

                        }
                        if (f < 0) {
                            f = 0;
                            mTopLayout.setVisibility(View.GONE);

                        }
                        mTopLayout.setAlpha((f) * 1);  //void android.view.View.setAlpha(float alpha)只能是0~1的小数
                        pf = f;
                    }
                } else if ((oldScrollY - scrollY) > 0) {
                    float f = (scrollY + 0f) / height;
                    if (Math.abs((f - pf)) >= 0.01) {
                        if (f > 1) {
                            f = 1f;
                            mTopLayout.setVisibility(View.VISIBLE);

                        }
                        if (f < 0) {
                            f = 0;
                            mTopLayout.setVisibility(View.GONE);

                        }
                        mTopBack.setAlpha((f) * 1);
                        pf = f;
                    }
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tool_back_btn:
                finish();
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.refresh_btn:
                getNetData(Integer.valueOf(classId));
                break;
            case R.id.cookbook_collect:
                if (currentState == NO_COLLECT){
                    mLike.setSelected(true);
                    mLike.setText("取消收藏");
                    CookbookSaveBean bean = new CookbookSaveBean();
                    if (current == 0){
                        bean.setCookName(listBean.getName());
                        bean.setCookId(listBean.getId());
                        bean.setPicUrl(listBean.getPic());
                        bean.setTag(listBean.getTag());
                    }else {
                        bean.setCookName(resultBean.getName());
                        bean.setCookId(resultBean.getId());
                        bean.setPicUrl(resultBean.getPic());
                        bean.setTag(resultBean.getTag());
                    }
                    bean.save();
                    currentState = COLLECT;
                }else {
                    mLike.setSelected(false);
                    mLike.setText("点击收藏");
                    if (current == 0){
                        DataSupport.deleteAll(CookbookSaveBean.class,"cookName = ?",listBean.getName());
                    }else {
                        DataSupport.deleteAll(CookbookSaveBean.class,"cookName = ?",resultBean.getName());
                    }
                    currentState = NO_COLLECT;

                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext,ShowCookProgressActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        if (current == LOCAL){
            bundle.putSerializable("progressData",listBean);
        }else {
            bundle.putSerializable("progressData",resultBean);
        }
        bundle.putInt("current",current);

        intent.putExtra("progress",bundle);
        startActivity(intent);
    }
}
