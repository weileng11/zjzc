package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/6.
 */
public class FinishRegisterResponse extends BaseResponse {

    /**
     * head_img :
     * name : 颜如玉
     * phone : 185***0520
     * level : 新用户
     */

    public DataBean data;

    public static class DataBean {
        public String head_img;
        public String name;
        public String phone;
        public String level;
    }
}
