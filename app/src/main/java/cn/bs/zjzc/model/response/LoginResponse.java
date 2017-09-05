package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/7.
 */
public class LoginResponse extends BaseResponse {


    /**
     * token : ed9e94b375e9df0604df8153b9c7b0b2
     * head_img : public/default.jpg
     * name : 张三
     * phone : 131***7552
     * level : 新用户
     * code : 1234
     */

    public DataBean data;

    public static class DataBean {
        public String token;
        public String head_img;
        public String name;
        public String phone;
        public String level;
        public String code;
    }
}
