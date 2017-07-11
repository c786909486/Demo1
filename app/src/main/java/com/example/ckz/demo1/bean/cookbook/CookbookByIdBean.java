package com.example.ckz.demo1.bean.cookbook;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CKZ on 2017/6/15.
 */

public class CookbookByIdBean implements Serializable{

    /**
     * status : 0
     * msg : ok
     * result : {"id":"22146","classid":"224","name":"毛血旺","peoplenum":"3-4人","preparetime":"10-20分钟","cookingtime":"10-20分钟","content":"毛血旺--四川重庆的风味名菜。<br>据说1970年前，重庆瓷器口古镇水码头有一胖大嫂当街支起卖杂碎汤的小摊，用猪头肉、猪骨加豌豆熬成汤，加入猪肺叶、肥肠，放入老姜、花椒，料酒用小火煨制，味道特别好。<br>在一个偶然机会，胖大嫂在杂碎汤里直接放入鲜生猪血旺，发现血旺越煮越嫩，味道更鲜，这道菜是将生血旺现烫现吃，遂取名毛血旺。 <br>时光像江陵江无声地流逝，麻辣诱惑对传统的毛血旺进行了改良和创新，将其汤汁红亮、麻辣烫鲜香、味浓味厚的特点不断发扬光大。<br>现在已是风靡大江南北的一道川味美味佳肴，受到了大众的喜爱。","pic":"http://api.jisuapi.com/recipe/upload/20160719/165256_19378.jpg","tag":"川菜,热菜,香辣","material":[{"mname":"蒜瓣","type":"0","amount":"6个"},{"mname":"葱姜","type":"0","amount":"适量"},{"mname":"香菜","type":"0","amount":"适量"},{"mname":"鸡精","type":"0","amount":"适量"},{"mname":"白糖","type":"0","amount":"2g"},{"mname":"料酒","type":"0","amount":"20ml"},{"mname":"生抽","type":"0","amount":"15ml"},{"mname":"郫县豆瓣酱","type":"0","amount":"50g"},{"mname":"红油火锅底料","type":"0","amount":"50g"},{"mname":"花椒","type":"0","amount":"5g"},{"mname":"红辣椒","type":"0","amount":"5个"},{"mname":"盐","type":"0","amount":"适量"},{"mname":"油","type":"0","amount":"适量"},{"mname":"盐方火腿","type":"1","amount":"50g"},{"mname":"黄鳝","type":"1","amount":"2条"},{"mname":"莴笋","type":"1","amount":"1根"},{"mname":"黄豆芽","type":"1","amount":"100g"},{"mname":"牛百叶","type":"1","amount":"250g"},{"mname":"鸭血","type":"1","amount":"300g"}],"process":[{"pcontent":"准备好所有的食材。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_51783.jpg"},{"pcontent":"莴笋去皮切块，锅中加少许盐把莴笋焯烫过凉。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_14503.jpg"},{"pcontent":"放入洗净的黄豆芽焯烫2分钟过凉。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_76886.jpg"},{"pcontent":"再放入牛百叶焯烫捞出过凉。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_90620.jpg"},{"pcontent":"放入去骨的黄鳝片焯烫，洗去上面的粘液备用。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_17260.jpg"},{"pcontent":"再把鸭血切块煮上2分钟过凉备用","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_48665.jpg"},{"pcontent":"把所需要的调味准备好。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_61499.jpg"},{"pcontent":"炒锅倒油爆香葱姜蒜。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_16612.jpg"},{"pcontent":"加入郫县豆瓣酱和火锅底料炒出香味。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100713_61205.jpg"},{"pcontent":"然后加入适量的清水。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100713_62099.jpg"},{"pcontent":"加入生抽。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100714_46469.jpg"},{"pcontent":"加入白糖。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100714_63429.jpg"},{"pcontent":"然后放入血块。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_61030.jpg"},{"pcontent":"加入黄鳝片。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_39565.jpg"},{"pcontent":"加入料酒煮上5-8分钟。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_85707.jpg"},{"pcontent":"然后再放入牛百叶。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_73622.jpg"},{"pcontent":"加入黄豆芽。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_30797.jpg"},{"pcontent":"加入莴笋快继续煮上2-3分钟。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_24829.jpg"},{"pcontent":"这是可以另取一个锅加入香油小火炸香黄花椒和红辣椒。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_83378.jpg"},{"pcontent":"血旺中最后放入火腿，加鸡精调味关火。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100716_15055.jpg"},{"pcontent":"把毛血旺盛入碗中浇入炸好的麻辣油。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100716_57293.jpg"},{"pcontent":"最后可撒上一些香菜即可。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100716_86127.jpg"}]}
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

    public static class ResultBean implements Serializable{
        /**
         * id : 22146
         * classid : 224
         * name : 毛血旺
         * peoplenum : 3-4人
         * preparetime : 10-20分钟
         * cookingtime : 10-20分钟
         * content : 毛血旺--四川重庆的风味名菜。<br>据说1970年前，重庆瓷器口古镇水码头有一胖大嫂当街支起卖杂碎汤的小摊，用猪头肉、猪骨加豌豆熬成汤，加入猪肺叶、肥肠，放入老姜、花椒，料酒用小火煨制，味道特别好。<br>在一个偶然机会，胖大嫂在杂碎汤里直接放入鲜生猪血旺，发现血旺越煮越嫩，味道更鲜，这道菜是将生血旺现烫现吃，遂取名毛血旺。 <br>时光像江陵江无声地流逝，麻辣诱惑对传统的毛血旺进行了改良和创新，将其汤汁红亮、麻辣烫鲜香、味浓味厚的特点不断发扬光大。<br>现在已是风靡大江南北的一道川味美味佳肴，受到了大众的喜爱。
         * pic : http://api.jisuapi.com/recipe/upload/20160719/165256_19378.jpg
         * tag : 川菜,热菜,香辣
         * material : [{"mname":"蒜瓣","type":"0","amount":"6个"},{"mname":"葱姜","type":"0","amount":"适量"},{"mname":"香菜","type":"0","amount":"适量"},{"mname":"鸡精","type":"0","amount":"适量"},{"mname":"白糖","type":"0","amount":"2g"},{"mname":"料酒","type":"0","amount":"20ml"},{"mname":"生抽","type":"0","amount":"15ml"},{"mname":"郫县豆瓣酱","type":"0","amount":"50g"},{"mname":"红油火锅底料","type":"0","amount":"50g"},{"mname":"花椒","type":"0","amount":"5g"},{"mname":"红辣椒","type":"0","amount":"5个"},{"mname":"盐","type":"0","amount":"适量"},{"mname":"油","type":"0","amount":"适量"},{"mname":"盐方火腿","type":"1","amount":"50g"},{"mname":"黄鳝","type":"1","amount":"2条"},{"mname":"莴笋","type":"1","amount":"1根"},{"mname":"黄豆芽","type":"1","amount":"100g"},{"mname":"牛百叶","type":"1","amount":"250g"},{"mname":"鸭血","type":"1","amount":"300g"}]
         * process : [{"pcontent":"准备好所有的食材。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_51783.jpg"},{"pcontent":"莴笋去皮切块，锅中加少许盐把莴笋焯烫过凉。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_14503.jpg"},{"pcontent":"放入洗净的黄豆芽焯烫2分钟过凉。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_76886.jpg"},{"pcontent":"再放入牛百叶焯烫捞出过凉。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100711_90620.jpg"},{"pcontent":"放入去骨的黄鳝片焯烫，洗去上面的粘液备用。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_17260.jpg"},{"pcontent":"再把鸭血切块煮上2分钟过凉备用","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_48665.jpg"},{"pcontent":"把所需要的调味准备好。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_61499.jpg"},{"pcontent":"炒锅倒油爆香葱姜蒜。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100712_16612.jpg"},{"pcontent":"加入郫县豆瓣酱和火锅底料炒出香味。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100713_61205.jpg"},{"pcontent":"然后加入适量的清水。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100713_62099.jpg"},{"pcontent":"加入生抽。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100714_46469.jpg"},{"pcontent":"加入白糖。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100714_63429.jpg"},{"pcontent":"然后放入血块。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_61030.jpg"},{"pcontent":"加入黄鳝片。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_39565.jpg"},{"pcontent":"加入料酒煮上5-8分钟。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_85707.jpg"},{"pcontent":"然后再放入牛百叶。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_73622.jpg"},{"pcontent":"加入黄豆芽。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_30797.jpg"},{"pcontent":"加入莴笋快继续煮上2-3分钟。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_24829.jpg"},{"pcontent":"这是可以另取一个锅加入香油小火炸香黄花椒和红辣椒。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100715_83378.jpg"},{"pcontent":"血旺中最后放入火腿，加鸡精调味关火。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100716_15055.jpg"},{"pcontent":"把毛血旺盛入碗中浇入炸好的麻辣油。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100716_57293.jpg"},{"pcontent":"最后可撒上一些香菜即可。","pic":"http://api.jisuapi.com/recipe/upload/20160802/100716_86127.jpg"}]
         */

        @SerializedName("id")
        private String id;
        @SerializedName("classid")
        private String classid;
        @SerializedName("name")
        private String name;
        @SerializedName("peoplenum")
        private String peoplenum;
        @SerializedName("preparetime")
        private String preparetime;
        @SerializedName("cookingtime")
        private String cookingtime;
        @SerializedName("content")
        private String content;
        @SerializedName("pic")
        private String pic;
        @SerializedName("tag")
        private String tag;
        @SerializedName("material")
        private List<MaterialBean> material;
        @SerializedName("process")
        private List<ProcessBean> process;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPeoplenum() {
            return peoplenum;
        }

        public void setPeoplenum(String peoplenum) {
            this.peoplenum = peoplenum;
        }

        public String getPreparetime() {
            return preparetime;
        }

        public void setPreparetime(String preparetime) {
            this.preparetime = preparetime;
        }

        public String getCookingtime() {
            return cookingtime;
        }

        public void setCookingtime(String cookingtime) {
            this.cookingtime = cookingtime;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public List<MaterialBean> getMaterial() {
            return material;
        }

        public void setMaterial(List<MaterialBean> material) {
            this.material = material;
        }

        public List<ProcessBean> getProcess() {
            return process;
        }

        public void setProcess(List<ProcessBean> process) {
            this.process = process;
        }

        public static class MaterialBean implements Serializable{
            /**
             * mname : 蒜瓣
             * type : 0
             * amount : 6个
             */

            @SerializedName("mname")
            private String mname;
            @SerializedName("type")
            private String type;
            @SerializedName("amount")
            private String amount;

            public String getMname() {
                return mname;
            }

            public void setMname(String mname) {
                this.mname = mname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }
        }

        public static class ProcessBean implements Serializable{
            /**
             * pcontent : 准备好所有的食材。
             * pic : http://api.jisuapi.com/recipe/upload/20160802/100711_51783.jpg
             */

            @SerializedName("pcontent")
            private String pcontent;
            @SerializedName("pic")
            private String pic;

            public String getPcontent() {
                return pcontent;
            }

            public void setPcontent(String pcontent) {
                this.pcontent = pcontent;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
