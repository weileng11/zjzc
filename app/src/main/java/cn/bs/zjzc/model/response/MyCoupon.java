package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Administrator on 2016/7/14.
 */
public class MyCoupon extends BaseResponse {

    /**
     * page : {"page":"1","page_count":"10","record_count":"100"}
     * list : [{"coupon_id":"1","end_time":"132156465","type":"1","name":"12323","discount":"5","max_money":"50.00","proportion":"","surplus_money":"","money":""},{"coupon_id":"2","end_time":"132156465","type":"2","name":"12323","discount":"","max_money":"","proportion":"50","surplus_money":"80","money":"100"}]
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

        public List<ListBean> list;

        public static class PageBean {
            public String page;
            public String page_count;
            public String record_count;
        }

        public static class ListBean {
            public String coupon_id;
            public String end_time;
            public String type;
            public String name;
            public String discount;
            public String max_money;
            public String proportion;
            public String surplus_money;
            public String money;
            public String state;
        }
    }
}
