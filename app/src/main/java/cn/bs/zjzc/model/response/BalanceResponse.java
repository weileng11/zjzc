package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/15.
 */
public class BalanceResponse extends BaseResponse {

    /**
     * money : 0.00
     * withdrawal : 0.00
     */

    public DataBean data;

    public static class DataBean {
        public String money;//帐号余额
        public String withdrawal;//可提现金额
    }
}
