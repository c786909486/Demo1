package com.example.ckz.demo1.activity.user;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.user.MyUserManager;
import com.example.ckz.demo1.view.LengthShowEditText;

public class UserDesignActivity extends AppCompatActivity implements View.OnClickListener{
    private LengthShowEditText mInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_design);
        mInput = (LengthShowEditText) findViewById(R.id.input);
        setClick();
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
                MyUserManager.getInstance(UserDesignActivity.this).updateUserDesign(mInput.getText(), new MyUserManager.OnUpdateSuccess() {
                    @Override
                    public void onSuccess() {
                        finish();
                    }

                    @Override
                    public void onFailed() {
                    }
                });
                break;
        }
    }
}
