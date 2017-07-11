package com.example.ckz.demo1.bean.cookbook;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by CKZ on 2017/5/31.
 */

public class CookbookDataBean  implements Serializable{

    /**
     * status : 0
     * msg : ok
     * result : {"num":"10","list":[{"id":"1","classid":"2","name":"炸茄盒","peoplenum":"3-4人","preparetime":"10分钟内","cookingtime":"10-20分钟","content":"炸茄盒外焦里嫩，入口不腻，咬上一口，淡淡的肉香和茄子的清香同时浮现，让人心动不已。","pic":"http://api.jisuapi.com/recipe/upload/20160719/115137_60657.jpg","tag":"健脾开胃,儿童,减肥,宴请,家常菜,小吃,炸,白领,私房菜,聚会","material":[{"mname":"鸡蛋","type":"0","amount":"1个"},{"mname":"姜","type":"0","amount":"适量"},{"mname":"蒜子","type":"0","amount":"适量"},{"mname":"葱","type":"0","amount":"适量"},{"mname":"盐","type":"0","amount":"适量"},{"mname":"白糖","type":"0","amount":"适量"},{"mname":"料酒","type":"0","amount":"适量"},{"mname":"酱油","type":"0","amount":"适量"},{"mname":"老抽","type":"0","amount":"适量"},{"mname":"胡椒粉","type":"0","amount":"适量"},{"mname":"麻油","type":"0","amount":"适量"},{"mname":"面粉","type":"0","amount":"适量"},{"mname":"生粉","type":"0","amount":"适量"},{"mname":"茄子","type":"1","amount":"适量"},{"mname":"猪肉","type":"1","amount":"300g"}],"process":[{"pcontent":"首先我们将猪肉剁成肉泥、姜切成姜米、葱切葱花、蒜子切成蒜末、茄子去皮，然后在每一小段中间切一刀，但不要切断，做成茄盒。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_29953.jpg"},{"pcontent":"然后我们来制作肉馅：将猪肉泥放入盘中，加入姜米、蒜末、葱花、少许盐、少许白糖、适量料酒、少量酱油、少量老抽、少许胡椒粉、淋入食用油、麻油，抓匀，接着再加入生粉，抓打至肉馅变得粘稠。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_42272.jpg"},{"pcontent":"将茄夹中间抹上生粉，用肉馅填满茄夹。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_24315.jpg"},{"pcontent":"填满肉馅后，再逐一将整个茄盒抹上生粉。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_72995.jpg"},{"pcontent":"接着我们来调面糊：准备一个碗，在碗中放入适量面粉、适量生粉、半勺盐、一个鸡蛋拌匀，再加少许清水，拌至粘稠状，备用。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_12103.jpg"},{"pcontent":"锅中放入半锅油，烧至8成热后，将茄盒放入面糊中裹上面糊，再逐个下油锅中，炸至茄盒表面呈金黄色后捞出沥油。这道菜就完成了。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_45617.jpg"}]}]}
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
         * num : 10
         * list : [{"id":"1","classid":"2","name":"炸茄盒","peoplenum":"3-4人","preparetime":"10分钟内","cookingtime":"10-20分钟","content":"炸茄盒外焦里嫩，入口不腻，咬上一口，淡淡的肉香和茄子的清香同时浮现，让人心动不已。","pic":"http://api.jisuapi.com/recipe/upload/20160719/115137_60657.jpg","tag":"健脾开胃,儿童,减肥,宴请,家常菜,小吃,炸,白领,私房菜,聚会","material":[{"mname":"鸡蛋","type":"0","amount":"1个"},{"mname":"姜","type":"0","amount":"适量"},{"mname":"蒜子","type":"0","amount":"适量"},{"mname":"葱","type":"0","amount":"适量"},{"mname":"盐","type":"0","amount":"适量"},{"mname":"白糖","type":"0","amount":"适量"},{"mname":"料酒","type":"0","amount":"适量"},{"mname":"酱油","type":"0","amount":"适量"},{"mname":"老抽","type":"0","amount":"适量"},{"mname":"胡椒粉","type":"0","amount":"适量"},{"mname":"麻油","type":"0","amount":"适量"},{"mname":"面粉","type":"0","amount":"适量"},{"mname":"生粉","type":"0","amount":"适量"},{"mname":"茄子","type":"1","amount":"适量"},{"mname":"猪肉","type":"1","amount":"300g"}],"process":[{"pcontent":"首先我们将猪肉剁成肉泥、姜切成姜米、葱切葱花、蒜子切成蒜末、茄子去皮，然后在每一小段中间切一刀，但不要切断，做成茄盒。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_29953.jpg"},{"pcontent":"然后我们来制作肉馅：将猪肉泥放入盘中，加入姜米、蒜末、葱花、少许盐、少许白糖、适量料酒、少量酱油、少量老抽、少许胡椒粉、淋入食用油、麻油，抓匀，接着再加入生粉，抓打至肉馅变得粘稠。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_42272.jpg"},{"pcontent":"将茄夹中间抹上生粉，用肉馅填满茄夹。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_24315.jpg"},{"pcontent":"填满肉馅后，再逐一将整个茄盒抹上生粉。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_72995.jpg"},{"pcontent":"接着我们来调面糊：准备一个碗，在碗中放入适量面粉、适量生粉、半勺盐、一个鸡蛋拌匀，再加少许清水，拌至粘稠状，备用。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_12103.jpg"},{"pcontent":"锅中放入半锅油，烧至8成热后，将茄盒放入面糊中裹上面糊，再逐个下油锅中，炸至茄盒表面呈金黄色后捞出沥油。这道菜就完成了。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_45617.jpg"}]}]
         */


        @SerializedName("list")
        private List<ListBean> list;



        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            /**
             * id : 1
             * classid : 2
             * name : 炸茄盒
             * peoplenum : 3-4人
             * preparetime : 10分钟内
             * cookingtime : 10-20分钟
             * content : 炸茄盒外焦里嫩，入口不腻，咬上一口，淡淡的肉香和茄子的清香同时浮现，让人心动不已。
             * pic : http://api.jisuapi.com/recipe/upload/20160719/115137_60657.jpg
             * tag : 健脾开胃,儿童,减肥,宴请,家常菜,小吃,炸,白领,私房菜,聚会
             * material : [{"mname":"鸡蛋","type":"0","amount":"1个"},{"mname":"姜","type":"0","amount":"适量"},{"mname":"蒜子","type":"0","amount":"适量"},{"mname":"葱","type":"0","amount":"适量"},{"mname":"盐","type":"0","amount":"适量"},{"mname":"白糖","type":"0","amount":"适量"},{"mname":"料酒","type":"0","amount":"适量"},{"mname":"酱油","type":"0","amount":"适量"},{"mname":"老抽","type":"0","amount":"适量"},{"mname":"胡椒粉","type":"0","amount":"适量"},{"mname":"麻油","type":"0","amount":"适量"},{"mname":"面粉","type":"0","amount":"适量"},{"mname":"生粉","type":"0","amount":"适量"},{"mname":"茄子","type":"1","amount":"适量"},{"mname":"猪肉","type":"1","amount":"300g"}]
             * process : [{"pcontent":"首先我们将猪肉剁成肉泥、姜切成姜米、葱切葱花、蒜子切成蒜末、茄子去皮，然后在每一小段中间切一刀，但不要切断，做成茄盒。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_29953.jpg"},{"pcontent":"然后我们来制作肉馅：将猪肉泥放入盘中，加入姜米、蒜末、葱花、少许盐、少许白糖、适量料酒、少量酱油、少量老抽、少许胡椒粉、淋入食用油、麻油，抓匀，接着再加入生粉，抓打至肉馅变得粘稠。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_42272.jpg"},{"pcontent":"将茄夹中间抹上生粉，用肉馅填满茄夹。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162541_24315.jpg"},{"pcontent":"填满肉馅后，再逐一将整个茄盒抹上生粉。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_72995.jpg"},{"pcontent":"接着我们来调面糊：准备一个碗，在碗中放入适量面粉、适量生粉、半勺盐、一个鸡蛋拌匀，再加少许清水，拌至粘稠状，备用。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_12103.jpg"},{"pcontent":"锅中放入半锅油，烧至8成热后，将茄盒放入面糊中裹上面糊，再逐个下油锅中，炸至茄盒表面呈金黄色后捞出沥油。这道菜就完成了。","pic":"http://api.jisuapi.com/recipe/upload/20160719/162542_45617.jpg"}]
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
                 * mname : 鸡蛋
                 * type : 0
                 * amount : 1个
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
                 * pcontent : 首先我们将猪肉剁成肉泥、姜切成姜米、葱切葱花、蒜子切成蒜末、茄子去皮，然后在每一小段中间切一刀，但不要切断，做成茄盒。
                 * pic : http://api.jisuapi.com/recipe/upload/20160719/162541_29953.jpg
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
}
