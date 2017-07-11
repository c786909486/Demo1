package com.example.ckz.demo1.activity.user;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.view.ClearEditText;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class RegisterActivity extends BaseActivity implements View.OnClickListener{

    private ClearEditText mPhone;
    private ClearEditText mPassword;

    private boolean saved = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        isRegistered();
        setClick();
    }

    private void initView() {
        mPhone = (ClearEditText) findViewById(R.id.phone_number);
        mPhone.setLeftText("+86");
        mPassword = (ClearEditText) findViewById(R.id.password);

    }

    private void setClick() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.next_step).setOnClickListener(this);
    }

    private void isRegistered(){

        BmobQuery<MyUserModule> query = new BmobQuery<MyUserModule>();
        query.addWhereEqualTo("username",mPhone.getText().toString()).findObjects(new FindListener<MyUserModule>() {
            @Override
            public void done(List<MyUserModule> list, BmobException e) {
                saved = list.size() != 0;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.next_step:
                if (mPhone.getText().toString().equals("") || mPassword.getText().toString().equals("")){
                    Toast.makeText(RegisterActivity.this,R.string.input_empty,Toast.LENGTH_SHORT).show();
                }else if (saved){
                    Toast.makeText(RegisterActivity.this,"该手机号已经被注册",Toast.LENGTH_SHORT).show();
                }
                else {
                    if (mPhone.getText().toString().length()!=11){
                        Toast.makeText(RegisterActivity.this,R.string.input_error_phone,Toast.LENGTH_SHORT).show();
                    }else if (mPassword.getText().length()<6){
                        Toast.makeText(RegisterActivity.this,"密码长度不可小于六位",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(this, PhoneCheckActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("phoneNumber",mPhone.getText().toString());
                        bundle.putString("password",mPassword.getText().toString());
                        intent.putExtra("userData",bundle);
                        startActivity(intent);
                    }
                }

                break;
        }
    }
}
