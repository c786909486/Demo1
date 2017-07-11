package com.example.ckz.demo1.activity.user;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.view.ClearEditText;

import java.util.Timer;
import java.util.TimerTask;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class PhoneCheckActivity extends BaseActivity implements View.OnClickListener{
    private static final int NORMAL = 0;
    private static final int SEND = 1;
    private int current = NORMAL;
    /**
     * 验证码短信模板名称，smsId
     */
    private static final String CODE_NAME = "lookworld";
    private int smsId = 0;

    private int s;
    /**
     * 手机号和密码
     */
    private String mPhoneNum;
    private String mPassword;
    private Bundle bundle;

    /**
     * UI
     */
    private ClearEditText mInputCode;
    private TextView sendMessage;
    private Button mNext;


    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_check);


        getIntentData();
        PhoneCheckActivityPermissionsDispatcher.needWithCheck(this);
        initView();
        setClick();
    }

    private void getIntentData() {
        bundle = getIntent().getBundleExtra("userData");
        mPhoneNum = bundle.getString("phoneNumber");
        mPassword = bundle.getString("password");
    }

    private void initView() {
        mInputCode = (ClearEditText) findViewById(R.id.input_code);
        sendMessage = (TextView) findViewById(R.id.send_message);
        mNext = (Button) findViewById(R.id.next_step);

    }
    private void setClick(){
        findViewById(R.id.back_btn).setOnClickListener(this);
        mNext.setOnClickListener(this);
        sendMessage.setOnClickListener(this);
    }

    /**
     * 请求验证码
     */
    private void getCheckCode(){
        BmobSMS.requestSMSCode(this, mPhoneNum, CODE_NAME, new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                if (e == null){//验证码发送成功
                    smsId = integer;

                }
            }
        });
    }

    /**
     * 倒计时
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x123:
                    if (Integer.valueOf(msg.obj.toString())<=0){
                        current = NORMAL;
                        sendMessage.setText("重新获取");
                        timer.cancel();
                        LogUtils.d("normal",msg.obj.toString());
                    }else {
                        current = SEND;
                        sendMessage.setText(msg.obj.toString()+"s");
                        LogUtils.d("send",msg.obj.toString());
                    }
            }
        }
    };
    private void startRead(){
        s = 60;
        new Thread(new Runnable() {
            @Override
            public void run() {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        s = s -1;
                        Message message = new Message();
                        message.obj = s;
                        message.what = 0x123;
                        handler.sendMessage(message);
                    }
                },0,1000);
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.next_step:
                if (mInputCode.getText().toString().equals("")){
                    Toast.makeText(PhoneCheckActivity.this,"请输入验证码",Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(PhoneCheckActivity.this,UserSetActivity.class);
                    intent.putExtra("userData",bundle);
                    startActivity(intent);
//                    BmobSMS.verifySmsCode(PhoneCheckActivity.this, mPhoneNum, mInputCode.getText().toString(), new VerifySMSCodeListener() {
//                        @Override
//                        public void done(BmobException e) {
//                            if (e == null){
//                                //验证成功,跳转到信息设置页面
//
//                            }else {
//                                //验证失败
//                                Toast.makeText(PhoneCheckActivity.this,"验证失败",Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });
                }
                break;
            case R.id.send_message:
                if (current == NORMAL){
                    //请求验证码，开始倒计时
                    getCheckCode();
                    startRead();
                    current = SEND;
                }else {
                    //不可点击
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //timer.cancel();
    }

    @NeedsPermission(Manifest.permission.READ_PHONE_STATE)
    void need() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhoneCheckActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale(Manifest.permission.READ_PHONE_STATE)
    void show(final PermissionRequest request) {
    }

    @OnPermissionDenied(Manifest.permission.READ_PHONE_STATE)
    void denied() {
    }

    @OnNeverAskAgain(Manifest.permission.READ_PHONE_STATE)
    void never() {
    }
}
