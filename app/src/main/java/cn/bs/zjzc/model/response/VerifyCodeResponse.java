package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/7.
 */
public class VerifyCodeResponse extends BaseResponse {

    /**
     * token : 6ff6edea43fa03c050377e89ff11ed50
     */

    public DataBean data;

    public static class DataBean {
        public String token;
    }
}
