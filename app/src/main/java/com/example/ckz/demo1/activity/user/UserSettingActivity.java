package com.example.ckz.demo1.activity.user;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.SettingActivity;
import com.example.ckz.demo1.activity.news.MainActivity;
import com.example.ckz.demo1.user.MyUserManager;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.LogUtils;
import com.example.vuandroidadsdk.camera.CameraHelper;
import com.example.vuandroidadsdk.camera.ClipPictureActivity;
import com.example.vuandroidadsdk.showpop.ShowPopup;
import com.example.vuandroidadsdk.utils.MyDialogUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;

public class UserSettingActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "UserSettingActivity";
    private Context context = this;
    private MyUserModule userModule;
    private RoundedImageView mUserIcon;
    private TextView mUserName;
    private TextView mUserSex;
    private TextView mUserDesign;
    private TextView mUId;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        initView();
        setClick();
    }

    private void initView() {
        mUserIcon = (RoundedImageView) findViewById(R.id.user_icon);
        mUserName = (TextView) findViewById(R.id.user_name);
        mUserSex = (TextView) findViewById(R.id.user_sex);
        mUserDesign = (TextView) findViewById(R.id.user_design);
        mUId = (TextView) findViewById(R.id.user_id);
    }

    private void setUIData(){
        //用户头像
        if (userModule.getUserIcon()!=null){
            Glide.with(this).load(userModule.getUserIcon().getUrl()).into(mUserIcon);
        }else {
            mUserIcon.setImageResource(R.mipmap.user_normal_icon);
        }
        //昵称
        mUserName.setText(userModule.getUserNicheng());
        //性别
        if (userModule.getUserSex()!=null) mUserSex.setText(userModule.getUserSex());
        //个性签名
        if (userModule.getUserDetail()!=null) {
            mUserDesign.setText(userModule.getUserDetail());
        }else {
            mUserDesign.setText(R.string.design_not_input);
        }
        //UID
        mUId.setText(userModule.getUserId());


    }

    private void setClick() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.user_icon_layout).setOnClickListener(this);
        findViewById(R.id.user_name_layout).setOnClickListener(this);
        findViewById(R.id.user_sex_layout).setOnClickListener(this);
        findViewById(R.id.user_design_layout).setOnClickListener(this);
        findViewById(R.id.quit_btn).setOnClickListener(this);
        findViewById(R.id.user_setting_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            //头像
            case R.id.user_icon_layout:
                //弹窗选择
                ShowPopup.getInstance(context).createSimplePopupWindow("拍照","相册","取消")
                        .defaultAnim()
                        .atBottom(v)
                        .setPositionClickListener(new ShowPopup.OnPositionClickListener() {
                            @Override
                            public void OnPositionClick(PopupWindow popup, View view, int position) {
                                switch (position){
                                    case 0:
                                        //拍照
                                        CameraHelper.getInstance(UserSettingActivity.this).openMyCamera();
                                        popup.dismiss();
                                        break;
                                    case 1:
                                        //打开相册
                                        CameraHelper.getInstance(UserSettingActivity.this).openAlbum();
                                        popup.dismiss();
                                        break;
                                    case 2:
                                        popup.dismiss();
                                        break;

                                }
                            }
                        });
                break;
            //用户名
            case R.id.user_name_layout:
                //跳转设置用户名
                startActivity(new Intent(context,UserNameActivity.class));
                break;
            //性别
            case R.id.user_sex_layout:
                //弹窗选择性别
                showDialogSex();
                break;
            //个性签名
            case R.id.user_design_layout:
                //跳转设置个性签名
                startActivity(new Intent(context,UserDesignActivity.class));
                break;
            //退出登陆
            case R.id.quit_btn:
                MyDialogUtils.getInstance().createNormalDialog(context, "", "确定要退出吗？", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyUserManager.getInstance(context).quitLogin();
                        startActivity(new Intent(context, MainActivity.class));
                    }
                });

                break;
            //设置
            case R.id.user_setting_layout:
                //跳转设置界面
                startActivity(new Intent(context, SettingActivity.class));
                break;


        }
    }
    private void showDialogSex(){
        final String[] sex = new String[]{"男","女"};
        MyDialogUtils.getInstance().createSingleListDialog(context, "选择您的性别",sex , new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {

                MyUserManager.getInstance(context).updateUserSex(sex[which], new MyUserManager.OnUpdateSuccess() {
                    @Override
                    public void onSuccess() {
                        mUserSex.setText(sex[which]);
                    }

                    @Override
                    public void onFailed() {

                    }
                });
                dialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            //相册
            case CameraHelper.ALBUM_REQUEST_CODE:
                LogUtils.d(TAG,resultCode+"");
               if (resultCode == RESULT_OK){
                   Intent intent = new Intent(context,ClipPictureActivity.class);
                   intent.putExtra("imageUri",data.getData().toString());
                   startActivityForResult(intent,CameraHelper.CLIP_PIC);
               }
                break;
            //相机
            case CameraHelper.OPEN_MYCAMERA:
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent(context,ClipPictureActivity.class);
                    intent.putExtra("imageUri",data.getStringExtra("imageUri"));
                    startActivityForResult(intent,CameraHelper.CLIP_PIC);
                }
                break;
            //裁剪
            case CameraHelper.CLIP_PIC:
                if (resultCode == RESULT_OK){
                    File file = new File(data.getStringExtra("imageUri"));
                   MyUserManager.getInstance(context).updateUserIcon(file, new MyUserManager.OnUpdateSuccess() {
                       @Override
                       public void onSuccess() {
                           mUserIcon.setImageURI(Uri.parse(data.getStringExtra("imageUri")));
                       }

                       @Override
                       public void onFailed() {

                       }
                   });

                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        userModule = MyUserModule.getCurrentUser(MyUserModule.class);
        setUIData();
    }
}
