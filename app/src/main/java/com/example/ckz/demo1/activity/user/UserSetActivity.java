package com.example.ckz.demo1.activity.user;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.bean.user.UserIdTable;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.LogUtils;
import com.example.vuandroidadsdk.utils.MyDialogUtils;
import com.example.ckz.demo1.util.ShowPopwindowHelper;
import com.example.ckz.demo1.view.CircleImageView;
import com.example.ckz.demo1.view.ClearEditText;

import java.io.File;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class UserSetActivity extends AppCompatActivity  implements View.OnClickListener {
    private CircleImageView mUserIcon;
    private ClearEditText mUserName;
    private ClearEditText mEmail;
    private TextView mSex;
    private Button mComplete;

    private ShowPopwindowHelper helper;

    private String mPhoneNum;
    private String mPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new ShowPopwindowHelper(this);
        setContentView(R.layout.activity_user_set);
        getIntentData();
        initView();
        setClick();
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getBundleExtra("userData");
        mPassword = bundle.getString("password");
        mPhoneNum = bundle.getString("phoneNumber");

    }

    private void initView() {
        mUserIcon = (CircleImageView) findViewById(R.id.user_icon);
        mUserName = (ClearEditText) findViewById(R.id.input_user_name);
        mEmail = (ClearEditText) findViewById(R.id.input_email);
        mSex = (TextView) findViewById(R.id.user_sex);
        mComplete = (Button) findViewById(R.id.complete);
    }

    private void setClick() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        mSex.setOnClickListener(this);
        mComplete.setOnClickListener(this);
        mUserIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.user_sex:
                //弹出性别选择对话框
                final String[] sex = new String[]{"男", "女"};
                MyDialogUtils.getInstance().createNormalListDialog(UserSetActivity.this, "请选择性别", sex, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSex.setText(sex[which]);
                    }
                });
                break;
            case R.id.complete:
                //完成注册，提交注册信息
                if (mUserName.getText().toString().equals("")){
                    Toast.makeText(this,"请输入昵称",Toast.LENGTH_SHORT).show();
                }else {
                    getNetUserId();
                }

                break;
            case R.id.user_icon:
                UserSetActivityPermissionsDispatcher.needWithCheck(UserSetActivity.this);
                //打开弹窗，选择来自相册或拍照
                helper.showBottomPopwindow(R.layout.popu_camera_layout, v);
                helper.setOnClick(R.id.from_album, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开相册选取图片
                        openAlbum();
                    }
                });
                helper.setOnClick(R.id.from_photo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //打开相机拍照
                        openCamera();
                    }
                });
                helper.setOnClick(R.id.cancle_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        helper.dismiss();
                    }
                });
                break;
        }
    }

    /**
     * 获取userId
     */
    private void getNetUserId(){
        BmobQuery<UserIdTable> query = new BmobQuery<UserIdTable>();
        query.getObject("0b8IQQQi", new QueryListener<UserIdTable>() {
            @Override
            public void done(UserIdTable userIdTable, BmobException e) {
                if (e == null){
                    finishRegister(userIdTable.getUserId());
                }
            }
        });
    }

    private void finishRegister(final Integer userId){
        UserIdTable table = new UserIdTable();
        table.setUserId(userId+1);
        table.update("0b8IQQQi",new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){

                } else {
                   LogUtils.d("SAVE_STATE",e.toString());
                }
            }
        });
        MyUserModule user = new MyUserModule();
        user.setUsername(mPhoneNum);
        user.setPassword(mPassword);
        user.setUserId(String.valueOf(userId));
        user.setMobilePhoneNumber(mPhoneNum);
        user.setMobilePhoneNumberVerified(true);
        user.setUserNicheng(mUserName.getText().toString());
        LogUtils.d("TAG","账号:"+mPhoneNum+"\n密码:"+mPassword+"\n昵称:"+mUserName.getText().toString());
        user.signUp(new SaveListener<MyUserModule>() {
            @Override
            public void done(MyUserModule myUserModule, BmobException e) {
                if (e == null){
                    Intent intent = new Intent(UserSetActivity.this,LoginAcrivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(UserSetActivity.this,"注册失败，"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    public static final String PHOTO_FILE_NAME = "fileImg.jpg";
    public static final int CAMERA_REQUEST_CODE = 0;
    public static final int ALBUM_REQUEST_CODE = 1;

    /**
     * 打开相机
     */
    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory()+"/images",PHOTO_FILE_NAME);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Uri photoURI = FileProvider.getUriForFile(this,
                   "com.example.ckz.demo1.fileProvider",
                    file);
            Log.d("tag",photoURI.getPath());
            // 申请临时访问权限
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        }else {
          //  intent.setDataAndType(getImageContentUri(UserSetActivity.this, newFile), "image/*");//自己使用Content Uri替换File Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE);
        helper.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 打开相册
     */
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, ALBUM_REQUEST_CODE);
        helper.dismiss();
    }


    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void need() {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        UserSetActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void show(final PermissionRequest request) {
    }

    @OnPermissionDenied({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void denied() {
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void never() {
    }
}
