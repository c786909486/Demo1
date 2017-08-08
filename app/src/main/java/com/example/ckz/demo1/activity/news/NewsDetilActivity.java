package com.example.ckz.demo1.activity.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.ckz.demo1.activity.user.LoginAcrivity;
import com.example.ckz.demo1.adapter.CommonAdapter;
import com.example.ckz.demo1.bean.user.news.CommentNews;
import com.example.ckz.demo1.bean.user.news.NewsSaveNetBean;
import com.example.ckz.demo1.bean.user.news.UserNewsComment;
import com.example.ckz.demo1.cache.ACache;
import com.example.ckz.demo1.refreshheader.ProgressLayout;
import com.example.ckz.demo1.user.MyUserManager;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.util.SPUtils;
import com.example.ckz.demo1.util.ScreenUtils;
import com.example.ckz.demo1.view.LoadingDialog;
import com.example.ckz.demo1.view.NestedListView;
import com.example.vuandroidadsdk.showpop.ShowPopup;
import com.example.vuandroidadsdk.utils.ShowToastUtil;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class NewsDetilActivity extends BaseActivity implements View.OnClickListener,TextWatcher,AdapterView.OnItemClickListener{
    private static final int NO_COLLECT = 0;
    private static final int COLLECT = 1;
    private static final int REQUEST_CODE = 100;
    private int currentState;

    private MyUserModule userModule;

    /**
     * UI
     */
    private ImageView mBackBtn;
    private TextView mNewsTitle;
    private ImageView mNewsPicture;
    private TextView mNewsContent;
    private TextView mTile;
    private TextView mTime;
    private TextView mFrom;
    private TextView mYuanWen;
    private NestedListView mList;
    private TwinklingRefreshLayout mRefresh;
    /**
     * bottomUI
     */
    private EditText mInput;
    private TextView mSend;
    private ImageView mCollection;

    private  LoadingDialog dialog;

    private List<UserNewsComment> mData;
    private CommonAdapter<UserNewsComment> mAdapter;
    private CommentNews listBean;

    private String objectId;
    private CommentNews news;
    private boolean isSave = false;

    private int num = 10;
    private ACache mACache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detil);
        userModule = BmobUser.getCurrentUser(MyUserModule.class);
        listBean = (CommentNews) getIntent().getSerializableExtra("NewsData");
        mACache = ACache.get(this);
        dialog = new LoadingDialog(this);
        saved();
        initView();
        setData();
        isCollected();
        setList();
        setRefresh();
    }

    private void setRefresh() {
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
                if (SPUtils.getBooleanSp(NewsDetilActivity.this,obj.getNewsComment()+obj.getCommentId(),false)){
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
                                        SPUtils.putBooleanSp(NewsDetilActivity.this,obj.getNewsComment()+obj.getCommentId(),false);
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
                                        SPUtils.putBooleanSp(NewsDetilActivity.this,obj.getNewsComment()+obj.getCommentId(),true);
                                    }
                                }
                            });
                        }
                    }
                });

                //显示评论
                holder.setText(R.id.news_comment,obj.getNewsComment());
                //弹窗按钮
                holder.setOnClickListener(R.id.popup_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final ShowPopup popup = ShowPopup.getInstance(NewsDetilActivity.this);
                        popup.createSimplePopupWindow(getString(R.string.huifu),getString(R.string.dianzan),getString(R.string.cai),
                                getString(R.string.cancel))
                                .defaultAnim().atBottom(v).setPositionClickListener(new ShowPopup.OnPositionClickListener() {
                            @Override
                            public void OnPositionClick(PopupWindow popupWindow,View view, int position) {
                                switch (position){
                                    case 0:
                                        //跳转到回复界面
                                        intentTo(position);
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
                                            Toast.makeText(NewsDetilActivity.this, R.string.isliked,Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onStart() {
        super.onStart();
        userModule = BmobUser.getCurrentUser(MyUserModule.class);
    }

    private void initView() {
        mBackBtn = (ImageView) findViewById(R.id.back_btn);
        mNewsTitle = (TextView) findViewById(R.id.news_detail_title);
        mNewsPicture = (ImageView) findViewById(R.id.news_picture);
        mNewsContent = (TextView) findViewById(R.id.news_content);
        mTile = (TextView) findViewById(R.id.news_title);
        mTime = (TextView) findViewById(R.id.publish_time);
        mFrom = (TextView) findViewById(R.id.from);
        mYuanWen = (TextView) findViewById(R.id.read_yuan);
        mRefresh = (TwinklingRefreshLayout) findViewById(R.id.refresh_layout);
        mCollection = (ImageView) findViewById(R.id.news_collection);
        mInput = (EditText) findViewById(R.id.comment_input);
        mSend = (TextView) findViewById(R.id.send_btn);
        mList = (NestedListView) findViewById(R.id.comment_list);

        mRefresh.setHeaderView(new ProgressLayout(this));

        mBackBtn.setOnClickListener(this);
        mYuanWen.setOnClickListener(this);
        mCollection.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mInput.addTextChangedListener(this);
        mList.setOnItemClickListener(this);
    }

    /**
     * 设置显示内容
     */
    private void setData(){
        //标题
        mNewsTitle.setText(listBean.getTitle());
        mTile.setText(listBean.getTitle());
        //图片
        if (listBean.getPic().equals("")){
            mNewsPicture.setVisibility(View.GONE);
        }else {
            Glide.with(this).load(listBean.getPic()).placeholder(R.mipmap.bg_activities_item_end_transparent).diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate().into(mNewsPicture);
        }
        //内容


        mNewsContent.setText(Html.fromHtml(listBean.getContent(),imageGetter,null));

        //发布时间
        if (!listBean.getTime().equals("")) mTime.setText(listBean.getTime().substring(0,10));
        if (!listBean.getSrc().equals("")) mFrom.setText(listBean.getSrc());
    }
    private Drawable drawable;
    Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String s) {
            if (drawable != null) {
                Log.d("TAG", "显示");
                return drawable;
            }
            else {
                Log.d("TAG", "加载"+s);
                initDrawable(s);
            }
            return null;
        }
    };

    /**
     * 加载网络图片
     * @param s
     */
    private void initDrawable(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Drawable drawable = Drawable.createFromStream(new URL(s).openStream(), "");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int width = ScreenUtils.getScreenWidth(NewsDetilActivity.this);

                            if (drawable != null) {
                                float picWid = drawable.getIntrinsicWidth();
                                float picHeight = drawable.getIntrinsicHeight();
                                Log.d("size","width:"+picWid+"\nheight:"+picHeight+"\nbili:"+picHeight/picWid);
                                drawable.setBounds(0,0, width, (int)((picHeight/picWid)*width));
                                NewsDetilActivity.this.drawable = drawable;
                                if (Build.VERSION.SDK_INT >= 24)
                                    mNewsContent.setText(Html.fromHtml(listBean.getContent(),Html.FROM_HTML_MODE_COMPACT,imageGetter,null));
                                else
                                    mNewsContent.setText(Html.fromHtml(listBean.getContent(),imageGetter,null));
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 判断新闻是否被收藏
     */
    private void isCollected(){
       if (userModule != null){
          if (SPUtils.getBooleanSp(NewsDetilActivity.this,listBean.getUrl()+userModule.getUserId(),false)){
              currentState = COLLECT;
              mCollection.setSelected(true);
          }else {
              BmobQuery<NewsSaveNetBean> query = new BmobQuery<NewsSaveNetBean>();
              query.addWhereEqualTo("url",listBean.getUrl()).addWhereEqualTo("userPhone",userModule.getMobilePhoneNumber()).findObjects(new FindListener<NewsSaveNetBean>() {
                  @Override
                  public void done(List<NewsSaveNetBean> list, BmobException e) {
                      if (e == null){
                          Log.d("SIZE",list.size()+"");
                          if (list.size() == 0){
                              currentState = NO_COLLECT;
                              mCollection.setSelected(false);
                              SPUtils.putBooleanSp(NewsDetilActivity.this,listBean.getUrl()+userModule.getUserId(),false);
                          }else {
                              currentState = COLLECT;
                              mCollection.setSelected(true);
                              SPUtils.putBooleanSp(NewsDetilActivity.this,listBean.getUrl()+userModule.getUserId(),true);
                          }
                      }else {
                          LogUtils.d("SAVE","未登陆");
                      }
                  }
              });
          }
       }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.read_yuan:
                //跳转webView界面
                Intent intent = new Intent(this,WebActivity.class);
                intent.putExtra("webUrl",listBean.getWeburl());
                startActivity(intent);
                break;
            //新闻收藏
            case R.id.news_collection:
                if (userModule!=null){
                    if (currentState == NO_COLLECT){
                        NewsSaveNetBean netBean = new NewsSaveNetBean();
                        netBean.setCategory(listBean.getCategory());
                        netBean.setContent(listBean.getContent());
                        netBean.setPic(listBean.getPic());
                        netBean.setSrc(listBean.getSrc());
                        netBean.setTime(listBean.getTime());
                        netBean.setTitle(listBean.getTitle());
                        netBean.setUrl(listBean.getUrl());
                        netBean.setWeburl(listBean.getWeburl());
                        netBean.setUserId(userModule.getUserId());
                        netBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null){
                                    Toast.makeText(NewsDetilActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                                    mCollection.setSelected(true);
                                    SPUtils.putBooleanSp(NewsDetilActivity.this,listBean.getUrl()+userModule.getUserId(),true);
                                    currentState = COLLECT;
                                }else {
                                    Toast.makeText(NewsDetilActivity.this,"收藏失败"+e.toString(),Toast.LENGTH_SHORT).show();
                                    LogUtils.d("failed",e.toString());
                                }
                            }
                        });

                    }else if (currentState == COLLECT){

                        BmobQuery<NewsSaveNetBean> query = new BmobQuery<NewsSaveNetBean>();
                        query.addWhereEqualTo("url",listBean.getUrl()).addWhereEqualTo("userPhone",userModule.getMobilePhoneNumber()).findObjects(new FindListener<NewsSaveNetBean>() {
                            @Override
                            public void done(final List<NewsSaveNetBean> list, BmobException e) {
                                if (e == null){
                                    NewsSaveNetBean netBean = new NewsSaveNetBean();
                                    netBean.setObjectId(list.get(0).getObjectId());
                                    LogUtils.d("objectId",list.get(0).getObjectId());
                                    netBean.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                Toast.makeText(NewsDetilActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
                                                SPUtils.putBooleanSp(NewsDetilActivity.this,listBean.getUrl()+userModule.getUserId(),false);
                                            }else {
                                                Toast.makeText(NewsDetilActivity.this,"取消收藏失败",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    mCollection.setSelected(false);
                                    currentState = NO_COLLECT;
                                }else {
                                    Toast.makeText(NewsDetilActivity.this,"取消收藏失败",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }else {
                    Intent intent1 = new Intent(NewsDetilActivity.this, LoginAcrivity.class);
                    startActivityForResult(intent1,REQUEST_CODE);
                }
                break;
            //发送评论
            case R.id.send_btn:
               if (mInput.getText().toString().length()>0){
                   if (MyUserModule.getCurrentUser()!=null){
                       MyUserManager.getInstance(NewsDetilActivity.this).sendComment(mInput.getText().toString(), news, new SaveListener<String>() {
                           @Override
                           public void done(String s, BmobException e) {
                               if (e == null) {
                                   mInput.postDelayed(new Runnable() {
                                       @Override
                                       public void run() {
                                           getNewsComment(true);
                                       }
                                   }, 200);
                               }
                               ShowToastUtil.showToast(NewsDetilActivity.this,R.string.send_sucess);
                               mInput.setText("");
                               InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                               imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                           }
                       });
                   }else {
                       startActivity(new Intent(NewsDetilActivity.this,LoginAcrivity.class));
                   }
               }else {
                   ShowToastUtil.showToast(NewsDetilActivity.this,R.string.input_comment);
               }
               break;

        }
    }

    /**
     * 新闻是否保存
     * @return
     */
    private boolean saved(){

        dialog.show();
        if (mACache.getAsObject(listBean.getUrl()+"bean")!=null){
            news = (CommentNews) mACache.getAsObject(listBean.getUrl()+"bean");
            objectId = mACache.getAsString(listBean.getUrl());
            LogUtils.d("currentData",news.getTitle()+objectId);
            getNewsComment(true);
        }else {
            BmobQuery<CommentNews> query = new BmobQuery<CommentNews>();
            query.addWhereEqualTo("url",listBean.getUrl()).findObjects(new FindListener<CommentNews>() {
                @Override
                public void done(List<CommentNews> list, BmobException e) {
                    if (e == null) {

                        if (list.size() != 0) {
                            isSave = true;
                            objectId = list.get(0).getObjectId();
                            news = list.get(0);
                            mACache.put(listBean.getUrl(),objectId);
                            mACache.put(listBean.getUrl()+"bean",news);
                            LogUtils.d("netData",news.getTitle()+objectId);
                            getNewsComment(true);
                        } else {
                            isSave = false;
                            news = new CommentNews();
                            news.setCategory(listBean.getCategory());
                            news.setContent(listBean.getContent());
                            news.setPic(listBean.getPic());
                            news.setSrc(listBean.getSrc());
                            news.setTime(listBean.getTime());
                            news.setTitle(listBean.getTitle());
                            news.setUrl(listBean.getUrl());
                            news.setWeburl(listBean.getWeburl());
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    news.save(new SaveListener<String>() {
                                        @Override
                                        public void done(String s, BmobException e) {
                                            if (e == null){
                                                objectId = news.getObjectId();
                                                LogUtils.d("objectId",objectId);
                                                mACache.put(listBean.getUrl(),objectId);
                                                mACache.put(listBean.getUrl()+"bean",news);
                                                LogUtils.d("netData",news.getTitle()+objectId);
//                                                getNewsComment(true);
                                                dialog.cancel();
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }


                    }

                }
            });
        }

        return isSave;
    }

    /**
     * 获取新闻评论
     */
    private void getNewsComment(boolean isRefresh){
        BmobQuery<UserNewsComment> query = new BmobQuery<UserNewsComment>();
        final CommentNews news = new CommentNews();
        news.setObjectId(objectId);
        query.addWhereEqualTo("newsSaveNetBean",new BmobPointer(news));
        query.include("userModule,newsSaveNetBean");
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
                       dialog.cancel();
                   }else {
                       Toast.makeText(NewsDetilActivity.this, R.string.net_error,Toast.LENGTH_SHORT).show();
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
                       Toast.makeText(NewsDetilActivity.this, R.string.net_error,Toast.LENGTH_SHORT).show();
                   }
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:
                if (resultCode == RESULT_OK){
                    mCollection.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mCollection.performClick();
                        }
                    },1000);
                }
                break;
        }
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
       intentTo(position);
    }

    private void intentTo(int position){
        Intent intent = new Intent(this,CommentDetialActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("commentData",mData.get(position));
        intent.putExtra("bundleData",bundle);
        startActivity(intent);
    }
}
