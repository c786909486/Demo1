package com.example.ckz.demo1.bean.db;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by CKZ on 2017/6/24.
 */

public class ExpressSaveBean extends DataSupport implements Serializable{

    //公司名
    private String companyName;

    //公司代码
    private String companyType;

    //快递单号
    private String expressCode;

    //运输状态
    private String expressState;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getExpressCode() {
        return expressCode;
    }

    public void setExpressCode(String expressCode) {
        this.expressCode = expressCode;
    }

    public String getExpressState() {
        return expressState;
    }

    public void setExpressState(String expressState) {
        this.expressState = expressState;
    }
}
