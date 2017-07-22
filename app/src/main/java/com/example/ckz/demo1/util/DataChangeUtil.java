package com.example.ckz.demo1.util;

import com.example.ckz.demo1.bean.news.NewsBean;
import com.example.ckz.demo1.bean.db.NewsSaveBean;
import com.example.ckz.demo1.bean.user.news.CommentNews;
import com.example.ckz.demo1.bean.user.news.NewsSaveNetBean;

/**
 * Created by CKZ on 2017/5/22.
 */

public class DataChangeUtil {
    public static NewsSaveBean news2save(NewsBean.ResultBean.ListBean listBean){
        NewsSaveBean newsBean = new NewsSaveBean();
        newsBean.setCategory(listBean.getCategory());
        newsBean.setContent(listBean.getContent());
        newsBean.setPic(listBean.getPic());
        newsBean.setSrc(listBean.getSrc());
        newsBean.setTime(listBean.getTime());
        newsBean.setTitle(listBean.getTitle());
        newsBean.setUrl(listBean.getUrl());
        newsBean.setWeburl(listBean.getWeburl());
        return newsBean;
    }

    public static CommentNews save2news(NewsSaveNetBean saveBean){
        CommentNews listBean = new CommentNews();
        listBean.setCategory(saveBean.getCategory());
        listBean.setContent(saveBean.getContent());
        listBean.setPic(saveBean.getPic());
        listBean.setSrc(saveBean.getSrc());
        listBean.setTime(saveBean.getTime());
        listBean.setTitle(saveBean.getTitle());
        listBean.setUrl(saveBean.getUrl());
        listBean.setWeburl(saveBean.getWeburl());
        return listBean;
    }
    public static CommentNews commentNews(NewsBean.ResultBean.ListBean saveBean){
        CommentNews listBean = new CommentNews();
        listBean.setCategory(saveBean.getCategory());
        listBean.setContent(saveBean.getContent());
        listBean.setPic(saveBean.getPic());
        listBean.setSrc(saveBean.getSrc());
        listBean.setTime(saveBean.getTime());
        listBean.setTitle(saveBean.getTitle());
        listBean.setUrl(saveBean.getUrl());
        listBean.setWeburl(saveBean.getWeburl());
        return listBean;
    }
}
