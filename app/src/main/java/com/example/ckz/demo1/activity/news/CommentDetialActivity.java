package com.example.ckz.demo1.activity.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.adapter.CommonAdapter;
import com.example.ckz.demo1.bean.user.news.UserNewsComment;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.SPUtils;
import com.example.ckz.demo1.view.CircleImageView;
import com.example.ckz.demo1.view.NestedListView;
import com.example.vuandroidadsdk.showpop.ShowPopup;
import com.example.vuandroidadsdk.utils.ShowToastUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class CommentDetialActivity extends BaseActivity implements View.OnClickListener,TextWatcher,AdapterView.OnItemClickListener{
    private UserNewsComment commentData;
    private Context context;

    /**
     * comment UI
     */
    private CircleImageView mUserIcon;
    private TextView mUserName;
    private TextView mCreateTime;
    private TextView mDingBtn;
    private ImageView mNewsPic;
    private TextView mNewsContent;
    private TextView mComment;
    private LinearLayout mNewsLayout;
    private NestedListView mList;
    private TwinklingRefreshLayout mRefresh;
    /**
     * bottom UI
     */
    private EditText mInput;
    private TextView mSend;
    private ImageView mCollection;

    private MyUserModule userModule;
    private MyUserModule preUser = null;

    private List<UserNewsComment> mData;
    private CommonAdapter<UserNewsComment> mAdapter;

    private int num = 10;

    private boolean isBelow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detial);
        context = CommentDetialActivity.this;
        userModule = MyUserModule.getCurrentUser(MyUserModule.class);
        getIntentData();
        initView();
        setUI();
        getNewsComment(true);
        setRefresh();
    }

    /**
     * 初始化控件
     */
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
        //bottomUI
        mCollection = (ImageView) findViewById(R.id.news_collection);
        mCollection.setVisibility(View.GONE);
        mInput = (EditText) findViewById(R.id.comment_input);
        mSend = (TextView) findViewById(R.id.send_btn);
        mList = (NestedListView) findViewById(R.id.comment_list);
        mRefresh = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);

        mNewsLayout.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mInput.addTextChangedListener(this);
        mList.setOnItemClickListener(this);
    }
    /**
     * 设置刷新加载
     */
    private void setRefresh(){
        ProgressLayout header = new ProgressLayout(this);
        header.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setHeaderView(header);
        mRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getNewsComment(true);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getNewsComment(false);
            }
        });
    }

    /**
     * 设置UI内容
     */
    private void setUI(){
        BmobFile userIcon = commentData.getUserModule().getUserIcon();
        if (userIcon!=null) Glide.with(this).load(userIcon.getUrl()).into(mUserIcon);
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
        mInput.setHint("回复"+commentData.getUserModule().getUserNicheng()+"：");

        setList();
    }

    /**
     * 绑定adapter
     */
    private void setList(){
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<UserNewsComment>((ArrayList<UserNewsComment>) mData,R.layout.item_news_comment) {
            @Override
            public void bindView(final ViewHolder holder, final UserNewsComment obj) {
                MyUserModule userModule = obj.getUserModule();

                //设置用户头像
                if (userModule.getUserIcon()!=null) holder.setImage(R.id.user_icon,userModule.getUserIcon());
                //用户昵称
                holder.setText(R.id.user_name,userModule.getUserNicheng());
                //发布时间
                holder.setText(R.id.create_time,obj.getCreatedAt().substring(5,16));
                //点赞
                holder.setText(R.id.ding_btn,String.valueOf(obj.getLikes()));
                if (SPUtils.getBooleanSp(context,obj.getNewsComment()+obj.getCommentId(),false)){
                    holder.setSelect(R.id.ding_btn,true);
                }
                holder.setOnClickListener(R.id.ding_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (holder.isSelected(R.id.ding_btn)){
                            //取消点赞

                            UserNewsComment newsComment = new UserNewsComment();
                            newsComment.setObjectId(obj.getObjectId());
                            newsComment.setLikes(obj.getLikes());
                            newsComment.update( new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null){
                                        holder.setSelect(R.id.ding_btn,false);
                                        holder.setText(R.id.ding_btn,String.valueOf(obj.getLikes()));
                                        SPUtils.putBooleanSp(context,obj.getNewsComment()+obj.getCommentId(),false);
                                    }
                                }
                            });
                        }else {
                            //点赞

                            UserNewsComment newsComment = new UserNewsComment();
                            newsComment.setObjectId(obj.getObjectId());
                            newsComment.setLikes(obj.getLikes()+1);
                            newsComment.update( new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null){
                                        holder.setSelect(R.id.ding_btn,true);
                                        holder.setText(R.id.ding_btn,String.valueOf(obj.getLikes()+1));
                                        SPUtils.putBooleanSp(context,obj.getNewsComment()+obj.getCommentId(),true);
                                    }
                                }
                            });
                        }
                    }
                });

                //显示评论
                if (obj.getPreUser()!=null){
//                    holder.setText(R.id.news_comment,"回复 "+obj.getPreUser().getUserNicheng()+"："+obj.getNewsComment());
                    holder.setText(R.id.news_comment,Html.fromHtml("<font color=\"gray\">回复 "+obj.getPreUser().getUserNicheng()+"：</font>"+obj.getNewsComment()));
                }else {
                    holder.setText(R.id.news_comment,obj.getNewsComment());
                }
                //弹窗按钮
                holder.setOnClickListener(R.id.popup_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ShowPopup popup = ShowPopup.getInstance(context);
                        popup.createSimplePopupWindow(getString(R.string.huifu),getString(R.string.dianzan),getString(R.string.cai),
                                getString(R.string.cancel))
                                .defaultAnim().atBottom(v).setPositionClickListener(new ShowPopup.OnPositionClickListener() {
                            @Override
                            public void OnPositionClick(PopupWindow popupWindow,View view, int position) {
                                switch (position){
                                    case 0:
                                        //跳转到回复界面
                                        isBelow = true;
                                        preUser = obj.getUserModule();
                                        openKeyboard();
                                        popup.closePopupWindow();
                                        break;
                                    case 1:
                                        //点赞
                                        holder.getView(R.id.ding_btn).performClick();
                                        popup.closePopupWindow();
                                        break;
                                    case 2:
                                        //踩
                                        if (holder.isSelected(R.id.ding_btn)){
                                            Toast.makeText(context, R.string.isliked,Toast.LENGTH_SHORT).show();
                                        }else {
                                            UserNewsComment newsComment = new UserNewsComment();
                                            newsComment.setObjectId(obj.getObjectId());
                                            newsComment.setHates(obj.getHates()+1);
                                            newsComment.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {

                                                }
                                            });
                                        }
                                        popup.closePopupWindow();
                                        break;
                                    case 3:
                                        //取消
                                        popup.closePopupWindow();
                                        break;
                                }
                            }
                        });
                    }
                });
            }
        };
        mList.setAdapter(mAdapter);
        setEmpty(mList);
    }

    /**
     * 设置enptyview
     * @param mList
     */
    private void setEmpty(ListView mList){
        TextView empty = new TextView(this);
        empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,350));
        empty.setGravity(Gravity.CENTER);
        empty.setText(R.string.no_comment);
        empty.setTextSize(15.0f);
        empty.setVisibility(View.GONE);
        ((ViewGroup)mList.getParent()).addView(empty);
        mList.setEmptyView(empty);
    }
    private void getIntentData(){
        commentData = (UserNewsComment) getIntent().getBundleExtra("bundleData").getSerializable("commentData");
    }

    /**
     * 获取网络数据
     */
    private void getNewsComment(boolean isRefresh){
        BmobQuery<UserNewsComment> query = new BmobQuery<UserNewsComment>();
        UserNewsComment comment = new UserNewsComment();
        comment.setObjectId(commentData.getObjectId());
        query.addWhereEqualTo("preComment",new BmobPointer(comment));
        query.include("userModule,preComment,preUser");
        query.order("-createdAt");
        if (isRefresh){
            query.setSkip(0).setLimit(10).findObjects(new FindListener<UserNewsComment>() {
                @Override
                public void done(List<UserNewsComment> list, BmobException e) {
                    if (e == null){
                        mData.removeAll(list);
                        mData.addAll(0,list);
                        mAdapter.notifyDataSetChanged();
                        mRefresh.finishRefreshing();
//                        dialog.cancel();
                    }else {
                        Toast.makeText(context, R.string.net_error,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            query.setSkip(num).setLimit(10).findObjects(new FindListener<UserNewsComment>() {
                @Override
                public void done(List<UserNewsComment> list, BmobException e) {
                    if (e == null){
                        mData.removeAll(list);
                        mData.addAll(list);
                        mAdapter.notifyDataSetChanged();
                        num +=10;
                        mRefresh.finishLoadmore();
                    }else {
                        Toast.makeText(context, R.string.net_error,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    /**
     * 弹出软键盘
     */
    private void openKeyboard(){
        mInput.setFocusable(true);
        mInput.setFocusableInTouchMode(true);
        mInput.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    /**
     * 关闭软键盘
     */
    private void closeKeyboard(){
//        mInput.setFocusable(false);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //点赞
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
            //点击新闻跳转到详情页
            case R.id.news_layout:
                Intent intent = new Intent(context,NewsDetilActivity.class);
                intent.putExtra("NewsData",commentData);
                startActivity(intent);
                break;
            //发表评论
            case R.id.send_btn:
                if (mInput.getText().toString().length()>0){
                    sendComment(mInput.getText().toString(),preUser);
                }else {
                    ShowToastUtil.showToast(context,R.string.input_comment);
                }


        }
    }

    /**
     * 发送评论
     * @param comment
     * @param preUser
     */
    private void sendComment(final String comment, final MyUserModule preUser){
        final UserNewsComment newsComment = new UserNewsComment();
        newsComment.setUserModule(userModule);
        newsComment.setPreComment(commentData);
        newsComment.setNewsComment(comment);
        newsComment.setLikes(0);
        newsComment.setHates(0);
        newsComment.setCommentSize(0);
        if (isBelow) newsComment.setPreUser(preUser);
        newsComment.setCommentId((int) System.currentTimeMillis());
        newsComment.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null){
//
                    getNewsComment(true);
                    ShowToastUtil.showToast(context,R.string.send_sucess);
                    mInput.setText("");
                    mInput.setHint("回复"+commentData.getUserModule().getUserNicheng()+"：");

                    isBelow = false;
                    closeKeyboard();
                }else {
                    ShowToastUtil.showToast(context,R.string.input_comment);
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length()>0){
            mSend.setSelected(true);
            if (s.length()>=10){
                mInput.setKeyListener(null);
            }
        }else {
            mSend.setSelected(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        preUser = mData.get(position).getUserModule();
        isBelow = true;
        mInput.setHint("回复 "+mData.get(position).getUserModule().getUserNicheng()+"：");
        mInput.setFocusable(true);
        mInput.setFocusableInTouchMode(true);
        mInput.requestFocus();
        closeKeyboard();
    }
}
