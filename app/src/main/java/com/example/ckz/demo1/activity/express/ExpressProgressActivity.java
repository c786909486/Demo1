package com.example.ckz.demo1.activity.express;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.adapter.express.ExpressProgressAdapter;
import com.example.ckz.demo1.bean.db.ExpressSaveBean;
import com.example.ckz.demo1.bean.express.CompanyResultBean;
import com.example.ckz.demo1.bean.express.ExpressProgressBean;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.util.MyTimeUtils;
import com.example.ckz.demo1.util.StatuBarColorUtil;
import com.example.ckz.demo1.view.NestedListView;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import org.litepal.crud.DataSupport;

import java.util.List;

public class ExpressProgressActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "ExpressProgressActivity";
    private String url;
    private String expressCode;
    private String expressCompany;

    private String companyName;

    /**
     * query UI
     */
    private RelativeLayout mQueryLayout;
    private TwinklingRefreshLayout mRefresh;
    private TextView mName;
    private TextView mCode;
    private NestedListView mList;
    private ExpressProgressAdapter mAdapter;
    private TextView mState;

    /**
     * loading
     */
    private RelativeLayout mLoadingLayout;

    /**
     * no data ui
     */
    private RelativeLayout mNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_progress);
        StatuBarColorUtil.setStatuBarColor(this,R.color.blue_dark);
        getIntentData();
        initView();
        hideAll();
        mLoadingLayout.setVisibility(View.VISIBLE);
        setClick();
    }

    private void initView() {
        //query ui
        mName = (TextView) findViewById(R.id.company_name);
        mCode = (TextView) findViewById(R.id.express_code);
        mList = (NestedListView) findViewById(R.id.progress_list);
        mRefresh = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        mQueryLayout = (RelativeLayout) findViewById(R.id.query_layout);
        mState = (TextView) findViewById(R.id.express_state);
        //loading ui
        mLoadingLayout = (RelativeLayout) findViewById(R.id.loading_layout);
        //no data
        mNoData = (RelativeLayout) findViewById(R.id.no_data_layout);

        mAdapter = new ExpressProgressAdapter(this);
        mList.setAdapter(mAdapter);
    }

    private void getExpressData(){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                ExpressProgressBean bean = JSON.parseObject(responseObj.toString(),ExpressProgressBean.class);
                if (bean.getMsg().equals("ok")){
                    hideAll();
                    mQueryLayout.setVisibility(View.VISIBLE);
                    mAdapter.addAll(bean.getResult().getList());
                    setUI(bean.getResult().getNumber(),bean.getResult().getType());
                    setState(bean.getResult().getDeliverystatus());
                    if (DataSupport.where("expressCode = ?",bean.getResult().getNumber()).find(ExpressSaveBean.class).size() == 0){
                        saveExpress(bean);
                    }
                }else {
                    hideAll();
                    mNoData.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                hideAll();
                mNoData.setVisibility(View.VISIBLE);
            }
        }));
    }

    /**
     * 保存记录，用于本地查询
     */
    private void saveExpress(ExpressProgressBean bean){
        ExpressSaveBean saveBean = new ExpressSaveBean();
        saveBean.setCompanyName(companyName);
        saveBean.setCompanyType(bean.getResult().getType());
        saveBean.setExpressCode(bean.getResult().getNumber());
        saveBean.setExpressState(bean.getResult().getDeliverystatus());
        saveBean.save();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getExpressData();
    }

    /**
     * 设置运输状态
     */
    private void setState(String index){
        String state = "";
        int position = Integer.valueOf(index);
        switch (position){
            case 1:
                state = "运输中";
                break;
            case 2:
                state = "派件中";
                break;
            case 3:
                state = "已签收";
                break;
            case 4:
                state = "派送失败";
                break;

        }
        mState.setText(state);

    }
    /**
     * 隐藏所有
     */
    private void hideAll(){
        mQueryLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mNoData.setVisibility(View.GONE);
    }

    /**
     * 设置界面
     */
    private void setUI(String expressCode,String expressCompany){
        List<CompanyResultBean> data = DataSupport.where("type = ?",expressCompany.toUpperCase()).find(CompanyResultBean.class);
        LogUtils.d(TAG,expressCompany.toLowerCase());
        if (data.size() == 0){
            mName.setText(expressCompany.toUpperCase());
        }else {
            companyName = data.get(0).getName();
            mName.setText(companyName);
        }

        mCode.setText(expressCode);
    }

    /**
     * 获取Intent数据
     */
    private void getIntentData(){
        Bundle bundle = getIntent().getBundleExtra("expressData");
        expressCode = bundle.getString("expressCode");
        expressCompany = bundle.getString("expressCompany");
        url=UrlApi.ExpressApi.queryExpress(expressCompany,expressCode);
        LogUtils.d(TAG,url);
    }

    private void setClick(){
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.button_back:
                finish();
                break;
        }
    }
}
