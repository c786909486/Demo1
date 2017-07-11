package com.example.ckz.demo1.bean.express;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CKZ on 2017/6/24.
 */

public class ExpressProgressBean {

    /**
     * status : 0
     * msg : ok
     * result : {"number":"71138768429503","type":"htky","list":[{"time":"2017-6-23 15:57:22","status":"[杭州市] 杭州市【余杭区科技园分部006】，78幢驿站 已签收                        "},{"time":"2017-6-23 15:05:58","status":"[杭州市] 到杭州市【余杭区科技园分部006】                        "},{"time":"2017-6-23 15:05:58","status":"[杭州市] 杭州市【余杭区科技园分部006】，【何大富/13567180950】正在派件                        "},{"time":"2017-6-23 11:10:25","status":"[杭州市] 杭州市【BEX杭州余杭区科技园分部】，正发往【余杭区科技园分部006】                        "},{"time":"2017-6-23 7:18:03","status":"[杭州市] 到杭州市【BEX杭州余杭区科技园分部】                        "},{"time":"2017-6-23 2:03:43","status":"[杭州市] 杭州市【杭州转运中心】，正发往【BEX杭州余杭区科技园分部】                        "},{"time":"2017-6-23 0:34:03","status":"[杭州市] 到杭州市【杭州转运中心】                        "},{"time":"2017-6-22 19:41:42","status":"[金华市] 金华市【永康集散中心】，正发往【杭州转运中心】                        "},{"time":"2017-6-22 17:58:37","status":"[金华市] 到金华市【永康集散中心】                        "},{"time":"2017-6-22 17:10:55","status":"[金华市] 金华市【义乌秦塘分部】，【杜英毅/18358910335】已揽收                        "}],"deliverystatus":"3","issign":"1"}
     */

    @SerializedName("status")
    private String status;
    @SerializedName("msg")
    private String msg;
    @SerializedName("result")
    private ResultBean result;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * number : 71138768429503
         * type : htky
         * list : [{"time":"2017-6-23 15:57:22","status":"[杭州市] 杭州市【余杭区科技园分部006】，78幢驿站 已签收                        "},{"time":"2017-6-23 15:05:58","status":"[杭州市] 到杭州市【余杭区科技园分部006】                        "},{"time":"2017-6-23 15:05:58","status":"[杭州市] 杭州市【余杭区科技园分部006】，【何大富/13567180950】正在派件                        "},{"time":"2017-6-23 11:10:25","status":"[杭州市] 杭州市【BEX杭州余杭区科技园分部】，正发往【余杭区科技园分部006】                        "},{"time":"2017-6-23 7:18:03","status":"[杭州市] 到杭州市【BEX杭州余杭区科技园分部】                        "},{"time":"2017-6-23 2:03:43","status":"[杭州市] 杭州市【杭州转运中心】，正发往【BEX杭州余杭区科技园分部】                        "},{"time":"2017-6-23 0:34:03","status":"[杭州市] 到杭州市【杭州转运中心】                        "},{"time":"2017-6-22 19:41:42","status":"[金华市] 金华市【永康集散中心】，正发往【杭州转运中心】                        "},{"time":"2017-6-22 17:58:37","status":"[金华市] 到金华市【永康集散中心】                        "},{"time":"2017-6-22 17:10:55","status":"[金华市] 金华市【义乌秦塘分部】，【杜英毅/18358910335】已揽收                        "}]
         * deliverystatus : 3
         * issign : 1
         */

        @SerializedName("number")
        private String number;
        @SerializedName("type")
        private String type;
        @SerializedName("deliverystatus")
        private String deliverystatus;
        @SerializedName("issign")
        private String issign;
        @SerializedName("list")
        private List<ListBean> list;

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDeliverystatus() {
            return deliverystatus;
        }

        public void setDeliverystatus(String deliverystatus) {
            this.deliverystatus = deliverystatus;
        }

        public String getIssign() {
            return issign;
        }

        public void setIssign(String issign) {
            this.issign = issign;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * time : 2017-6-23 15:57:22
             * status : [杭州市] 杭州市【余杭区科技园分部006】，78幢驿站 已签收
             */

            @SerializedName("time")
            private String time;
            @SerializedName("status")
            private String status;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }
}
