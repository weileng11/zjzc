package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/7/7.
 */
public class FAQListResponse extends BaseResponse {

    /**
     * page : {"page":"1","page_count":"10","record_count":"100"}
     * list : [{"question":"问题","answer":"答案"},{"question":"问题","answer":"答案"}]
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
         * question : 问题
         * answer : 答案
         */

        public List<ListBean> list;

        public static class PageBean {
            public String page;
            public String page_count;
            public String record_count;
        }

        public static class ListBean {
            public String question;
            public String answer;
        }
    }
}
