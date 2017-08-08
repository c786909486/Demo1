package com.example.ckz.demo1.activity.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.user.MyUserManager;
import com.example.ckz.demo1.view.ClearEditText;

public class UserNameActivity extends AppCompatActivity implements View.OnClickListener{
    private ClearEditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);
        initView();
        setClick();
    }

    private void initView() {
        mInput = (ClearEditText) findViewById(R.id.input);
    }

    private void setClick() {
        findViewById(R.id.cancel_btn).setOnClickListener(this);
        findViewById(R.id.save_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cancel_btn:
                finish();
                break;
            case R.id.save_btn:
                //更新昵称
                MyUserManager.getInstance(UserNameActivity.this).updateUserNicheng(mInput.getText().toString(), new MyUserManager.OnUpdateSuccess() {
                    @Override
                    public void onSuccess() {
                        finish();
                    }

                    @Override
                    public void onFailed() {
                        finish();
                    }
                });
//                findViewById(R.id.save_btn).postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        finish();
//                    }
//                }, 1000);
                break;

        }

    }
}
