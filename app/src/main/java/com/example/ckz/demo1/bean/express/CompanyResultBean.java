package com.example.ckz.demo1.bean.express;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by CKZ on 2017/6/22.
 */

public  class CompanyResultBean extends DataSupport implements Serializable{
    /**
     * name : 德邦
     * type : DEPPON
     * letter : D
     * tel : 95353
     * number : 330060412
     */

    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("letter")
    private String letter;
    @SerializedName("tel")
    private String tel;
    @SerializedName("number")
    private String number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}