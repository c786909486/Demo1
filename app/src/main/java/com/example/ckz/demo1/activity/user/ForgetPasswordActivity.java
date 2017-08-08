package com.example.ckz.demo1.activity.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.view.ClearEditText;

public class ForgetPasswordActivity extends AppCompatActivity implements View.OnClickListener{
    private ClearEditText mPhone;
    private ClearEditText mPassword;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();

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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.next_step:
                if (mPhone.getText().toString().equals("") || mPassword.getText().toString().equals("")){
                    Toast.makeText(context,R.string.input_empty,Toast.LENGTH_SHORT).show();
                } else {
                    if (mPhone.getText().toString().length()!=11){
                        Toast.makeText(context,R.string.input_error_phone,Toast.LENGTH_SHORT).show();
                    }else if (mPassword.getText().length()<6){
                        Toast.makeText(context,"密码长度不可小于六位",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(this, ResetPasswordActivity.class);
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

