package com.example.ckz.demo1.bean.news;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CKZ on 2017/5/17.
 */

public class NewsBeanItem implements Serializable{

    /**
     * status : 0
     * msg : ok
     * result : ["头条","新闻","财经","体育","娱乐","军事","教育","科技","NBA","股票","星座","女性","健康","育儿"]
     */

    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private List<String> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getResult() {
        return result;
    }

    public void setResult(List<String> result) {
        this.result = result;
    }
}
