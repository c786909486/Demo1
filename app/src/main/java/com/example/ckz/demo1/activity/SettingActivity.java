package com.example.ckz.demo1.activity;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.util.GlideCacheUtil;
import com.example.ckz.demo1.util.Util;
import com.example.vuandroidadsdk.utils.MyDialogUtils;
import com.example.vuandroidadsdk.utils.ShowToastUtil;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mVersion;
    private TextView mCacheSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        setClick();
    }

    private void initView() {
        mVersion = (TextView) findViewById(R.id.version);
        mCacheSize = (TextView) findViewById(R.id.cache_size);

        mVersion.setText(Util.getVersionName(this));
        mCacheSize.setText(GlideCacheUtil.getInstance().getCacheSize(this));
    }

    private void setClick() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.cache_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.cache_layout:
                //清除缓存
                MyDialogUtils.getInstance().createNormalDialog(this, "", "确定要清楚缓存吗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GlideCacheUtil.getInstance().clearImageAllCache(SettingActivity.this, new GlideCacheUtil.OnClearSuccess() {
                            @Override
                            public void onSuccess() {
                                mCacheSize.setText(GlideCacheUtil.getInstance().getCacheSize(SettingActivity.this));
                            }

                            @Override
                            public void onFaild() {
                                ShowToastUtil.showToast(SettingActivity.this,"清除失败");
                            }
                        });
                    }
                });

                break;
        }
    }
}
