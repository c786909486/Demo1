package com.example.ckz.demo1.activity.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.bean.user.news.UserNewsComment;
import com.example.ckz.demo1.util.SPUtils;
import com.example.ckz.demo1.view.CircleImageView;

import cn.bmob.v3.b.V;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class CommentDetialActivity extends BaseActivity implements View.OnClickListener{
    private UserNewsComment commentData;
    private Context context;

    /**
     * UI
     */
    private CircleImageView mUserIcon;
    private TextView mUserName;
    private TextView mCreateTime;
    private TextView mDingBtn;
    private ImageView mNewsPic;
    private TextView mNewsContent;
    private TextView mComment;
    private LinearLayout mNewsLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detial);
        context = CommentDetialActivity.this;
        getIntentData();
        initView();
        setUI();
    }

    private void initView() {
        findViewById(R.id.popup_btn).setVisibility(View.GONE);

        mUserIcon = (CircleImageView) findViewById(R.id.user_icon);
        mUserName = (TextView) findViewById(R.id.user_name);
        mCreateTime = (TextView) findViewById(R.id.create_time);
        mDingBtn = (TextView) findViewById(R.id.ding_btn);
        mNewsPic = (ImageView) findViewById(R.id.news_pic);
        mNewsContent = (TextView) findViewById(R.id.news_content);
        mComment = (TextView) findViewById(R.id.news_comment);
        mNewsLayout = (LinearLayout) findViewById(R.id.news_layout);

        mNewsLayout.setOnClickListener(this);
    }
    private void setUI(){
        BmobFile userIcon = commentData.getUserModule().getUserIcon();
        if (userIcon!=null) Glide.with(this).load(userIcon).into(mUserIcon);
        mUserName.setText(commentData.getUserModule().getUserNicheng());
        mCreateTime.setText(commentData.getCreatedAt().substring(5,16));
        mComment.setText(commentData.getNewsComment());
        if (commentData.getCommentNews().getPic()!=null){
            mNewsPic.setVisibility(View.VISIBLE);
            Glide.with(this).load(commentData.getCommentNews().getPic()).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate().into(mNewsPic);
        }else {
            mNewsPic.setVisibility(View.GONE);
        }
        mNewsContent.setText(commentData.getCommentNews().getTitle());
        if (SPUtils.getBooleanSp(context,commentData.getNewsComment()+commentData.getCommentId(),false)){
            mDingBtn.setSelected(true);
        }
        mDingBtn.setOnClickListener(this);

    }

    private void getIntentData(){
        commentData = (UserNewsComment) getIntent().getBundleExtra("bundleData").getSerializable("commentData");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ding_btn:
                if (mDingBtn.isSelected()){
                    //取消点赞

                    UserNewsComment newsComment = new UserNewsComment();
                    newsComment.setObjectId(commentData.getObjectId());
                    newsComment.setLikes(commentData.getLikes());
                    newsComment.update( new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                mDingBtn.setSelected(false);
                                mDingBtn.setText(String.valueOf(commentData.getLikes()));
                                SPUtils.putBooleanSp(context,commentData.getNewsComment()+commentData.getCommentId(),false);
                            }
                        }
                    });
                }else {
                    //点赞

                    UserNewsComment newsComment = new UserNewsComment();
                    newsComment.setObjectId(commentData.getObjectId());
                    newsComment.setLikes(commentData.getLikes()+1);
                    newsComment.update( new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null){
                                mDingBtn.setSelected(true);
                                mDingBtn.setText(String.valueOf(commentData.getLikes()+1));
                                SPUtils.putBooleanSp(context,commentData.getNewsComment()+commentData.getCommentId(),true);
                            }
                        }
                    });
                }
                break;
            case R.id.news_layout:
                Intent intent = new Intent(context,NewsDetilActivity.class);
                intent.putExtra("NewsData",commentData);
        }
    }
}
