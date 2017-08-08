package com.example.ckz.demo1.user;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.ckz.demo1.activity.user.UserSettingActivity;
import com.example.ckz.demo1.bean.user.news.CommentNews;
import com.example.ckz.demo1.bean.user.news.UserNewsComment;
import com.example.vuandroidadsdk.utils.ShowToastUtil;

import java.io.File;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by CKZ on 2017/8/5.
 */

public class MyUserManager {

    public interface OnUpdateSuccess{
        void onSuccess();
        void onFailed();
    }

    public interface OnRequestListener{
        void onSuccess();
        void onFaild(cn.bmob.sms.exception.BmobException e);
    }

    public static final String SMS_NAME = "lookworld";

    private static MyUserManager myUserManager;

    private Context context;

    private MyUserManager(Context context){
        this.context = context;
    }

    public static MyUserManager getInstance(Context context){
        if (myUserManager == null){
            synchronized (MyUserManager.class){
                if (myUserManager == null){
                    myUserManager = new MyUserManager(context);
                }
            }
        }
        return myUserManager;
    }

    //请求验证码
    public void requestSmsCode(String phoneNum,@Nullable final OnRequestListener listener){
        BmobSMS.requestSMSCode(context, phoneNum, SMS_NAME, new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, cn.bmob.sms.exception.BmobException e) {
                if (e == null){
                    if (listener !=null){
                        listener.onSuccess();
                    }
                }else {
                    if (listener!=null){
                        listener.onFaild(e);
                    }
                }
            }
        });
    }

    private void userUpdate(MyUserModule userModule,UpdateListener listener){
        MyUserModule currentUser = MyUserModule.getCurrentUser(MyUserModule.class);
        userModule.update(currentUser.getObjectId(),listener);
    }
    //更新用户性别
    public void updateUserSex(String userSex, @Nullable final OnUpdateSuccess listener){
        MyUserModule userModule = new MyUserModule();
        userModule.setUserSex(userSex);
        userUpdate(userModule, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ShowToastUtil.showToast(context,"设置成功");
                    if (listener!=null){
                        listener.onSuccess();
                    }
                }else {
                    ShowToastUtil.showToast(context,"设置失败："+e.toString());
                    if (listener!=null){
                        listener.onFailed();
                    }
                }
            }
        });
    }
    //更新用户昵称
    public void updateUserNicheng(String userNicheng, @Nullable final OnUpdateSuccess listener){
        MyUserModule userModule = new MyUserModule();
        userModule.setUserNicheng(userNicheng);
        userUpdate(userModule, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ShowToastUtil.showToast(context,"设置成功");
                    if (listener!=null){
                        listener.onSuccess();
                    }
                }else {
                    ShowToastUtil.showToast(context,"设置失败："+e.toString());
                    if (listener!=null){
                        listener.onFailed();
                    }
                }
            }
        });
    }
    //更新用户个性签名
    public void updateUserDesign(String userDesign, @Nullable final OnUpdateSuccess listener){
        MyUserModule userModule = new MyUserModule();
        userModule.setUserDetail(userDesign);
        userUpdate(userModule, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ShowToastUtil.showToast(context,"设置成功");
                    if (listener!=null){
                        listener.onSuccess();
                    }
                }else {
                    ShowToastUtil.showToast(context,"设置失败："+e.toString());
                    if (listener!=null){
                        listener.onFailed();
                    }
                }
            }
        });
    }
    //更新用户头像
    public void updateUserIcon(File file, @Nullable final OnUpdateSuccess listener){
        final MyUserModule userModule = new MyUserModule();
        final BmobFile iconFile = new BmobFile(file);
        iconFile.upload(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    userModule.setUserIcon(iconFile);
                    userUpdate(userModule, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                ShowToastUtil.showToast(context,"设置成功");
                                if (listener!=null){
                                    listener.onSuccess();
                                }
                            }else {
                                ShowToastUtil.showToast(context,"设置失败："+e.toString());
                                if (listener!=null){
                                    listener.onFailed();
                                }
                            }
                        }
                    });
                }
            }
        });

    }
    //更新用户主页背景
    public void updateUserBackground(BmobFile backgroundFile, @Nullable final OnUpdateSuccess listener){
        MyUserModule userModule = new MyUserModule();
        userModule.setUserIcon(backgroundFile);
        userUpdate(userModule, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ShowToastUtil.showToast(context,"设置成功");
                    if (listener!=null){
                        listener.onSuccess();
                    }
                }else {
                    ShowToastUtil.showToast(context,"设置失败："+e.toString());
                    if (listener!=null){
                        listener.onFailed();
                    }
                }
            }
        });
    }

    //提交评论
    public void sendComment(String comment, CommentNews news, SaveListener<String> listener){
        UserNewsComment newsComment = new UserNewsComment();
        newsComment.setUserModule(MyUserModule.getCurrentUser(MyUserModule.class));
        newsComment.setCommentNews(news);
        newsComment.setNewsComment(comment);
        newsComment.setLikes(0);
        newsComment.setHates(0);
        newsComment.setCommentSize(0);
        newsComment.setCommentId((int) System.currentTimeMillis());
        newsComment.save(listener);
    }

    //推出登陆
    public void quitLogin(){
        MyUserModule.logOut();
    }

    //重置密码
    public void resetPassword(String smsCode, String newPassword, @Nullable final OnUpdateSuccess listener){
        BmobUser.resetPasswordBySMSCode(smsCode, newPassword, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    if (listener!=null){
                        listener.onSuccess();
                    }
                }else {
                    if (listener!=null){
                        listener.onFailed();
                    }
                }
            }
        });
    }

}
