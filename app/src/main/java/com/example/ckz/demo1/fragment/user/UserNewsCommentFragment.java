package com.example.ckz.demo1.fragment.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ckz.demo1.R;
import com.example.ckz.demo1.activity.news.CommentDetialActivity;
import com.example.ckz.demo1.activity.news.NewsDetilActivity;
import com.example.ckz.demo1.adapter.CommonAdapter;
import com.example.ckz.demo1.bean.user.news.CommentNews;
import com.example.ckz.demo1.bean.user.news.UserNewsComment;
import com.example.ckz.demo1.fragment.base.BaseFragment;
import com.example.ckz.demo1.user.MyUserModule;
import com.example.ckz.demo1.util.LogUtils;
import com.example.ckz.demo1.util.SPUtils;
import com.example.vuandroidadsdk.showpop.ShowPopup;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by CKZ on 2017/7/11.
 */

public class UserNewsCommentFragment extends BaseFragment implements AdapterView.OnItemClickListener{
    private static final String TAG = "UserNewsCommentFragment";
    private TwinklingRefreshLayout mRefresh;
    private ListView mList;

    private List<UserNewsComment> mData;
    private CommonAdapter<UserNewsComment> mAdapter;

    private int num =10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_comment,container,false);
        initView(view);
        setRefresh();
        mRefresh.startRefresh();
        return view;
    }

    private void initView(View view) {
        mRefresh = (TwinklingRefreshLayout) view.findViewById(R.id.refresh_layout);
        mList = (ListView) view.findViewById(R.id.comment_list);
        setList();
    }

    private void setRefresh(){
        ProgressLayout header = new ProgressLayout(getContext());
        header.setColorSchemeResources(R.color.colorPrimary);
        mRefresh.setHeaderView(header);
        mRefresh.setEnableRefresh(false);
        mRefresh.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                getNetData(true);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                getNetData(false);
            }
        });
    }

    private void setList() {
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<UserNewsComment>((ArrayList<UserNewsComment>) mData,R.layout.item_user_comment) {
            @Override
            public void bindView(final ViewHolder holder, final UserNewsComment obj) {
                //设置用户头像
                MyUserModule userModule = obj.getUserModule();
                if (userModule.getUserIcon()!=null) holder.setImage(R.id.user_icon,userModule.getUserIcon());
                //用户昵称
                holder.setText(R.id.user_name,userModule.getUserNicheng());
                //发布时间
                holder.setText(R.id.create_time,obj.getCreatedAt().substring(5,16));
                //点赞
                holder.setText(R.id.ding_btn,String.valueOf(obj.getLikes()));
                if (SPUtils.getBooleanSp(getContext(),obj.getNewsComment()+obj.getCommentId(),false)){
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
                                        SPUtils.putBooleanSp(getContext(),obj.getNewsComment()+obj.getCommentId(),false);
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
                                        SPUtils.putBooleanSp(getContext(),obj.getNewsComment()+obj.getCommentId(),true);
                                    }
                                }
                            });
                        }
                    }
                });

                //显示评论
                if (obj.getPreUser()!=null){
//                    holder.setText(R.id.news_comment,"回复 "+obj.getPreUser().getUserNicheng()+"："+obj.getNewsComment());
                    holder.setText(R.id.news_comment, Html.fromHtml("<font color=\"gray\">回复 "+obj.getPreUser().getUserNicheng()+"：</font>"+obj.getNewsComment()));
                }else {
                    holder.setText(R.id.news_comment,obj.getNewsComment());
                }
                //弹窗按钮
                holder.setVisibility(R.id.popup_btn,View.GONE);
//                holder.setOnClickListener(R.id.popup_btn, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        final ShowPopup popup = new ShowPopup(getContext());
//                        popup.createSimplePopupWindow(getString(R.string.huifu),getString(R.string.dianzan),getString(R.string.cai),
//                                getString(R.string.cancel))
//                                .defaultAnim().atBottom(v).setPositionClickListener(new ShowPopup.OnPositionClickListener() {
//                            @Override
//                            public void OnPositionClick(View view, int position) {
//                                switch (position){
//                                    case 0:
//                                        //跳转到回复界面
//                                        intentTo(position);
//                                        popup.closePopupWindow();
//                                        break;
//                                    case 1:
//                                        //点赞
//                                        holder.getView(R.id.ding_btn).performClick();
//                                        popup.closePopupWindow();
//                                        break;
//                                    case 2:
//                                        //踩
//                                        if (holder.isSelected(R.id.ding_btn)){
//                                            Toast.makeText(getContext(), R.string.isliked,Toast.LENGTH_SHORT).show();
//                                        }else {
//                                            UserNewsComment newsComment = new UserNewsComment();
//                                            newsComment.setObjectId(obj.getObjectId());
//                                            newsComment.setHates(obj.getHates()+1);
//                                            newsComment.update(new UpdateListener() {
//                                                @Override
//                                                public void done(BmobException e) {
//
//                                                }
//                                            });
//                                        }
//                                        popup.closePopupWindow();
//                                        break;
//                                    case 3:
//                                        //取消
//                                        popup.closePopupWindow();
//                                        break;
//                                }
//                            }
//                        });
//                    }
//                });
                CommentNews news = obj.getCommentNews();
                UserNewsComment preNews = obj.getPreComment();

                if (news != null){
                    holder.setImage(R.id.news_pic,news.getPic());
                    holder.setText(R.id.news_content,news.getTitle());
                    LogUtils.d(TAG,"评论"+news.getTitle());
                }else {
                    holder.setImage(R.id.news_pic,preNews.getCommentNews().getPic());
                    holder.setText(R.id.news_content,preNews.getCommentNews().getTitle());
                    LogUtils.d(TAG,"回复"+preNews.getCommentNews().getTitle());
                }

            }
        };
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);
        setEmpty();
    }

    private void setEmpty() {
        TextView empty = new TextView(getContext());
        empty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,350));
        empty.setGravity(Gravity.CENTER);
        empty.setText(R.string.no_user_comment);
        empty.setTextSize(15.0f);
        empty.setVisibility(View.GONE);
        ((ViewGroup)mList.getParent()).addView(empty);
        mList.setEmptyView(empty);
    }

    private void intentTo(int position){
        Intent intent = new Intent(getContext(),NewsDetilActivity.class);
        CommentNews news = mData.get(position).getCommentNews();
        UserNewsComment preNews = mData.get(position).getPreComment();

        if (news != null){
            intent.putExtra("NewsData",news);
            LogUtils.d(TAG,"评论"+news.getTitle());
        }else {
            intent.putExtra("NewsData",preNews.getCommentNews());
            LogUtils.d(TAG,"回复"+preNews.getCommentNews().getTitle());
        }
        startActivity(intent);
    }
    /**
     * 获取网络数据
     */
    private void getNetData(final boolean isRefresh){
        BmobQuery<UserNewsComment> query = new BmobQuery<UserNewsComment>();
        MyUserModule userModule = MyUserModule.getCurrentUser(MyUserModule.class);
        query.addWhereEqualTo("userModule", new BmobPointer(userModule));
        query.include("userModule,preComment.newsSaveNetBean,preUser,newsSaveNetBean");
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
                        Toast.makeText(getContext(), R.string.net_error,Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), R.string.net_error,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        intentTo(position);
    }
}
