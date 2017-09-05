package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/6/17.
 */
public class NewsListResponse extends BaseResponse {


    /**
     * page : {"now_page":1,"page_num":2,"total":"2"}
     * list : [{"id":"157","state":"1","content":"全部普通用户","push_time":"1466167449"},{"id":"102","state":"1","content":"指定广州普通用户23","push_time":"1466150843"}]
     */

    public DataBean data;

    public static class DataBean {
        /**
         * now_page : 1
         * page_num : 2
         * total : 2
         */

        public PageBean page;
        /**
         * id : 157
         * state : 1
         * content : 全部普通用户
         * push_time : 1466167449
         */

        public List<ListBean> list;

        public static class PageBean {
            public int now_page;
            public int page_num;
            public String total;
        }

        public static class ListBean {
            public String id;
            public String state;
            public String content;
            public String push_time;
        }
    }
}
