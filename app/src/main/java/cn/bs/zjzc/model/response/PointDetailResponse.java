package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/7/11.
 */
public class PointDetailResponse extends BaseResponse {

    /**
     * page : {"page":"1","page_count":"10","record_count":"100"}
     * list : [{"id":"1","remark":"订单完成","change_points":"10","change_time":"1431245212"},{"id":"2","remark":"取消订单","change_points":"-10","change_time":"1431245212"}]
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
         * id : 1
         * remark : 订单完成
         * change_points : 10
         * change_time : 1431245212
         */

        public List<ListBean> list;

        public static class PageBean {
            public String page;
            public String page_count;
            public String record_count;
        }

        public static class ListBean {
            public String id;
            public String remark;
            public String change_points;
            public String change_time;
        }
    }
}
