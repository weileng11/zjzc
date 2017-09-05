package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/13.
 */
public class UserDataResponse extends BaseResponse {
    /**
     * name : 王小明
     * phone : 123456789
     * money : 0.00
     * points : 50
     * age : 90后
     * sex : 男
     * company_job : 博纳移动-php程序员
     * head_img : http://aaa/a.jpg
     * type : 普通用户
     * state : 1
     * level : 新用户
     * industry : 金融业
     * star : 3
     * apply_state : 0
     */

    public DataBean data;

    public static class DataBean {
        public String name;
        public String phone;
        public String money;
        public String points;
        public String age;
        public String sex;
        public String company_job;
        public String head_img;
        public String type;
        public String state;
        public String level;
        public String industry;
        public String star;
        public String apply_state;
    }



}
