package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/7/6.
 */
public class FundDetailResponse extends BaseResponse {

    /**
     * page : {"page":"1","page_count":"10","record_count":"100"}
     * list : [{"id":"1","order_num":"WM12016070210261132456","type":"2","money":"10.00","time":"14314510231"}]
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
         * order_num : WM12016070210261132456
         * type : 2
         * money : 10.00
         * time : 14314510231
         */

        public List<ListBean> list;

        public static class PageBean {
            public String page;
            public String page_count;
            public String record_count;
        }

        public static class ListBean {
            public String id;
            public String order_num;
            public String type;
            public String money;
            public String time;
        }
    }
}
