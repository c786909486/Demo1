package com.example.ckz.demo1.activity.news;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.base.BaseActivity;
import com.example.ckz.demo1.bean.news.NewsBean;
import com.example.ckz.demo1.bean.db.NewsSaveBean;
import com.example.ckz.demo1.bean.user.NewsSaveNetBean;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.DataChangeUtil;
import com.example.ckz.demo1.util.LogUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class NewsDetilActivity extends BaseActivity implements View.OnClickListener{
    private static final int NO_COLLECT = 0;
    private static final int COLLECT = 1;
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
    private ImageView mCollection;

    private NewsBean.ResultBean.ListBean listBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detil);
        listBean = (NewsBean.ResultBean.ListBean) getIntent().getSerializableExtra("NewsData");
        userModule = BmobUser.getCurrentUser(MyUserModule.class);
        initView();
        setData();
        isCollected();


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
        mCollection = (ImageView) findViewById(R.id.news_collection);

        mBackBtn.setOnClickListener(this);
        mYuanWen.setOnClickListener(this);
        mCollection.setOnClickListener(this);
    }

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
        mNewsContent.setText(Html.fromHtml(listBean.getContent()));
        //发布时间
        if (!listBean.getTime().equals("")) mTime.setText(listBean.getTime().substring(0,10));
        if (!listBean.getSrc().equals("")) mFrom.setText(listBean.getSrc());
    }

    private void isCollected(){
       if (userModule != null){
           List<NewsSaveBean> mData = DataSupport.where("url = ?",listBean.getUrl()).find(NewsSaveBean.class);
           BmobQuery<NewsSaveNetBean> query = new BmobQuery<NewsSaveNetBean>();
           query.addWhereEqualTo("url",listBean.getUrl()).findObjects(new FindListener<NewsSaveNetBean>() {
               @Override
               public void done(List<NewsSaveNetBean> list, BmobException e) {
                   if (e == null){
                       Log.d("SIZE",list.size()+"");
                       if (list.size() == 0){
                           currentState = NO_COLLECT;
                           mCollection.setSelected(false);
                       }else {
                           currentState = COLLECT;
                           mCollection.setSelected(true);
                       }
                   }
               }
           });
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
                        netBean.setUserPhone(BmobUser.getCurrentUser().getUsername());
                        netBean.setObjectId(listBean.getUrl());
                        netBean.save(new SaveListener<String>() {
                            @Override
                            public void done(String s, BmobException e) {
                                if (e == null){
                                    Toast.makeText(NewsDetilActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();
                                    mCollection.setSelected(true);
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
                            public void done(List<NewsSaveNetBean> list, BmobException e) {
                                if (e == null){
                                    NewsSaveNetBean netBean = new NewsSaveNetBean();
                                    netBean.setObjectId(list.get(0).getObjectId());
                                    LogUtils.d("objectId",list.get(0).getObjectId());
                                    netBean.delete(new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if (e == null){
                                                Toast.makeText(NewsDetilActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
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


                        // sendBroadcast(new Intent("android.intent.action.REFRESH_UI"));

                    }
                }

        }
    }
}
