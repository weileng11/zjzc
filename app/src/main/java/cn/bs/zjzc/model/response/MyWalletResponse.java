package cn.bs.zjzc.model.response;

/**
 * Created by Ming on 2016/6/14.
 */
public class MyWalletResponse extends BaseResponse {

    /**
     * money : 0.00
     * points : 0
     * coupon_num : 0
     */

    public DataBean data;

    public static class DataBean {
        public String money;
        public String points;
        public String coupon_num;
    }
}
