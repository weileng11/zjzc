package cn.bs.zjzc.model.response;

import java.util.List;

/**
 * Created by Ming on 2016/7/5.
 */
public class EvaluationListResponse extends BaseResponse {

    /**
     * page : {"page":"1","page_count":"10","record_count":"100"}
     * list : [{"phone":"180********","time":"121234564","level":"4.0","content":"太慢了，但是服务还行"}]
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
         * phone : 180********
         * time : 121234564
         * level : 4.0
         * content : 太慢了，但是服务还行
         */

        public List<ListBean> list;

        public static class PageBean {
            public String page;
            public String page_count;
            public String record_count;
        }

        public static class ListBean {
            public String phone;
            public String time;
            public String level;
            public String content;
        }
    }
}
