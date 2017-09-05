package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/6/29.
 */
public class OrderListResponse extends BaseResponse {


    /**
     * page : {"page":"1","page_count":"10","record_count":"100"}
     * list : [{"order_id":"1","type":"1","money":"5","take_time":"1434513245","place_time":"1453421312","freight":"4","tip_money":"1","insured":"0.00","insured_money":"0.00","goods_name":"","service_name":"","service_money":"0.00","take_address":"天河城","take_add_address":"大排档","distance":"23","receipt_address":"云城西路","receipt_add_address":"今尚科技401c","coupon_money":"0.00","state":"1","evaluate_state":"0"}]
     */

    public DataBean data;

    public static class DataBean {
        /**
         * page : 1
         * page_count : 10
         * record_count : 100
         */

        public PageBean page;
        /**
         * order_id : 1
         * type : 1
         * money : 5
         * take_time : 1434513245
         * place_time : 1453421312
         * freight : 4
         * tip_money : 1
         * insured : 0.00
         * insured_money : 0.00
         * goods_name :
         * service_name :
         * service_money : 0.00
         * take_address : 天河城
         * take_add_address : 大排档
         * distance : 23
         * receipt_address : 云城西路
         * receipt_add_address : 今尚科技401c
         * coupon_money : 0.00
         * state : 1
         * evaluate_state : 0
         */

        public List<ListBean> list;

        public static class PageBean {
            public String page;
            public String page_count;
            public String record_count;
        }

        public static class ListBean {
            public String order_id;
            public String type;
            public String money;
            public String take_time;
            public String place_time;
            public String freight;
            public String tip_money;
            public String insured;
            public String insured_money;
            public String goods_name;
            public String service_name;
            public String service_money;
            public String take_address;
            public String take_add_address;
            public String distance;
            public String receipt_address;
            public String receipt_add_address;
            public String coupon_money;
            public String state;
            public String evaluate_state;
        }
    }
}
