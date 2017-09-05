package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Administrator on 2016/6/29.
 */
public class OrderDetail extends BaseResponse {


    /**
     * id : 1
     * order_num : WM1201606281234
     * place_time : 1432654561
     * place_userid : 1
     * type : 1
     * take_x : 14.131324
     * take_y : 13.465471
     * take_address : 天河城
     * take_add_address : 大排档
     * take_province_id : 3
     * take_city_id : 5
     * take_name : 饭店老板
     * take_phone : 18002230461
     * take_time : 1451324546
     * take_real_time : 1461324654
     * receipt_x : 17.161564
     * receipt_y : 20.456521
     * receipt_address : 云城西路
     * receipt_add_address : 今尚科技401c
     * receipt_province_id : 3
     * receipt_city_id : 5
     * receipt_name : 王小明
     * receipt_phone : 18819270482
     * remark_txt : 有汤，别打翻了
     * remark_img : ["/public/upsldjfl.jpg","/public/upsldjfl.jpg"]
     * freight : 8.00
     * distance : 10.00
     * tip_money : 2.00
     * insured : 0.00
     * insured_money : 0.00
     * coupon_money : 0.00
     * goods_name :
     * service_name :
     * service_money : 0.00
     * take_userid : 2
     * take_order_time : 143456461
     * pay_type : 1
     * pay_time : 14646512332
     * balance_pay : 0.00
     * complete_time : 143214423
     * cancel_time : 0
     * cancel_reason :
     * commission : 0.00
     * reward : 0.00
     * state : 3
     * take_province : 广东
     * take_city : 广州
     * receipt_province : 广东
     * receipt_city : 广州
     * money : 10.00
     * other_user : {"phone":"18121445612","name":"送外卖","star":"5.0"}
     * evaluate_state : 0
     */

    public DataBean data;

    public static class DataBean {
        public String id;
        public String order_num;
        public String place_time;
        public String place_userid;
        public String type;
        public String take_x;
        public String take_y;
        public String take_address;
        public String take_add_address;
        public String take_province_id;
        public String take_city_id;
        public String take_name;
        public String take_phone;
        public String take_time;
        public String take_real_time;
        public String receipt_x;
        public String receipt_y;
        public String receipt_address;
        public String receipt_add_address;
        public String receipt_province_id;
        public String receipt_city_id;
        public String receipt_name;
        public String receipt_phone;
        public String remark_txt;
        public String freight;
        public String distance;
        public String tip_money;
        public String insured;
        public String insured_money;
        public String coupon_money;
        public String goods_name;
        public String service_name;
        public String service_money;
        public String take_userid;
        public String take_order_time;
        public String pay_type;
        public String pay_time;
        public String balance_pay;
        public String complete_time;
        public String cancel_time;
        public String cancel_reason;
        public String commission;
        public String reward;
        public String state;
        public String take_province;
        public String take_city;
        public String receipt_province;
        public String receipt_city;
        public String money;
        /**
         * phone : 18121445612
         * name : 送外卖
         * star : 5.0
         */

        public OtherUserBean other_user;
        public int evaluate_state;
        public String can_cancel_time;
        public List<String> remark_img;

        public static class OtherUserBean {
            public String phone;
            public String name;
            public String star;
        }
    }
}
