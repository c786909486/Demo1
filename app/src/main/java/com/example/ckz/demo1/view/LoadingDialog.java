package com.example.ckz.demo1.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;

import com.example.ckz.demo1.R;

/**
 * Created by CKZ on 2017/7/12.
 */

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading_layout);
        setCanceledOnTouchOutside(false);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
