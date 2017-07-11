package com.example.ckz.demo1.http;

import android.support.annotation.NonNull;

import com.example.ckz.demo1.R;

/**
 * Created by CKZ on 2017/5/11.
 */

public class UrlApi {
    private static String APP_KEY = "288e8058d5fdbc7d";
    /**
     * 新闻类api
     */
    public static class NewsApi{
        private static String ROOT_URL = "http://api.jisuapi.com/news/";

        /**
         * 新闻内容
         */
        public static String newsApi(String type,int start){
            StringBuilder urlBuilder = new StringBuilder(ROOT_URL).append("get").append("?").append("channel=").append(type).append("&start=").append(String.valueOf(start))
                    .append("&num=10").append("&appkey=").append(APP_KEY);
            return urlBuilder.toString();
        }

        /**
         * 新闻类目api
         */
        public static String newsItem (){
            StringBuilder stringBuilder = new StringBuilder(ROOT_URL).append("channel").append("?").append("appkey=").append(APP_KEY);
            return stringBuilder.toString();
        }

        /**
         * 新闻搜索
         */
        public static String newsSearch(String keyword){
            StringBuilder builder = new StringBuilder(ROOT_URL).append("search").append("?").append("keyword=").append(keyword).append("&").append("appkey=").append(APP_KEY);
            return builder.toString();
        }
    }

    /**
     * 菜谱类api
     */
    public static class CookbookApi{
        private static String ROOT_UTL = "http://api.jisuapi.com/recipe/";

        /**
         * 获取分类中的菜谱数据
         */
        public static String getItemCookbook(int classid,int start,int num){
            StringBuilder builder = new StringBuilder(ROOT_UTL).append("byclass").append("?").append("classid=").append(classid).append("&")
                    .append("start=").append(start).append("&").append("num=").append(num).append("&").append("appkey=").append(APP_KEY);
            return builder.toString();
        }

        /**
         * 获取分类列表
         */
        public static String getItem(){
            StringBuilder builder = new StringBuilder(ROOT_UTL).append("class").append("?").append("appkey=").append(APP_KEY);
            return builder.toString();
        }

        /**
         * 根据id查询彩票
         */
        public static String getCookbookById(int id){
            StringBuilder builder = new StringBuilder(ROOT_UTL).append("detail").append("?").append("id=").append(id).append("&").append("appkey=").append(APP_KEY);
            return builder.toString();
        }

        /**
         * 搜索菜谱
         */
        public static String searchCookbook(String keyword,int num){
            StringBuilder builder = new StringBuilder(ROOT_UTL).append("search").append("?").append("keyword=").append(keyword).append("&")
                    .append("num=").append(num).append("&").append("appkey=").append(APP_KEY);
            return builder.toString();
        }
    }

    /**
     * 快递类api
     */
    public static class ExpressApi{
        private static String ROOT_URL = "http://api.jisuapi.com/express/";

        public static String getCompany(){
            StringBuilder builder = new StringBuilder(ROOT_URL).append("type").append("?").append("appkey").append("=").append(APP_KEY);
            return builder.toString();
        }

        public static String queryExpress(String type,String code){
            StringBuilder builder = new StringBuilder(ROOT_URL).append("query").append("?").append("appkey").append("=").append(APP_KEY)
                    .append("&").append("type").append("=").append(type).append("&").append("number").append("=").append(code);
            return builder.toString();
        }
    }

}
