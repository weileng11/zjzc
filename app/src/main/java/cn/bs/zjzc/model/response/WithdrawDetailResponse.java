package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/6/16.
 */
public class WithdrawDetailResponse extends BaseResponse {

    /**
     * page : {"now_page":"1","page_num":"10","total":"100"}
     * list : [{"id":"1","money":"100","apply_time":"2016年06月16日 10:06:52","state":"2"},{"id":"2","money":"100","apply_time":"2016年06月16日 10:06:52","state":"1"}]
     */

    public DataBean data;

    public static class DataBean {
        /**
         * now_page : 1
         * page_num : 10
         * total : 100
         */

        public PageBean page;
        /**
         * id : 1
         * money : 100
         * apply_time : 2016年06月16日 10:06:52
         * state : 2
         */

        public List<ListBean> list;

        public static class PageBean {
            public String now_page;
            public String page_num;
            public String total;
        }

        public static class ListBean {
            public String id;
            public String money;
            public String apply_time;
            public String state;
        }
    }
}
