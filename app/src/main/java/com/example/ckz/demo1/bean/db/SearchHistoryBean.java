package com.example.ckz.demo1.bean.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by CKZ on 2017/5/26.
 */

public class SearchHistoryBean extends DataSupport implements Serializable{

    private String history;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}
