package com.example.ckz.demo1.activity.express;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.bean.express.CompanyBean;
import com.example.ckz.demo1.bean.express.CompanyResultBean;
import com.example.ckz.demo1.http.UrlApi;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.util.StatuBarColorUtil;
import com.example.ckz.demo1.view.ClearEditText;
import com.example.ckz.demo1.zxing.app.CaptureActivity;
import com.example.vuandroidadsdk.okhttp.CommonOkHttpClient;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataHandle;
import com.example.vuandroidadsdk.okhttp.listener.DisposeDataListener;
import com.example.vuandroidadsdk.okhttp.request.CommonRequest;

import org.litepal.crud.DataSupport;

import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class ExpressMainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "ExpressMainActivity";
    public static final int REQUEST_QRCODE = 3;
    public static final int REQUEST_CPCODE = 1;
    private Context mContext;

    private ClearEditText mExpressId;
    private TextView mCompany;
    private TextView mStartQuery;


    private int dataSize;
    private CompanyResultBean company = null;

    private String expressCode;
    private String expressCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_main);
        mContext = ExpressMainActivity.this;
        StatuBarColorUtil.setStatuBarColor(this,R.color.blue_dark);
        initView();
       if (DataSupport.findAll(CompanyResultBean.class).size() == 0){
           saveCompany();
           LogUtils.d(TAG,"Net");
       }
        setTextChange();
        setClick();
    }

    private void initView() {
        mExpressId = (ClearEditText) findViewById(R.id.input_express);
        mCompany = (TextView) findViewById(R.id.input_company);
        mStartQuery = (TextView) findViewById(R.id.start_query);
        mCompany.setKeyListener(null);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_QRCODE:
                if (resultCode == RESULT_OK){
                    String code = data.getStringExtra("SCAN_RESULT");
                    mExpressId.setText(code);
                    mStartQuery.performClick();
                }
                break;
            case REQUEST_CPCODE:
                if (resultCode == RESULT_OK){
                    company = (CompanyResultBean) data.getSerializableExtra("companyData");
                    mCompany.setText(company.getName());
                }

        }
    }

    /**
     * 设置点击事件
     */
    private void setClick(){
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.scan_btn).setOnClickListener(this);
        findViewById(R.id.company_layout).setOnClickListener(this);
        findViewById(R.id.clear_btn).setOnClickListener(this);
        findViewById(R.id.query_history).setOnClickListener(this);
        mCompany.setOnClickListener(this);
        mStartQuery.setOnClickListener(this);
    }

    /**
     * 设置查询按钮的状态
     */
    private void setTextChange(){
        mExpressId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setButtonState(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mCompany.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0){
                    findViewById(R.id.clear_btn).setVisibility(View.VISIBLE);
                }else {
                    findViewById(R.id.clear_btn).setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 快递单号为空时，查询按钮为灰色；不为空是为蓝色
     */
    private void setButtonState(CharSequence s){
        if (s.length() == 0){
            mStartQuery.setSelected(false);
        }else {
            mStartQuery.setSelected(true);
        }
    }

    /**
     * 获取查询数据
     */
    private void getQueryData(){
        if (company == null){
            expressCompany = "auto";
        }else {
            expressCompany = company.getType();
        }
        expressCode = mExpressId.getText().toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.scan_btn:
                //跳转扫码界面
                ExpressMainActivityPermissionsDispatcher.needWithCheck(this);
                Intent qrIntent = new Intent(mContext, CaptureActivity.class);
                startActivityForResult(qrIntent,REQUEST_QRCODE);

                break;
            case R.id.input_company:
                findViewById(R.id.company_layout).performClick();
                break;
            case R.id.start_query:
                //开始查询
                if (mStartQuery.isSelected()){
                    getQueryData();
                    LogUtils.d(TAG,"start query");
                    LogUtils.d(TAG,expressCode);
                    LogUtils.d(TAG,expressCompany);
                    Intent quIntent = new Intent(mContext,ExpressProgressActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("expressCompany",expressCompany);
                    bundle.putString("expressCode",expressCode);
                    quIntent.putExtra("expressData",bundle);
                    startActivity(quIntent);
                }

                break;
            case R.id.company_layout:
                //跳转公司选择界面
                Intent cpIntent = new Intent(mContext,CompanyChooseActivity.class);
                cpIntent.putExtra("dataSize",dataSize);
                startActivityForResult(cpIntent,REQUEST_CPCODE);
                break;
            case R.id.clear_btn:
                mCompany.setText("");
                break;
            case R.id.query_history:
                Intent intent = new Intent(mContext,ExpressHistoryActivity.class);
                startActivity(intent);
                break;

        }
    }

    /**
     * 获取快递公司列表
     * 存储到数据库中
     */
    private void saveCompany(){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(UrlApi.ExpressApi.getCompany(),null),new DisposeDataHandle(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                CompanyBean companyBean = JSON.parseObject(responseObj.toString(),CompanyBean.class);
                dataSize = companyBean.getResult().size();

                for (CompanyResultBean bean : companyBean.getResult()) {
                    bean.save();
                }

            }

            @Override
            public void onFailure(Object reasonObj) {

            }
        }));
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    void need() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ExpressMainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void show(final PermissionRequest request) {
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    void denied() {
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void never() {
    }
}
