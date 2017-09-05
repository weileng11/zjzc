package cn.bs.zjzc.model.response;

/**
 * 获取订单总费用--支付前
 * Created by mgc on 2016/7/1.
 */
public class OrderAmountResponse extends BaseResponse {


    /**
     * total_money : 44
     * coupon_money : 0.00
     * money : 63284.24
     */

    public DataBean data;

    public static class DataBean {
        public String total_money;
        public String coupon_money;
        public String money;

        @Override
        public String toString() {
            return "DataBean{" +
                    "total_money=" + total_money +
                    ", coupon_money='" + coupon_money + '\'' +
                    ", money='" + money + '\'' +
                    '}';
        }
    }
}
