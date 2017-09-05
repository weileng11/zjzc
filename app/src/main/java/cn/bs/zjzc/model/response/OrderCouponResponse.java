package cn.bs.zjzc.model.response;

import java.io.Serializable;
import java.util.List;

/**
 * 订单可用优惠券
 * Created by mgc on 2016/7/2.
 */
public class OrderCouponResponse extends BaseResponse {

    /**
     * coupon_id : 1
     * end_time : 132156465
     * type : 1
     * name : 12323
     * discount : 5
     * max_money : 50.00
     * proportion :
     * surplus_money :
     * money :
     */

    public List<DataBean> data;

    @Override
    public String toString() {
        return "OrderCouponResponse{" +
                "data=" + data +
                '}';
    }

    public static class DataBean implements Serializable{
        public String coupon_id;//优惠券id
        public String end_time;//过期时间
        public String type;//优惠券类型（1 折扣券，2 优惠券）
        public String name;//优惠券名称
        public String discount;//折扣
        public String max_money;//最大抵扣金额
        public String proportion;//优惠券使用比例
        public String surplus_money;//优惠券剩余金额
        public String money;//优惠券总金额

        @Override
        public String toString() {
            return "DataBean{" +
                    "coupon_id='" + coupon_id + '\'' +
                    ", end_time='" + end_time + '\'' +
                    ", type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", discount='" + discount + '\'' +
                    ", max_money='" + max_money + '\'' +
                    ", proportion='" + proportion + '\'' +
                    ", surplus_money='" + surplus_money + '\'' +
                    ", money='" + money + '\'' +
                    '}';
        }
    }
}
