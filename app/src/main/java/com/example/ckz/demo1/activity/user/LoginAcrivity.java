package com.example.ckz.demo1.activity.user;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.application.MyApplication;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.LeftDrawableUtils;
import com.example.ckz.demo1.view.ClearEditText;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginAcrivity extends BaseActivity implements View.OnClickListener{
    private ClearEditText mUserName;
    private ClearEditText mPassword;
    private Button mLoginBtn;
    private Button mRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acrivity);
        initView();
        setClick();
        setLoginState();
    }

    private void initView() {
        mUserName = (ClearEditText) findViewById(R.id.user_name);
        mPassword = (ClearEditText) findViewById(R.id.password);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mRegister = (Button) findViewById(R.id.register_btn);
    }

    /**
     * 设置登陆按钮状态，当用户名和密码都不为空时，才可点击
     */
    private void setLoginState(){
        TextChange change = new TextChange();
        mUserName.addTextChangedListener(change);
        mPassword.addTextChangedListener(change);

    }

    private void setClick() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        mLoginBtn.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        findViewById(R.id.forget_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.login_btn:
                if (mLoginBtn.isSelected()){
                    login();
                }else {
                    Toast.makeText(MyApplication.getInstance(),R.string.no_name_mima,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.register_btn:
                Intent intent = new Intent(LoginAcrivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.forget_btn:
                startActivity(new Intent(LoginAcrivity.this,ForgetPasswordActivity.class));
                break;
        }
    }



    private void login() {
        MyUserModule user = new MyUserModule();
        user.setUsername(mUserName.getText().toString());
        user.setPassword(mPassword.getText().toString());
        user.login(new SaveListener<MyUserModule>() {
            @Override
            public void done(MyUserModule userModule, BmobException e) {
                if (e == null){
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else {
                    Toast.makeText(LoginAcrivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    class TextChange implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (mUserName.getText().toString().length()>0 && mPassword.getText().toString().length()>0){
                mLoginBtn.setSelected(true);
            }else {
                mLoginBtn.setSelected(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
